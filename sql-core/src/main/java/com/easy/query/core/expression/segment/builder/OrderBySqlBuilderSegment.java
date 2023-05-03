package com.easy.query.core.expression.segment.builder;

import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.enums.SqlKeywordEnum;
import com.easy.query.core.expression.segment.OrderByColumnSegment;
import com.easy.query.core.expression.segment.OrderColumnSegmentImpl;
import com.easy.query.core.expression.segment.SqlSegment;

import java.util.List;

/**
 * @FileName: PredicateSqlSegmentBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/13 22:39
 * @author xuejiaming
 */
public class OrderBySqlBuilderSegment extends AbstractSqlBuilderSegment {

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {

        StringBuilder sb = new StringBuilder();
        List<SqlSegment> sqlSegments = getSqlSegments();
        boolean asc = false;
        //从后往前追加
        for (int i = sqlSegments.size() - 1; i >= 0; i--) {
            SqlSegment sqlSegment = sqlSegments.get(i);
            OrderByColumnSegment orderColumnSegment = (OrderByColumnSegment) sqlSegment;

            if (sb.length() != 0) {
                sb.insert(0, SqlKeywordEnum.DOT.toSql());
            }
            String orderSql = sqlSegment.toSql(sqlParameterCollector);
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

    @Override
    public SqlBuilderSegment cloneSqlBuilder() {
        OrderBySqlBuilderSegment orderBySqlBuilderSegment = new OrderBySqlBuilderSegment();
        copyTo(orderBySqlBuilderSegment);
        return orderBySqlBuilderSegment;
    }
}
