package com.easy.query.core.proxy.func.column;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.parser.core.base.SimpleSQLTableOwner;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnFuncSelector;
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
    ColumnFuncSelector getColumnConcatSelector();
   default ProxyColumnFuncSelector keepStyle(){
       getColumnConcatSelector().keepStyle();
       return this;
   }
   default <TProxy,T> ProxyColumnFuncSelector expression(SQLColumn<TProxy, T> sqlColumn){
       return column(sqlColumn);
   }
   default ProxyColumnFuncSelector expression(Query<?> subQuery){
       return subQuery(subQuery);
   }
   default ProxyColumnFuncSelector expression(DSLSQLFunctionAvailable dslsqlFunctionAvailable){
       getColumnConcatSelector().sqlFuncExpression(dslsqlFunctionAvailable.getTable(), dslsqlFunctionAvailable.func());
       return this;
   }
   default ProxyColumnFuncSelector expression(SQLFunction sqlFunction){
       return sqlFunc(sqlFunction);
   }
   default <T> ProxyColumnFuncSelector collection(Collection<T> collections){
       getColumnConcatSelector().collection(collections);
       return this;
   }
   default <TProxy,T> ProxyColumnFuncSelector column(SQLColumn<TProxy, T> sqlColumn){
       getColumnConcatSelector().column(new SimpleSQLTableOwner(sqlColumn.getTable()),sqlColumn.getValue());
       return this;
   }
   default ProxyColumnFuncSelector columns(SQLColumn<?, ?>... sqlColumns){
       if(EasyArrayUtil.isNotEmpty(sqlColumns)){
           for (SQLColumn<?, ?> sqlColumn : sqlColumns) {
               getColumnConcatSelector().column(new SimpleSQLTableOwner(sqlColumn.getTable()),sqlColumn.getValue());
           }
       }
       return this;
   }
    default ProxyColumnFuncSelector value(Object val){
        getColumnConcatSelector().value(val);
        return this;
    }
    default ProxyColumnFuncSelector format(Object valFormat){
        getColumnConcatSelector().format(valFormat);
        return this;
    }
    default ProxyColumnFuncSelector sqlFunc(SQLFunction sqlFunction){
        getColumnConcatSelector().sqlFunc(sqlFunction);
        return this;
    }
    default ProxyColumnFuncSelector subQuery(Query<?> subQuery){
        getColumnConcatSelector().subQuery(subQuery);
        return this;
    }

//    default ProxyColumnFuncSelector sqlParameter(SQLParameter sqlParameter){
//        getColumnConcatSelector().sqlParameter(sqlParameter);
//       return this;
//    }
}
