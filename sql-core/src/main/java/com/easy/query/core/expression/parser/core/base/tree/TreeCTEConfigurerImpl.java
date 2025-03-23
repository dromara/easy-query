package com.easy.query.core.expression.parser.core.base.tree;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/10/23 21:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class TreeCTEConfigurerImpl implements TreeCTEConfigurer {
    private final TreeCTEOption treeCTEOption;

    public TreeCTEConfigurerImpl(TreeCTEOption treeCTEOption){

        this.treeCTEOption = treeCTEOption;
    }
    @Override
    public void setLimitDeep(int limitDeep) {
        treeCTEOption.setLimitDeep(limitDeep);
    }

    @Override
    public void setUp(boolean up) {
        treeCTEOption.setUp(up);
    }

    @Override
    public void setUnionAll(boolean unionAll) {
        treeCTEOption.setUnionAll(unionAll);
    }

    @Override
    public void setCTETableName(String cteTableName) {
        treeCTEOption.setCTETableName(cteTableName);
    }

    @Override
    public void setDeepColumnName(String deepColumnName) {
        treeCTEOption.setDeepColumnName(deepColumnName);
    }

    @Override
    public void setChildFilter(SQLExpression1<WherePredicate<?>> whereExpression) {
        treeCTEOption.setChildFilter(whereExpression);
    }
}
