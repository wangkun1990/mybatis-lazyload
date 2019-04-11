package com.mybatis.aop;

import com.mybatis.util.TraceIdGenerator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

@Aspect
public class Slf4jTracerAop {

    private static final String TRACE_ID = "traceId";

    private ThreadLocal<ProceedingJoinPoint> beforeThreadLocal = new ThreadLocal<>();

    @Pointcut("execution(* com.mybatis..*(..))")
    public void traceLog() {

    }

    @Around("traceLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        if (beforeThreadLocal.get() == null) {
            beforeThreadLocal.set(pjp);
        }
        try {
            String traceId = MDC.get(TRACE_ID);
            if (StringUtils.hasText(traceId)) {
                MDC.put(TRACE_ID, traceId);
            } else {
                MDC.put(TRACE_ID, TraceIdGenerator.generate());
            }
            return pjp.proceed();
        } finally {
            if (pjp == beforeThreadLocal.get()) {
                MDC.remove(TRACE_ID);
                beforeThreadLocal.remove();
            }
        }
    }
}
