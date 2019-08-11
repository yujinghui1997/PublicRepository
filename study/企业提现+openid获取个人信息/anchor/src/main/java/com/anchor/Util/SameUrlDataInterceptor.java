package com.anchor.Util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SameUrlDataInterceptor extends HandlerInterceptorAdapter {

	 /**
     * 覆盖父类的preHandle方法
     * 预处理回调方法，实现处理器的预处理,验证是否为重复提交，第三个参数为响应的处理器，自定义Controller
     * 返回值：true表示继续流程（如调用下一个拦截器或处理器）；false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，此时我们需要通过response来产生响应；
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 判断handler参数是否为HandlerMethod类的实例
        if (handler instanceof HandlerMethod) {
            // 2. 获取方法注解查看方式是否有PreventRepeat注解
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            PreventRepeat annotation = method.getAnnotation(PreventRepeat.class);
            if (annotation != null) {
                // 3. 调用重复数据验证方法
                boolean result = repeatDataValidator(request);
                if(result){
                    return false;
                }
                else{
                    return true;
                }
            }else{
                return true;
            }
        } else {
            // 4. 如果参数不是HandlerMethod类的实例则调用父类的preHandle方法
            return super.preHandle(request, response, handler);
        }
    }

    /**
     * 验证同一个url数据是否相同提交,相同返回true
     * @param httpServletRequest
     * @return
     */
    public boolean repeatDataValidator(HttpServletRequest httpServletRequest) throws Exception{
        try {
            // 1. 将请求参数转换为json字符串 需要在pom内引用jackson-databind
            ObjectMapper objectMapper = new ObjectMapper();
            String params = objectMapper.writeValueAsString(httpServletRequest.getParameterMap());
            // 2. 获取当前请求的url地址 并以url为key 参数为值存在map内
            String url=httpServletRequest.getRequestURI();
            Map<String,String> map=new HashMap(4);
            map.put(url, params);
            String nowUrlParams=map.toString();
            // 3. 获取session中上一次请求存储的url和参数字符串
            Object preUrlParams=httpServletRequest.getSession().getAttribute("oldUrlParams");
            // 4. 如果上一个数据为null,表示还没有访问页面 将当前方位的url和请求参数存储到session中
            if(preUrlParams == null) {
                httpServletRequest.getSession().setAttribute("oldUrlParams", nowUrlParams);
                return false;
            } else {
                // 5. 判断上一次访问的url和参数与本次是否相同 如相同则表示重复数据
                if(preUrlParams.toString().equals(nowUrlParams)){
                	System.err.println("旧URL"+preUrlParams.toString());
                	System.err.println("新URL"+nowUrlParams);
                    return true;
                }else{
                    httpServletRequest.getSession().setAttribute("oldUrlParams", nowUrlParams);
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 此处是我自定义异常
            throw new Exception();
        }
	
    }
	
	
}
