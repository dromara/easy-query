package com.easy.query.core.basic.extension.conversion;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/8/5 23:08
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnValueSQLConverter {


    /**
     * select查询
     * @param table
     * @param propertyName
     * @param sqlPropertyConverter
     */
    void columnConverter(TableAvailable table, String propertyName, SQLPropertyConverter sqlPropertyConverter);

    /**
     * insert update entity
     * udpate set
     * where
     * @param table
     * @param propertyName
     * @param sqlParameter
     * @param sqlPropertyConverter
     */
    void valueConverter(TableAvailable table, String propertyName, SQLParameter sqlParameter, SQLPropertyConverter sqlPropertyConverter);
}
