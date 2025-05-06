package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.basic.extension.version.VersionStrategy;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/3/27 15:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class VersionPropertySQLParameter implements BeanSQLParameter {
    private final BeanSQLParameter beanSQLParameter;
    private final VersionStrategy easyVersionStrategy;

    public VersionPropertySQLParameter(BeanSQLParameter beanSQLParameter, VersionStrategy easyVersionStrategy){

        this.beanSQLParameter = beanSQLParameter;
        this.easyVersionStrategy = easyVersionStrategy;
    }
    @Override
    public void setBean(Object bean) {
        beanSQLParameter.setBean(bean);
    }

    @Override
    public boolean hasBean() {
        return beanSQLParameter.hasBean();
    }

    @Override
    public TableAvailable getTableOrNull() {
        return beanSQLParameter.getTableOrNull();
    }

    @Override
    public String getPropertyNameOrNull() {
        return beanSQLParameter.getPropertyNameOrNull();
    }

    @Override
    public Object getValue() {
        Object value = beanSQLParameter.getValue();
        return easyVersionStrategy.nextVersion(getTableOrNull().getEntityMetadata(), getPropertyNameOrNull(),value);
    }
}
