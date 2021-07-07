package com.fajar.employeedata.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/web")
@Slf4j
public class UtilitiesController  {  
	@Autowired
	private ObjectMapper objectMapper;
	
	@RequestMapping(value = "/app-error", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.POST, RequestMethod.GET}) 
	public void errorNotFound(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
		 
		int httpErrorCode = getErrorCode(httpRequest);

		if (200 == httpErrorCode) {
			httpResponse.sendRedirect(httpRequest.getContextPath()+"/index");
			return;
		}
		
		
		Map<String, Object> payload = new LinkedHashMap<String, Object>();
		payload.put("code", httpErrorCode);
		payload.put("message", getErrorMessage(httpRequest));
		httpResponse.setStatus(httpErrorCode);

		String jsonString = objectMapper.writeValueAsString(payload); 
		
		httpResponse.getWriter().write(jsonString);
		
		
	}
	private String getErrorMessage(HttpServletRequest httpServletRequest) {
		try {
			Object exception = httpServletRequest.getAttribute("javax.servlet.error.exception");
			log.error("======= !! HANDLING exception: {}", exception);
			if (exception != null && exception instanceof NestedServletException) {
				NestedServletException nestedServletException = (NestedServletException) exception;
				return nestedServletException.getRootCause().getMessage();
			}
			
			return String.valueOf(exception);
		} catch (Exception e) {
			return "Error occured";
		}
	}
	private int getErrorCode(HttpServletRequest httpRequest) {
		if (null == httpRequest.getAttribute("javax.servlet.error.status_code")) {
			return 200;
		}
		try {
			Integer status_code = (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
			log.debug("status_code:{}", status_code);
			return status_code;
		} catch (Exception e) {

			return 500;
		}
	}
}
