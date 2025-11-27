package com.easy.query.core.func.column;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2023/10/12 14:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnNameExpression extends ColumnExpression {
    @NotNull TableAvailable getTableNotNull();
    @NotNull String getColumnName();
}
