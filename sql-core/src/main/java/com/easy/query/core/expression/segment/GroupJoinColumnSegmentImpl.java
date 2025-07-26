package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.visitor.TableVisitor;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLSegmentUtil;
import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2025/7/26 15:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupJoinColumnSegmentImpl implements GroupJoinColumnSegment {
    private final PredicateSegment predicateSegment;
    private final SQLSegment then;
    private final SQLSegment elseEnd;
    private String alias;

    public GroupJoinColumnSegmentImpl(PredicateSegment predicateSegment, SQLSegment then, SQLSegment elseEnd) {
        this.predicateSegment = predicateSegment;
        this.then = then;
        this.elseEnd = elseEnd;
    }

    @Override
    public PredicateSegment getPredicateSegment() {
        return predicateSegment;
    }

    @Override
    public ColumnMetadata getColumnMetadata() {
        return null;
    }

    @Override
    public ColumnSegment cloneSQLColumnSegment() {
        return new GroupJoinColumnSegmentImpl(predicateSegment, then, elseEnd);
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public void setAlias(String alias) {
        this.alias = alias;
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
    public String toSQL(ToSQLContext toSQLContext) {
        String predicateSegmentSQL = predicateSegment.toSQL(toSQLContext);
        if(EasyStringUtil.isBlank(predicateSegmentSQL)){
            return then.toSQL(toSQLContext);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("(CASE WHEN ");
        sql.append(predicateSegmentSQL).append(" THEN ")
                .append(then.toSQL(toSQLContext)).append(" ELSE ").append(elseEnd.toSQL(toSQLContext)).append(" END)");
        return sql.toString();
    }

    @Override
    public void accept(TableVisitor visitor) {

        EasySQLSegmentUtil.tableVisit(predicateSegment, visitor);
        EasySQLSegmentUtil.sqlSegmentTableVisit(then, visitor);
        EasySQLSegmentUtil.sqlSegmentTableVisit(elseEnd, visitor);
    }
}
