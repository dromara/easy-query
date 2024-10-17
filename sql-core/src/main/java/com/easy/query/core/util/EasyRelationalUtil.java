package com.easy.query.core.util;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.RelationEntityTableAvailable;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.SimpleEntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.factory.SQLExpressionInvokeFactory;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.DefaultTableExpressionBuilder;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;

/**
 * create time 2024/6/7 21:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyRelationalUtil {
    public static TableAvailable getRelationTable(EntityExpressionBuilder entityExpressionBuilder,TableAvailable leftTable,String property){
        QueryRuntimeContext runtimeContext = entityExpressionBuilder.getRuntimeContext();
        NavigateMetadata navigateMetadata = leftTable.getEntityMetadata().getNavigateNotNull(property);
        if(navigateMetadata.getRelationType()!= RelationTypeEnum.OneToOne&&navigateMetadata.getRelationType()!= RelationTypeEnum.ManyToOne){
            throw new EasyQueryInvalidOperationException("navigate relation table should [OneToOne or ManyToOne],now is "+navigateMetadata.getRelationType());
        }
        Class<?> navigateEntityClass = navigateMetadata.getNavigatePropertyType();
        EntityTableExpressionBuilder entityTableExpressionBuilder = entityExpressionBuilder.addRelationEntityTableExpression(new RelationTableKey(leftTable.getEntityClass(), navigateEntityClass), key -> {
            EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateEntityClass);
//            TableAvailable leftTable = getTable();
            RelationEntityTableAvailable rightTable = new RelationEntityTableAvailable(key,leftTable, entityMetadata, false);
            EntityTableExpressionBuilder tableExpressionBuilder = new DefaultTableExpressionBuilder(rightTable, MultiTableTypeEnum.LEFT_JOIN, runtimeContext);
            AndPredicateSegment andPredicateSegment = new AndPredicateSegment();

            SQLExpressionInvokeFactory easyQueryLambdaFactory = runtimeContext.getSQLExpressionInvokeFactory();
            WherePredicate<Object> sqlPredicate = easyQueryLambdaFactory.createWherePredicate(rightTable, entityExpressionBuilder, andPredicateSegment);
            sqlPredicate.and(()->{
                sqlPredicate.multiEq(true,new SimpleEntitySQLTableOwner<>(leftTable), navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), navigateMetadata.getSelfPropertiesOrPrimary());
                if(navigateMetadata.hasPredicateFilterExpression()){
                    navigateMetadata.predicateFilterApply(sqlPredicate);
                }
            });
            tableExpressionBuilder.getOn().addPredicateSegment(andPredicateSegment);
            return tableExpressionBuilder;
        });
        return entityTableExpressionBuilder.getEntityTable();
    }
}
