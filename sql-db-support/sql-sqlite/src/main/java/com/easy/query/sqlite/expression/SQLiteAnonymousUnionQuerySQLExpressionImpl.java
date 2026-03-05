package com.easy.query.sqlite.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLUnionEnum;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.impl.AnonymousUnionQuerySQLExpressionImpl;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.util.Iterator;
import java.util.List;

/**
 * create time 2026/3/5 21:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLiteAnonymousUnionQuerySQLExpressionImpl extends AnonymousUnionQuerySQLExpressionImpl {
    public SQLiteAnonymousUnionQuerySQLExpressionImpl(EntitySQLExpressionMetadata entitySQLExpressionMetadata, List<EntityQuerySQLExpression> querySQLExpressions, SQLUnionEnum sqlUnion) {
        super(entitySQLExpressionMetadata, querySQLExpressions, sqlUnion);
    }
    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);
        Iterator<EntityQuerySQLExpression> iterator = querySQLExpressions.iterator();
        EntityQuerySQLExpression firstQuerySQLExpression = iterator.next();
        String unionSQL = " " + sqlUnion.getSQL() + " ";
        StringBuilder sql = new StringBuilder();
        sql.append(" ").append(firstQuerySQLExpression.toSQL(toSQLContext)).append(" ");
        while(iterator.hasNext()){
            sql.append(unionSQL);
            EntityQuerySQLExpression querySQLExpression = iterator.next();
            sql.append(" ").append(querySQLExpression.toSQL(toSQLContext)).append(" ");
        }
        return sql.toString();
    }
}
