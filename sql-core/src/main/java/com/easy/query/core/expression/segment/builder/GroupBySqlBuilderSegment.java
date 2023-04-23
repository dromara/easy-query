package com.easy.query.core.expression.segment.builder;

import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.SqlKeywordEnum;
import com.easy.query.core.expression.segment.SqlSegment;

import java.util.Iterator;
import java.util.List;

/**
 * @FileName: PredicateSqlSegmentBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/13 22:39
 * @author xuejiaming
 */
public class GroupBySqlBuilderSegment extends AbstractSqlBuilderSegment {

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {

        StringBuilder sb=new StringBuilder();
        List<SqlSegment> sqlSegments = getSqlSegments();
        if(!sqlSegments.isEmpty()){
            Iterator<SqlSegment> iterator = sqlSegments.iterator();
            SqlSegment first = iterator.next();
            sb.append(first.toSql(sqlParameterCollector));
            while (iterator.hasNext()){
                SqlSegment sqlSegment = iterator.next();
                sb.append(SqlKeywordEnum.DOT.toSql()).append(sqlSegment.toSql(sqlParameterCollector));
            }
        }

        return sb.toString();
    }

    @Override
    public SqlBuilderSegment cloneSqlBuilder() {
        GroupBySqlBuilderSegment groupBySqlBuilderSegment = new GroupBySqlBuilderSegment();
        copyTo(groupBySqlBuilderSegment);
        return groupBySqlBuilderSegment;
    }
}
