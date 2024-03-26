package com.easy.query.core.proxy;

import com.easy.query.api.proxy.util.EasyPropertyLambdaUtil;
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

/**
 * create time 2023/12/1 22:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSelectExpression extends TablePropColumn{

    default void asc() {
         asc(true);
    }

    default void asc(boolean condition) {
        asc(condition,null);
    }
    default void asc(OrderByModeEnum nullsModeEnum) {
         asc(true,nullsModeEnum);
    }

    default void asc(boolean condition, OrderByModeEnum nullsModeEnum) {
        if (condition) {
           getEntitySQLContext().accept(new SQLOrderSelectImpl(s -> {
               s.setAsc(true);

               if(nullsModeEnum!=null){
                   SQLFunc fx = getEntitySQLContext().getRuntimeContext().fx();
                   SQLFunction orderByNullsModeFunction = fx.orderByNullsMode(this.getValue(), true, nullsModeEnum);
                   s.func(this.getTable(), orderByNullsModeFunction,false);
               }else{
                   s.column(this.getTable(), this.getValue());
               }
           }));
        }
    }
//
//    default void nullsLast() {
//        nullsLast(true);
//    }
//
//    default void nullsLast(boolean condition) {
//        if (condition) {
//           getEntitySQLContext().accept(new SQLOrderSelectImpl(s -> {
//               s.sqlNativeSegment("NULLS LAST",c->c.expression());
//               s.setAsc(true);
//               s.column(this.getTable(), this.getValue());
//           }));
//        }
//    }

    default void desc() {
        desc(true);
    }

    default void desc(boolean condition) {
        desc(condition,null);
    }
    default void desc(OrderByModeEnum nullsModeEnum) {
        desc(true,nullsModeEnum);
    }

    default void desc(boolean condition, OrderByModeEnum nullsModeEnum) {
        if (condition) {
            getEntitySQLContext().accept(new SQLOrderSelectImpl(s -> {
                s.setAsc(false);

                if(nullsModeEnum!=null){
                    SQLFunc fx = getEntitySQLContext().getRuntimeContext().fx();
                    SQLFunction orderByNullsModeFunction = fx.orderByNullsMode(this.getValue(), false, nullsModeEnum);
                    s.func(this.getTable(), orderByNullsModeFunction,false);
                }else{
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
    default <TEntity,TR> SQLSelectAsExpression as(Property<TEntity,TR> propertyAlias) {
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

    default <TProperty> PropTypeColumn<TProperty> toDraft(Class<TProperty> propType){
        if(PropTypeColumn.class.isAssignableFrom(this.getClass())){
            PropTypeColumn<? extends TProperty> propTypeColumn = ((PropTypeColumn<?>) this).setPropertyType(propType);
            return EasyObjectUtil.typeCastNullable(propTypeColumn);
        }
        return new SelectToDraftColumn<>(this,propType);
    }

}
