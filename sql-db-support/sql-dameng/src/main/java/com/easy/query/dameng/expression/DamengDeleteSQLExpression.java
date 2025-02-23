package com.easy.query.dameng.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.DeleteSQLExpressionImpl;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

/**
 * create time 2023/7/28 14:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class DamengDeleteSQLExpression extends DeleteSQLExpressionImpl {
    public DamengDeleteSQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata, EntityTableSQLExpression table) {
        super(entitySQLExpressionMetadata, table);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        if(tables.size()>1){
            throw new UnsupportedOperationException();
        }
        return super.toSQL(toSQLContext);
    }
}
