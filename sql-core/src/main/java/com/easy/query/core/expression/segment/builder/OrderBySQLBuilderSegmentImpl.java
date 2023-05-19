package com.easy.query.core.expression.segment.builder;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLKeywordEnum;
import com.easy.query.core.expression.segment.OrderByColumnSegment;
import com.easy.query.core.expression.segment.SQLSegment;

import java.util.List;

/**
 * @Description: 文件说明
 * @Date: 2023/2/13 22:39
 * @author xuejiaming
 */
public class OrderBySQLBuilderSegmentImpl extends AbstractSQLBuilderSegment {

    @Override
    public String toSQL(ToSQLContext sqlParameterCollector) {

        StringBuilder sb = new StringBuilder();
        List<SQLSegment> sqlSegments = getSQLSegments();
        boolean asc = false;
        //从后往前追加
        for (int i = sqlSegments.size() - 1; i >= 0; i--) {
            SQLSegment sqlSegment = sqlSegments.get(i);
            OrderByColumnSegment orderColumnSegment = (OrderByColumnSegment) sqlSegment;

            if (sb.length() != 0) {
                sb.insert(0, SQLKeywordEnum.DOT.toSQL());
            }
            String orderSQL = sqlSegment.toSQL(sqlParameterCollector);
            if (sb.length() == 0) {
                sb.insert(0, orderSQL);

            } else {
                if (asc == orderColumnSegment.isAsc()) {
                    sb.insert(0, orderSQL.substring(0,orderSQL.length()-(asc?4:5)));//4=asc.length+1,5=desc.length+1
                }else{
                    sb.insert(0, orderSQL);
                }
            }
            asc = orderColumnSegment.isAsc();
        }
        return sb.toString();
    }

    @Override
    public SQLBuilderSegment cloneSQLBuilder() {
        OrderBySQLBuilderSegmentImpl orderBySQLBuilderSegment = new OrderBySQLBuilderSegmentImpl();
        copyTo(orderBySQLBuilderSegment);
        return orderBySQLBuilderSegment;
    }
}
