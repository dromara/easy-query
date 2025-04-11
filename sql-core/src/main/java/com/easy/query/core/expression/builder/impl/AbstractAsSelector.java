package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.basic.entity.EntityMappingRule;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.PartResult;
import com.easy.query.core.enums.EntityMetadataTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.List;

/**
 * create time 2024/9/29 16:12
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractAsSelector<TChain> extends AbstractSelector<TChain> {
    protected final Class<?> resultClass;
    protected final EntityMetadata resultEntityMetadata;

    public AbstractAsSelector(EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLBuilderSegment sqlBuilderSegment, EntityMetadata resultEntityMetadata) {
        super(entityQueryExpressionBuilder, sqlBuilderSegment);
        this.resultEntityMetadata = resultEntityMetadata;
        this.resultClass = resultEntityMetadata.getEntityClass();
    }

    @Override
    protected TChain columnAnonymousAll(AnonymousEntityTableExpressionBuilder table) {
        if (PartResult.class.isAssignableFrom(resultEntityMetadata.getEntityClass())) {
            return super.columnAnonymousAll(table);
        } else {

            EntityQueryExpressionBuilder queryExpressionBuilder = getAnonymousTableQueryExpressionBuilder(table);
            if (EasySQLSegmentUtil.isNotEmpty(queryExpressionBuilder.getProjects())) {

                List<SQLSegment> sqlSegments = queryExpressionBuilder.getProjects().getSQLSegments();
                EntityMappingRule entityMappingRule = runtimeContext.getEntityMappingRule();
                //匿名表内部设定的不查询
                for (SQLSegment sqlSegment : sqlSegments) {

                    if (sqlSegment instanceof SQLEntityAliasSegment) {
                        SQLEntityAliasSegment sqlEntityAliasSegment = (SQLEntityAliasSegment) sqlSegment;

                        String propertyName = entityMappingRule.getAnonymousPropertyNameFromSQLSegment(sqlEntityAliasSegment, table.getEntityTable());
                        if (propertyName != null) {
                            if (resultEntityMetadata.getColumnOrNull(propertyName) != null) {
                                ColumnSegment columnSegment = sqlSegmentFactory.createSelectColumnSegment(table.getEntityTable(), propertyName, expressionContext, sqlEntityAliasSegment.getAlias());
                                sqlBuilderSegment.append(columnSegment);
                            } else if (resultEntityMetadata.getEntityMetadataType() == EntityMetadataTypeEnum.MAP) {
                                ColumnSegment columnSegment = sqlSegmentFactory.createAnonymousColumnSegment(table.getEntityTable(), expressionContext, sqlEntityAliasSegment.getAlias());
                                sqlBuilderSegment.append(columnSegment);
                            }
                        } else {
                            ColumnSegment columnSegment = sqlSegmentFactory.createAnonymousColumnSegment(table.getEntityTable(), expressionContext, sqlEntityAliasSegment.getAlias());
                            sqlBuilderSegment.append(columnSegment);
                        }
                    } else {
                        throw new EasyQueryException("columnAll not found column:" + EasyClassUtil.getInstanceSimpleName(sqlSegment));
                    }
                }
            }
            return castChain();
        }
    }
}
