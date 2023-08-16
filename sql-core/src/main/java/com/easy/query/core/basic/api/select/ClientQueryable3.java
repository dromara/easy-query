package com.easy.query.core.basic.api.select;

import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.select.extension.queryable3.SQLAggregate3Extension;
import com.easy.query.core.basic.api.select.extension.queryable3.SQLGroup3Extension;
import com.easy.query.core.basic.api.select.extension.queryable3.SQLHaving3Extension;
import com.easy.query.core.basic.api.select.extension.queryable3.SQLJoin3Extension;
import com.easy.query.core.basic.api.select.extension.queryable3.SQLOrderBy3Extension;
import com.easy.query.core.basic.api.select.extension.queryable3.SQLSelect3Extension;
import com.easy.query.core.basic.api.select.extension.queryable3.SQLWhere3Extension;
import com.easy.query.core.basic.api.select.extension.queryable3.override.ClientOverrideQueryable3;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.lambda.SQLFuncExpression3;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import com.easy.query.core.expression.parser.core.base.ColumnResultSelector;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

import java.math.BigDecimal;
import java.util.function.Function;


/**
 * @author xuejiaming
 * @FileName: Select3.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:44
 */
public interface ClientQueryable3<T1, T2, T3> extends ClientOverrideQueryable3<T1,T2,T3> ,
        SQLAggregate3Extension<T1, T2, T3>,
        SQLJoin3Extension<T1, T2, T3>,
        SQLWhere3Extension<T1, T2, T3>,
        SQLSelect3Extension<T1, T2, T3>,
        SQLGroup3Extension<T1, T2, T3>,
        SQLHaving3Extension<T1, T2, T3>,
        SQLOrderBy3Extension<T1, T2, T3> {

    SQLExpressionProvider<T2> getSQLExpressionProvider2();

    SQLExpressionProvider<T3> getSQLExpressionProvider3();
}
