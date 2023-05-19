package com.easy.query.core.sharding.route;

import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.RouteFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.OrPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.ShardingPredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.expression.segment.condition.predicate.ShardingPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ValuesPredicate;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.sharding.route.descriptor.EntityRouteDescriptor;
import com.easy.query.core.sharding.route.descriptor.PredicateRouteDescriptor;
import com.easy.query.core.sharding.route.descriptor.RouteDescriptor;
import com.easy.query.core.sharding.rule.RouteRuleFilter;
import com.easy.query.core.util.EasyBeanUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyStringUtil;

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
public class RoutePredicateDiscover<T> {

    private static final String GREATER_THAN = SQLPredicateCompareEnum.GT.getSQL();
    private static final String GREATER_THAN_OR_EQUAL = SQLPredicateCompareEnum.GE.getSQL();
    private static final String LESS_THAN = SQLPredicateCompareEnum.LT.getSQL();
    private static final String LESS_THAN_OR_EQUAL = SQLPredicateCompareEnum.LE.getSQL();
    private static final String EQUAL = SQLPredicateCompareEnum.EQ.getSQL();
    private static final String NOT_EQUAL = SQLPredicateCompareEnum.NE.getSQL();
    private static final String LIKE = SQLPredicateCompareEnum.LIKE.getSQL();
    private static final String NOT_IN = SQLPredicateCompareEnum.NOT_IN.getSQL();
    private static final String IN = SQLPredicateCompareEnum.IN.getSQL();
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

    private ShardingOperatorEnum translateOperator(SQLPredicateCompare operator, Object value) {
        String operatorText = operator.getSQL();
        ShardingOperatorEnum shardingOperatorEnum = operatorTranslate.get(operatorText);
        if (shardingOperatorEnum != null) {
            if (Objects.equals(ShardingOperatorEnum.ALL_LIKE, shardingOperatorEnum)) {
                if (value != null) {
                    String valueStr = value.toString();
                    if (!EasyStringUtil.startsWith(valueStr, "%")) {
                        return ShardingOperatorEnum.LIKE_MATCH_LEFT;
                    }
                    if (!EasyStringUtil.endsWith(valueStr, "%")) {
                        return ShardingOperatorEnum.LIKE_MATCH_RIGHT;
                    }
                }
            }
            return shardingOperatorEnum;
        }
        if (Objects.equals(IN, operatorText)) {
            return ShardingOperatorEnum.EQUAL;
        }
        if (Objects.equals(NOT_IN, operatorText)) {
            return ShardingOperatorEnum.NOT_EQUAL;
        }
        return ShardingOperatorEnum.UN_KNOWN;
    }

    private final RouteDescriptor routeDescriptor;
    private final EntityMetadata entityMetadata;
    private final RouteRuleFilter<T> routeRuleFilter;
    private final Set<String> shardingProperties;
    private final String mainShardingProperty;

    public RoutePredicateDiscover(RouteDescriptor routeDescriptor, RouteRuleFilter<T> routeRuleFilter, boolean shardingTableRoute) {
        this.routeDescriptor = routeDescriptor;

        this.entityMetadata = routeDescriptor.getTable().getEntityMetadata();
        this.routeRuleFilter = routeRuleFilter;
        if (shardingTableRoute) {
            shardingProperties = entityMetadata.getShardingTablePropertyNames();
            mainShardingProperty = entityMetadata.getShardingTablePropertyName();
        } else {
            shardingProperties = entityMetadata.getShardingDataSourcePropertyNames();
            mainShardingProperty = entityMetadata.getShardingDataSourcePropertyName();
        }
    }

    public RoutePredicateExpression<T> getRouteParseExpression() {
        if (routeDescriptor instanceof PredicateRouteDescriptor) {
            return getPredicateSQLRouteParseExpression((PredicateRouteDescriptor) routeDescriptor);
        } else if (routeDescriptor instanceof EntityRouteDescriptor) {
            Object entity = ((EntityRouteDescriptor) routeDescriptor).getEntity();

            if(entity==null){
                throw new EasyQueryInvalidOperationException("route parse not support null entity.");
            }
            return getEntitySQLRouteParseExpression(entity);
        }
        throw new UnsupportedOperationException(EasyClassUtil.getInstanceSimpleName(routeDescriptor));
    }

    private RoutePredicateExpression<T> getPredicateSQLRouteParseExpression(PredicateRouteDescriptor predicateRouteDescriptor) {

        List<PredicateSegment> predicates = predicateRouteDescriptor.getPredicates();
        if(EasyCollectionUtil.isEmpty(predicates)){
            return RoutePredicateExpression.getDefault();
        }
        if(EasyCollectionUtil.isSingle(predicates)){
            PredicateSegment predicate = EasyCollectionUtil.first(predicates);
            if(predicate instanceof ShardingPredicateSegment){
                return parsePredicate((ShardingPredicateSegment) predicate);
            }
            return RoutePredicateExpression.getDefault();
        }
        RoutePredicateExpression<T> defaultRoutePredicate = RoutePredicateExpression.getDefault();
        for (PredicateSegment predicate : predicates) {
            if(predicate instanceof ShardingPredicateSegment){
                RoutePredicateExpression<T> parseRoutePredicate = parsePredicate((ShardingPredicateSegment) predicate);
                defaultRoutePredicate=defaultRoutePredicate.and(parseRoutePredicate);
            }
        }
        return defaultRoutePredicate;

    }

    private RoutePredicateExpression<T> parsePredicate(ShardingPredicateSegment where) {
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
            if (EasyCollectionUtil.isNotEmpty(where.getChildren())) {
                RoutePredicateExpression<T> routePredicate = RoutePredicateExpression.getDefault();
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

    private RoutePredicateExpression<T> parseValuePredicate(ValuePredicate valuePredicate) {
        TableAvailable table = valuePredicate.getTable();
        String propertyName = valuePredicate.getPropertyName();
        if (Objects.equals(table,routeDescriptor.getTable()) && shardingProperties.contains(propertyName)) {
            SQLParameter parameter = valuePredicate.getParameter();
            if (parameter instanceof ConstSQLParameter) {
                ConstSQLParameter constSQLParameter = (ConstSQLParameter) parameter;
                Object value = constSQLParameter.getValue();

                SQLPredicateCompare operator = valuePredicate.getOperator();
                ShardingOperatorEnum shardingOperator = translateOperator(operator, value);
                RouteFunction<T> routePredicate = routeRuleFilter.routeFilter(table,value, shardingOperator, propertyName, Objects.equals(mainShardingProperty, propertyName),false);
                return new RoutePredicateExpression<T>(routePredicate);
            }
            //System.out.println("不支持的sql参数:"+ EasyClassUtil.getInstanceSimpleName(parameter));
        }
        return RoutePredicateExpression.getDefault();
    }

    private RoutePredicateExpression<T> parseValuesPredicate(ValuesPredicate valuesPredicate) {
        TableAvailable table = valuesPredicate.getTable();
        String propertyName = valuesPredicate.getPropertyName();
        if (Objects.equals(table,routeDescriptor.getTable()) && shardingProperties.contains(propertyName)) {

            SQLPredicateCompare operator = valuesPredicate.getOperator();
            ShardingOperatorEnum shardingOperator = translateOperator(operator, null);
            boolean in = Objects.equals(ShardingOperatorEnum.EQUAL, shardingOperator);
            RoutePredicateExpression<T> containsRoutePredicate = in ? RoutePredicateExpression.getDefaultFalse() : RoutePredicateExpression.getDefault();

            Collection<SQLParameter> parameters = valuesPredicate.getParameters();
            for (SQLParameter parameter : parameters) {
                ConstSQLParameter constSQLParameter = (ConstSQLParameter) parameter;
                Object value = constSQLParameter.getValue();
                RouteFunction<T> routePredicate = routeRuleFilter.routeFilter(table,value, shardingOperator, propertyName, Objects.equals(mainShardingProperty, propertyName),false);
                if(in){
                    containsRoutePredicate.or(new RoutePredicateExpression<T>(routePredicate));
                }else{
                    containsRoutePredicate.and(new RoutePredicateExpression<T>(routePredicate));
                }
            }
        }
        return RoutePredicateExpression.getDefault();
    }

    private RoutePredicateExpression<T> getEntitySQLRouteParseExpression(Object entity) {
        ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(mainShardingProperty);
        Property<Object, ?> shardingKeyPropertyGetter = EasyBeanUtil.getFastBean(entityMetadata.getEntityClass()).getBeanGetter(columnMetadata.getProperty());
        Object shardingValue = shardingKeyPropertyGetter.apply(entity);
        RouteFunction<T> routePredicate = routeRuleFilter.routeFilter(routeDescriptor.getTable(),shardingValue, ShardingOperatorEnum.EQUAL, mainShardingProperty, true,true);
        return new RoutePredicateExpression<T>(routePredicate);
    }
}
