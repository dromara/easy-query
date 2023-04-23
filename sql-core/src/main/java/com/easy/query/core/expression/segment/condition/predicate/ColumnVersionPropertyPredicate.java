package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.basic.jdbc.parameter.VersionPropertySQLParameter;
import com.easy.query.core.basic.plugin.version.EasyVersionStrategy;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.SQLUtil;

/**
 * create time 2023/3/27 16:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnVersionPropertyPredicate extends ColumnPropertyPredicate{

    private final EasyVersionStrategy easyVersionStrategy;

    public ColumnVersionPropertyPredicate(EntityTableExpressionBuilder table, String propertyName, EasyVersionStrategy easyVersionStrategy, EntityExpressionBuilder sqlEntityExpression) {
        super(table, propertyName, sqlEntityExpression);

        this.easyVersionStrategy = easyVersionStrategy;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        SQLUtil.addParameter(sqlParameterCollector,new VersionPropertySQLParameter(new PropertySQLParameter(table,propertyName),easyVersionStrategy));
        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        return sqlColumnSegment + " = ?";
    }
}
