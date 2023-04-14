package com.easy.query.core.sharding.merge.executor.common;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;

import java.util.List;

/**
 * create time 2023/4/14 16:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityExecuteSqlUnit extends BaseSqlUnit{

    private final List<Object> entities;
    private final List<SQLParameter> parameters;

    public EntityExecuteSqlUnit(String sql, List<Object> entities, List<SQLParameter> parameters) {
        super(sql);
        this.entities = entities;
        this.parameters = parameters;
    }

    public List<Object> getEntities() {
        return entities;
    }

    public List<SQLParameter> getParameters() {
        return parameters;
    }
}
