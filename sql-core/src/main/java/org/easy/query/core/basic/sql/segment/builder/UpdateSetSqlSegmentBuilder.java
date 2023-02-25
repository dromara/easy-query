package org.easy.query.core.basic.sql.segment.builder;

import org.easy.query.core.basic.sql.segment.segment.SqlSegment;
import org.easy.query.core.enums.SqlKeywordEnum;

import java.util.Iterator;
import java.util.List;

/**
 * @FileName: UpdateSetSqlSegmentBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:15
 * @Created by xuejiaming
 */
public class UpdateSetSqlSegmentBuilder extends AbstractSqlSegmentBuilder {
    @Override
    public String toSql() {

        StringBuilder sb=new StringBuilder();
        List<SqlSegment> sqlSegments = getSqlSegments();
        if(!sqlSegments.isEmpty()){
            Iterator<SqlSegment> iterator = sqlSegments.iterator();
            SqlSegment first = iterator.next();
            sb.append(first.getSql());
            while(iterator.hasNext()){
                SqlSegment sqlSegment = iterator.next();
                sb.append(SqlKeywordEnum.DOT.getSql()).append(sqlSegment.getSql());
            }
        }
        return sb.toString();
    }
}
