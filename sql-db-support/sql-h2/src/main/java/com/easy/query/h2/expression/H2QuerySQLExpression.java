package com.easy.query.h2.expression;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.sql.builder.ExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.AnonymousTreeCTERECURSIVEQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.SQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.expression.sql.expression.impl.QuerySQLExpressionImpl;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.util.List;

/**
 * create time 2023/5/17 22:36
 * 文件说明
 *
 * @author xuejiaming
 */
public class H2QuerySQLExpression extends QuerySQLExpressionImpl {
    public H2QuerySQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata) {
        super(entitySQLExpressionMetadata);
    }
    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        boolean root = EasySQLExpressionUtil.expressionInvokeRoot(toSQLContext);
        if (root) {
            if (entitySQLExpressionMetadata.getExpressionContext().hasDeclareExpressions()) {
                StringBuilder sb = new StringBuilder("WITH ");
                List<ExpressionBuilder> declareExpressions = entitySQLExpressionMetadata.getExpressionContext().getDeclareExpressions();

                boolean isRecursive = EasyCollectionUtil.any(declareExpressions, s -> s instanceof AnonymousTreeCTERECURSIVEQueryExpressionBuilder);
                if(isRecursive){
                    sb.append("RECURSIVE ");
                }
                for (ExpressionBuilder declareExpression : declareExpressions) {
                    SQLExpression expression = declareExpression.toExpression();
                    String sql = expression.toSQL(toSQLContext);
                    sb.append(sql).append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append(toSQL0(true, toSQLContext));
                return sb.toString();
            }
        }
        return toSQL0(root, toSQLContext);
    }
}
