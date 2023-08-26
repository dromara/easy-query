package com.easy.query.core.expression.parser.core.base.scec;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/7/29 22:57
 * 原生sql属性表达式
 *
 * @author xuejiaming
 */
public interface SQLNativePropertyExpressionContext {
    /**
     * 当前表的使用的列,自动识别所属表别名
     * @param property 使用哪个列
     * @return
     */
    SQLNativePropertyExpressionContext expression(String property);
   <TEntity> SQLNativePropertyExpressionContext expression(ClientQueryable<TEntity> subQuery);
    /**
     * 当前表的使用的列,自动识别所属表别名
     * @param table 哪张表
     * @param property 哪个列
     * @return
     */
    SQLNativePropertyExpressionContext expression(TableAvailable table, String property);

    /**
     * 参数,将以[?]参数化形式拼接到sql中
     * @param val
     * @return
     */
    SQLNativePropertyExpressionContext value(Object val);

    /**
     * 将以字符串常量形式直接拼接到sql中,并不是参数化
     * 请使用format方法
     * @param formatVal
     * @return
     */
    @Deprecated
   default SQLNativePropertyExpressionContext constValue(Object formatVal){
        return format(formatVal);
    }

    /**
     * 将以字符串常量形式直接拼接到sql中,并不是参数化
     * @param constVal
     * @return
     */
    SQLNativePropertyExpressionContext format(Object constVal);

    /**
     * 如果当前是一个select的片段那么可以独立设置别名,当然也可以通过sqlSegment的片段来拼接
     * @param alias
     * @return
     */
    SQLNativePropertyExpressionContext setAlias(String alias);
}
