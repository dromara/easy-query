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
 * create time 2024/8/22 17:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class Blog2StarToStringColumnValueSQLConverter implements ColumnValueSQLConverter {
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
        SQLFunction starToStrFunction = fx.cast("star", String.class);
        sqlPropertyConverter.sqlNativeSegment(table,starToStrFunction,columnMetadata.getName());
    }

    @Override
    public void propertyColumnConvert(TableAvailable table, ColumnMetadata columnMetadata, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext) {
        SQLFunc fx = runtimeContext.fx();
        SQLFunction starToStrFunction = fx.cast("star", String.class);
        sqlPropertyConverter.sqlNativeSegment(table,starToStrFunction,null);
    }

    @Override
    public void valueConvert(TableAvailable table, ColumnMetadata columnMetadata, SQLParameter sqlParameter, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext, boolean isCompareValue) {
        //因为不做插入和修改所以这个star属性被用作条件比较的时候条件值还是原先的值
        sqlPropertyConverter.sqlNativeSegment("{0}",context->{
            context.value(sqlParameter);
        });
    }
}
