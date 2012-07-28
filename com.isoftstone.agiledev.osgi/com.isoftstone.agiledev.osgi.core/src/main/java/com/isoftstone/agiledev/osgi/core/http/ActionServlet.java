package com.isoftstone.agiledev.osgi.core.http;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isoftstone.agiledev.osgi.commons.util.AppUtils;
import com.isoftstone.agiledev.osgi.core.web.Action;
import com.isoftstone.agiledev.osgi.core.web.ActionContext;
import com.isoftstone.agiledev.osgi.core.web.annotation.RequestModel;
import com.isoftstone.agiledev.osgi.core.web.annotation.RequestParameter;
import com.isoftstone.agiledev.osgi.core.web.annotation.Results;


@SuppressWarnings("serial")
public class ActionServlet  extends HttpServlet{

	private BundleContext context;
	private Logger logger = LoggerFactory.getLogger(ActionServlet.class);
	private Properties runtime = AppUtils.getRuntime();
	private String contextPath = null;
	
	public ActionServlet(BundleContext context) {
		super();
		this.context = context;
	}
	public ActionServlet() {
		super();
	}

	
	public void init(ServletConfig config) throws ServletException {
		contextPath = runtime.getProperty("webContext");
	}
	public void destroy() {
		super.destroy();
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			this.genericService(req, resp);
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			this.genericService(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 执行服务
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void genericService(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			ActionContext.setBundleContext(this.context);
			ActionContext.setRequest(request);
			ActionContext.setResponse(response);
			
			String requestURI = request.getRequestURI();
			String path = requestURI.substring(requestURI.indexOf(contextPath)+contextPath.length()+"/controller".length()+1);
			
			String[] p = path.split("/");
			path = p[0]+"/"+p[1];
			String method = null;
			if(p.length<3){
				method = "execute";
			}else{
				method = p[2];
			}
			logger.info("request path is:"+path+",action is:"+method);
			
			ServiceReference[] ref = 
					(ServiceReference[]) 
						context.getServiceReferences(Action.class.getName(), "(path="+path+")");
			if(ref!=null){
				if(ref.length>1){
					throw new Exception("more than one action path:"+path+" registed!");
				}
				try {
					Action action = (Action) context.getService(ref[0]);
					//填充action参数 
//					fillActionModel(action,request);
					Method m = null;
					//TODO 需要处理重载	7.24
					//TODO 不需要处理重载，action请求路径是唯一的 7.28
					Method[] ms = action.getClass().getDeclaredMethods();
					for (Method mm : ms) {
						if(mm.getName().equals(method)){
							m = mm;
							break;
						}
					}
					Object[] parameterValues = null;
					if(m!=null){
						if(m.getParameterTypes().length>0){
							parameterValues = this.getActionParameters(action,request,m);
						}
					}else{
						logger.error("action:["+path+"] is not defined!");
						throw new Exception("action:["+path+"] is not defined!");
					}
					
					
					//执行action
					String result = null;
					result = (String) m.invoke(action, parameterValues);
						
					this.dispatchResult(action,result);
					
					context.ungetService(ref[0]);
				} catch (Exception e) {
					logger.error("exception in ActionServlet",e);
				}
			}else{
				throw new Exception("no action registed in path:"+path);
			}

		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	/**
	 * 执行result
	 * @param action
	 * @param result
	 * @param request
	 * @param response
	 */
	private void dispatchResult(Action action,String result){
		try {
				
			//获取result
			Results rs = action.getClass().getAnnotation(Results.class);
			if(rs==null){
				com.isoftstone.agiledev.osgi.core.web.annotation.Action annotation = action.getClass()
									.getAnnotation(com.isoftstone.agiledev.osgi.core.web.annotation.Action.class);
				com.isoftstone.agiledev.osgi.core.web.annotation.Result[] results = annotation.results();
				for (com.isoftstone.agiledev.osgi.core.web.annotation.Result r : results) {
					if(r.name().equals(result)){					
						doResult(r,action,r.type());
					}
				}
			}else{
				for (com.isoftstone.agiledev.osgi.core.web.annotation.Result r : rs.value()) {
					if(r.name().equals(result)){
						doResult(r,action,r.type());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void doResult(com.isoftstone.agiledev.osgi.core.web.annotation.Result r,Action action,String resultType) 
			throws InvalidSyntaxException, Exception,IllegalAccessException, InvocationTargetException,NoSuchMethodException {
		ServiceReference[] resultRef = (ServiceReference[]) context.getServiceReferences(com.isoftstone.agiledev.osgi.core.web.result.Result.class.getName(), "(result="+resultType+")");
		if(resultRef!=null && resultRef.length>0){
			com.isoftstone.agiledev.osgi.core.web.result.Result resultHandle = (com.isoftstone.agiledev.osgi.core.web.result.Result) context.getService(resultRef[0]);
			Map<String, String> params = new HashMap<String, String>();
			
			if(r.params()!=null && r.params().length!=0){
				for(int i=0;i<r.params().length;i++){
					params.put(r.params()[i], r.params()[++i]);
				}
				resultHandle.execute(action,params);
			}
			
			context.ungetService(resultRef[0]);
		}
	}
	
	/**
	 * 填充Action中的参数，model
	 * @param action
	 * @param request
	 * @return
	 */
//	@SuppressWarnings("rawtypes")
//	private Action fillActionModel(Action action,HttpServletRequest request)throws Exception{
//		Map params = request.getParameterMap();
//		//填充model
//		if(action instanceof ModelDriven){
//			ModelDriven modelDriven = (ModelDriven) action;
//			Object model = modelDriven.getModel();
//			for (Field f : model.getClass().getDeclaredFields()) {
//				BeanUtils.setProperty(model, f.getName(), params.get(f.getName()));
//			}
//		}else{//填充参数
//			for (Object o : params.keySet()) {
//				for (Field f: action.getClass().getDeclaredFields()) {
//					if(f.getName().equals(o.toString())){
//						BeanUtils.setProperty(action, f.getName(), params.get(o));
//						break;
//					}
//				}
//			}
//		}
//		return action;
//	}
	
	private Object[] getActionParameters(Action action,HttpServletRequest request,Method method){
		
		Annotation[][] as = method.getParameterAnnotations();
		Class<?>[] parametersType = method.getParameterTypes();
		List<Object> values = new ArrayList<Object>();
		for(int i=0;i<as.length;i++){
			Annotation[] annotation = as[i];
			if(annotation[0].annotationType().getName().equals(RequestParameter.class.getName())){
				String value = this.parameterHandler(request, ((RequestParameter)annotation[0]));
				values.add(value);
			}else if(annotation[0].annotationType().getName().equals(RequestModel.class.getName())){
				Object value = this.modelHandler(request, ((RequestModel)annotation[0]),parametersType[i]);
				values.add(value);
			}
		}
		return values.toArray(new Object[]{});
	}
	/**
	 * 解析requestParameter类型的请求参数
	 * @param request
	 * @param annotation
	 * @return
	 */
	private String parameterHandler(HttpServletRequest request,RequestParameter annotation){
		return request.getParameter(annotation.value());
	}
	
	/**
	 * 解析requestModel类型的请求参数
	 * @param request
	 * @param annotation
	 * @param parameterType
	 * @return
	 */
	private Object modelHandler(HttpServletRequest request,RequestModel annotation,Class<?> parameterType){
		try {
			Map<?,?> params = request.getParameterMap();
			Object o = parameterType.newInstance();
			for (Field f : parameterType.getDeclaredFields()) {
				BeanUtils.setProperty(o, f.getName(), params.get(f.getName()));
			}
			return o;
		} catch (Exception e) {
			logger.error("error in modelHandle", e);
		}
		return null;
	}
}
