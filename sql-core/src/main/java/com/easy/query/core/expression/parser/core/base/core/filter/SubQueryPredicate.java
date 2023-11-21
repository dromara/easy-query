package com.easy.query.core.expression.parser.core.base.core.filter;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;

/**
 * create time 2023/9/25 16:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SubQueryPredicate<T1, TChain> extends EntitySQLTableOwner<T1>, FilterAvailable, ChainCast<TChain> {
    default <TProperty> TChain gt(String property, Query<TProperty> subQuery) {
        return gt(true, property, subQuery);
    }

    default <TProperty> TChain gt(boolean condition, String property, Query<TProperty> subQuery) {
        if (condition) {
            getFilter().gt(getTable(), property, subQuery);
        }
        return castChain();
    }

    default <TProperty> TChain ge(String property, Query<TProperty> subQuery) {
        return ge(true, property, subQuery);
    }

    default <TProperty> TChain ge(boolean condition, String property, Query<TProperty> subQuery) {
        if (condition) {
            getFilter().ge(getTable(), property, subQuery);
        }
        return castChain();
    }

    default <TProperty> TChain eq(String property, Query<TProperty> subQuery) {
        return eq(true, property, subQuery);
    }

    default <TProperty> TChain eq(boolean condition, String property, Query<TProperty> subQuery) {
        if (condition) {
            getFilter().eq(getTable(), property, subQuery);
        }
        return castChain();
    }

    default <TProperty> TChain ne(String property, Query<TProperty> subQuery) {
        return ne(true, property, subQuery);
    }

    default <TProperty> TChain ne(boolean condition, String property, Query<TProperty> subQuery) {
        if (condition) {
            getFilter().ne(getTable(), property, subQuery);
        }
        return castChain();
    }

    default <TProperty> TChain le(String property, Query<TProperty> subQuery) {
        return le(true, property, subQuery);
    }

    default <TProperty> TChain le(boolean condition, String property, Query<TProperty> subQuery) {
        if (condition) {
            getFilter().le(getTable(), property, subQuery);
        }
        return castChain();
    }

    default <TProperty> TChain lt(String property, Query<TProperty> subQuery) {
        return lt(true, property, subQuery);
    }

    default <TProperty> TChain lt(boolean condition, String property, Query<TProperty> subQuery) {
        if (condition) {
            getFilter().lt(getTable(), property, subQuery);
        }
        return castChain();
    }

    default <TProperty> TChain in(String property, Query<TProperty> subQuery) {
        return in(true, property, subQuery);
    }

    default <TProperty> TChain in(boolean condition, String property, Query<TProperty> subQuery) {
        if (condition) {
            getFilter().in(getTable(), property, subQuery);
        }
        return castChain();
    }


    default <TProperty> TChain notIn(String property, Query<TProperty> subQuery) {
        return notIn(true, property, subQuery);
    }

    default <TProperty> TChain notIn(boolean condition, String property, Query<TProperty> subQuery) {
        if (condition) {
            getFilter().notIn(getTable(), property, subQuery);
        }
        return castChain();
    }

    default <T2> TChain exists(Query<T2> subQuery) {
        return exists(true, subQuery);
    }

   default  <T2> TChain exists(boolean condition, Query<T2> subQuery){
       if (condition) {
           getFilter().exists(subQuery);
       }
       return castChain();
   }

    default <T2> TChain notExists(Query<T2> subQuery) {
        return notExists(true, subQuery);
    }

    default <T2> TChain notExists(boolean condition, Query<T2> subQuery){
        if (condition) {
            getFilter().notExists(subQuery);
        }
        return castChain();
    }

}
