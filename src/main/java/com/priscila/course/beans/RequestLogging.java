package com.priscila.course.beans;

import java.util.List;

public class RequestLogging {

	private String uri;
	private String method;
	private List<Header> headers;
	private String body;
	
	public RequestLogging() {
		super();
	}
	
	public RequestLogging(String uri, String method, List<Header> headers, String body) {
		super();
		this.uri = uri;
		this.method = method;
		this.headers = headers;
		this.body = body;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<Header> getHeaders() {
		return headers;
	}

	public void setHeaders(List<Header> headers) {
		this.headers = headers;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "RequestLogging [uri=" + uri + ", method=" + method + ", headers=" + headers + ", body=" + body + "]";
	}

	
}
