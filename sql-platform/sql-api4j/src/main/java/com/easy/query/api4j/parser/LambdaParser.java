package com.easy.query.api4j.parser;

import com.easy.query.core.expression.lambda.Property;

/**
 * create time 2023/11/7 10:33
 * 文件说明
 *
 * @author xuejiaming
 */
public interface LambdaParser {
    <T> String getPropertyName(Property<T, ?> property);
}
