package com.isoftstone.agiledev.web.springmvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Conventions;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import com.isoftstone.agiledev.web.springmvc.easyui.BindResultField;
import com.isoftstone.agiledev.web.springmvc.easyui.EasyUIBindingException;

public class BindExceptionHanderExceptionResolver extends
		AnnotationMethodHandlerAdapter implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		BindException bindException;
		ModelAndView mav = new ModelAndView();
		if (ex instanceof BindException) {
			bindException = (BindException) ex;
			ExtendedModelMap implicitModel = new BindingAwareModelMap();

			if (bindException.getBindingResult().hasErrors()) {
				BindingResult result=bindException.getBindingResult();
				EasyUIBindingException easyUIBindingEx=new EasyUIBindingException();
				BindResultField[] bindResultFields=new BindResultField[result.getErrorCount()];
				easyUIBindingEx.setValid(false);
			
				for (int i = 0; i < result.getErrorCount(); i++) {
					FieldError fieldError=result.getFieldErrors().get(i);
					BindResultField bindResultField=new BindResultField();
					bindResultField.setField(fieldError.getField());
					bindResultField.setRejectedValue(fieldError.getRejectedValue());
					bindResultField.setDefMessage(fieldError.getDefaultMessage());
					bindResultFields[i]=bindResultField;
				}
				easyUIBindingEx.setFields(bindResultFields);
				implicitModel.addAttribute("bindException", easyUIBindingEx);
				}
			return mav.addAllObjects(implicitModel);
		}
		return mav;
	}

}
