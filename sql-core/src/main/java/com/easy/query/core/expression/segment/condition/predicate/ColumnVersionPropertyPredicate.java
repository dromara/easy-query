package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.basic.jdbc.parameter.VersionPropertySQLParameter;
import com.easy.query.core.basic.plugin.version.EasyVersionStrategy;
import com.easy.query.core.expression.parser.abstraction.internal.EntityTableAvailable;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.SQLUtil;
import com.easy.query.core.util.SqlExpressionUtil;

/**
 * create time 2023/3/27 16:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnVersionPropertyPredicate extends ColumnPropertyPredicate{

    private final EasyVersionStrategy easyVersionStrategy;

    public ColumnVersionPropertyPredicate(EntityTableAvailable table, String propertyName, EasyVersionStrategy easyVersionStrategy, EasyQueryRuntimeContext runtimeContext) {
        super(table, propertyName, runtimeContext);

        this.easyVersionStrategy = easyVersionStrategy;
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        SQLUtil.addParameter(sqlParameterCollector,new VersionPropertySQLParameter(new PropertySQLParameter(table,propertyName),easyVersionStrategy));
        String sqlColumnSegment = SqlExpressionUtil.getSqlOwnerColumn(runtimeContext,table,propertyName);
        return sqlColumnSegment + " = ?";
    }

    @Override
    public SQLParameter getParameter() {
        return new VersionPropertySQLParameter(new PropertySQLParameter(table,propertyName),easyVersionStrategy);
    }
}
