package com.easy.query.core.expression.segment.scec.context.core;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.text.MessageFormat;

/**
 * create time 2023/9/28 20:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLNativeExpressionChain<TChain> extends SQLNativeExpression{
    TChain expression(TableAvailable table, String property);
    <TEntity> TChain expression(Query<TEntity> subQuery);
    TChain value(Object val);

    TChain format(Object formatVal);


    /**
     * 别名 column_name
     * @param alias
     * @return
     */
    TChain setAlias(String alias);

    /**
     * 因为默认{@link MessageFormat#format(Object)}会将参数"ifnull(id,'')"改为"ifnull(id,')"
     * 会认为单引号是转义符所以这边需要将单引号全部改为双引号,所以采用和输入文本一样的风格
     * 如果需要参数化将文本单引号改成双引号
     * @return
     */
    TChain keepStyle();

    /**
     * 不将单引号设置为双引号
     * @return
     */
    TChain messageFormat();
}
