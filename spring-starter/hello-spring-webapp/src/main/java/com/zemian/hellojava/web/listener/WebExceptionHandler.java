package com.zemian.hellojava.web.listener;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class WebExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(Exception ex) {
        ModelAndView ret = new ModelAndView("/error");
        ret.addObject("exceptionStacktrace", ExceptionUtils.getStackTrace(ex));
        return ret;
    }
}
