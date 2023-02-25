package org.easy.query.core.basic.sql.segment.builder;

import org.easy.query.core.basic.sql.segment.segment.SqlSegment;
import org.easy.query.core.enums.SqlKeywordEnum;
import org.easy.query.core.basic.sql.segment.segment.OrderColumnSegment;

import java.util.List;

/**
 * @FileName: PredicateSqlSegmentBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/13 22:39
 * @Created by xuejiaming
 */
public class OrderBySqlSegmentBuilder extends AbstractSqlSegmentBuilder {

    @Override
    public String toSql() {

        StringBuilder sb = new StringBuilder();
        List<SqlSegment> sqlSegments = getSqlSegments();
        boolean asc = false;
        //从后往前追加
        for (int i = sqlSegments.size() - 1; i >= 0; i--) {
            SqlSegment sqlSegment = sqlSegments.get(i);
            OrderColumnSegment orderColumnSegment = (OrderColumnSegment) sqlSegment;

            if (sb.length() != 0) {
                sb.insert(0, SqlKeywordEnum.DOT.getSql());
            }
            String orderSql = sqlSegment.getSql();
            if (sb.length() == 0) {
                sb.insert(0, orderSql);

            } else {
                if (asc == orderColumnSegment.isAsc()) {
                    sb.insert(0, orderSql.substring(0,orderSql.length()-(asc?4:5)));//4=asc.length+1,5=desc.length+1
                }else{
                    sb.insert(0, orderSql);
                }
            }
            asc = orderColumnSegment.isAsc();
        }
        return sb.toString();
    }
}
