package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLColumnSegment;

/**
 * create time 2023/7/30 21:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativePredicate implements Predicate{
    @Override
    public SQLColumnSegment cloneSQLColumnSegment() {
        throw new UnsupportedOperationException();
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
    public String toSQL(ToSQLContext toSQLContext) {
        return null;
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return null;
    }
}
