package org.easy.query.mysql.base;

import org.easy.query.core.abstraction.EasyQueryLambdaFactory;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.UpdateSetSqlBuilderSegment;
import org.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;
import org.easy.query.core.query.EasySqlDeleteExpression;
import org.easy.query.core.query.SqlEntityTableExpression;
import org.easy.query.core.query.SqlExpressionContext;
import org.easy.query.core.util.ClassUtil;

import java.util.Collection;

/**
 * @FileName: MySQLDeleteExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:36
 * @Created by xuejiaming
 */
public class MySQLDeleteExpression extends EasySqlDeleteExpression {


    public MySQLDeleteExpression(SqlExpressionContext sqlExpressionContext, boolean expressionDelete) {
        super(sqlExpressionContext,expressionDelete);
    }

}
