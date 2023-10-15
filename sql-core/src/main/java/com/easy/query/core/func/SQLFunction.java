package com.easy.query.core.func;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;

/**
 * create time 2023/10/5 22:03
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFunction {
    /**
     * 获取sql片段, @param defaultTable
     * @param defaultTable 默认单属性时候使用的表
     * @return
     */
    String sqlSegment(TableAvailable defaultTable);
    int paramMarks();
//    void setAlias(String alias);
//    void setPropertyAlias(String propertyAlias);
    void consume(SQLNativeChainExpressionContext context);
}
