package com.easy.query.core.proxy;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.OnlySelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.impl.SQLOrderSelectImpl;
import com.easy.query.core.proxy.impl.SQLSelectAsImpl;
import com.easy.query.core.proxy.impl.SQLSelectImpl;
import com.easy.query.core.proxy.set.DSLUpdateSet;
import com.easy.query.core.proxy.sql.Select;

/**
 * create time 2023/12/1 22:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSelectExpression extends TablePropColumn, DSLUpdateSet {

    default SQLOrderByExpression asc() {
        return asc(true);
    }

    default SQLOrderByExpression asc(boolean condition) {
        if (condition) {
            return new SQLOrderSelectImpl(s -> {
                s.setAsc(true);
                s.column(this.getTable(), this.getValue());
            });
        }
        return SQLOrderByExpression.empty;
    }

    default SQLOrderByExpression desc() {
        return desc(true);
    }

    default SQLOrderByExpression desc(boolean condition) {
        if (condition) {
            return new SQLOrderSelectImpl(s -> {
                s.setAsc(false);
                s.column(this.getTable(), this.getValue());
            });
        }
        return SQLOrderByExpression.empty;
    }


    /**
     * 设置别名
     *
     * @param propColumn
     * @return
     */
    default SQLSelectAsExpression as(TablePropColumn propColumn) {
        return as(propColumn.getValue());
    }

    default SQLSelectAsExpression as(String propertyAlias) {
        return new SQLSelectAsImpl(s -> {
            s.columnAs(this.getTable(), this.getValue(), propertyAlias);
        }, s -> {
            s.columnAs(this.getTable(), this.getValue(), propertyAlias);
        }, s -> {
            throw new UnsupportedOperationException();
        });
    }

    default SQLSelectExpression concat(SQLSelectExpression... sqlSelectAses) {
        return concat(true, sqlSelectAses);
    }

    default SQLSelectExpression concat(boolean condition, SQLSelectExpression... sqlSelectAs) {
        if (condition) {
            SQLSelectExpression expression = Select.of(sqlSelectAs);
            return new SQLSelectImpl(x -> {
                accept(x);
                expression.accept(x);
            });
        }
        return SQLSelectExpression.empty;
    }

    default void accept(Selector s) {
        TableAvailable table = this.getTable();
        String value = this.getValue();
        if (table != null && value != null) {
            s.column(table, value);
        }
    }
    default void accept(AsSelector s) {
        TableAvailable table = this.getTable();
        String value = this.getValue();
        if (table != null && value != null) {
            s.column(table, value);
        }
    }

    default void accept(OnlySelector s) {
        s.column(getTable(), getValue());
    }

    SQLSelectExpression empty = new SQLSelectImpl(x -> {
    });

}
