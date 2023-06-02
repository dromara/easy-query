package com.easy.query.core.basic.plugin.logicdel;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.LogicDeleteMetadata;
import com.easy.query.core.util.EasyBeanUtil;

/**
 * create time 2023/3/28 09:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class LogicDeleteBuilder {
    private final EntityMetadata entityMetadata;
    private final String propertyName;
    private final Class<?> propertyType;

    public LogicDeleteBuilder(EntityMetadata entityMetadata,String propertyName,Class<?> propertyType){

        this.entityMetadata = entityMetadata;
        this.propertyName = propertyName;
        this.propertyType = propertyType;
    }

    /**
     * 配置软删除
     *
     * @param sqlWherePredicateSQLExpression
     * @param sqlColumnSetterSQLExpression
     */
    public void configure(SQLExpression1<WherePredicate<Object>> sqlWherePredicateSQLExpression, SQLExpression1<ColumnSetter<Object>> sqlColumnSetterSQLExpression) {
        entityMetadata.setLogicDeleteMetadata(new LogicDeleteMetadata(propertyName, sqlWherePredicateSQLExpression, sqlColumnSetterSQLExpression));
    }

    public Property<Object, ?> getPropertyLambda() {
        return EasyBeanUtil.getFastBean(entityMetadata.getEntityClass()).getBeanGetter(propertyName, propertyType);
    }

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Class<?> getPropertyType() {
        return propertyType;
    }
}
