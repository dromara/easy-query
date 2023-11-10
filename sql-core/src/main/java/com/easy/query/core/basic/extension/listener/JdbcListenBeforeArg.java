package com.easy.query.core.basic.extension.listener;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;

import java.util.List;
import java.util.Map;

/**
 * create time 2023/11/10 23:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class JdbcListenBeforeArg {
    private final String traceId;
    private final String sql;
    private final List<List<SQLParameter>> sqlParameters;
    private final long start;
    private Map<String,Object> state;

    public JdbcListenBeforeArg(String traceId, String sql, List<List<SQLParameter>> sqlParameters){

        this.traceId = traceId;
        this.sql = sql;
        this.sqlParameters = sqlParameters;
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
}
