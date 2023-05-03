package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.basic.plugin.version.EasyVersionStrategy;
import com.easy.query.core.expression.parser.abstraction.internal.EntityTableAvailable;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

/**
 * create time 2023/3/27 15:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class VersionPropertySQLParameter implements BeanSqlParameter{
    private final BeanSqlParameter beanSqlParameter;
    private final EasyVersionStrategy easyVersionStrategy;

    public VersionPropertySQLParameter(BeanSqlParameter beanSqlParameter, EasyVersionStrategy easyVersionStrategy){

        this.beanSqlParameter = beanSqlParameter;
        this.easyVersionStrategy = easyVersionStrategy;
    }
    @Override
    public void setBean(Object bean) {
        beanSqlParameter.setBean(bean);
    }

    @Override
    public EntityTableAvailable getTable() {
        return beanSqlParameter.getTable();
    }

    @Override
    public String getPropertyName() {
        return beanSqlParameter.getPropertyName();
    }

    @Override
    public Object getValue() {
        Object value = beanSqlParameter.getValue();
        return easyVersionStrategy.nextVersion(getTable().getEntityMetadata(),getPropertyName(),value);
    }
}
