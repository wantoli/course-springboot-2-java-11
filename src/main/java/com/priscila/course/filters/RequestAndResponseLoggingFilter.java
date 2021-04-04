package com.priscila.course.filters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.priscila.course.beans.Header;
import com.priscila.course.beans.RequestLogging;
import com.priscila.course.beans.RequestResponseLogging;
import com.priscila.course.beans.ResponseLogging;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.MDC;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Component
public class RequestAndResponseLoggingFilter extends OncePerRequestFilter {

	private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(MediaType.valueOf("text/*"),
					MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON,
					MediaType.APPLICATION_ATOM_XML,
					MediaType.valueOf("application/*+json"),
					MediaType.valueOf("application/*+xml"),
					MediaType.MULTIPART_FORM_DATA);
	
	private static final String CORRELATION_ID_HEADER_NAME = "correlationID";
	private static final String FLOWID_HEADER_NAME = "flowId";
	
	@Value("${info.app.name}")
	private String appName;
	
	private final Logger log = LoggerFactory.getLogger(RequestAndResponseLoggingFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
			throws ServletException, IOException{
		
		if (isAsyncDispatch(request) || request.getServletPath().startsWith("/actuator")) {
			filterChain.doFilter(request, response);
		} else {
			doFilterWrapped(wrapRequest(request), wrapResponse(response), filterChain);
		}
	}
	
	protected void doFilterWrapped(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response,
			FilterChain filterChain) throws ServletException, IOException {
		
		Date requestTime = new Date();
		
		try {
			beforeRequest(request, response);
			filterChain.doFilter(request, response);
		} finally {
			afterRequest (request, response, requestTime);
			response.copyBodyToResponse();
			response.addHeader("time-elapsed", String.valueOf(requestTime.getTime() - new Date().getTime()));
		}
	}
	
	protected void beforeRequest(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
		MDC.put(CORRELATION_ID_HEADER_NAME, this.getCorrelationIdFromHeader(request));
		MDC.put(FLOWID_HEADER_NAME, this.getFlowIdFromHeader(request) );
		MDC.put("applicationName", this.appName);
		MDC.put("requestURI", request.getRequestURI());
		MDC.put("httpMethod", request.getMethod());
		MDC.put("requestId", this.generateUniqueId());
	}
	
	
	protected void afterRequest(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, Date requestTime) {
	
		if (!Objects.isNull(MDC.get(CORRELATION_ID_HEADER_NAME)))
			response.addHeader(CORRELATION_ID_HEADER_NAME, MDC.get(CORRELATION_ID_HEADER_NAME));
		if (!Objects.isNull(MDC.get(FLOWID_HEADER_NAME)))
			response.addHeader(FLOWID_HEADER_NAME, FLOWID_HEADER_NAME);
		
		if (log.isTraceEnabled() || log.isInfoEnabled()) {
			try {
				logRequestResponse(request, response, requestTime);
			}catch (Exception e){
				log.warn("Falha ao processar logger Request/Response", e);
			}
		}
		
		MDC.clear();
	}
	
	private void logRequestResponse(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response,
					Date requestTime) {
		
		RequestLogging requestLogBean = new RequestLogging();
		ResponseLogging responseLogBean = new ResponseLogging ();
		String queryString = request.getQueryString();
		
	
		responseLogBean.setDuration(requestTime.getTime() - new Date().getTime());
		
		requestLogBean.setHeaders(new ArrayList<>());
		responseLogBean.setHeaders(new ArrayList<>());

		if (queryString == null) {
			requestLogBean.setUri(request.getRequestURI());
		} else {
			requestLogBean.setUri(request.getRequestURI() + "?" + queryString);
		}
		
		Collections.list(request.getHeaderNames())
						.forEach(headerName -> Collections.list(request.getHeaders(headerName))
									.forEach(headerValue -> requestLogBean.getHeaders().add(new Header(headerName, headerValue))));
		
		requestLogBean.setMethod(request.getMethod());
		requestLogBean.setBody(this.getRequestBody(request)); 
				
		response.getHeaderNames().forEach(headerName -> response.getHeaders(headerName)
						.forEach(headerValue -> responseLogBean.getHeaders().add(new Header(headerName, headerValue))));
		
		responseLogBean.setStatusCode(String.valueOf(response.getStatusCode()));
		responseLogBean.setStatusText(HttpStatus.valueOf(response.getStatus()).getReasonPhrase());
		responseLogBean.setBody(this.getResponseBody(response));
		
		if (response.getStatus() >= 500) {
			log.error(HttpStatus.valueOf(response.getStatus()).getReasonPhrase(), 
					kv("requestResponse", new RequestResponseLogging(requestLogBean, responseLogBean)));
		} else if (response.getStatus() >= 400 && response.getStatus() < 500) {
			log.info(HttpStatus.valueOf(response.getStatus()).getReasonPhrase(), 
					kv("requestResponse", new RequestResponseLogging(requestLogBean, responseLogBean)));
		} else if (response.getStatus() >= 200 && response.getStatus() < 300){
			log.info(HttpStatus.valueOf(response.getStatus()).getReasonPhrase(), 
					kv("requestResponse", new RequestResponseLogging(requestLogBean, responseLogBean)));
		} else {
			log.trace(HttpStatus.valueOf(response.getStatus()).getReasonPhrase(), 
					kv("requestResponse", new RequestResponseLogging(requestLogBean, responseLogBean)));
		}
		
	}
	
	
	private String getRequestBody(ContentCachingRequestWrapper request) {
		byte[] content = request.getContentAsByteArray();
		
		if (content.length > 0) {
			return getContent(content, request.getContentType(), request.getCharacterEncoding());
		}
		
		return null;
	}
	
	private String getResponseBody(ContentCachingResponseWrapper response) {
		byte[] content = response.getContentAsByteArray();
		
		if (content.length > 0) {
			return getContent(content, response.getContentType(), response.getCharacterEncoding());
		}
		
		return null;
	}	
	
	private String getContent(byte[] content, String contentType, String contentEncondig) {
		MediaType mediaType = MediaType.valueOf(contentType);
		Boolean visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
		String contentString = "";
		
		if  (visible) {
			try {
				contentString = new String(content, contentEncondig);
			}catch (UnsupportedEncodingException e) {
				contentString = "[" + content.length + " bytes content]";
			}
		}else {
			contentString = "[" + content.length + " bytes content]";
		}
				
		return contentString;
	}
	
	private ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
		if (request instanceof ContentCachingRequestWrapper) {
			return (ContentCachingRequestWrapper) request;
		} else {
			return new ContentCachingRequestWrapper(request);
		}
	}

	private ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
		if (response instanceof ContentCachingResponseWrapper) {
			return (ContentCachingResponseWrapper) response;
		} else {
			return new ContentCachingResponseWrapper(response);
		}
	}
	
	private String getCorrelationIdFromHeader(final HttpServletRequest request) {
		String correlationId = request.getHeader(CORRELATION_ID_HEADER_NAME);
		if (StringUtils.isEmpty(correlationId)) {
			correlationId = generateUniqueId();
		}
		return correlationId;
	}
	
	private String generateUniqueId() {
		return UUID.randomUUID().toString();
	}
	
	private String getFlowIdFromHeader(final HttpServletRequest request) {
		return request.getHeader(FLOWID_HEADER_NAME);
	}
}

	