package com.easyquery.springbootdemo.domain.proxy;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.fetcher.AbstractFetcher;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easyquery.springbootdemo.domain.HelpCityEntity;
import com.easy.query.core.proxy.columns.types.SQLStringTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLLocalDateTimeTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLBooleanTypeColumn;

/**
 * this file automatically generated by easy-query, don't modify it
 * 当前文件是easy-query自动生成的请不要随意修改
 * 如果出现属性冲突请使用@ProxyProperty进行重命名
 *
 * @author easy-query
 */
public class HelpCityEntityProxy extends AbstractProxyEntity<HelpCityEntityProxy, HelpCityEntity> {

    private static final Class<HelpCityEntity> entityClass = HelpCityEntity.class;

    public static HelpCityEntityProxy createTable() {
        return new HelpCityEntityProxy();
    }

    public HelpCityEntityProxy() {
    }

    /**
     * {@link HelpCityEntity#getName}
     */
    public SQLStringTypeColumn<HelpCityEntityProxy> name() {
        return getStringTypeColumn("name");
    }

    /**
     * {@link HelpCityEntity#getProvinceName}
     */
    public SQLStringTypeColumn<HelpCityEntityProxy> provinceName() {
        return getStringTypeColumn("provinceName");
    }

    /**
     * {@link HelpCityEntity#getProvinceId}
     */
    public SQLStringTypeColumn<HelpCityEntityProxy> provinceId() {
        return getStringTypeColumn("provinceId");
    }

    /**
     * {@link HelpCityEntity#getId}
     */
    public SQLStringTypeColumn<HelpCityEntityProxy> id() {
        return getStringTypeColumn("id");
    }

    /**
     * 创建时间;创建时间
     * {@link HelpCityEntity#getCreateTime}
     */
    public SQLLocalDateTimeTypeColumn<HelpCityEntityProxy> createTime() {
        return getLocalDateTimeTypeColumn("createTime");
    }

    /**
     * 修改时间;修改时间
     * {@link HelpCityEntity#getUpdateTime}
     */
    public SQLLocalDateTimeTypeColumn<HelpCityEntityProxy> updateTime() {
        return getLocalDateTimeTypeColumn("updateTime");
    }

    /**
     * 创建人;创建人
     * {@link HelpCityEntity#getCreateBy}
     */
    public SQLStringTypeColumn<HelpCityEntityProxy> createBy() {
        return getStringTypeColumn("createBy");
    }

    /**
     * 修改人;修改人
     * {@link HelpCityEntity#getUpdateBy}
     */
    public SQLStringTypeColumn<HelpCityEntityProxy> updateBy() {
        return getStringTypeColumn("updateBy");
    }

    /**
     * 是否删除;是否删除
     * {@link HelpCityEntity#getDeleted}
     */
    public SQLBooleanTypeColumn<HelpCityEntityProxy> deleted() {
        return getBooleanTypeColumn("deleted");
    }


    @Override
    public Class<HelpCityEntity> getEntityClass() {
        return entityClass;
    }


    /**
     * 数据库列的简单获取
     *
     * @return
     */
    public HelpCityEntityProxyFetcher FETCHER = new HelpCityEntityProxyFetcher(this, null, SQLSelectAsExpression.empty);


    public static class HelpCityEntityProxyFetcher extends AbstractFetcher<HelpCityEntityProxy, HelpCityEntity, HelpCityEntityProxyFetcher> {

        public HelpCityEntityProxyFetcher(HelpCityEntityProxy proxy, HelpCityEntityProxyFetcher prev, SQLSelectAsExpression sqlSelectAsExpression) {
            super(proxy, prev, sqlSelectAsExpression);
        }


        /**
         * {@link HelpCityEntity#getName}
         */
        public HelpCityEntityProxyFetcher name() {
            return add(getProxy().name());
        }

        /**
         * {@link HelpCityEntity#getProvinceName}
         */
        public HelpCityEntityProxyFetcher provinceName() {
            return add(getProxy().provinceName());
        }

        /**
         * {@link HelpCityEntity#getProvinceId}
         */
        public HelpCityEntityProxyFetcher provinceId() {
            return add(getProxy().provinceId());
        }

        /**
         * {@link HelpCityEntity#getId}
         */
        public HelpCityEntityProxyFetcher id() {
            return add(getProxy().id());
        }

        /**
         * 创建时间;创建时间
         * {@link HelpCityEntity#getCreateTime}
         */
        public HelpCityEntityProxyFetcher createTime() {
            return add(getProxy().createTime());
        }

        /**
         * 修改时间;修改时间
         * {@link HelpCityEntity#getUpdateTime}
         */
        public HelpCityEntityProxyFetcher updateTime() {
            return add(getProxy().updateTime());
        }

        /**
         * 创建人;创建人
         * {@link HelpCityEntity#getCreateBy}
         */
        public HelpCityEntityProxyFetcher createBy() {
            return add(getProxy().createBy());
        }

        /**
         * 修改人;修改人
         * {@link HelpCityEntity#getUpdateBy}
         */
        public HelpCityEntityProxyFetcher updateBy() {
            return add(getProxy().updateBy());
        }

        /**
         * 是否删除;是否删除
         * {@link HelpCityEntity#getDeleted}
         */
        public HelpCityEntityProxyFetcher deleted() {
            return add(getProxy().deleted());
        }


        @Override
        protected HelpCityEntityProxyFetcher createFetcher(HelpCityEntityProxy cp, AbstractFetcher<HelpCityEntityProxy, HelpCityEntity, HelpCityEntityProxyFetcher> prev, SQLSelectAsExpression sqlSelectExpression) {
            return new HelpCityEntityProxyFetcher(cp, this, sqlSelectExpression);
        }
    }

}
