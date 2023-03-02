package org.easy.query.core.expression.segment.builder;

import org.easy.query.core.expression.segment.SqlSegment;
import org.easy.query.core.enums.SqlKeywordEnum;

import java.util.Iterator;
import java.util.List;

/**
 * @FileName: UpdateSetSqlSegmentBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:15
 * @Created by xuejiaming
 */
public class UpdateSetSqlBuilderSegment extends AbstractSqlBuilderSegment {
    @Override
    public String toSql() {

        StringBuilder sb=new StringBuilder();
        List<SqlSegment> sqlSegments = getSqlSegments();
        if(!sqlSegments.isEmpty()){
            Iterator<SqlSegment> iterator = sqlSegments.iterator();
            SqlSegment first = iterator.next();
            sb.append(first.toSql());
            while(iterator.hasNext()){
                SqlSegment sqlSegment = iterator.next();
                sb.append(SqlKeywordEnum.DOT.toSql()).append(sqlSegment.toSql());
            }
        }
        return sb.toString();
    }
}
