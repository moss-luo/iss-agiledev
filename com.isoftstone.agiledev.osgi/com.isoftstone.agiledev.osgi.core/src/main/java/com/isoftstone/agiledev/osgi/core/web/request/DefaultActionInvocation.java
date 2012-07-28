package com.isoftstone.agiledev.osgi.core.web.request;

import java.lang.reflect.Method;

import com.isoftstone.agiledev.osgi.core.web.Action;
import com.isoftstone.agiledev.osgi.core.web.ActionInvocation;
import com.isoftstone.agiledev.osgi.core.web.ValueStack;

public class DefaultActionInvocation implements ActionInvocation {

	private Action action;
	private ValueStack stack;
	private Method method;
	private Object[] methodParameters=null;
	@Override
	public Action getAction() {
		return action;
	}

	@Override
	public ValueStack getStack() {
		return stack;
	}

	@Override
	public Method getMethod() {
		return method;
	}
	
	public String invock(){
		try {
				
			if(this.method.getReturnType()!=null){
				return (String) this.method.invoke(this.action, this.methodParameters);
			}else{
				this.method.invoke(this.action, this.methodParameters);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public void setStack(ValueStack stack) {
		this.stack = stack;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	@Override
	public void setMethodParameter(Object[] values) {
		this.methodParameters = values;
	}

}
