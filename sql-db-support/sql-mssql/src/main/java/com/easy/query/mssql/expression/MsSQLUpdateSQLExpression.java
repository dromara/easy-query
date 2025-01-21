package com.easy.query.mssql.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.sql.TableContext;
import com.easy.query.core.expression.sql.ToTableContext;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.expression.sql.expression.impl.UpdateSQLExpressionImpl;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

/**
 * create time 2023/7/27 17:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLUpdateSQLExpression extends UpdateSQLExpressionImpl {
    private static final Log log= LogFactory.getLog(MsSQLUpdateSQLExpression.class);
    public MsSQLUpdateSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        super(entitySQLExpressionMetadata, table);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);
        if(EasySQLSegmentUtil.isEmpty(setColumns)){
            log.warn("'UPDATE' statement without 'SET',not generate sql execute");
            return null;
        }
        EntityTableSQLExpression easyTableSQLExpression = tables.get(0);
        String tableName = easyTableSQLExpression.toSQL(toSQLContext);
        ToTableContext toTableContext = toSQLContext.getToTableContext();
        int tableSize = toTableContext.getTableSize();
        if(tableSize>1){
            String alias = toTableContext.getAlias(easyTableSQLExpression.getEntityTable());
            return "UPDATE " + alias + " SET " + setColumns.toSQL(toSQLContext) +" FROM "+ tableName + " WHERE " +
                    where.toSQL(toSQLContext);

        }else{
            return "UPDATE " + tableName + " SET " + setColumns.toSQL(toSQLContext) + " WHERE " +
                    where.toSQL(toSQLContext);
        }
    }
}
