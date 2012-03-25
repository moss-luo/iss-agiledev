package org.sinner.study.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
/**
 * 在controller方法返回datagridjson时,给ModelMap注入当前action，用于在DatagridView中获取总记录数
 * @author sinner
 *
 */
@Service
@Aspect
public class InjectControllerAspect{// implements AfterReturningAdvice {

	@Pointcut(value = "execution(* org.sinner.study.controller..*.*(..))")
	public void point(){
	}
	 @Around("point()")
	 public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		  Object controller = pjp.getTarget();
		  Object result = pjp.proceed();
		  if(result==null || !"datagridjson".equals(result.toString()))return result;
		  System.out.println("注入controller:"+controller);
		  Object[] args = pjp.getArgs();
		  for (Object object : args) {
			 if(object instanceof ModelMap){
				 ModelMap mm = (ModelMap) object;
				 mm.addAttribute("controller", controller);
			 }
		  }
		  System.out.println("退出");
		  return result;
	 }

}
