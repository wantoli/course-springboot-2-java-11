package com.priscila.course.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.priscila.course.beans.Header;
import com.priscila.course.beans.RequestLogging;
import com.priscila.course.beans.RequestResponseLogging;
import com.priscila.course.beans.ResponseLogging;

import static net.logstash.logback.argument.StructuredArguments.kv;

public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor{
	
	private final Logger log = LoggerFactory.getLogger(RestTemplateLoggingInterceptor.class);
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
		throws IOException {
		
		return executeRequest(request, body, execution);
	}
	
	private ClientHttpResponse executeRequest(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
		throws IOException{
		
		Date start = new Date();
		
		try {
			ClientHttpResponse response = execution.execute(request, body);
			
			this.appendLog(start, body, request, response);
			return response;
			
		} catch (Exception e) {
			this.appendLog(start, body, request, e);
			throw e;
		}
	}
	
	private void appendLog(Date start, byte[] body, HttpRequest request, ClientHttpResponse response)
			throws IOException 	{
		
		if (log.isTraceEnabled() || response.getStatusCode().isError()) {
			
			RequestResponseLogging logging = new RequestResponseLogging();
			StringBuilder sb = new StringBuilder();
			
			logging.setRequest(new RequestLogging(request.getURI().toString(), request.getMethod().toString(),
					new ArrayList<>(), new String(body, "UTF-8")));
			
			logging.setResponse(new ResponseLogging(request.getURI().toString(), response.getStatusText(),
					new Date().getTime() - start.getTime(), new ArrayList<>(), null));
			
			request.getHeaders().forEach((key, value) -> {
				value.forEach(v -> logging.getRequest().getHeaders().add(new Header(key, v)));
			});
			
			response.getHeaders().forEach((key, value) -> {
				value.forEach(v -> logging.getResponse().getHeaders().add(new Header(key, v)));
			});
			
			try (BufferedReader bufferReader = new BufferedReader(
					new InputStreamReader(response.getBody(), "UTF-8"))) {
				
				String line = bufferReader.readLine();
				while(line != null) {
					sb.append(line);
					line = bufferReader.readLine();
				}
				
			}
			
			logging.getResponse().setBody(sb.toString());
			
			if (response.getStatusCode().isError()) {
				log.error(response.getStatusText(), kv("requestResponse", logging));
			} else {
				log.trace(response.getStatusText(), kv("requestResponse", logging));
			}
		}
	}
	
	private void appendLog(Date start, byte[] body, HttpRequest request, Exception e) throws IOException {
		
		RequestResponseLogging logging = new RequestResponseLogging();
		
		logging.setRequest(new RequestLogging(request.getURI().toString(), request.getMethod().toString(),
				new ArrayList<>(), new String(body, "UTF-8")));
		logging.setResponse(new ResponseLogging("0", e.getMessage(),
				new Date().getTime()-start.getTime(), null, e.toString()));		
	
		request.getHeaders().forEach((key, value) -> {
			value.forEach(v -> logging.getRequest().getHeaders().add(new Header(key, v)));
			
		});
		
		log.error(e.getMessage(), kv("requestResponse", logging));
	}
	
}
