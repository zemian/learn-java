package com.zemian.hellojava.web.listener;

import com.zemian.hellojava.web.controller.api.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackages = "com.zemian.hellojava.web.controller.api")
public class ApiExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public @ResponseBody
    ApiResult<Object> defaultErrorHandler(HttpServletRequest req, Exception ex) {
        String uri = req.getRequestURI();
        LOG.info("Error occured for uri={}", uri, ex);
        return ApiResult.error(ex.getMessage());
    }
}
