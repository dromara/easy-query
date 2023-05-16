package com.easy.query.core.sharding.route;

import com.easy.query.core.expression.lambda.RouteFunction;

/**
 * create time 2023/4/19 08:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class RoutePredicateExpression<T> {
    private static final RouteFunction<?> DEFAULT_TRUE_ROUTE_PREDICATE=v->true;
    private static final RouteFunction<?> DEFAULT_FALSE_ROUTE_PREDICATE=v->false;
    public static  <TSource> RoutePredicateExpression<TSource> getDefault(){
        return new RoutePredicateExpression<TSource>();
    }
    public static <TSource> RoutePredicateExpression<TSource> getDefaultFalse(){
        return new RoutePredicateExpression<>(DEFAULT_FALSE_ROUTE_PREDICATE);
    }


    private final RouteFunction<T> routePredicate;

    public RoutePredicateExpression(){
        this(DEFAULT_TRUE_ROUTE_PREDICATE);
    }
    public RoutePredicateExpression(RouteFunction routePredicate){
        this.routePredicate = routePredicate;
    }
    public RouteFunction<T> getRoutePredicate() {
        return routePredicate;
    }

    public RoutePredicateExpression<T> and(RoutePredicateExpression<T> routePredicateExpression){
        RouteFunction<T> routePredicate = routePredicateExpression.getRoutePredicate();
        RouteFunction<T> func=v->this.routePredicate.apply(v)&& routePredicate.apply(v);
        return new RoutePredicateExpression<T>(func);
    }
    public RoutePredicateExpression<T> or(RoutePredicateExpression<T> routePredicateExpression){

        RouteFunction<T> routePredicate = routePredicateExpression.getRoutePredicate();
        RouteFunction<T> func=v->this.routePredicate.apply(v)|| routePredicate.apply(v);
        return new RoutePredicateExpression<T>(func);
    }
}
