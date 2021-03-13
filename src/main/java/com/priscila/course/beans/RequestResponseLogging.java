package com.priscila.course.beans;

import org.slf4j.MDC;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestResponseLogging {

	@JsonProperty("request")
	private RequestLogging request;
	
	@JsonProperty("response")
	private ResponseLogging response;
	
	@JsonProperty("correlationId")
	private String correlationId;

	public RequestResponseLogging(RequestLogging request, ResponseLogging response) {
		super();
		this.request = request;
		this.response = response;
		this.correlationId = MDC.get("correlationId");
	}
	
	public RequestResponseLogging() {
		super();

		this.correlationId = MDC.get("correlationId");
	}

	public RequestLogging getRequest() {
		return request;
	}

	public void setRequest(RequestLogging request) {
		this.request = request;
	}

	public ResponseLogging getResponse() {
		return response;
	}

	public void setResponse(ResponseLogging response) {
		this.response = response;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	@Override
	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (Exception e) {
			return "RequestResponseLogging [request=" + request + ", response=" + response + ", correlationId="
				+ correlationId + "]";
		}
	}
	
	
}
