package com.easy.query.core.func.ifnull;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.SQLAliasNativePropertyExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/10/5 22:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class IfNullSQLFunction implements SQLFunction {
    private final TableAvailable table;
    private final String property;
    private final Object def;

    public IfNullSQLFunction(TableAvailable table, String property, Object def){

        this.table = table;
        this.property = property;
        this.def = def;
    }
    @Override
    public String sqlSegment() {
        return "IFNULL({0},{1})";
    }

    @Override
    public void consume(SQLNativePropertyExpressionContext context) {
        context.keepStyle()
                .expression(this.table,this.property)
                .value(this.def);
    }

    @Override
    public void consume(SQLAliasNativePropertyExpressionContext context) {
        context.keepStyle()
                .expression(this.table,this.property)
                .value(this.def);
    }
}
