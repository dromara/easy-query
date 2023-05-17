package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.basic.plugin.version.EasyVersionStrategy;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/3/27 15:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class VersionPropertySQLParameter implements BeanSQLParameter {
    private final BeanSQLParameter beanSQLParameter;
    private final EasyVersionStrategy easyVersionStrategy;

    public VersionPropertySQLParameter(BeanSQLParameter beanSQLParameter, EasyVersionStrategy easyVersionStrategy){

        this.beanSQLParameter = beanSQLParameter;
        this.easyVersionStrategy = easyVersionStrategy;
    }
    @Override
    public void setBean(Object bean) {
        beanSQLParameter.setBean(bean);
    }

    @Override
    public TableAvailable getTable() {
        return beanSQLParameter.getTable();
    }

    @Override
    public String getPropertyName() {
        return beanSQLParameter.getPropertyName();
    }

    @Override
    public Object getValue() {
        Object value = beanSQLParameter.getValue();
        return easyVersionStrategy.nextVersion(getTable().getEntityMetadata(),getPropertyName(),value);
    }
}
