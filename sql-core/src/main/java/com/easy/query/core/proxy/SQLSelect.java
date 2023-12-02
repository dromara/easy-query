package com.easy.query.core.proxy;

import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.proxy.impl.SQLOrderSelectImpl;
import com.easy.query.core.proxy.impl.SQLSelectAsImpl;

/**
 * create time 2023/12/1 22:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSelect extends TablePropColumn {

    default SQLOrderSelect asc() {
        return asc(true);
    }

   default SQLOrderSelect asc(boolean condition){
       if (condition) {
           return new SQLOrderSelectImpl(s -> {
               s.setAsc(true);
               s.column(this.getTable(), this.value());
           });
       }
       return SQLOrderSelect.empty;
   }

    default SQLOrderSelect desc() {
        return desc(true);
    }

   default SQLOrderSelect desc(boolean condition){
       if (condition) {
           return new SQLOrderSelectImpl(s -> {
               s.setAsc(false);
               s.column(this.getTable(), this.value());
           });
       }
       return SQLOrderSelect.empty;
   }

    default SQLSelectAs as(TablePropColumn propColumn){
        return new SQLSelectAsImpl(s -> {
            s.columnAs(this.getTable(), this.value(), propColumn.value());
        });
    }

    default void accept(Selector f){
        f.column(this.getTable(), this.value());
    }
}
