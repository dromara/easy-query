package com.easy.query.core.proxy.func.column;

import com.easy.query.api.proxy.util.EasyParamExpressionUtil;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.base.SimpleSQLTableOwner;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import com.easy.query.core.util.EasyArrayUtil;

import java.util.Collection;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyColumnFuncSelector {
    ColumnFuncSelector getColumnFuncSelector();

    default ProxyColumnFuncSelector parameters(Object... params) {
        if (params != null) {
            for (Object param : params) {
                EasyParamExpressionUtil.acceptParameters(getColumnFuncSelector(), param);
            }
        }
        return this;
    }

    default <T> ProxyColumnFuncSelector expression(PropTypeColumn<T> sqlColumn) {
        PropTypeColumn.columnFuncSelector(getColumnFuncSelector(), sqlColumn);
        return this;
    }

    default ProxyColumnFuncSelector expression(Query<?> subQuery) {
        return subQuery(subQuery);
    }

    default ProxyColumnFuncSelector expression(SQLFunction sqlFunction) {
        return sqlFunc(sqlFunction);
    }

    default ColumnFuncSelector expression(PredicateSegment sqlSegment) {
        return getColumnFuncSelector().expression(sqlSegment);
    }

    default <T> ProxyColumnFuncSelector collection(Collection<T> collections) {
        getColumnFuncSelector().collection(collections);
        return this;
    }

    default <TProxy, T> ProxyColumnFuncSelector column(SQLColumn<TProxy, T> sqlColumn) {
        getColumnFuncSelector().column(sqlColumn.getTable(), sqlColumn.getValue());
        return this;
    }


    default ProxyColumnFuncSelector columns(SQLColumn<?, ?>... sqlColumns) {
        if (EasyArrayUtil.isNotEmpty(sqlColumns)) {
            for (SQLColumn<?, ?> sqlColumn : sqlColumns) {
                getColumnFuncSelector().column(sqlColumn.getTable(), sqlColumn.getValue());
            }
        }
        return this;
    }

    default ProxyColumnFuncSelector value(Object val) {
        getColumnFuncSelector().value(val);
        return this;
    }

    default ProxyColumnFuncSelector format(Object valFormat) {
        getColumnFuncSelector().format(valFormat);
        return this;
    }

    default ProxyColumnFuncSelector sqlFunc(SQLFunction sqlFunction) {
        getColumnFuncSelector().sqlFunc(sqlFunction);
        return this;
    }

    default ProxyColumnFuncSelector subQuery(Query<?> subQuery) {
        getColumnFuncSelector().subQuery(subQuery);
        return this;
    }

//    default ProxyColumnFuncSelector sqlParameter(SQLParameter sqlParameter){
//        getColumnConcatSelector().sqlParameter(sqlParameter);
//       return this;
//    }
}
