package com.isoftstone.agiledev.validater;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONUtil;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.opensymphony.xwork2.util.AnnotationUtils;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.validator.FieldValidator;
import com.opensymphony.xwork2.validator.Validator;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import com.opensymphony.xwork2.validator.validators.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.validators.RequiredStringValidator;
import com.opensymphony.xwork2.validator.validators.StringLengthFieldValidator;

/**
 * 对提交过来的bean进行服务器端校验
 * @author xwpu
 *
 */
@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class ValidateInterceptor extends MethodFilterInterceptor {
	private Map<String,List<String>> errors = new Hashtable<String,List<String>>();
	private ValueStack stack = null;
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		stack = invocation.getStack();
		
		Object action = invocation.getAction();
//		String requestType = request.getHeader("agiledev-ajax-request-type");
//		if(requestType==null || requestType.equalsIgnoreCase("init-form"))
//			return invocation.invoke();
		String requestType = request.getParameter("agiledev-ajax-request-type");
		if(requestType==null || !"validate".equals(requestType))return invocation.invoke();
		
		
		
		Class<?> clazz = action.getClass();
		while (clazz != Object.class) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				//判断action中的哪个属性是pojo,是需要校验的对象
				boolean oldAccessible = field.isAccessible();
				field.setAccessible(true);
				try {
				
					//校验action字段
					for (Annotation annotation : field.getAnnotations()) {
						if(isRegisterAnnotation(annotation)){
							Map<String,List<String>> temp = validate(action,annotation,field.getName());
							errors.putAll(temp);
						}
					}

//					//struts校验
//					Method[] methods = clazz.getDeclaredMethods();
//					for (Method method : methods) {
//						if(method.isAnnotationPresent(VisitorFieldValidator.class)){
//							Object bean = method.invoke(action,new Object[]{});
//							this.doValidate(action,bean);
//						}
//					}
					
					//校验页面提交过来的bean
					if(field.isAnnotationPresent(Validation.class)){
						doValidate(action, field.get(action));
					}else if(field.isAnnotationPresent(Validations.class)){
						Validations vs = field.getAnnotation(Validations.class);
						for(Validation v : vs.value()){
							String fieldName = v.fieldName();
							String[] params = v.params();
							Map<String,String> annotationParameter = new HashMap<String,String>();
							for(int i=0;i<params.length;i++){
								annotationParameter.put(params[i], params[i+1]);
								++i;
							}
							Class<? extends Annotation> annotationClazz = v.validType();
							FieldValidatorSupport validator = (FieldValidatorSupport) VALIDATOR.get(annotationClazz);
							validator.setFieldName(fieldName);
							for (String s : annotationParameter.keySet()) {
								BeanUtils.setProperty(validator, s, annotationParameter.get(s));
							}
							validator.setValueStack(stack);
							validator.validate(field.get(action));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					field.setAccessible(oldAccessible);
				}
			}
			clazz = clazz.getSuperclass();
		}
		if(errors.size()>0){
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/json;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
//			JSONArray json = JSONArray.fromObject(errors);
			String json = JSONUtil.serialize(errors);
			out.print(json.toString());
			out.flush();
			out.close();
			errors.clear();
			return null;
		}
		//如果校验通过,则执行具体的action业务
		return invocation.invoke();
	}

	private void doValidate( Object action,Object targetValidationBean) throws IllegalAccessException {
		Class<?> targetValidateObjectClazz = targetValidationBean.getClass();
		Field[] targetValidateObjectFields = targetValidateObjectClazz.getDeclaredFields();
		for (Field validateField: targetValidateObjectFields) {
			Annotation[] annotations = validateField.getAnnotations();
			for (Annotation annotation : annotations) {
				Map<String,List<String>> temp = validate(targetValidationBean,annotation,validateField.getName());
				if(temp!=null)
					errors.putAll(temp);
			}
		}
		Method[] targetValidateObjectMethods = targetValidateObjectClazz.getDeclaredMethods();
		for (Method validateMethod : targetValidateObjectMethods) {
			//获取setter或者getter对应的字段名称
			String fieldName = AnnotationUtils.resolvePropertyName(validateMethod);
			Annotation[] annotations = validateMethod.getAnnotations();
			for (Annotation annotation : annotations) {
				Map<String,List<String>> temp = validate(targetValidationBean,annotation,fieldName);
				if(temp!=null)
					errors.putAll(temp);
			}
		}
	}
	
	/**
	 * 利用struts2自带的验证框架校验提交的数据。
	 * @param bean
	 * @param annotation
	 * @param fieldName
	 * @return
	 */
	private Map<String,List<String>> validate(Object bean,Annotation annotation,String fieldName){
		if(VALIDATOR.containsKey(annotation.getClass())){
			try {
				FieldValidator validator = (FieldValidator) VALIDATOR.get(annotation.getClass());
				validator.setFieldName(fieldName);
				validator.validate(bean);
				return validator.getValidatorContext().getFieldErrors();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private boolean isRegisterAnnotation(Annotation annotation){
		return VALIDATOR.containsKey(annotation.getClass());
	}

	private static String validators;
	private static Map<Class<? extends Annotation>,Validator> VALIDATOR = new HashMap<Class<? extends Annotation>,Validator>();
	public static Validator getValidator(Class AnnotationClazz){
		for (Class c : VALIDATOR.keySet()) {
			if(c.getName().equals(AnnotationClazz.getName())){
				return VALIDATOR.get(AnnotationClazz);
			}
		}
		return null;
	}
	
	/**
	 * 初始化agiledev-validator组件自带的及struts2提供的部分校验器，并且支持struts.xml文件中扩展其他校验器
	 */
	static{
		com.opensymphony.xwork2.validator.validators.EmailValidator emailValidator = new com.opensymphony.xwork2.validator.validators.EmailValidator();
		emailValidator.setValidatorType("email");
		VALIDATOR.put(EmailValidator.class, emailValidator);
		
		RequiredStringValidator requiredStringValidator = new RequiredStringValidator();
		requiredStringValidator.setValidatorType("required");
		VALIDATOR.put(com.opensymphony.xwork2.validator.annotations.RequiredStringValidator.class, requiredStringValidator);
		              
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		requiredFieldValidator.setValidatorType("required");
		VALIDATOR.put(com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator.class, requiredFieldValidator);
		
		StringLengthFieldValidator stringLengthFieldValidator = new StringLengthFieldValidator();
		stringLengthFieldValidator.setValidatorType("length");
		VALIDATOR.put(com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator.class, stringLengthFieldValidator);
		
		VALIDATOR.put(Mobile.class, new MobileValidator());
		VALIDATOR.put(IDCard.class, new IDCardValidator());
		VALIDATOR.put(IPAddress.class, new IPAddrValidator());
		VALIDATOR.put(NickName.class, new NickNameValidator());
		VALIDATOR.put(ErrorChar.class, new ErrorCharValidator());
		//扩展配置文件配置的其他校验器
		if(validators!=null && validators.contains(":")){
			for (String s : validators.split(";")) {
				try {
					FieldValidator v = (FieldValidator) Class.forName(s.split(":")[1]).newInstance();
					if(s.split(":").length >= 3){
						v.setValidatorType(s.split(":")[2]);
					}
					VALIDATOR.put( (Class<? extends Annotation>) Class.forName(s.split(":")[0]),v);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 通过struts配置文件配置自定义校验器，配置方式如下
	 * 注解:校验器[:validatorType]
	 * 如果是自定义校验器，必须扩展自FieldValidator，并且在构造方法中或者覆盖setValidatorType方法来表明该校验器的校验类型。
	 * 
	 * 实例：org.sinner.annotation.Email:org.sinner.validator.EmailValidator:email
	 * @param s
	 */
	public void setValidators(String s){
		validators = s;
	}
}
