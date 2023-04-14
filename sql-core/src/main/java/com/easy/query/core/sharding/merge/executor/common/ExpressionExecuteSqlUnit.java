package com.easy.query.core.sharding.merge.executor.common;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;

import java.util.List;

/**
 * create time 2023/4/14 16:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class ExpressionExecuteSqlUnit extends BaseSqlUnit{

    private final List<SQLParameter> parameters;

    public ExpressionExecuteSqlUnit(String sql,List<SQLParameter> parameters) {
        super(sql);
        this.parameters = parameters;
    }

    public List<SQLParameter> getParameters() {
        return parameters;
    }
}
