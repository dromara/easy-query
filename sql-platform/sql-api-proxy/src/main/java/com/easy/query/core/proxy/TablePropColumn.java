package com.easy.query.core.proxy;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.expression.parser.core.PropColumn;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * create time 2023/7/11 22:05
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TablePropColumn extends PropColumn, SQLTableOwner, EntitySQLContextAvailable {
    default Object _toFunctionSerializeValue(Object value) {
        TableAvailable tableOrNull = this.getTable();
        String propOrNull = this.getValue();
        if (tableOrNull != null && propOrNull != null) {
            ColumnMetadata columnMetadata = tableOrNull.getEntityMetadata().getColumnNotNull(propOrNull);
            ValueConverter<?, ?> valueConverter = columnMetadata.getValueConverter();
            return valueConverter.serialize(EasyObjectUtil.typeCastNullable(value), columnMetadata);
        }
        return value;
    }
    default Collection<?> _toFunctionSerializeValues(Collection<?> values) {
        if(EasyCollectionUtil.isNotEmpty(values)){
            TableAvailable tableOrNull = this.getTable();
            String propOrNull = this.getValue();
            if (tableOrNull != null && propOrNull != null) {
                ColumnMetadata columnMetadata = tableOrNull.getEntityMetadata().getColumnNotNull(propOrNull);
                ValueConverter<?, ?> valueConverter = columnMetadata.getValueConverter();
                List<Object> objects = new ArrayList<>(values.size());
                for (Object value : values) {
                    Object serialize = valueConverter.serialize(EasyObjectUtil.typeCastNullable(value), columnMetadata);
                    objects.add(serialize);
                }
                return objects;
            }
        }
        return values;
    }
}
