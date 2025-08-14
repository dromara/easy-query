package com.easy.query.core.expression.sql.builder.factory;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.ContextTypeEnum;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SQLUnionEnum;
import com.easy.query.core.expression.EntityTableAvailable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.AnonymousManyJoinEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.MapUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.AnonymousQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.AnonymousTreeCTERECURSIVEQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.AnonymousUnionQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.AnonymousCteTableQueryExpressionBuilder;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.Collection;
import java.util.List;

/**
 * @Description: 文件说明
 * create time 2023/3/4 22:43
 * @author xuejiaming
 */
public interface ExpressionBuilderFactory {
    ExpressionContext createExpressionContext(QueryRuntimeContext runtimeContext, ContextTypeEnum contextType);
   default EntityTableExpressionBuilder createEntityTableExpressionBuilder(EntityMetadata entityMetadata, MultiTableTypeEnum multiTableType, ExpressionContext expressionContext){
       return createEntityTableExpressionBuilder(new EntityTableAvailable(entityMetadata,false),multiTableType,expressionContext);
   }
    EntityTableExpressionBuilder createEntityTableExpressionBuilder(TableAvailable tableAvailable, MultiTableTypeEnum multiTableType, ExpressionContext expressionContext);
   default EntityTableExpressionBuilder createAnonymousEntityTableExpressionBuilder(EntityMetadata entityMetadata,MultiTableTypeEnum multiTableType, EntityQueryExpressionBuilder entityQueryExpressionBuilder){
       return createAnonymousEntityTableExpressionBuilder(new EntityTableAvailable(entityMetadata,true),multiTableType,entityQueryExpressionBuilder);
   }
    EntityTableExpressionBuilder createAnonymousEntityTableExpressionBuilder(TableAvailable tableAvailable, MultiTableTypeEnum multiTableType, EntityQueryExpressionBuilder entityQueryExpressionBuilder);
    AnonymousManyJoinEntityTableExpressionBuilder createAnonymousManyGroupEntityTableExpressionBuilder(ExpressionContext expressionContext,TableAvailable tableAvailable, MultiTableTypeEnum multiTableType, EntityQueryExpressionBuilder entityQueryExpressionBuilder, String[] defaultKeys);
    EntityQueryExpressionBuilder createEntityQueryExpressionBuilder(ExpressionContext sqlExpressionContext,Class<?> queryClass);
    default EntityQueryExpressionBuilder createAnonymousQueryExpressionBuilder(String sql, Collection<Object> sqlParams, ExpressionContext sqlExpressionContext, Class<?> queryClass){
        return new AnonymousQueryExpressionBuilder(sql,sqlParams,sqlExpressionContext,queryClass);
    }
    default EntityQueryExpressionBuilder createAnonymousUnionQueryExpressionBuilder(List<EntityQueryExpressionBuilder> entityQueryExpressionBuilders, ExpressionContext sqlExpressionContext,Class<?> queryClass, SQLUnionEnum sqlUnion){
        return new AnonymousUnionQueryExpressionBuilder(entityQueryExpressionBuilders,sqlExpressionContext,queryClass,sqlUnion);
    }
    default EntityQueryExpressionBuilder createAnonymousCTEQueryExpressionBuilder(String cteTableName,EntityQueryExpressionBuilder sqlAnonymousUnionEntityQueryExpressionBuilder, ExpressionContext queryExpressionContext, Class<?> queryClass){
        return new AnonymousTreeCTERECURSIVEQueryExpressionBuilder(cteTableName,sqlAnonymousUnionEntityQueryExpressionBuilder,queryExpressionContext,queryClass);
    }
    default EntityQueryExpressionBuilder createAnonymousWithTableQueryExpressionBuilder(String withTableName,EntityQueryExpressionBuilder sqlAnonymousUnionEntityQueryExpressionBuilder, ExpressionContext queryExpressionContext, Class<?> queryClass){
        return new AnonymousCteTableQueryExpressionBuilder(withTableName,sqlAnonymousUnionEntityQueryExpressionBuilder,queryExpressionContext,queryClass);
    }
    EntityInsertExpressionBuilder createEntityInsertExpressionBuilder(ExpressionContext sqlExpressionContext,Class<?> queryClass);
    EntityUpdateExpressionBuilder createEntityUpdateExpressionBuilder(ExpressionContext sqlExpressionContext,Class<?> queryClass, boolean expression);
    MapUpdateExpressionBuilder createMapUpdateExpressionBuilder(ExpressionContext sqlExpressionContext);
    EntityDeleteExpressionBuilder createEntityDeleteExpressionBuilder(ExpressionContext sqlExpressionContext,Class<?> queryClass, boolean expression);

    EntityInsertExpressionBuilder createMapInsertExpressionBuilder(ExpressionContext sqlExpressionContext);

}
