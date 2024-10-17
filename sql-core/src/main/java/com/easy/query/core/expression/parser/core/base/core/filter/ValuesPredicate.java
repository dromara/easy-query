package com.easy.query.core.expression.parser.core.base.core.filter;

import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.MultiCollection;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * create time 2023/9/25 16:35
 * 集合判断
 *
 * @author xuejiaming
 */
public interface ValuesPredicate<T1, TChain> extends EntitySQLTableOwner<T1>, FilterAvailable, ChainCast<TChain> {

    /**
     * column in collection
     * 集合为空返回False
     */
    default TChain in(String property, Collection<?> collection) {
        return in(true, property, collection);
    }

    /**
     * column in collection
     * 集合为空返回False
     */
    default TChain in(boolean condition, String property, Collection<?> collection) {
        if (condition) {
            getFilter().in(getTable(), property, collection);
        }
        return castChain();
    }

    default TChain relationIn(String[] properties, Supplier<List<List<Object>>> relationIdCreator) {
        getFilter().relationIn(getTable(), properties, relationIdCreator);
        return castChain();
    }

    default <TProperty> TChain in(String property, TProperty[] collection) {
        return in(true, property, collection);
    }

    default <TProperty> TChain in(boolean condition, String property, TProperty[] collection) {
        if (condition) {
            getFilter().in(getTable(), property, collection);
        }
        return castChain();
    }

    /**
     * column not in collection
     * 集合为空返回True
     */
    default TChain notIn(String property, Collection<?> collection) {
        return notIn(true, property, collection);
    }

    /**
     * column not in collection
     * 集合为空返回True
     */
    default TChain notIn(boolean condition, String property, Collection<?> collection) {
        if (condition) {
            getFilter().notIn(getTable(), property, collection);
        }
        return castChain();
    }


    /**
     * 属性property不在collection的集合里面
     * 集合为空返回True
     *
     * @param property
     * @param collection
     * @param <TProperty>
     * @return
     */
    default <TProperty> TChain notIn(String property, TProperty[] collection) {
        return notIn(true, property, collection);
    }

    /**
     * 属性property不在collection的集合里面
     * 集合为空返回True
     *
     * @param condition
     * @param property
     * @param collection
     * @param <TProperty>
     * @return
     */
    default <TProperty> TChain notIn(boolean condition, String property, TProperty[] collection) {
        if (condition) {
            getFilter().notIn(getTable(), property, collection);
        }
        return castChain();
    }
}
