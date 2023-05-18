package com.easy.query.core.sharding.route;

import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.EntityPrepareParseResult;
import com.easy.query.core.expression.executor.parser.PredicatePrepareParseResult;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
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
import com.easy.query.core.expression.sql.expression.EntityPredicateSQLExpression;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.enums.sharding.ShardingOperatorEnum;
import com.easy.query.core.sharding.rule.RouteRuleFilter;
import com.easy.query.core.util.EasyBeanUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;
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

    private final PrepareParseResult prepareParseResult;
    private final TableAvailable table;
    private final EntityMetadata entityMetadata;
    private final RouteRuleFilter<T> routeRuleFilter;
    private final Set<String> shardingProperties;
    private final String mainShardingProperty;

    public RoutePredicateDiscover(PrepareParseResult prepareParseResult, TableAvailable table, RouteRuleFilter<T> routeRuleFilter, boolean shardingTableRoute) {
        this.prepareParseResult = prepareParseResult;
        this.table = table;

        this.entityMetadata = table.getEntityMetadata();
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
        if (prepareParseResult instanceof PredicatePrepareParseResult) {
            EntityPredicateSQLExpression easyEntityPredicateSQLExpression = ((PredicatePrepareParseResult) prepareParseResult).getEntityPredicateSQLExpression();
            return getPredicateSQLRouteParseExpression(easyEntityPredicateSQLExpression);
        } else if (prepareParseResult instanceof EntityPrepareParseResult) {
            List<Object> entities = ((EntityPrepareParseResult) prepareParseResult).getEntities();

            if(EasyCollectionUtil.isNotSingle(entities)){
                throw new EasyQueryInvalidOperationException("route parse not support multi entity or empty entity:"+entities.size());
            }
            return getEntitySQLRouteParseExpression(entities.get(0));
        }
        throw new UnsupportedOperationException(EasyClassUtil.getInstanceSimpleName(prepareParseResult));
    }

    private RoutePredicateExpression<T> getPredicateSQLRouteParseExpression(EntityPredicateSQLExpression easyEntityPredicateSQLExpression) {

        PredicateSegment where = easyEntityPredicateSQLExpression.getWhere();
        if (EasySQLSegmentUtil.isNotEmpty(where)) {
            if(where instanceof ShardingPredicateSegment){
                return parsePredicate((ShardingPredicateSegment)where);
            }
        }
        return RoutePredicateExpression.getDefault();

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
        Class<?> entityClass = valuePredicate.getTable().getEntityClass();
        String propertyName = valuePredicate.getPropertyName();
        if (Objects.equals(entityMetadata.getEntityClass(), entityClass) && shardingProperties.contains(propertyName)) {
            SQLParameter parameter = valuePredicate.getParameter();
            if (parameter instanceof ConstSQLParameter) {
                ConstSQLParameter constSQLParameter = (ConstSQLParameter) parameter;
                Object value = constSQLParameter.getValue();

                SQLPredicateCompare operator = valuePredicate.getOperator();
                ShardingOperatorEnum shardingOperator = translateOperator(operator, value);
                RouteFunction<T> routePredicate = routeRuleFilter.routeFilter(value, shardingOperator, propertyName, Objects.equals(mainShardingProperty, propertyName),false);
                return new RoutePredicateExpression<T>(routePredicate);
            }
            System.out.println("不支持的sql参数:"+ EasyClassUtil.getInstanceSimpleName(parameter));
        }
        return RoutePredicateExpression.getDefault();
    }

    private RoutePredicateExpression<T> parseValuesPredicate(ValuesPredicate valuesPredicate) {
        Class<?> entityClass = valuesPredicate.getTable().getEntityClass();
        String propertyName = valuesPredicate.getPropertyName();
        if (Objects.equals(entityMetadata.getEntityClass(), entityClass) && shardingProperties.contains(propertyName)) {

            SQLPredicateCompare operator = valuesPredicate.getOperator();
            ShardingOperatorEnum shardingOperator = translateOperator(operator, null);
            boolean in = Objects.equals(ShardingOperatorEnum.EQUAL, shardingOperator);
            RoutePredicateExpression<T> containsRoutePredicate = in ? RoutePredicateExpression.getDefaultFalse() : RoutePredicateExpression.getDefault();

            Collection<SQLParameter> parameters = valuesPredicate.getParameters();
            for (SQLParameter parameter : parameters) {
                ConstSQLParameter constSQLParameter = (ConstSQLParameter) parameter;
                Object value = constSQLParameter.getValue();
                RouteFunction<T> routePredicate = routeRuleFilter.routeFilter(value, shardingOperator, propertyName, Objects.equals(mainShardingProperty, propertyName),false);
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
        RouteFunction<T> routePredicate = routeRuleFilter.routeFilter(shardingValue, ShardingOperatorEnum.EQUAL, mainShardingProperty, true,true);
        return new RoutePredicateExpression<T>(routePredicate);
    }
}
