package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/7/2 17:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnAsSegmentImpl implements SQLEntityAliasSegment {
    private final CloneableSQLSegment sqlColumnSegment;
    private String alias;
    private final QueryRuntimeContext runtimeContext;

    public SQLColumnAsSegmentImpl(CloneableSQLSegment sqlColumnSegment, String alias, QueryRuntimeContext runtimeContext){

        this.sqlColumnSegment = sqlColumnSegment;
        this.alias = alias;
        this.runtimeContext = runtimeContext;
    }
    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        String sqlColumn = sqlColumnSegment.toSQL(toSQLContext);
        String alias = getAlias();
        if(alias ==null){
            return sqlColumn;
        }

        return sqlColumn+" AS "+ EasySQLExpressionUtil.getQuoteName(runtimeContext, alias);
    }

    @Override
    public String getAlias() {
        return alias;
    }
    @Override
    public void setAlias(String alias) {
        this.alias=alias;
    }

    @Override
    public TableAvailable getTable() {
        return null;
    }

    @Override
    public String getPropertyName() {
        return null;
    }

    @Override
    public SQLEntitySegment cloneSQLColumnSegment() {
        return new SQLColumnAsSegmentImpl(sqlColumnSegment.cloneSQLColumnSegment(),alias,runtimeContext);
    }
}
