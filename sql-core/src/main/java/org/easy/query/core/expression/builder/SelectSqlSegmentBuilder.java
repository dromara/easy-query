package org.easy.query.core.expression.builder;

import org.easy.query.core.expression.segment.SqlSegment;
import org.easy.query.core.enums.SqlKeywordEnum;

import java.util.Iterator;
import java.util.List;

/**
 * @FileName: SelectSqlSegmentBuilder.java
 * @Description: 查询projects
 * @Date: 2023/2/13 22:39
 * @Created by xuejiaming
 */
public class SelectSqlSegmentBuilder extends AbstractSqlSegmentBuilder {

    @Override
    public String toSql() {
        StringBuilder sb = new StringBuilder();
        List<SqlSegment> sqlSegments = getSqlSegments();
        if (!sqlSegments.isEmpty()) {
            Iterator<SqlSegment> iterator = sqlSegments.iterator();
            SqlSegment first = iterator.next();
            sb.append(first.getSql());
            while (iterator.hasNext()) {
                SqlSegment sqlSegment = iterator.next();
                sb.append(SqlKeywordEnum.DOT.getSql()).append(sqlSegment.getSql());
            }
        }
        return sb.toString();
    }
}
