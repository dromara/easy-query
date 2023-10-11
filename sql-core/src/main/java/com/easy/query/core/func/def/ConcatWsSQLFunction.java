package com.easy.query.core.func.def;

import com.easy.query.core.expression.parser.core.SQLColumnOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.SQLAliasNativePropertyExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.util.EasyArrayUtil;

import java.util.Objects;

/**
 * create time 2023/10/11 22:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class ConcatWsSQLFunction implements SQLFunction {
    private final SQLColumnOwner[] sqlColumns;
    private final String separator;

    public ConcatWsSQLFunction(String separator,SQLColumnOwner[] sqlColumns) {
        Objects.requireNonNull(separator,"concat_ws separator can not be null");
        if (EasyArrayUtil.isEmpty(sqlColumns)) {
            throw new IllegalArgumentException("ConcatSQLFunction columns empty");
        }
        this.separator = separator;
        this.sqlColumns = sqlColumns;
    }

    @Override
    public String sqlSegment() {
        Iterable<String> params = EasyArrayUtil.select(sqlColumns, (t, i) -> "{" + (i+1) + "}");
        return String.format("CONCAT_WS({0},%s)", String.join(",", params));
    }

    @Override
    public void consume(SQLNativePropertyExpressionContext context) {
        context.value(separator);
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
        context.value(separator);
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
