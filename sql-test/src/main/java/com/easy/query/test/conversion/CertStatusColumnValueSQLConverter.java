package com.easy.query.test.conversion;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.SQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2024/5/1 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public class CertStatusColumnValueSQLConverter  implements ColumnValueSQLConverter {
    @Override
    public boolean isRealColumn() {
        //他不是一个真实存在的列所以返回false
        return false;
    }

    @Override
    public boolean isMergeSubQuery() {
        //期间没有用到别的数据库所以是false
        return false;
    }

    @Override
    public void selectColumnConvert(TableAvailable table, ColumnMetadata columnMetadata, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext) {
        SQLFunc fx = runtimeContext.fx();
        //计算出两者天数差值 前面是大的时间后面是小的时间
        SQLFunction durationDay = fx.duration(x->x.column(table,"invalidTime").sqlFunc(fx.now()), DateTimeDurationEnum.Days);
        //计算出来的时间如果大于30天表示是正常的,大于等于0表示临期的小于0表示过期的
        SQLFunction sqlFunction = fx.anySQLFunction("(CASE WHEN {0}>30 THEN 1 WHEN {0}>=0 THEN 2 ELSE 3 END)", c -> {
            c.sqlFunc(durationDay);
        });
        String sqlSegment = sqlFunction.sqlSegment(table);
        sqlPropertyConverter.sqlNativeSegment(sqlSegment,context->{
            sqlFunction.consume(context.getSQLNativeChainExpressionContext());
            context.setAlias(columnMetadata.getName());//因为是返回所以需要设置别名
        });
    }

    @Override
    public void propertyColumnConvert(TableAvailable table, ColumnMetadata columnMetadata, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext) {
        SQLFunc fx = runtimeContext.fx();
        SQLFunction durationDay = fx.duration(x->x.column(table,"invalidTime").sqlFunc(fx.now()), DateTimeDurationEnum.Days);
        SQLFunction sqlFunction = fx.anySQLFunction("(CASE WHEN {0}>30 THEN 1 WHEN {0}>=0 THEN 2 ELSE 3 END)", c -> {
            c.sqlFunc(durationDay);
        });
        String sqlSegment = sqlFunction.sqlSegment(table);
        sqlPropertyConverter.sqlNativeSegment(sqlSegment,context->{
            sqlFunction.consume(context.getSQLNativeChainExpressionContext());
            //当做属性用所以不需要别名
        });
    }

    @Override
    public void valueConvert(TableAvailable table, ColumnMetadata columnMetadata, SQLParameter sqlParameter, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext, boolean isCompareValue) {
        //因为不做插入和修改所以这个status属性被用作条件比较的时候条件值还是原先的值
        sqlPropertyConverter.sqlNativeSegment("{0}",context->{
            context.value(sqlParameter);
        });
    }
}