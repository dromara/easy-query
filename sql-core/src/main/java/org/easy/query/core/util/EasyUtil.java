package org.easy.query.core.util;

import org.easy.query.core.abstraction.metadata.ColumnMetadata;
import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.expression.segment.SqlEntityProjectSegment;
import org.easy.query.core.query.SqlEntityQueryExpression;
import org.easy.query.core.query.SqlEntityTableExpression;

import java.util.List;

/**
 * @FileName: EasyUtil.java
 * @Description: 文件说明
 * @Date: 2023/3/4 13:12
 * @Created by xuejiaming
 */
public class EasyUtil {
    private EasyUtil(){}
    public static SqlEntityTableExpression getPredicateTableByOffset(SqlEntityQueryExpression sqlEntityExpression, int offsetForward) {
        List<SqlEntityTableExpression> tables = sqlEntityExpression.getTables();
        if (tables.isEmpty()) {
            throw new EasyQueryException("cant get current join table");
        }
        int i = getNextTableIndex(sqlEntityExpression) - 1 - offsetForward;
        return tables.get(i);
    }
    public static SqlEntityTableExpression getCurrentPredicateTable(SqlEntityQueryExpression sqlEntityExpression) {
        return getPredicateTableByOffset(sqlEntityExpression,0);
    }
    public static SqlEntityTableExpression getPreviewPredicateTable(SqlEntityQueryExpression sqlEntityExpression) {
        return getPredicateTableByOffset(sqlEntityExpression,1);
    }

    public static ColumnMetadata getColumnMetadata(SqlEntityTableExpression tableExpression,String propertyName){
        return tableExpression.getEntityMetadata().getColumn(propertyName);
    }

    public static int getNextTableIndex(SqlEntityQueryExpression sqlEntityExpression){
        return sqlEntityExpression.getTables().size();
    }
    public static String getAnonymousPropertyName(SqlEntityProjectSegment sqlEntityProject){
        String alias = sqlEntityProject.getAlias();
        if(StringUtil.isBlank(alias)){
            return sqlEntityProject.getPropertyName();
        }
        return alias;
    }
}

