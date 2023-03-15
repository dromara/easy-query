package com.easy.query.core.expression.segment.builder;

import com.easy.query.core.enums.SqlKeywordEnum;
import com.easy.query.core.expression.segment.SqlSegment;

import java.util.Iterator;
import java.util.List;

/**
 * @FileName: SelectSqlSegmentBuilder.java
 * @Description: 查询projects
 * @Date: 2023/2/13 22:39
 * @Created by xuejiaming
 */
public class ProjectSqlBuilderSegment extends AbstractSqlBuilderSegment {

    @Override
    public String toSql() {
        StringBuilder sb = new StringBuilder();
        List<SqlSegment> sqlSegments = getSqlSegments();
        if (!sqlSegments.isEmpty()) {
            Iterator<SqlSegment> iterator = sqlSegments.iterator();
            SqlSegment first = iterator.next();
            sb.append(first.toSql());
            while (iterator.hasNext()) {
                SqlSegment sqlSegment = iterator.next();
                sb.append(SqlKeywordEnum.DOT.toSql()).append(sqlSegment.toSql());
            }
        }
        return sb.toString();
    }

    @Override
    public SqlBuilderSegment cloneSqlBuilder() {
        ProjectSqlBuilderSegment projectSqlBuilderSegment = new ProjectSqlBuilderSegment();
        copyTo(projectSqlBuilderSegment);
        return projectSqlBuilderSegment;
    }
}
