package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.core.base.SQLColumn;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/6/22 21:24
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxySelector {
    Selector getSelector();

   default ProxySelector columns(SQLColumn<?>... columns){
       if(columns != null){
           for (SQLColumn<?> sqlColumn : columns) {
               column(sqlColumn);
           }
       }
       return this;
   }
   default ProxySelector column(SQLColumn<?> column){
       Selector selector = getSelector();
       selector.column(column.getTable(),column.value());
       return this;
   }

   default ProxySelector columnFunc(ProxyColumnPropertyFunction proxyColumnPropertyFunction){
       getSelector().columnFunc(proxyColumnPropertyFunction.getColumn().getTable(), proxyColumnPropertyFunction.getColumnPropertyFunction());
       return this;
   }

   default ProxySelector columnIgnore(SQLColumn<?> column){
       getSelector().columnIgnore(column.getTable(),column.value());
       return this;
   }

   default ProxySelector columnAll(TableAvailable table){
       getSelector().columnAll(table);
       return this;
   }
}
