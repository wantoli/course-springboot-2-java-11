package com.priscila.course.beans;

import java.util.List;

public class ResponseLogging {

	private String statusCode;
	private String statusText;
	private Long duration;
	private List<Header> headers;
	private String body;
	
	public ResponseLogging() {
		super();
	}
	
	public ResponseLogging(String statusCode, String statusText, Long duration, 
			List<Header> headers, String body) {
		super();
		this.statusCode = statusCode;
		this.statusText = statusText;
		this.duration = duration;
		this.headers = headers;
		this.body = body;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
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
		return "ResponseLogging [statusCode=" + statusCode + ", statusText=" + statusText + ", duration=" + duration
				+ ", headers=" + headers + ", body=" + body + "]";
	}
	
	
}
