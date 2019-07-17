package com.mybatis.aop;

import com.mybatis.util.TracerIdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MQTracerLogAop {

    @Pointcut("execution(* com.mybatis.rabbitmq.RabbitMessageListener.message(..))")
    public void traceMQLog() {

    }


    /**
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("traceMQLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
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
}
