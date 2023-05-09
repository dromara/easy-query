package com.easy.query.core.expression.segment.builder;

import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.SqlKeywordEnum;
import com.easy.query.core.expression.segment.AggregationColumnSegment;
import com.easy.query.core.expression.segment.SqlSegment;

import java.util.Iterator;
import java.util.List;

/**
 * @FileName: SelectSqlSegmentBuilder.java
 * @Description: 查询projects
 * @Date: 2023/2/13 22:39
 * @author xuejiaming
 */
public class ProjectSqlBuilderSegmentImpl extends AbstractSqlBuilderSegment implements ProjectSqlBuilderSegment {

    private boolean projectHasAggregate=false;
    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        StringBuilder sb = new StringBuilder();
        List<SqlSegment> sqlSegments = getSqlSegments();
        if (!sqlSegments.isEmpty()) {
            Iterator<SqlSegment> iterator = sqlSegments.iterator();
            SqlSegment first = iterator.next();
            sb.append(first.toSql(sqlParameterCollector));
            while (iterator.hasNext()) {
                SqlSegment sqlSegment = iterator.next();
                sb.append(SqlKeywordEnum.DOT.toSql()).append(sqlSegment.toSql(sqlParameterCollector));
            }
        }
        return sb.toString();
    }

    @Override
    public SqlBuilderSegment cloneSqlBuilder() {
        ProjectSqlBuilderSegmentImpl projectSqlBuilderSegment = new ProjectSqlBuilderSegmentImpl();
        projectSqlBuilderSegment.projectHasAggregate=projectHasAggregate;
        copyTo(projectSqlBuilderSegment);
        return projectSqlBuilderSegment;
    }

    @Override
    public void append(SqlSegment sqlSegment) {
        super.append(sqlSegment);
        if(sqlSegment instanceof AggregationColumnSegment){
            projectHasAggregate=true;
        }
    }

    @Override
    public boolean hasAggregateColumns() {
        return false;
    }
}
