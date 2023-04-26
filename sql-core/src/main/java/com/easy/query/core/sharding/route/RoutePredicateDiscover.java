package com.easy.query.core.sharding.route;

import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.enums.SqlPredicateCompareEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.EntityPrepareParseResult;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.executor.parser.QueryPrepareParseResult;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.OrPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.ShardingPredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.expression.segment.condition.predicate.ShardingPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ValuesPredicate;
import com.easy.query.core.expression.sql.expression.EasyEntityPredicateSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.enums.ShardingOperatorEnum;
import com.easy.query.core.sharding.rule.RouteRuleFilter;
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.EasyUtil;
import com.easy.query.core.util.SqlSegmentUtil;
import com.easy.query.core.util.StringUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * create time 2023/4/19 08:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class RoutePredicateDiscover {

    private static final String GREATER_THAN = SqlPredicateCompareEnum.GT.getSql();
    private static final String GREATER_THAN_OR_EQUAL = SqlPredicateCompareEnum.GE.getSql();
    private static final String LESS_THAN = SqlPredicateCompareEnum.LT.getSql();
    private static final String LESS_THAN_OR_EQUAL = SqlPredicateCompareEnum.LE.getSql();
    private static final String EQUAL = SqlPredicateCompareEnum.EQ.getSql();
    private static final String NOT_EQUAL = SqlPredicateCompareEnum.NE.getSql();
    private static final String LIKE = SqlPredicateCompareEnum.LIKE.getSql();
    private static final String NOT_IN = SqlPredicateCompareEnum.NOT_IN.getSql();
    private static final String IN = SqlPredicateCompareEnum.IN.getSql();
    private static final Map<String, ShardingOperatorEnum> operatorTranslate;

    static {
        operatorTranslate = new HashMap<>();
        operatorTranslate.put(GREATER_THAN, ShardingOperatorEnum.GREATER_THAN);
        operatorTranslate.put(GREATER_THAN_OR_EQUAL, ShardingOperatorEnum.GREATER_THAN_OR_EQUAL);
        operatorTranslate.put(LESS_THAN, ShardingOperatorEnum.LESS_THAN);
        operatorTranslate.put(LESS_THAN_OR_EQUAL, ShardingOperatorEnum.LESS_THAN_OR_EQUAL);
        operatorTranslate.put(EQUAL, ShardingOperatorEnum.EQUAL);
        operatorTranslate.put(NOT_EQUAL, ShardingOperatorEnum.NOT_EQUAL);
        operatorTranslate.put(LIKE, ShardingOperatorEnum.ALL_LIKE);
    }

    private ShardingOperatorEnum translateOperator(SqlPredicateCompare operator, Object value) {
        String operatorText = operator.getSql();
        ShardingOperatorEnum shardingOperatorEnum = operatorTranslate.get(operatorText);
        if (shardingOperatorEnum != null) {
            if (Objects.equals(ShardingOperatorEnum.ALL_LIKE, shardingOperatorEnum)) {
                if (value != null) {
                    String valueStr = value.toString();
                    if (!StringUtil.startsWith(valueStr, "%")) {
                        return ShardingOperatorEnum.LIKE_MATCH_LEFT;
                    }
                    if (!StringUtil.endsWith(valueStr, "%")) {
                        return ShardingOperatorEnum.LIKE_MATCH_RIGHT;
                    }
                }
            }
            return shardingOperatorEnum;
        }
        if (Objects.equals(IN, operatorText)) {
            return ShardingOperatorEnum.EQUAL;
        }
        if (Objects.equals(NOT_EQUAL, operatorText)) {
            return ShardingOperatorEnum.NOT_EQUAL;
        }
        return ShardingOperatorEnum.UN_KNOWN;
    }

    private final PrepareParseResult prepareParseResult;
    private final EntityMetadata entityMetadata;
    private final RouteRuleFilter routeRuleFilter;
    private final Set<String> shardingProperties;
    private final String mainShardingProperty;

    public RoutePredicateDiscover(PrepareParseResult prepareParseResult, EntityMetadata entityMetadata, RouteRuleFilter routeRuleFilter, boolean shardingTableRoute) {
        this.prepareParseResult = prepareParseResult;

        this.entityMetadata = entityMetadata;
        this.routeRuleFilter = routeRuleFilter;
        if (shardingTableRoute) {
            shardingProperties = entityMetadata.getShardingTablePropertyNames();
            mainShardingProperty = entityMetadata.getShardingTablePropertyName();
        } else {
            shardingProperties = entityMetadata.getShardingDataSourcePropertyNames();
            mainShardingProperty = entityMetadata.getShardingDataSourcePropertyName();
        }
    }

    public RoutePredicateExpression getRouteParseExpression() {
        if (prepareParseResult instanceof QueryPrepareParseResult) {
            EasyQuerySqlExpression easyQuerySqlExpression = ((QueryPrepareParseResult) prepareParseResult).getEasyQuerySqlExpression();
            return getPredicateSqlRouteParseExpression(easyQuerySqlExpression);
        } else if (prepareParseResult instanceof EntityPrepareParseResult) {
            List<Object> entities = ((EntityPrepareParseResult) prepareParseResult).getEntities();

            if(ArrayUtil.isNotSingle(entities)){
                throw new EasyQueryInvalidOperationException("route parse not support multi entity or empty entity:"+entities.size());
            }
            return getEntitySqlRouteParseExpression(entities.get(0));
        }
        throw new UnsupportedOperationException(ClassUtil.getInstanceSimpleName(prepareParseResult));
    }

    private RoutePredicateExpression getPredicateSqlRouteParseExpression(EasyEntityPredicateSqlExpression easyEntityPredicateSqlExpression) {

        PredicateSegment where = easyEntityPredicateSqlExpression.getWhere();
        if (SqlSegmentUtil.isNotEmpty(where)) {
            if(where instanceof ShardingPredicateSegment){
                return parsePredicate((ShardingPredicateSegment)where);
            }
        }
        return RoutePredicateExpression.getDefault();

    }

    private RoutePredicateExpression parsePredicate(ShardingPredicateSegment where) {
        if (where.isPredicate()) {
            Predicate predicate = where.getPredicate();
            if (predicate instanceof ShardingPredicate) {
                if (predicate instanceof ValuePredicate) {
                    return parseValuePredicate((ValuePredicate) predicate);
                }
                if (predicate instanceof ValuesPredicate) {
                    return parseValuesPredicate((ValuesPredicate) predicate);
                }
            }
        } else {
            if (ArrayUtil.isNotEmpty(where.getChildren())) {
                RoutePredicateExpression routePredicate = RoutePredicateExpression.getDefault();
                for (PredicateSegment child : where.getChildren()) {
                    if(child instanceof AndPredicateSegment){
                        routePredicate=routePredicate.and(parsePredicate((AndPredicateSegment)child));
                    }else if(child instanceof OrPredicateSegment){
                        routePredicate=routePredicate.or(parsePredicate((OrPredicateSegment)child));
                    }
                }
                return routePredicate;
            }
        }
        return RoutePredicateExpression.getDefault();
    }

    private RoutePredicateExpression parseValuePredicate(ValuePredicate valuePredicate) {
        Class<?> entityClass = valuePredicate.getTable().getEntityClass();
        String propertyName = valuePredicate.getPropertyName();
        if (Objects.equals(entityMetadata.getEntityClass(), entityClass) && shardingProperties.contains(propertyName)) {
            SQLParameter parameter = valuePredicate.getParameter();
            if (parameter instanceof ConstSQLParameter) {
                ConstSQLParameter constSQLParameter = (ConstSQLParameter) parameter;
                Object value = constSQLParameter.getValue();

                SqlPredicateCompare operator = valuePredicate.getOperator();
                ShardingOperatorEnum shardingOperator = translateOperator(operator, value);
                RouteFunction<String> routePredicate = routeRuleFilter.routeFilter(value, shardingOperator, propertyName, Objects.equals(mainShardingProperty, propertyName));
                return new RoutePredicateExpression(routePredicate);
            }
            System.out.println("不支持的sql参数");
        }
        return RoutePredicateExpression.getDefault();
    }

    private RoutePredicateExpression parseValuesPredicate(ValuesPredicate valuesPredicate) {
        Class<?> entityClass = valuesPredicate.getTable().getEntityClass();
        String propertyName = valuesPredicate.getPropertyName();
        if (Objects.equals(entityMetadata.getEntityClass(), entityClass) && shardingProperties.contains(propertyName)) {

            SqlPredicateCompare operator = valuesPredicate.getOperator();
            ShardingOperatorEnum shardingOperator = translateOperator(operator, null);
            boolean in = Objects.equals(ShardingOperatorEnum.EQUAL, shardingOperator);
            RoutePredicateExpression containsRoutePredicate = in ? RoutePredicateExpression.getDefaultFalse() : RoutePredicateExpression.getDefault();

            Collection<SQLParameter> parameters = valuesPredicate.getParameters();
            for (SQLParameter parameter : parameters) {
                ConstSQLParameter constSQLParameter = (ConstSQLParameter) parameter;
                Object value = constSQLParameter.getValue();
                RouteFunction<String> routePredicate = routeRuleFilter.routeFilter(value, shardingOperator, propertyName, Objects.equals(mainShardingProperty, propertyName));
                if(in){
                    containsRoutePredicate.or(new RoutePredicateExpression(routePredicate));
                }else{
                    containsRoutePredicate.and(new RoutePredicateExpression(routePredicate));
                }
            }
        }
        return RoutePredicateExpression.getDefault();
    }

    private RoutePredicateExpression getEntitySqlRouteParseExpression(Object entity) {
        ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(mainShardingProperty);
        Property<Object, ?> shardingKeyPropertyGetter = EasyUtil.getFastBean(entityMetadata.getEntityClass()).getBeanGetter(columnMetadata.getProperty());
        Object shardingValue = shardingKeyPropertyGetter.apply(entity);
        RouteFunction<String> routePredicate = routeRuleFilter.routeFilter(shardingValue, ShardingOperatorEnum.EQUAL, mainShardingProperty, true);
        return new RoutePredicateExpression(routePredicate);
    }
}
