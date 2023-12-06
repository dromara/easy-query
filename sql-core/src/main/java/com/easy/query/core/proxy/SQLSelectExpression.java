package com.easy.query.core.proxy;

import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.impl.SQLOrderSelectImpl;
import com.easy.query.core.proxy.impl.SQLSelectAsImpl;
import com.easy.query.core.proxy.impl.SQLSelectImpl;

/**
 * create time 2023/12/1 22:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSelectExpression extends TablePropColumn {

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

    default SQLSelectAsExpression as(TablePropColumn propColumn) {
        return new SQLSelectAsImpl(s -> {
            s.columnAs(this.getTable(), this.getValue(), propColumn.getValue());
        }, s -> {
            s.columnAs(this.getTable(), this.getValue(), propColumn.getValue());
        });
    }

    default SQLSelectExpression concat(SQLSelectExpression select) {
        return new SQLSelectImpl(x -> {
            accept(x);
            select.accept(x);
        });
    }

    default void accept(Selector s) {
        TableAvailable table = this.getTable();
        String value = this.getValue();
        if (table != null && value != null) {
            s.column(table, value);
        }
    }

    SQLSelectExpression empty = new SQLSelectImpl(x -> {
    });
}
