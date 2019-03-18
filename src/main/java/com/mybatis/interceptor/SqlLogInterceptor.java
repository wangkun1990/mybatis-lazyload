package com.mybatis.interceptor;

import com.commons.util.ReflectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.OgnlCache;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * 获取sql语句，包含sql参数
 *
 * @author kun.wang
 */
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
public class SqlLogInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlLogInterceptor.class);

    /**
     * 超过多少毫秒算慢sql
     * 单位毫秒,默认2000毫秒
     */
    private Integer slowSqlTime = 2000;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler handler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = handler.getBoundSql();
        final String sql = boundSql.getSql();
        final Object param = boundSql.getParameterObject();
        String sql2 = "";
        if (param == null) {
            sql2 = sql.replaceFirst("\\?", "''");
        } else {
            sql2 = bindCustomObjectParam(boundSql, param);
        }
        sql2 = beautifySql(sql2);
        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        Long costTime = System.currentTimeMillis() - start;
        if (costTime > slowSqlTime) {
            LOGGER.warn("sql with param = {} cost time = {} ms, slowSqlTime = {} ms", new Object[]{sql2, costTime, slowSqlTime});
        } else {
            LOGGER.info("sql with param = {}, cost time = {} ms", new Object[] {sql2, costTime});
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.slowSqlTime = Integer.valueOf(properties.getProperty("slowSqlTime"));
    }

    private String bindCustomObjectParam(BoundSql boundSql, Object param) {
        LOGGER.info("object class = {}", param.getClass());
        String sql = boundSql.getSql();
        try {
            List<String> paramNames = new ArrayList<>();
            List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
            for (ParameterMapping mapping : paramMapping) {
                String propName = mapping.getProperty();
                int index = propName.indexOf(".");
                if (index > 0) {
                    propName = propName.substring(index + 1);
                }
                if (!paramNames.contains(propName)) {
                    paramNames.add(propName);
                }
            }
            if ((param instanceof Number) || (param instanceof Boolean) || (param instanceof Character) || (param instanceof Void)) {
                sql = sql.replaceFirst("\\?", Objects.toString(param));
            } else if (param instanceof String) {
                sql = sql.replaceFirst("\\?", "'" + param + "'");
            } else if (DefaultSqlSession.StrictMap.class == param.getClass()) {
                DefaultSqlSession.StrictMap strictMap = (DefaultSqlSession.StrictMap) param;
                if (strictMap.containsKey("array")) {
                    Object[] array = (Object[]) strictMap.get("array");
                    for (Object object : array) {
                        sql = appendParams(sql, object, paramNames);
                    }
                } else if (strictMap.containsKey("collection")) {
                    Collection<Object> collection = (Collection<Object>) strictMap.get("collection");
                    for (Object object : collection) {
                        sql = appendParams(sql, object, paramNames);
                    }
                }
            } else {
                List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
                for (ParameterMapping mapping : parameterMappings) {
                    String propName = mapping.getProperty();
                    if (propName.startsWith(ForEachSqlNode.ITEM_PREFIX) && Map.class.isAssignableFrom(param.getClass())) {
                        sql = appendParams(sql, boundSql.getAdditionalParameter(propName), new ArrayList<>());
                    } else {
                        Object value = OgnlCache.getValue(propName, param);
                        sql = appendParams(sql, value, new ArrayList<>());
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("bindCustomObjectParam Exception", e);
        }
        return sql;
    }

    private String appendParams(String sql, Object value, List<String> paramNames) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        String tmpSql = sql;
        if (ReflectUtils.isPrimitive(value.getClass())) {
            return appendParam(tmpSql, value);
        } else {
            LOGGER.error("isCglibProxyClass = {}", ClassUtils.isCglibProxyClass(value.getClass()));
            for (String paramName : paramNames) {
                Field field;
                try {
                    // 如何判断一个对象是否是代理对象,jdk代理,cglib代理,javassist代理?
                    if (ClassUtils.isCglibProxyClass(value.getClass())) {
                        // 代理对象
                        field = value.getClass().getSuperclass().getDeclaredField(paramName);
                    } else {
                        // 非代理对象
                        field = value.getClass().getDeclaredField(paramName);
                    }
                } catch (Exception e) {
                    field = value.getClass().getSuperclass().getDeclaredField(paramName);
                }
                field.setAccessible(true);
                Object fieldValue = field.get(value);
                tmpSql = appendParam(tmpSql, fieldValue);
            }
            return tmpSql;
        }
    }

    private String beautifySql(String sql) {
        sql = sql.replaceAll("[\\s\n ]+", " ");
        return sql;
    }


    private String appendParam(String sql, Object paramValue) {
        String tmpSql = sql;
        if (NumberUtils.isCreatable(paramValue.toString())) {
            return tmpSql.replaceFirst("\\?", Objects.toString(paramValue));
        }
        return tmpSql.replaceFirst("\\?", "'" + paramValue + "'");
    }
}
