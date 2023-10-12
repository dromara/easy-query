package com.easy.query.core.func.def;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.concat.ConcatColumnExpression;
import com.easy.query.core.func.concat.ConcatExpression;
import com.easy.query.core.func.concat.ConcatFormatExpression;
import com.easy.query.core.func.concat.ConcatValueExpression;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2023/10/11 22:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class ConcatSQLFunction extends AbstractSQLFunction {

    private final List<ConcatExpression> concatExpressions;

    public ConcatSQLFunction(List<ConcatExpression> concatExpressions) {
        if (EasyCollectionUtil.isEmpty(concatExpressions)) {
            throw new IllegalArgumentException("ConcatSQLFunction columns empty");
        }
        this.concatExpressions = concatExpressions;
    }

    @Override
    public String sqlSegment() {
        Iterable<String> params = EasyCollectionUtil.select(concatExpressions, (t, i) -> "{" + i + "}");
        return String.format("CONCAT(%s)", String.join(",", params));
    }

    @Override
    public int paramMarks() {
        return concatExpressions.size();
    }

    @Override
    protected void consume0(SQLNativeChainExpressionContext context) {
        for (ConcatExpression concatExpression : concatExpressions) {
            if(concatExpression instanceof ConcatColumnExpression){
                ConcatColumnExpression concatColumnExpression = (ConcatColumnExpression) concatExpression;
                TableAvailable tableOrNull = concatColumnExpression.getTableOrNull();
                if(tableOrNull==null){
                    context.expression(concatColumnExpression.getProperty());
                } else {
                    context.expression(tableOrNull, concatColumnExpression.getProperty());
                }
            }else if(concatExpression instanceof ConcatValueExpression){
                ConcatValueExpression concatValueExpression = (ConcatValueExpression) concatExpression;
                context.value(concatValueExpression.getValue());

            }else if(concatExpression instanceof ConcatFormatExpression){
                ConcatFormatExpression concatFormatExpression = (ConcatFormatExpression) concatExpression;
                context.format(concatFormatExpression.getFormat());
            }else{
                throw new UnsupportedOperationException(EasyClassUtil.getInstanceSimpleName(concatExpression));
            }
        }
    }
}
