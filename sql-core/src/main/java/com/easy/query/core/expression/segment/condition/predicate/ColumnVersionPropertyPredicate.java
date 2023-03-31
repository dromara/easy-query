package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.VersionPropertySQLParameter;
import com.easy.query.core.basic.plugin.version.EasyVersionStrategy;
import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;

/**
 * create time 2023/3/27 16:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnVersionPropertyPredicate extends ColumnPropertyPredicate{

    private final EasyVersionStrategy easyVersionStrategy;

    public ColumnVersionPropertyPredicate(EntityTableExpression table, String propertyName, EasyVersionStrategy easyVersionStrategy, EntityExpression sqlEntityExpression) {
        super(table, propertyName, sqlEntityExpression);

        this.easyVersionStrategy = easyVersionStrategy;
    }

    @Override
    public String toSql() {

        sqlEntityExpression.addParameter(new VersionPropertySQLParameter(new PropertySQLParameter(table,propertyName),easyVersionStrategy));
        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        return sqlColumnSegment + " = ?";
    }
}
