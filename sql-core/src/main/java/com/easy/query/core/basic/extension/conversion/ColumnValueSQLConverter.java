package com.easy.query.core.basic.extension.conversion;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/8/5 23:08
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnValueSQLConverter {


    /**
     *
     * @param table
     * @param propertyName
     * @param sqlPropertyConverter
     */
    void columnConverter(TableAvailable table, String propertyName, SQLPropertyConverter sqlPropertyConverter);

    /**
     *
     * @param table
     * @param propertyName
     * @param val
     * @param sqlPropertyConverter
     */
    void valueConverter(TableAvailable table,String propertyName,Object val, SQLPropertyConverter sqlPropertyConverter);
}
