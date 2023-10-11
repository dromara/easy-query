package com.easy.query.core.func.concat;

import com.easy.query.core.expression.parser.core.DefaultSQLColumnOwner;
import com.easy.query.core.expression.parser.core.SQLColumnOwner;
import com.easy.query.core.expression.parser.core.SQLTableOwner;

import java.util.List;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultColumnConcatSelector implements ColumnConcatSelector{
    private final List<SQLColumnOwner> sqlColumns;

    public DefaultColumnConcatSelector(List<SQLColumnOwner> sqlColumns){

        this.sqlColumns = sqlColumns;
    }
    @Override
    public ColumnConcatSelector column(String property) {
        sqlColumns.add(new DefaultSQLColumnOwner(null,property));
        return this;
    }

    @Override
    public ColumnConcatSelector column(SQLTableOwner tableOwner, String property) {
        sqlColumns.add(new DefaultSQLColumnOwner(tableOwner,property));
        return this;
    }
}
