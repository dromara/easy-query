package com.easy.query.core.sharding.route;

import com.easy.query.core.expression.lambda.RouteFunction;

/**
 * create time 2023/4/19 08:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class RoutePredicateExpression {
    private static final RouteFunction<String> DEFAULT_TRUE_ROUTE_PREDICATE=v->true;
    private static final RouteFunction<String> DEFAULT_FALSE_ROUTE_PREDICATE=v->true;
    public static RoutePredicateExpression getDefault(){
        return new RoutePredicateExpression();
    }
    public static RoutePredicateExpression getDefaultFalse(){
        return new RoutePredicateExpression(DEFAULT_FALSE_ROUTE_PREDICATE);
    }


    private final RouteFunction<String> routePredicate;

    public RoutePredicateExpression(){
        this(DEFAULT_TRUE_ROUTE_PREDICATE);
    }
    public RoutePredicateExpression(RouteFunction<String> routePredicate){
        this.routePredicate = routePredicate;
    }
    public RouteFunction<String> getRoutePredicate() {
        return routePredicate;
    }

    public RoutePredicateExpression and(RoutePredicateExpression routePredicateExpression){
        RouteFunction<String> routePredicate = routePredicateExpression.getRoutePredicate();
        RouteFunction<String> func=v->this.routePredicate.apply(v)&& routePredicate.apply(v);
        return new RoutePredicateExpression(func);
    }
    public RoutePredicateExpression or(RoutePredicateExpression routePredicateExpression){

        RouteFunction<String> routePredicate = routePredicateExpression.getRoutePredicate();
        RouteFunction<String> func=v->this.routePredicate.apply(v)|| routePredicate.apply(v);
        return new RoutePredicateExpression(func);
    }
}
