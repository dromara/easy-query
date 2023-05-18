package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;
import com.easy.query.core.basic.jdbc.parameter.VersionPropertySQLParameter;
import com.easy.query.core.basic.plugin.version.EasyVersionStrategy;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/3/27 16:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnVersionPropertyPredicate extends ColumnPropertyPredicate{

    private final EasyVersionStrategy easyVersionStrategy;

    public ColumnVersionPropertyPredicate(TableAvailable table, String propertyName, EasyVersionStrategy easyVersionStrategy, QueryRuntimeContext runtimeContext) {
        super(table, propertyName, runtimeContext);

        this.easyVersionStrategy = easyVersionStrategy;
    }

    @Override
    public String toSQL(SQLParameterCollector sqlParameterCollector) {
        EasySQLUtil.addParameter(sqlParameterCollector,new VersionPropertySQLParameter(new PropertySQLParameter(table,propertyName),easyVersionStrategy));
        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumn(runtimeContext,table,propertyName);
        return sqlColumnSegment + " = ?";
    }

    @Override
    public SQLParameter getParameter() {
        return new VersionPropertySQLParameter(new PropertySQLParameter(table,propertyName),easyVersionStrategy);
    }
}
