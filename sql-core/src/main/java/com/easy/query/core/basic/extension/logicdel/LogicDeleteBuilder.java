package com.easy.query.core.basic.extension.logicdel;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.metadata.LogicDeleteMetadata;

/**
 * create time 2023/3/28 09:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class LogicDeleteBuilder {
    private final Class<?> entityClass;
    private final String propertyName;
    private final Class<?> propertyType;

    public LogicDeleteBuilder(Class<?> entityClass,String propertyName,Class<?> propertyType){
        this.entityClass = entityClass;

        this.propertyName = propertyName;
        this.propertyType = propertyType;
    }

    /**
     * 配置软删除
     *
     * @param sqlWherePredicateSQLExpression
     * @param sqlColumnSetterSQLExpression
     */
    public LogicDeleteMetadata build(SQLActionExpression1<WherePredicate<Object>> sqlWherePredicateSQLExpression, SQLActionExpression1<ColumnSetter<Object>> sqlColumnSetterSQLExpression) {
         return new LogicDeleteMetadata(propertyName, sqlWherePredicateSQLExpression, sqlColumnSetterSQLExpression);
    }
    public String getPropertyName() {
        return propertyName;
    }

    public Class<?> getPropertyType() {
        return propertyType;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }
}
