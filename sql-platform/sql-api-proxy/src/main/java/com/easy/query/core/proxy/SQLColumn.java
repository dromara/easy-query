package com.easy.query.core.proxy;

import com.easy.query.core.proxy.columns.SQLAnyColumn;
import com.easy.query.core.proxy.columns.impl.SQLAnyColumnImpl;
import com.easy.query.core.proxy.extension.ColumnComparableExpression;
import com.easy.query.core.proxy.impl.PropValueConvertColumnImpl;
import com.easy.query.core.proxy.set.DSLColumnSet;
import com.easy.query.core.util.EasyObjectUtil;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * create time 2023/6/22 13:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLColumn<TProxy, TProperty> extends ColumnComparableExpression<TProperty>,
        PropTypeColumn<TProperty>, DSLColumnSet<TProxy,TProperty> {
//    default Object asJsonMap(){
//        return null;
//    }
//    default Object asJsonArray(){
//        return null;
//    }

    default <TRProxy,TRProperty> SQLAnyColumn<TRProxy, TRProperty> asJsonMap() {
        Class<?> propertyType = getPropertyType();
        SQLAnyColumnImpl<TProxy, TProperty> column = new SQLAnyColumnImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), EasyObjectUtil.typeCastNullable(propertyType));
        column._setProxy(castChain());
        return EasyObjectUtil.typeCastNullable(column);
    }
    default <TRProxy,TRProperty> SQLAnyColumn<TRProxy, TRProperty> asAny() {
        Class<?> propertyType = getPropertyType();
        SQLAnyColumnImpl<TProxy, TProperty> column = new SQLAnyColumnImpl<>(this.getEntitySQLContext(), this.getTable(), this.getValue(), EasyObjectUtil.typeCastNullable(propertyType));
        column._setProxy(castChain());
        return EasyObjectUtil.typeCastNullable(column);
    }

    /**
     * t.createTime().valueConvert(d->d.format(DateTimeFormatter.ofPattern("yyyy-MM")))
     * 内存转换,入参可能为null
     * @param converter 内存转换表达式
     * @return 返回一个呗转换后的列表达式
     * @param <TR> 发挥类型
     */
    default <TR> PropValueConvertColumn<TR,TProperty> valueConvert(Function<TProperty,TR> converter) {
        return new PropValueConvertColumnImpl<>(this,converter);
    }

    @Override
    default <TR> SQLColumn<TProxy, TR> asAnyType(Class<TR> clazz) {
        _setPropertyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
    void _setProxy(TProxy tProxy);
}
