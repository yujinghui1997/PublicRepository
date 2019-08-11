package com.store.Util;

import com.store.Util.LocalLock;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
/**
 * 本章先基于 本地缓存来做，后续讲解 redis 方案
 *
 * @author Levin
 * @since 2018/6/12 0012
 */
@Aspect
@Configuration
public class LockMethodInterceptor {

	private static final String guizhe="execution(public * *(..)) && @annotation(com.store.Util.LocalLock)";
    private static final Cache CACHES = CacheBuilder.newBuilder()
            .maximumSize(1000) // 最大缓存 100 个
            .expireAfterWrite(5, TimeUnit.SECONDS)  // 设置写缓存后 5 秒钟过期
            .build();
   
    /**
     * 环绕通知 带有 public标记的并且 带有 @localLock 注解的方法 为切入点
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around(guizhe)
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
    	System.out.println(pjp);
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        LocalLock localLock = method.getAnnotation(LocalLock.class);
        String key = getKey(localLock.key(), pjp.getArgs());
        System.out.println(key);
        System.out.println(CACHES.getIfPresent(key) != null);
        if (!StringUtils.isEmpty(key)) {
            if (CACHES.getIfPresent(key) != null) {
                throw new RuntimeException("请勿重复请求");
            }
            // 如果是第一次请求,就将 key 当前对象压入缓存中
            CACHES.put(key, key);
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException();
        } finally {
            // TODO 为了演示效果,这里就不调用 CACHES.invalidate(key); 代码了
        	
        }
    }

    /**
     * key 的生成策略,如果想灵活可以写成接口与实现类的方式（TODO 后续讲解）
     *
     * @param keyExpress 表达式
     * @param args       参数
     * @return 生成的key
     */
    private String getKey(String keyExpress, Object[] args) {
        for (int i = 0; i < args.length; i++) {
            keyExpress = keyExpress.replace("arg[" + i + "]", args[i].toString());
        }
        return keyExpress;
    }
}
