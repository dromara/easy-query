package com.easy.query.test.dameng.entity.proxy;

import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.SQLStringColumn;
import com.easy.query.core.proxy.fetcher.AbstractFetcher;
import com.easy.query.test.dameng.entity.DamengMyTopic;

/**
 * this file automatically generated by easy-query, don't modify it
 * 当前文件是easy-query自动生成的请不要随意修改
 *
 * @author xuejiaming
 */
public class DamengMyTopicProxy extends AbstractProxyEntity < DamengMyTopicProxy, DamengMyTopic > {

    private static final Class < DamengMyTopic > entityClass = DamengMyTopic .class;

    public static DamengMyTopicProxy createTable () {
        return new DamengMyTopicProxy ();
    }

    public DamengMyTopicProxy () {
    }

    /**
     * {@link DamengMyTopic#getId}
     */
    public SQLStringColumn< DamengMyTopicProxy, String> id(){
    return getStringColumn("id", java.lang.String.class);
}

    /**
     * {@link DamengMyTopic#getStars}
     */
    public SQLColumn < DamengMyTopicProxy, java.lang.Integer> stars(){
    return get("stars", java.lang.Integer.class);
}

    /**
     * {@link DamengMyTopic#getTitle}
     */
    public SQLColumn < DamengMyTopicProxy, java.lang.String> title(){
    return get("title", java.lang.String.class);
}

    /**
     * {@link DamengMyTopic#getCreateTime}
     */
    public SQLColumn < DamengMyTopicProxy, java.time.LocalDateTime> createTime(){
    return get("createTime", java.time.LocalDateTime.class);
}


    @Override
    public Class < DamengMyTopic > getEntityClass () {
        return entityClass;
    }


    /**
     * 数据库列的简单获取
     * @return
     */
    public DamengMyTopicProxyFetcher FETCHER = new DamengMyTopicProxyFetcher (this, null, SQLSelectAsExpression.empty);


    public static class DamengMyTopicProxyFetcher extends AbstractFetcher<DamengMyTopicProxy, DamengMyTopic, DamengMyTopicProxyFetcher> {

        public DamengMyTopicProxyFetcher (DamengMyTopicProxy proxy, DamengMyTopicProxyFetcher prev, SQLSelectAsExpression sqlSelectAsExpression) {
        super(proxy, prev, sqlSelectAsExpression);
    }


        /**
         * {@link DamengMyTopic#getId}
         */
        public DamengMyTopicProxyFetcher id() {
            return add(getProxy().id());
        }

        /**
         * {@link DamengMyTopic#getStars}
         */
        public DamengMyTopicProxyFetcher stars() {
            return add(getProxy().stars());
        }

        /**
         * {@link DamengMyTopic#getTitle}
         */
        public DamengMyTopicProxyFetcher title() {
            return add(getProxy().title());
        }

        /**
         * {@link DamengMyTopic#getCreateTime}
         */
        public DamengMyTopicProxyFetcher createTime() {
            return add(getProxy().createTime());
        }


        @Override
        protected DamengMyTopicProxyFetcher createFetcher(
            DamengMyTopicProxy cp,
            AbstractFetcher<DamengMyTopicProxy, DamengMyTopic, DamengMyTopicProxyFetcher> prev,
            SQLSelectAsExpression sqlSelectExpression
        ) {
            return new DamengMyTopicProxyFetcher (cp, this, sqlSelectExpression);
        }
    }

}