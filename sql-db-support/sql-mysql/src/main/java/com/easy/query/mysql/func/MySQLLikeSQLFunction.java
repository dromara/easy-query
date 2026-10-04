package com.easy.query.mysql.func;

import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.common.LikeFunctionValue;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncValueExpression;
import com.easy.query.core.func.column.ColumnPropertyExpression;
import com.easy.query.core.func.column.impl.ColumnFuncValueExpressionImpl;
import com.easy.query.core.func.def.impl.AbstractLikeSQLFunction;
import com.easy.query.core.metadata.ColumnMetadata;

import java.util.List;

/**
 * create time 2024/3/11 20:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQLLikeSQLFunction extends AbstractLikeSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final SQLLikeEnum sqlLikeEnum;

    public MySQLLikeSQLFunction(List<ColumnExpression> columnExpressions, SQLLikeEnum sqlLikeEnum) {

        this.columnExpressions = columnExpressions;
        this.sqlLikeEnum = sqlLikeEnum;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        MySQLLikeFunctionTemplateGenerator templateGenerator = new MySQLLikeFunctionTemplateGenerator(
                columnExpressions, sqlLikeEnum, o -> getColumnFuncValueExpression(o)
                , "{0} LIKE CONCAT({1},'%') ESCAPE '\\\\'"
                , "{0} LIKE CONCAT('%',{1}) ESCAPE '\\\\'"
                , "{0} LIKE CONCAT('%',{1},'%') ESCAPE '\\\\'"
                , "{0} LIKE CONCAT({1},'%')"
                , "{0} LIKE CONCAT('%',{1})"
                , "{0} LIKE CONCAT('%',{1},'%')"
        );
        return templateGenerator.sqlSegment(defaultTable);
    }

    @Override
    public int paramMarks() {
        return columnExpressions.size();
    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }
}
