package com.easy.query.test.conversion;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.SQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.func.def.enums.MathMethodEnum;
import com.easy.query.core.func.def.enums.NumberCalcEnum;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2024/3/28 21:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class UserAgeColumnValueSQLConverter implements ColumnValueSQLConverter {
    @Override
    public void selectColumnConvert(TableAvailable table, ColumnMetadata columnMetadata, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext) {
        SQLFunc fx = runtimeContext.fx();
        SQLFunction durationDay = fx.duration(x->x.sqlFunc(fx.now()).column(table,"birthday"), DateTimeDurationEnum.Days);
        SQLFunction sqlFunction = fx.numberCalc(x -> x.sqlFunc(durationDay).value(365), NumberCalcEnum.NUMBER_DEVIDE);
        SQLFunction ageSQLFunction = fx.math(x -> x.sqlFunc(sqlFunction), MathMethodEnum.Ceiling);
        String sqlSegment = ageSQLFunction.sqlSegment(table);

        sqlPropertyConverter.sqlNativeSegment(sqlSegment,context->{
            ageSQLFunction.consume(context.getSQLNativeChainExpressionContext());
            context.setAlias(columnMetadata.getName());
            //.constValue(dialect.getQuoteName(columnMetadata.getName()));//如果这边也是用变量就会导致join下不是别名而是带具体表的列比如:t.`phone`
        });
    }

    @Override
    public void propertyColumnConvert(TableAvailable table, ColumnMetadata columnMetadata, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext) {
        SQLFunc fx = runtimeContext.fx();
        SQLFunction durationDay = fx.duration(x->x.sqlFunc(fx.now()).column(table,"birthday"), DateTimeDurationEnum.Days);
        SQLFunction sqlFunction = fx.numberCalc(x -> x.sqlFunc(durationDay).value(365), NumberCalcEnum.NUMBER_DEVIDE);
        SQLFunction ageSQLFunction = fx.math(x -> x.sqlFunc(sqlFunction), MathMethodEnum.Ceiling);
        String sqlSegment = ageSQLFunction.sqlSegment(table);
        sqlPropertyConverter.sqlNativeSegment(sqlSegment,context->{
            ageSQLFunction.consume(context.getSQLNativeChainExpressionContext());
            //.constValue(dialect.getQuoteName(columnMetadata.getName()));//如果这边也是用变量就会导致join下不是别名而是带具体表的列比如:t.`phone`
        });
    }

    @Override
    public void valueConvert(TableAvailable table, ColumnMetadata columnMetadata, SQLParameter sqlParameter, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext,boolean isCompareValue) {
        sqlPropertyConverter.sqlNativeSegment("{0}",context->{
            context.value(sqlParameter);
        });
    }
}
