package com.easy.query.mysql.func;

import com.easy.query.core.common.LikeFunctionTemplateGenerator;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncValueExpression;

import java.util.List;
import java.util.function.Function;

/**
 * create time 2026/10/4 15:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQLLikeFunctionTemplateGenerator extends LikeFunctionTemplateGenerator {
    public MySQLLikeFunctionTemplateGenerator(List<ColumnExpression> columnExpressions, SQLLikeEnum sqlLikeEnum, Function<ColumnExpression, ColumnFuncValueExpression> columnFuncValueExpressionFunction, String escapeStartsWith, String escapeEndsWith, String escapeContainsWith, String startsWith, String endsWith, String containsWith) {
        super(columnExpressions, sqlLikeEnum, columnFuncValueExpressionFunction, escapeStartsWith, escapeEndsWith, escapeContainsWith, startsWith, endsWith, containsWith);
    }

    /**
     * 转义 LIKE 中的特殊字符：%, _, \
     * 使用 ESCAPE '\'
     */
    @Override
    protected String escape(String input) {
        if (input == null) return null;

        return input
                .replace("\\", "\\\\")   // 转义反斜杠
                .replace("%", "\\%")      // 转义百分号
                .replace("_", "\\_");     // 转义下划线
    }
}
