package com.easy.query.core.proxy;

import com.easy.query.api.proxy.util.EasyPropertyLambdaUtil;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.OnlySelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.proxy.impl.SQLOrderSelectImpl;
import com.easy.query.core.proxy.impl.SQLSelectAsImpl;
import com.easy.query.core.proxy.impl.SQLSelectImpl;
import com.easy.query.core.proxy.impl.draft.SelectToDraftColumn;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.function.Function;

/**
 * create time 2023/12/1 22:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSelectExpression extends TablePropColumn {

    default void orderBy(boolean asc) {
        orderBy(asc, null);
    }

    default void orderBy(boolean asc, @Nullable OrderByModeEnum nullsModeEnum) {
        orderBy(true, asc, nullsModeEnum);
    }

    default void orderBy(boolean condition, boolean asc, @Nullable OrderByModeEnum nullsModeEnum) {
        if (condition) {
            if (asc) {
                asc(nullsModeEnum);
            } else {
                desc(nullsModeEnum);
            }
        }
    }

    /**
     * 使用正序 order by column asc
     */
    default void asc() {
        asc(true);
    }

    /**
     * 使用正序 order by column asc ,{@param condition} 为false那么order by将不会生效也可以用if来进行包裹
     *
     * @param condition 是否生效asc
     */
    default void asc(boolean condition) {
        asc(condition, null);
    }

    default void asc(@Nullable OrderByModeEnum nullsModeEnum) {
        asc(true, nullsModeEnum);
    }

    /**
     * 采用正序排序生成 order by column asc并且可以设置nulls模式比如null排在最前还是最后
     *
     * @param condition
     * @param nullsModeEnum
     */
    default void asc(boolean condition, @Nullable OrderByModeEnum nullsModeEnum) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLOrderSelectImpl(s -> {
                s.setAsc(true);
                if (nullsModeEnum != null) {
                    SQLFunc fx = getEntitySQLContext().getRuntimeContext().fx();
                    SQLFunction orderByNullsModeFunction = fx.orderByNullsMode(this.getTable(), this.getValue(), true, nullsModeEnum);
                    s.func(this.getTable(), orderByNullsModeFunction, false);
                } else {
                    s.column(this.getTable(), this.getValue());
                }
            }));
        }
    }

    default void desc() {
        desc(true);
    }

    default void desc(boolean condition) {
        desc(condition, null);
    }

    default void desc(OrderByModeEnum nullsModeEnum) {
        desc(true, nullsModeEnum);
    }

    /**
     * 采用正序排序生成 order by column desc并且可以设置nulls模式比如null排在最前还是最后
     *
     * @param condition
     * @param nullsModeEnum
     */
    default void desc(boolean condition, OrderByModeEnum nullsModeEnum) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLOrderSelectImpl(s -> {
                s.setAsc(false);

                if (nullsModeEnum != null) {
                    SQLFunc fx = getEntitySQLContext().getRuntimeContext().fx();
                    SQLFunction orderByNullsModeFunction = fx.orderByNullsMode(this.getTable(), this.getValue(), false, nullsModeEnum);
                    s.func(this.getTable(), orderByNullsModeFunction, false);
                } else {
                    s.column(this.getTable(), this.getValue());
                }
            }));
        }
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
    default <TEntity, TR> SQLSelectAsExpression as(Property<TEntity, TR> propertyAlias) {
        return as(EasyPropertyLambdaUtil.getPropertyName(propertyAlias));
    }

    default SQLSelectExpression _concat(SQLSelectExpression... sqlSelectAses) {
        return _concat(true, sqlSelectAses);
    }

    default SQLSelectExpression _concat(boolean condition, SQLSelectExpression... sqlSelectAs) {
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

    default <TProperty> PropTypeColumn<TProperty> toDraft(Class<TProperty> propType) {
        if (PropTypeColumn.class.isAssignableFrom(this.getClass())) {
            PropTypeColumn<? extends TProperty> propTypeColumn = ((PropTypeColumn<?>) this).asAnyType(propType);
            return EasyObjectUtil.typeCastNullable(propTypeColumn);
        }
        return new SelectToDraftColumn<>(this, propType);
    }

}
