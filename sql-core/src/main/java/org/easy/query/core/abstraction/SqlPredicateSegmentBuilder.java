package org.easy.query.core.abstraction;

import org.easy.query.core.exception.JDQCException;
import org.easy.query.core.segments.PredicateSegment0;

import java.util.List;

/**
 * @FileName: PredicateSqlSegmentBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/13 22:39
 * @Created by xuejiaming
 */
public class SqlPredicateSegmentBuilder extends AbstractSqlSegmentBuilder {

    @Override
    public String toSql() {
        List<SqlSegment0> sqlSegments = getSqlSegments();
        StringBuilder predicateSql = new StringBuilder();
        for (SqlSegment0 sqlSegment : sqlSegments) {
            if(sqlSegment instanceof PredicateSegment0){
                boolean begin = predicateSql.length() == 0;
                predicateSql.append(begin?"":" AND ").append(sqlSegment.toSql());

            }else {
                throw new JDQCException(sqlSegment.getClass().getSimpleName()+" can't translate sql segment");
            }
        }
        return predicateSql.toString();
    }
}
