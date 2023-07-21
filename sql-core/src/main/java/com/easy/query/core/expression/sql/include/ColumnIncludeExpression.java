package com.easy.query.core.expression.sql.include;


import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/7/20 10:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnIncludeExpression {
    private final TableAvailable table;
    private final String selfProperty;
    private final String aliasProperty;
    private final SQLExpression1<AsSelector> includeSelectorExpression;

    public ColumnIncludeExpression(TableAvailable table, String selfProperty, String aliasProperty, SQLExpression1<AsSelector> includeSelectorExpression){

        this.table = table;
        this.selfProperty = selfProperty;
        this.aliasProperty = aliasProperty;
        this.includeSelectorExpression = includeSelectorExpression;
    }

    public TableAvailable getTable() {
        return table;
    }

    public String getSelfProperty() {
        return selfProperty;
    }

    public String getAliasProperty() {
        return aliasProperty;
    }

    public SQLExpression1<AsSelector> getIncludeSelectorExpression() {
        return includeSelectorExpression;
    }
}
