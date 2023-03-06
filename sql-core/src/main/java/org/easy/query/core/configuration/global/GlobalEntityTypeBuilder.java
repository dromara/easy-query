package org.easy.query.core.configuration.global;

import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.abstraction.metadata.LogicDeleteMetadata;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;

/**
 * @FileName: GlobalEntityTypeBuilder.java
 * @Description: 文件说明
 * @Date: 2023/3/6 22:44
 * @Created by xuejiaming
 */
public class GlobalEntityTypeBuilder {
    private final EntityMetadata entityMetadata;

    public GlobalEntityTypeBuilder(EntityMetadata entityMetadata){

        this.entityMetadata = entityMetadata;
    }
    public Class<?> getEntityClass(){
        return entityMetadata.getEntityClass();
    }
    public void configLogicDelete(SqlExpression<SqlPredicate<?>> queryFilterExpression, SqlExpression<SqlColumnSetter<?>> deletedSqlExpression){
        LogicDeleteMetadata logicDeleteMetadata = new LogicDeleteMetadata(queryFilterExpression, deletedSqlExpression);
        entityMetadata.setLogicDeleteMetadata(logicDeleteMetadata);
    }
}
