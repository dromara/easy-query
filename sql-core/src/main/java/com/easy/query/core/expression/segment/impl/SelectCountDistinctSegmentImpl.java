package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.SelectCountDistinctSegment;
import com.easy.query.core.expression.visitor.TableVisitor;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author xuejiaming
 * @FileName: SelectCountSegment.java
 * @Description: 文件说明
 * create time 2023/3/3 12:52
 */
public class SelectCountDistinctSegmentImpl implements SelectCountDistinctSegment {

    private final Collection<SQLSegment> sqlSegments;

    public SelectCountDistinctSegmentImpl(Collection<SQLSegment> sqlSegments) {

        this.sqlSegments = sqlSegments;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        if (EasyCollectionUtil.isEmpty(sqlSegments)) {
            return "COUNT(*)";
        }
        String distinctColumns = sqlSegments.stream().map(o -> {
            if (o instanceof SQLEntityAliasSegment) {
                SQLEntityAliasSegment aliasSegment = (SQLEntityAliasSegment) o;
                String alias = aliasSegment.getAlias();
                if(alias!=null){
                    aliasSegment.setAlias(null);
                    String sql = o.toSQL(toSQLContext);
                    aliasSegment.setAlias(alias);
                    return sql;
                }
            }
            return o.toSQL(toSQLContext);
        }).collect(Collectors.joining(","));
        return "COUNT(DISTINCT " + distinctColumns + ")";
    }

    @Override
    public TableAvailable getTable() {
        return null;
    }

    @Override
    public void accept(TableVisitor visitor) {
        for (SQLSegment sqlSegment : sqlSegments) {
            EasySQLSegmentUtil.sqlSegmentTableVisit(sqlSegment,visitor);
        }
    }

    @Override
    public CloneableSQLSegment cloneSQLColumnSegment() {
        return new SelectCountDistinctSegmentImpl(sqlSegments);
    }
}
