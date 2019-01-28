package com.mybatis.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.xmltags.OgnlCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
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

    private Logger logger = LoggerFactory.getLogger(SqlLogInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler handler = (StatementHandler) invocation.getTarget();
        //MetaObject metaObject = SystemMetaObject.forObject(handler);
        BoundSql boundSql = handler.getBoundSql();
        final String sql = boundSql.getSql();
        final Object param = boundSql.getParameterObject();
        String sql2 = "";
        if (param == null) {
            sql2 = sql.replaceFirst("\\?", "''");
        } else {
            sql2 = bindCustomObjectParam(boundSql, param, sql);
        }
        logger.warn("sql : {}", sql2);
        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        logger.warn("proceed cost time = {} ms", (System.currentTimeMillis() - start));
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private String bindCustomObjectParam(BoundSql boundSql, Object param, String sql) {
        String sql2 = new String(sql);
        logger.warn("Param Class = {}", param.getClass());

        try {
            if ((param instanceof Integer) || (param instanceof Float) || (param instanceof Long)
                    || (param instanceof Double) || (param instanceof Short) || (param instanceof Byte)
                    || (param instanceof Boolean) || (param instanceof Character) || (param instanceof Void)) {
                sql2 = sql2.replaceFirst("\\?", Objects.toString(param));
            } else {
                List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
                for (ParameterMapping mapping : paramMapping) {
                    String propName = mapping.getProperty();
                    Object value = OgnlCache.getValue(propName, param);
                    Class<?> javaType = mapping.getJavaType();
                    if (String.class == javaType) {
                        sql2 = sql2.replaceFirst("\\?", "'" + value + "'");
                    } else {
                        sql2 = sql2.replaceFirst("\\?", Objects.toString(value));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }


        return sql2;
    }
}
