package com.easy.query.core.expression.parser.core.base.tree;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/10/22 12:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TreeCTEConfigurer {
    /**
     * 最大深度 第一个为0
     * @param limitDeep -1表示不限制(默认)，0表示只查询第一级
     */
    void setLimitDeep(int limitDeep);

    /**
     * 查询方向,默认向下
     * @param up 方向 true向上,false向下(默认)
     */
    void setUp(boolean up);

    /**
     * 是否使用union all
     * @param unionAll true使用unionAll(默认),false使用union
     */
    void setUnionAll(boolean unionAll);

    /**
     * 设置cte表别名默认as_tree_cte
     * @param cteTableName 名称
     */
    void setCTETableName(String cteTableName);

    /**
     * 默认深度列别名
     * @param deepColumnName 默认 cte_deep
     */
    void setDeepColumnName(String deepColumnName);

    void setChildFilter(SQLExpression1<WherePredicate<?>> whereExpression);
}
