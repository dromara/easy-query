package com.easy.query.core.proxy.core.tuple.proxy;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.SQLAnyColumn;
import com.easy.query.core.proxy.columns.types.SQLAnyTypeColumn;
import com.easy.query.core.proxy.core.draft.proxy.AbstractDraftProxy;
import com.easy.query.core.proxy.core.tuple.Tuple9;
import com.easy.query.core.proxy.fetcher.AbstractFetcher;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Optional;

/**
 * this file automatically generated by easy-query, don't modify it
 * 当前文件是easy-query自动生成的请不要随意修改
 *
 * @author xuejiaming
 */
public class Tuple9Proxy<T1,T2,T3,T4,T5,T6,T7,T8,T9> extends AbstractDraftProxy<Tuple9Proxy<T1,T2,T3,T4,T5,T6,T7,T8,T9>, Tuple9<T1,T2,T3,T4,T5,T6,T7,T8,T9>> {

    private static final Class<Tuple9> entityClass = Tuple9.class;


    public Tuple9Proxy(PropTypeColumn<T1> column1, PropTypeColumn<T2> column2, PropTypeColumn<T3> column3,
                       PropTypeColumn<T4> column4, PropTypeColumn<T5> column5, PropTypeColumn<T6> column6,
                       PropTypeColumn<T7> column7, PropTypeColumn<T8> column8, PropTypeColumn<T9> column9) {
        super(9);
        fetch(0, column1, "value1");
        fetch(1, column2, "value2");
        fetch(2, column3, "value3");
        fetch(3, column4, "value4");
        fetch(4, column5, "value5");
        fetch(5, column6, "value6");
        fetch(6, column7, "value7");
        fetch(7, column8, "value8");
        fetch(8, column9, "value9");
    }

    /**
     * {@link Tuple9#getValue1}
     */
    public SQLAnyTypeColumn<Tuple9Proxy<T1,T2,T3,T4,T5,T6,T7,T8,T9>, T1> value1() {
        return getAnyTypeColumn("value1",EasyObjectUtil.typeCastNullable(Optional.ofNullable(getDraftPropTypes()[0]).map(o->o.getPropertyType()).orElse(null)));
    }

    /**
     * {@link Tuple9#getValue2()}
     */
    public SQLAnyTypeColumn<Tuple9Proxy<T1,T2,T3,T4,T5,T6,T7,T8,T9>, T2> value2() {
        return getAnyTypeColumn("value2",EasyObjectUtil.typeCastNullable(Optional.ofNullable(getDraftPropTypes()[1]).map(o->o.getPropertyType()).orElse(null)));
    }
    /**
     * {@link Tuple9#getValue3()}
     */
    public SQLAnyTypeColumn<Tuple9Proxy<T1,T2,T3,T4,T5,T6,T7,T8,T9>, T3> value3() {
        return getAnyTypeColumn("value3",EasyObjectUtil.typeCastNullable(Optional.ofNullable(getDraftPropTypes()[2]).map(o->o.getPropertyType()).orElse(null)));
    }
    /**
     * {@link Tuple9#getValue4()}
     */
    public SQLAnyTypeColumn<Tuple9Proxy<T1,T2,T3,T4,T5,T6,T7,T8,T9>, T4> value4() {
        return getAnyTypeColumn("value4",EasyObjectUtil.typeCastNullable(Optional.ofNullable(getDraftPropTypes()[3]).map(o->o.getPropertyType()).orElse(null)));
    }
    /**
     * {@link Tuple9#getValue5()}
     */
    public SQLAnyTypeColumn<Tuple9Proxy<T1,T2,T3,T4,T5,T6,T7,T8,T9>, T5> value5() {
        return getAnyTypeColumn("value5",EasyObjectUtil.typeCastNullable(Optional.ofNullable(getDraftPropTypes()[4]).map(o->o.getPropertyType()).orElse(null)));
    }
    /**
     * {@link Tuple9#getValue6()}
     */
    public SQLAnyTypeColumn<Tuple9Proxy<T1,T2,T3,T4,T5,T6,T7,T8,T9>, T6> value6() {
        return getAnyTypeColumn("value6",EasyObjectUtil.typeCastNullable(Optional.ofNullable(getDraftPropTypes()[5]).map(o->o.getPropertyType()).orElse(null)));
    }
    /**
     * {@link Tuple9#getValue7()}
     */
    public SQLAnyTypeColumn<Tuple9Proxy<T1,T2,T3,T4,T5,T6,T7,T8,T9>, T7> value7() {
        return getAnyTypeColumn("value7",EasyObjectUtil.typeCastNullable(Optional.ofNullable(getDraftPropTypes()[6]).map(o->o.getPropertyType()).orElse(null)));
    }
    /**
     * {@link Tuple9#getValue8()}
     */
    public SQLAnyTypeColumn<Tuple9Proxy<T1,T2,T3,T4,T5,T6,T7,T8,T9>, T8> value8() {
        return getAnyTypeColumn("value8",EasyObjectUtil.typeCastNullable(Optional.ofNullable(getDraftPropTypes()[7]).map(o->o.getPropertyType()).orElse(null)));
    }
    /**
     * {@link Tuple9#getValue9()}
     */
    public SQLAnyTypeColumn<Tuple9Proxy<T1,T2,T3,T4,T5,T6,T7,T8,T9>, T9> value9() {
        return getAnyTypeColumn("value9",EasyObjectUtil.typeCastNullable(Optional.ofNullable(getDraftPropTypes()[8]).map(o->o.getPropertyType()).orElse(null)));
    }


    @Override
    public Class<Tuple9<T1,T2,T3,T4,T5,T6,T7,T8,T9>> getEntityClass() {
        return EasyObjectUtil.typeCastNullable(entityClass);
    }


    /**
     * 数据库列的简单获取
     *
     * @return
     */
    public Tuple9ProxyFetcher<T1,T2,T3,T4,T5,T6,T7,T8,T9> FETCHER = new Tuple9ProxyFetcher<>(this, null, SQLSelectAsExpression.empty);


    public static class Tuple9ProxyFetcher<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9> extends AbstractFetcher<Tuple9Proxy<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9>, Tuple9<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9>, Tuple9ProxyFetcher<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9>> {

        public Tuple9ProxyFetcher(Tuple9Proxy<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9> proxy, Tuple9ProxyFetcher<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9> prev, SQLSelectAsExpression sqlSelectAsExpression) {
            super(proxy, prev, sqlSelectAsExpression);
        }


        /**
         * {@link Tuple9#getValue1}
         */
        public Tuple9ProxyFetcher<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9> value1() {
            return add(getProxy().value1());
        }


        /**
         * {@link Tuple9#getValue2}
         */
        public Tuple9ProxyFetcher<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9> value2() {
            return add(getProxy().value2());
        }
        /**
         * {@link Tuple9#getValue3}
         */
        public Tuple9ProxyFetcher<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9> value3() {
            return add(getProxy().value3());
        }
        /**
         * {@link Tuple9#getValue4}
         */
        public Tuple9ProxyFetcher<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9> value4() {
            return add(getProxy().value4());
        }
        /**
         * {@link Tuple9#getValue5}
         */
        public Tuple9ProxyFetcher<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9> value5() {
            return add(getProxy().value5());
        }
        /**
         * {@link Tuple9#getValue6}
         */
        public Tuple9ProxyFetcher<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9> value6() {
            return add(getProxy().value6());
        }
        /**
         * {@link Tuple9#getValue7}
         */
        public Tuple9ProxyFetcher<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9> value7() {
            return add(getProxy().value7());
        }
        /**
         * {@link Tuple9#getValue8}
         */
        public Tuple9ProxyFetcher<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9> value8() {
            return add(getProxy().value8());
        }
        /**
         * {@link Tuple9#getValue9}
         */
        public Tuple9ProxyFetcher<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9> value9() {
            return add(getProxy().value9());
        }


        @Override
        protected Tuple9ProxyFetcher<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9> createFetcher(
                Tuple9Proxy<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9> cp,
                AbstractFetcher<Tuple9Proxy<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9>, Tuple9<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9>, Tuple9ProxyFetcher<TF1,TF2,TF3,TF4,TF5,TF6,TF7,TF8,TF9>> prev,
                SQLSelectAsExpression sqlSelectExpression
        ) {
            return new Tuple9ProxyFetcher<>(cp, this, sqlSelectExpression);
        }
    }

}
