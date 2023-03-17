//package org.easy.query.core.configuration.types;
//
//import com.easy.query.core.abstraction.metadata.EntityMetadata;
//import com.easy.query.core.abstraction.metadata.LogicDeleteMetadata;
//import com.easy.query.core.expression.lambda.SqlExpression;
//import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
//import com.easy.query.core.expression.parser.abstraction.SqlPredicate;
//
///**
// * @FileName: EntityTypeBuilder.java
// * @Description: 文件说明
// * @Date: 2023/2/26 23:02
// * @Created by xuejiaming
// */
//public class EntityTypeBuilder<T> {
//    private final EntityMetadata entityMetadata;
//
//    public EntityTypeBuilder(EntityMetadata entityMetadata){
//
//        this.entityMetadata = entityMetadata;
//    }
//    public void configLogicDelete(SqlExpression<SqlPredicate<T>> queryFilterExpression, SqlExpression<SqlColumnSetter<T>> deletedSqlExpression){
//        LogicDeleteMetadata logicDeleteMetadata = new LogicDeleteMetadata((SqlExpression)queryFilterExpression, (SqlExpression)deletedSqlExpression);
//        entityMetadata.setLogicDeleteMetadata(logicDeleteMetadata);
//    }
//}