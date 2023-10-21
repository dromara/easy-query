package com.easy.query.core.expression.segment.scec.context.core;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;

import java.util.List;

/**
 * create time 2023/9/28 20:51
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLNativeExpression {
    List<ParamExpression> getExpressions();

    String getAlias();

    /**
     * 保持输入参数格式化时的单引号(true 默认会将单引号替换为双引号 replace("'", "''"))
     * @return
     */
    boolean isKeepStyle();

    /**
     * 默认属性
     * @return
     */
    String getPropertyOrNull();

    /**
     * 默认表
     * @return
     */
    TableAvailable getTableOrNull();
}
