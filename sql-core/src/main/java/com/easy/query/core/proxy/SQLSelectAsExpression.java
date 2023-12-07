package com.easy.query.core.proxy;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.impl.SQLSelectAsImpl;

import java.util.Collection;

/**
 * create time 2023/12/1 22:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSelectAsExpression extends SQLSelectExpression, SQLGroupByExpression {
    default SQLSelectAsExpression concat(SQLSelectAsExpression sqlSelectAs) {
        return concat(true,sqlSelectAs);
    }
    default SQLSelectAsExpression concat(boolean condition,SQLSelectAsExpression sqlSelectAs) {
        if(condition){
            return new SQLSelectAsImpl(x -> {
                accept(x);
                sqlSelectAs.accept(x);
            }, x -> {
                accept(x);
                sqlSelectAs.accept(x);
            },x->{
                accept(x);
                sqlSelectAs.accept(x);
            });
        }
        return SQLSelectAsExpression.empty;
    }

    default void accept(AsSelector s) {
        s.column(this.getTable(), this.getValue());
    }

    SQLSelectAsExpression empty = new SQLSelectAsImpl(s -> {
    }, s -> {
    }, s -> {
    });

    static SQLSelectAsExpression createDefault(TableAvailable table, String property) {
        return new SQLSelectAsImpl(x -> {
            x.column(table, property);
        }, x -> {
            x.column(table, property);
        }, x -> {
            x.column(table, property);
        },table,property);
    }

    static <TProxy> SQLSelectAsExpression createColumnExclude(SQLColumn<TProxy, ?> column, Collection<SQLColumn<TProxy, ?>> ignoreColumns) {
        return new SQLSelectAsImpl(x -> {
            x.column(column.getTable(), column.getValue());
            for (SQLColumn<TProxy, ?> ignoreColumn : ignoreColumns) {
                x.columnIgnore(ignoreColumn.getTable(), ignoreColumn.getValue());
            }
        }, x -> {
            x.column(column.getTable(), column.getValue());
            for (SQLColumn<TProxy, ?> ignoreColumn : ignoreColumns) {
                x.columnIgnore(ignoreColumn.getTable(), ignoreColumn.getValue());
            }
        }, x -> {
            x.column(column.getTable(), column.getValue());
            for (SQLColumn<TProxy, ?> ignoreColumn : ignoreColumns) {
                x.columnIgnore(ignoreColumn.getTable(), ignoreColumn.getValue());
            }
        });
    }
}
