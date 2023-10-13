package com.easy.query.api4j.sql.scec;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;

import java.text.MessageFormat;
import java.util.Collection;

/**
 * create time 2023/9/28 21:20
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLNativeLambdaExpressionChain<T1,TChain> {
    TChain expression(Property<T1, ?> property);
    <T2> TChain expression(EntitySQLTableOwner<T2> table, Property<T2, ?> property);

    <TEntity> TChain expression(Queryable<TEntity> subQuery);

    TChain value(Object val);
    /**
     * 将参数以[?,?,?]参数化形式拼接到sql中
     * @param values
     * @return
     * @param <T>
     */
    <T> TChain collection(Collection<T> values);

    TChain format(Object formatVal);

    TChain setAlias(String alias);

    /**
     * 因为默认{@link MessageFormat#format(Object)}会将参数"ifnull(id,'')"改为"ifnull(id,')"
     * 会认为单引号是转义符所以这边需要将单引号全部改为双引号,所以采用和输入文本一样的风格
     * 如果需要参数化将文本单引号改成双引号
     * @return
     */
    TChain keepStyle();

    /**
     * 使用message format 格式化风格 单引号作为转义
     * @return
     */
    TChain messageFormat();
}
