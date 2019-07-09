package com.mybatis.aop;

import com.mybatis.util.TracerIdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ScheduledTracerLogAop {

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void traceLog() {

    }

    /**
     * 不要在其他地方调用带有org.springframework.scheduling.annotation.Scheduled 注解的方法
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("traceLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Scheduled scheduled = methodSignature.getMethod().getAnnotation(Scheduled.class);
        if (scheduled != null) {
            String traceId = MDC.get(TracerIdGenerator.TRACE_ID);
            if (StringUtils.isBlank(traceId)) {
                MDC.put(TracerIdGenerator.TRACE_ID, TracerIdGenerator.generate());
            }
            try {
                return pjp.proceed();
            } finally {
                MDC.remove(TracerIdGenerator.TRACE_ID);
            }
        }
        return pjp.proceed();
    }
}
