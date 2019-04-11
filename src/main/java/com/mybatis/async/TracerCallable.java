package com.mybatis.async;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

public abstract class TracerCallable<V> implements Callable {
    private Map<String, String> context;

    public TracerCallable() {
        this.context = MDC.getCopyOfContextMap();
    }
    @Override
    public V call() throws Exception {
        Map<String, String> previous = MDC.getCopyOfContextMap();
        if (context == null) {
            MDC.clear();
        } else {
            MDC.setContextMap(context);
        }
        try {
            return callWithMdc();
        } finally {
            if (previous == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(previous);
            }
        }
    }


    public abstract V callWithMdc();
}
