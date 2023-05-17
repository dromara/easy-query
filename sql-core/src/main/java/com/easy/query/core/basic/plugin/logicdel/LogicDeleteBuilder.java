package com.easy.query.core.basic.plugin.logicdel;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLColumnSetter;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.LogicDeleteMetadata;
import com.easy.query.core.util.BeanUtil;

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
     * @param sqlWherePredicateSQLExpression
     * @param sqlColumnSetterSQLExpression
     */
    public void configure(SQLExpression<SQLWherePredicate<Object>> sqlWherePredicateSQLExpression, SQLExpression<SQLColumnSetter<Object>> sqlColumnSetterSQLExpression){
        entityMetadata.setLogicDeleteMetadata(new LogicDeleteMetadata(propertyName,sqlWherePredicateSQLExpression, sqlColumnSetterSQLExpression));
    }
    public Property<Object,?> getPropertyLambda(){
        return BeanUtil.getFastBean(entityMetadata.getEntityClass()).getBeanGetter(propertyName,propertyType);
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
