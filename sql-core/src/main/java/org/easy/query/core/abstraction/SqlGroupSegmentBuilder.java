package org.easy.query.core.abstraction;

import org.easy.query.core.enums.SqlKeywordEnum;

import java.util.List;

/**
 * @FileName: PredicateSqlSegmentBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/13 22:39
 * @Created by xuejiaming
 */
public class SqlGroupSegmentBuilder extends AbstractSqlSegmentBuilder {

    @Override
    public String toSql() {

        StringBuilder sb=new StringBuilder();
        List<SqlSegment> sqlSegments = getSqlSegments();
        for (SqlSegment sqlSegment : sqlSegments) {
            if(sb.length()==0){
                sb.append(SqlKeywordEnum.GROUP_BY.getSql()).append(" ");
            }else{
                sb.append(SqlKeywordEnum.DOT.getSql());
            }
            sb.append(sqlSegment.getSql());

        }
        return sb.toString();
    }
}
