package com.easy.query.core.func.def;

import com.easy.query.core.expression.parser.core.SQLColumnOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.SQLAliasNativePropertyExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/10/11 22:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class ConcatSQLFunction implements SQLFunction {
    private final SQLColumnOwner[] sqlColumns;

    public ConcatSQLFunction(SQLColumnOwner[] sqlColumns) {
        if (EasyArrayUtil.isEmpty(sqlColumns)) {
            throw new IllegalArgumentException("ConcatSQLFunction columns empty");
        }
        this.sqlColumns = sqlColumns;
    }

    @Override
    public String sqlSegment() {
        Iterable<String> params = EasyArrayUtil.select(sqlColumns, (t, i) -> "{" + i + "}");
        return String.format("CONCAT(%s)", String.join(",", params));
    }

    @Override
    public void consume(SQLNativePropertyExpressionContext context) {
        for (SQLColumnOwner sqlColumn : sqlColumns) {
            TableAvailable table = sqlColumn.getTable();
            if(table==null){
                context.expression(sqlColumn.getProperty());
            }else{
                context.expression(table,sqlColumn.getProperty());
            }
        }
    }

    @Override
    public void consume(SQLAliasNativePropertyExpressionContext context) {
        for (SQLColumnOwner sqlColumn : sqlColumns) {
            TableAvailable table = sqlColumn.getTable();
            if(table==null){
                context.expression(sqlColumn.getProperty());
            }else{
                context.expression(table,sqlColumn.getProperty());
            }
        }
    }
}
