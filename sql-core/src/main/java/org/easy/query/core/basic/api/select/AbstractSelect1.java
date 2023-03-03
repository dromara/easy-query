//package org.easy.query.core.basic.api.select;
//
//import org.easy.query.core.abstraction.EasyQuerySqlBuilderProvider;
//import org.easy.query.core.expression.lambda.SqlExpression2;
//import org.easy.query.core.abstraction.metadata.EntityMetadata;
//import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
//import org.easy.query.core.enums.MultiTableTypeEnum;
//import org.easy.query.core.impl.Select1SqlProvider;
//import org.easy.query.core.expression.context.SelectContext;
//import org.easy.query.core.query.builder.SqlTableInfo;
//
///**
// *
// * @FileName: AbstractSelect1.java
// * @Description: 文件说明
// * @Date: 2023/2/6 23:43
// * @Created by xuejiaming
// */
//public abstract class AbstractSelect1<T1> extends AbstractSelect<T1> {
//
//    private final Select1SqlProvider<T1> sqlPredicateProvider;
//    public AbstractSelect1(Class<T1> t1Class, SelectContext selectContext) {
//        super(t1Class,selectContext);
//        EntityMetadata entityMetadata = selectContext.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(t1Class);
//        entityMetadata.checkTable();
//        selectContext.addSqlTable(new SqlTableInfo(entityMetadata,selectContext.getAlias(),selectContext.getNextTableIndex(), MultiTableTypeEnum.FROM));
//        sqlPredicateProvider= new Select1SqlProvider<>(selectContext);
//    }
//
//    @Override
//    protected EasyQuerySqlBuilderProvider<T1> getSqlBuilderProvider1() {
//        return sqlPredicateProvider;
//    }
//
//}
