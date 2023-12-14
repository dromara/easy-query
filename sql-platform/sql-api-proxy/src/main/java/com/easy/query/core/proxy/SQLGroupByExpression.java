package com.easy.query.core.proxy;

import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.proxy.impl.SQLGroupSelectImpl;

/**
 * create time 2023/12/2 18:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLGroupByExpression extends TablePropColumn {

    default SQLGroupByExpression _thenBy(SQLGroupByExpression sqlGroupSelect){
        return _thenBy(true,sqlGroupSelect);
    }
    default SQLGroupByExpression _thenBy(boolean condition, SQLGroupByExpression sqlGroupSelect){
        if(condition){
            return new SQLGroupSelectImpl(x->{
                accept(x);
                sqlGroupSelect.accept(x);
            });
        }
        return SQLGroupByExpression.empty;
    }
    default void accept(GroupSelector s) {
        s.column(this.getTable(), this.getValue());
    }

    SQLGroupByExpression empty = new SQLGroupSelectImpl(s -> {
    });
}
