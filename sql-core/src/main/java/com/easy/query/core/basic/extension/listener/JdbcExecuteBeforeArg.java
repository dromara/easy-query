package com.easy.query.core.basic.extension.listener;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.ExecuteMethodEnum;

import java.util.List;
import java.util.Map;

/**
 * create time 2023/11/10 23:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class JdbcExecuteBeforeArg {
    /**
     * 执行追踪id
     */
    private final String traceId;
    /**
     * 执行sql
     */
    private final String sql;
    /**
     * batch下该参数集合可能会大于1,非batch下长度小于等于1
     */
    private final List<List<SQLParameter>> sqlParameters;
    private final ExecuteMethodEnum executeMethod;
    /**
     * 开始时间毫秒
     */
    private final long start;
    /**
     * 自行传递的参数
     */
    private Map<String,Object> state;

    public JdbcExecuteBeforeArg(String traceId, String sql, List<List<SQLParameter>> sqlParameters, ExecuteMethodEnum executeMethod){

        this.traceId = traceId;
        this.sql = sql;
        this.sqlParameters = sqlParameters;
        this.executeMethod = executeMethod;
        this.start = System.currentTimeMillis();
    }

    public String getTraceId() {
        return traceId;
    }

    public String getSql() {
        return sql;
    }

    public List<List<SQLParameter>> getSqlParameters() {
        return sqlParameters;
    }

    public long getStart() {
        return start;
    }

    public Map<String, Object> getState() {
        return state;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }

    public ExecuteMethodEnum getExecuteMethod() {
        return executeMethod;
    }
}
