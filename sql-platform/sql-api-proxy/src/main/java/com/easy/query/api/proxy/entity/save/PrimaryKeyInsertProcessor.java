package com.easy.query.api.proxy.entity.save;

import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.PrimaryKeyOnSaveInsertEnum;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2025/10/16 22:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class PrimaryKeyInsertProcessor {
    private final PrimaryKeyOnSaveInsertEnum globalStrategy;
    private PrimaryKeyOnSaveInsertEnum expressionStrategy;
    private SQLFuncExpression<Object> primaryKeyOnInsertGetter;

    public PrimaryKeyInsertProcessor(QueryRuntimeContext runtimeContext) {
        EasyQueryOption easyQueryOption = runtimeContext.getQueryConfiguration().getEasyQueryOption();
        this.globalStrategy = easyQueryOption.getPrimaryKeyOnSaveInsert();
    }

    public void setExpressionStrategy(SQLFuncExpression<Object> primaryKeyOnInsertGetter) {
        if (primaryKeyOnInsertGetter == null) {
            this.expressionStrategy = PrimaryKeyOnSaveInsertEnum.NO_ACTION;
        }
        this.primaryKeyOnInsertGetter = primaryKeyOnInsertGetter;
    }

    public void setPrimaryKeyOnInsert(Object entity, EntityMetadata entityMetadata) {
        if (expressionStrategy == PrimaryKeyOnSaveInsertEnum.NO_ACTION) {
            return;
        }
        if (this.primaryKeyOnInsertGetter != null) {
            for (String keyProperty : entityMetadata.getKeyProperties()) {
                Object primaryKey = primaryKeyOnInsertGetter.apply();
                ColumnMetadata columnKeyMetadata = entityMetadata.getColumnNotNull(keyProperty);
                columnKeyMetadata.getSetterCaller().call(entity, primaryKey);
            }
        } else {
            if (globalStrategy == PrimaryKeyOnSaveInsertEnum.SET_NULL) {
                for (String keyProperty : entityMetadata.getKeyProperties()) {
                    ColumnMetadata columnKeyMetadata = entityMetadata.getColumnNotNull(keyProperty);
                    columnKeyMetadata.getSetterCaller().call(entity, null);
                }
            }
        }
    }

}
