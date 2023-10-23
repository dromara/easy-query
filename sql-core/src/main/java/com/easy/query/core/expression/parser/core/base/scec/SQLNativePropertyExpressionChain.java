package com.easy.query.core.expression.parser.core.base.scec;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;

import java.text.MessageFormat;
import java.util.Collection;

/**
 * create time 2023/9/28 21:13
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLNativePropertyExpressionChain<TChain> {
    SQLNativeChainExpressionContext getSQLNativeChainExpressionContext();

    /**
     * 当前表的使用的列,自动识别所属表别名
     *
     * @param property 使用哪个列
     * @return
     */
    TChain expression(String property);

    /**
     * 当前表的使用的列,自动识别所属表别名
     *
     * @param sqlTableOwner 哪张表
     * @param property      哪个列
     * @return
     */
    TChain expression(SQLTableOwner sqlTableOwner, String property);

    TChain expression(TableAvailable table, String property);
    TChain columnName(String columnName);
    TChain columnName(TableAvailable table, String columnName);

    <TEntity> TChain expression(ClientQueryable<TEntity> subQuery);

    /**
     * 参数,将以[?]参数化形式拼接到sql中
     *
     * @param val
     * @return
     */
    TChain value(Object val);

    /**
     * 将参数以[?,?,?]参数化形式拼接到sql中
     * @param values
     * @return
     * @param <T>
     */
    <T> TChain collection(Collection<T> values);

    /**
     * 将以字符串常量形式直接拼接到sql中,并不是参数化
     *
     * @param constVal
     * @return
     */
    TChain format(Object constVal);

    /**
     * 如果当前是一个select的片段那么可以独立设置别名,当然也可以通过sqlSegment的片段来拼接
     *
     * @param alias
     * @return
     */
    TChain setAlias(String alias);

    /**
     * 因为默认{@link MessageFormat#format(Object)}会将参数"ifnull(id,'')"改为"ifnull(id,')"
     * 会认为单引号是转义符所以这边需要将单引号全部改为双引号,所以采用和输入文本一样的风格
     * 如果需要参数化将文本单引号改成双引号
     *
     * @return
     */
    TChain keepStyle();

    /**
     * 不将单引号设置为双引号
     *
     * @return
     */
    TChain messageFormat();
}
