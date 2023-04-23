package com.easy.query.core.expression.segment.builder;

import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.SqlKeywordEnum;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.segment.SqlSegment;

import java.util.*;

/**
 * @FileName: UpdateSetSqlSegmentBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:15
 * @author xuejiaming
 */
public class UpdateSetSqlBuilderSegment extends AbstractSqlBuilderSegment {

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {

        StringBuilder sb=new StringBuilder();
        List<SqlSegment> sqlSegments = getSqlSegments();
        if(!sqlSegments.isEmpty()){
            Iterator<SqlSegment> iterator = sqlSegments.iterator();
            SqlSegment first = iterator.next();
            sb.append(first.toSql(sqlParameterCollector));
            while(iterator.hasNext()){
                SqlSegment sqlSegment = iterator.next();
                sb.append(SqlKeywordEnum.DOT.toSql()).append(sqlSegment.toSql(sqlParameterCollector));
            }
        }
        return sb.toString();
    }

    @Override
    public SqlBuilderSegment cloneSqlBuilder() {
        UpdateSetSqlBuilderSegment updateSetSqlBuilderSegment = new UpdateSetSqlBuilderSegment();
        copyTo(updateSetSqlBuilderSegment);
        return updateSetSqlBuilderSegment;
    }
}
