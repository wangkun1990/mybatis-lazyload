package com.mybatis.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.xmltags.OgnlCache;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;
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
        LOGGER.info("sql : {}", sql2);
        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        Long costTime = System.currentTimeMillis() - start;
        if (costTime > slowSqlTime) {
            LOGGER.warn("sql:{} cost {} ms, slowSqlTime = {} ms", new Object[]{sql2, costTime, slowSqlTime});
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
        String sql = boundSql.getSql();
        try {
            if ((param instanceof Number) || (param instanceof Boolean) || (param instanceof Character) || (param instanceof Void)) {
                sql = sql.replaceFirst("\\?", Objects.toString(param));
            } else if (param instanceof String) {
                sql = sql.replaceFirst("\\?", "'" + param + "'");
            } else if (DefaultSqlSession.StrictMap.class == param.getClass()) {
                DefaultSqlSession.StrictMap strictMap = (DefaultSqlSession.StrictMap) param;
                if (strictMap.containsKey("array")) {
                    Object[] array = (Object[]) strictMap.get("array");
                    for (Object object : array) {
                        sql = appendParams(sql, object);
                    }
                } else if (strictMap.containsKey("collection")) {
                    Collection<Object> collection = (Collection<Object>) strictMap.get("collection");
                    for (Object object : collection) {
                        sql = appendParams(sql, object);
                    }
                }
            } else {
                List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
                for (ParameterMapping mapping : paramMapping) {
                    String propName = mapping.getProperty();
                    Object value = OgnlCache.getValue(propName, param);
                    sql = appendParams(sql, value);
                }
            }
        } catch (Exception e) {
            LOGGER.error("bindCustomObjectParam Exception", e);
        }
        return sql;
    }

    private String appendParams(String sql, Object value) {
        String tmpSql = sql;
        if (value.getClass() == String.class) {
            return tmpSql.replaceFirst("\\?", "'" + value + "'");
        } else {
            return tmpSql.replaceFirst("\\?", Objects.toString(value));
        }
    }
}
