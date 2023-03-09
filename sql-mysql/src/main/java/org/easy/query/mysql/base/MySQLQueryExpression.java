package org.easy.query.mysql.base;

import org.easy.query.core.abstraction.EasyQueryLambdaFactory;
import org.easy.query.core.abstraction.EasySqlExpressionFactory;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.configuration.types.EasyQueryConfiguration;
import org.easy.query.core.configuration.types.GlobalQueryFilterConfiguration;
import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.segment.SqlSegment;
import org.easy.query.core.expression.segment.condition.AndPredicateSegment;
import org.easy.query.core.expression.segment.condition.PredicateSegment;
import org.easy.query.core.query.*;
import org.easy.query.core.util.ArrayUtil;
import org.easy.query.core.util.StringUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @FileName: MySQLEntityExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 13:04
 * @Created by xuejiaming
 */
public class MySQLQueryExpression extends EasySqlQueryExpression {
    public MySQLQueryExpression(SqlExpressionContext queryExpressionContext) {
        super(queryExpressionContext);
    }



    @Override
    public SqlEntityQueryExpression cloneSqlQueryExpression() {

        SqlExpressionContext sqlExpressionContext = getSqlExpressionContext();
        EasySqlExpressionFactory sqlExpressionFactory = getRuntimeContext().getSqlExpressionFactory();
        MySQLQueryExpression sqlEntityQueryExpression = (MySQLQueryExpression) sqlExpressionFactory.createSqlEntityQueryExpression(sqlExpressionContext);
        sqlEntityQueryExpression.where = super.where;
        sqlEntityQueryExpression.group = super.group;
        sqlEntityQueryExpression.having = super.having;
        sqlEntityQueryExpression.order = super.order;
        sqlEntityQueryExpression.offset = super.offset;
        sqlEntityQueryExpression.rows = super.rows;
        List<SqlSegment> sqlSegments = super.projects.getSqlSegments();
        sqlEntityQueryExpression.projects.getSqlSegments().addAll(sqlSegments);
        sqlEntityQueryExpression.tables.addAll(super.tables);
        return sqlEntityQueryExpression;
    }
}
