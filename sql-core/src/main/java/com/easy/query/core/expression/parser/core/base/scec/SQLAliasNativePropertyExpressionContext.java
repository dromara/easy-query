package com.easy.query.core.expression.parser.core.base.scec;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.parser.core.SQLTableOwner;

/**
 * create time 2023/9/12 22:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAliasNativePropertyExpressionContext extends SQLNativePropertyExpressionContext{
    SQLAliasNativePropertyExpressionContext expressionAlias(String property);
    SQLAliasNativePropertyExpressionContext setPropertyAlias(String property);
    /**
     * 当前表的使用的列,自动识别所属表别名
     * @param property 使用哪个列
     * @return
     */
    @Override
    SQLAliasNativePropertyExpressionContext expression(String property);
    /**
     * 当前表的使用的列,自动识别所属表别名
     * @param sqlTableOwner 哪张表
     * @param property 哪个列
     * @return
     */
    @Override
    SQLAliasNativePropertyExpressionContext expression(SQLTableOwner sqlTableOwner, String property);
    @Override
    <TEntity> SQLAliasNativePropertyExpressionContext expression(ClientQueryable<TEntity> subQuery);

    /**
     * 参数,将以[?]参数化形式拼接到sql中
     * @param val
     * @return
     */
    @Override
    SQLAliasNativePropertyExpressionContext value(Object val);

    /**
     * 将以字符串常量形式直接拼接到sql中,并不是参数化
     * 请使用format方法
     * @param formatVal
     * @return
     */
    @Override
    @Deprecated
    default SQLAliasNativePropertyExpressionContext constValue(Object formatVal){
        return format(formatVal);
    }

    /**
     * 将以字符串常量形式直接拼接到sql中,并不是参数化
     * @param formatVal
     * @return
     */
    @Override
    SQLAliasNativePropertyExpressionContext format(Object formatVal);

    /**
     * 如果当前是一个select的片段那么可以独立设置别名,当然也可以通过sqlSegment的片段来拼接
     * @param alias
     * @return
     */
    @Override
    SQLAliasNativePropertyExpressionContext setAlias(String alias);
}
