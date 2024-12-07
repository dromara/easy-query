package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.expression.visitor.TableVisitor;

/**
 * create time 2023/7/29 19:38
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ParamExpression {
    void accept(TableVisitor visitor);
}
