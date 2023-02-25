package org.easy.query.core.abstraction;

import org.easy.query.core.enums.SqlKeywordEnum;

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
        for (SqlSegment sqlSegment : sqlSegments) {
            if(sb.length()!=0){
                sb.append(SqlKeywordEnum.DOT.getSql());
            }
            sb.append(sqlSegment.getSql()).append(" = ?");

        }
        return sb.toString();
    }
}
