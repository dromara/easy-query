package com.easy.query.core.util;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.segment.scec.expression.ColumnConstParameterExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.FormatValueParamExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.segment.scec.expression.SubQueryParamExpressionImpl;
import org.jetbrains.annotations.Nullable;

/**
 * create time 2025/8/13 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyParamExpressionUtil {
    public static ParamExpression getParamExpression( @Nullable Object value) {
        if (value == null) {
            return new FormatValueParamExpressionImpl("NULL");
        } if (value instanceof Query) {
            Query<?> query = (Query<?>) value;
            return new SubQueryParamExpressionImpl(query);
        } else {
            return new ColumnConstParameterExpressionImpl(value);
        }
    }
}
