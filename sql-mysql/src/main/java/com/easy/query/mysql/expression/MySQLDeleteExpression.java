package com.easy.query.mysql.expression;

import com.easy.query.core.abstraction.EasyQueryLambdaFactory;
import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.UpdateSetSqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;
import com.easy.query.core.query.EasySqlDeleteExpression;
import com.easy.query.core.query.SqlEntityTableExpression;
import com.easy.query.core.query.SqlExpressionContext;
import com.easy.query.core.util.ClassUtil;

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
