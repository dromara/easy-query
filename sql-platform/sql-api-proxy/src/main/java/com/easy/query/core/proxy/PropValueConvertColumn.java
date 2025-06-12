package com.easy.query.core.proxy;

import com.easy.query.core.basic.extension.conversion.ValueConverter;

import java.util.function.Function;

/**
 * create time 2025/6/12 21:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PropValueConvertColumn<TRProperty, TDBProperty> extends PropTypeColumn<TRProperty> {

    Function<TDBProperty,TRProperty>  getValueConverter();
}
