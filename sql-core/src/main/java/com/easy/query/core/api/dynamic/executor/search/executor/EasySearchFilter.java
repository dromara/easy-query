package com.easy.query.core.api.dynamic.executor.search.executor;

import com.easy.query.core.basic.jdbc.parameter.SQLRawParameter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.builder.impl.FilterImpl;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;


/**
 * easy search过滤器
 *
 * @author bkbits
 */
public class EasySearchFilter
        extends FilterImpl {

    public EasySearchFilter(
            QueryRuntimeContext runtimeContext,
            ExpressionContext expressionContext,
            PredicateSegment predicateSegment,
            boolean reverse,
            ValueFilter conditionAcceptAssert
    ) {
        super(runtimeContext, expressionContext, predicateSegment, reverse, conditionAcceptAssert);
    }

    @Override
    public void appendThisPredicate(
            TableAvailable table,
            String property,
            Object val,
            SQLPredicateCompare condition
    ) {
        if (val != null) {
            ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(property);
            if (!columnMetadata.getPropertyType().equals(val.getClass()) &&
                    columnMetadata.getValueConverter() != null) {
                //对值进行特殊处理，避免在缺少转换器时，因类型不匹配报错
                val = new SQLRawParameter(val, null);
            }
        }

        super.appendThisPredicate(table, property, val, condition);
    }
}
