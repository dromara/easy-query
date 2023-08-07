package com.easy.query.core.basic.extension.conversion;

import com.easy.query.core.expression.parser.core.base.core.SQLPropertyNative;

/**
 * create time 2023/8/5 23:08
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnValueSQLConverter {


    /**
     *
     * @param propertyName
     * @param sqlPropertyNative
     */
    void columnConverter(String propertyName, SQLPropertyNative<Object> sqlPropertyNative);

    /**
     *
     * @param propertyName
     * @param val
     * @param sqlPropertyNative
     */
    void valueConverter(String propertyName,Object val, SQLPropertyNative<Object> sqlPropertyNative);
}
