package com.easy.query.core.proxy;

import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.impl.def.EntityResultColumnMetadata;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.Setter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.proxy.columns.impl.EmptyPropTypeColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLStringColumnImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.util.Collection;

/**
 * create time 2023/12/18 22:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PropTypeColumn<TProperty> extends PropTypeSetColumn<TProperty> {

    static <TProp> PropTypeColumn<TProp> createEmpty() {
        return new EmptyPropTypeColumnImpl<>();
    }

    @Override
    <TR> PropTypeColumn<TR> asAnyType(Class<TR> clazz);

    static <TR> void selectColumn(AsSelector asSelector, PropTypeColumn<TR> column) {

        if (column instanceof DSLSQLFunctionAvailable) {
            SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) column).func().apply(asSelector.getRuntimeContext().fx());
            asSelector.sqlFunc(column.getTable(), sqlFunction);
        } else {
            column.accept(asSelector);
        }
    }

    static void acceptAnyValue(ColumnFuncSelector columnFuncSelector, Object val) {
        if (val instanceof PropTypeColumn) {
            PropTypeColumn<?> column = (PropTypeColumn<?>) val;
            if (column instanceof DSLSQLFunctionAvailable) {
                SQLFunc fx = column.getEntitySQLContext().getRuntimeContext().fx();
                SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) column).func().apply(fx);
                columnFuncSelector.sqlFunc(column.getTable(), sqlFunction);
            } else {
                columnFuncSelector.column(column.getTable(), column.getValue());
            }
        } else if (val instanceof SQLFunction) {
            columnFuncSelector.sqlFunc((SQLFunction) val);
        } else if (val instanceof PredicateSegment) {
            columnFuncSelector.expression((PredicateSegment) val);
        } else if (val instanceof SQLSegment) {
            columnFuncSelector.sql((SQLSegment) val);
        } else if (val instanceof Collection) {
            columnFuncSelector.collection((Collection<?>) val);
        } else {
            columnFuncSelector.value(val);
        }
    }

    static <TR> void columnFuncSelector(ColumnFuncSelector columnFuncSelector, PropTypeColumn<TR> column) {
        if (column instanceof DSLSQLFunctionAvailable) {
            SQLFunc fx = column.getEntitySQLContext().getRuntimeContext().fx();
            SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) column).func().apply(fx);
            columnFuncSelector.sqlFunc(column.getTable(), sqlFunction);
        } else {
            columnFuncSelector.column(column.getTable(), column.getValue());
        }
    }

    static void sqlNativeSelectColumn(SQLNativeExpressionContext sqlNativeExpressionContext, PropTypeColumn<?> column) {
        if (column instanceof DSLSQLFunctionAvailable) {
            SQLFunc fx = column.getEntitySQLContext().getRuntimeContext().fx();
            SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) column).func().apply(fx);
            sqlNativeExpressionContext.sqlFunction(sqlFunction);
        } else {
            sqlNativeExpressionContext.expression(column.getTable(), column.getValue());
        }
    }

    static void columnFuncSetter(Setter setter, TableAvailable table, String property, PropTypeColumn<?> column) {
        if (column instanceof DSLSQLFunctionAvailable) {
            SQLFunc fx = column.getEntitySQLContext().getRuntimeContext().fx();
            SQLFunction sqlFunction = ((DSLSQLFunctionAvailable) column).func().apply(fx);
            setter.setFunc(table, property, sqlFunction);
        } else {
            setter.setWithColumn(table, property, column.getValue());
        }
    }

    default ResultColumnMetadata getTableResultColumnMetadataOrNull(int index) {
        PropTypeColumn<TProperty> column = this;
        if (column.getTable() != null) {
            EntityMetadata entityMetadata = column.getTable().getEntityMetadata();
            ColumnMetadata columnMetadata = entityMetadata.getColumnOrNull(column.getValue());
            if (columnMetadata != null) {
                if (columnMetadata.getValueConverter() != null) {
                    return new EntityResultColumnMetadata(index, entityMetadata, columnMetadata);
                }
            }
        }
        return null;
//        return new BasicResultColumnMetadata(column.getPropertyType(), null, new BasicJdbcProperty(index, column.getPropertyType()));
    }
}
