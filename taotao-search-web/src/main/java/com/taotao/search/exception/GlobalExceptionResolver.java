package com.taotao.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class GlobalExceptionResolver implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, 
			HttpServletResponse response, Object handler,
			Exception e) {
		logger.info("into golbalExceptionResolver");
		logger.debug("testing handler type: ", handler.getClass());
		//print exception on console
		e.printStackTrace();
		//write exception in log file
		logger.error("system error", e);
		//sms third-party
		//mail jmail
		//show error page
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "something wrong");
		modelAndView.setViewName("error/exception");
		return modelAndView;
	}

}
