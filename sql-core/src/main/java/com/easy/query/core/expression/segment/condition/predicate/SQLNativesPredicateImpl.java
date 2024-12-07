package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.visitor.TableVisitor;

/**
 * create time 2023/10/13 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativesPredicateImpl implements Predicate{
    private final QueryRuntimeContext runtimeContext;
    private final Predicate predicateLeft;
    private final SQLPredicateCompare sqlPredicateCompare;
    private final Predicate predicateRight;

    public SQLNativesPredicateImpl(QueryRuntimeContext runtimeContext, Predicate predicateLeft,SQLPredicateCompare sqlPredicateCompare, Predicate predicateRight){

        this.runtimeContext = runtimeContext;
        this.predicateLeft = predicateLeft;
        this.sqlPredicateCompare = sqlPredicateCompare;
        this.predicateRight = predicateRight;
    }
    @Override
    public Predicate cloneSQLColumnSegment() {
        return new SQLNativesPredicateImpl(runtimeContext,predicateLeft.cloneSQLColumnSegment(),sqlPredicateCompare,predicateRight.cloneSQLColumnSegment());
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return predicateLeft.toSQL(toSQLContext)+" "+sqlPredicateCompare.getSQL()+" "+predicateRight.toSQL(toSQLContext);
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return sqlPredicateCompare;
    }

    @Override
    public String getPropertyName() {
        return null;
    }

    @Override
    public TableAvailable getTable() {
        return null;
    }

    @Override
    public void accept(TableVisitor visitor) {
        visitor.visit(predicateLeft.getTable());
        visitor.visit(predicateRight.getTable());
    }
}
