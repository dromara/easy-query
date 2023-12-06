package com.easy.query.test.entity.company;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.AbstractValueObjectProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.fetcher.AbstractFetcher;

/**
 * create time 2023/11/7 19:55
 * 文件说明
 *
 * @author xuejiaming
 */

public class ValueCompany1Proxy extends AbstractProxyEntity<ValueCompany1Proxy, ValueCompany> {

    private static final Class<ValueCompany> entityClass = ValueCompany.class;

    public static ValueCompany1Proxy createTable() {
        return new ValueCompany1Proxy();
    }

    private ValueCompany1Proxy() {
    }


    /**
     * {@link ValueCompany#id}
     */
    public SQLColumn<ValueCompany1Proxy, String> id() {
        return get("id");
    }

    /**
     * {@link ValueCompany#name}
     * 企业名称
     */
    public SQLColumn<ValueCompany1Proxy, java.lang.String> name() {
        return get("name");
    }

    /**
     * {@link ValueCompany#address}
     * 企业地址信息
     */
    public ValueCompanyAddressProxy address() {
        return getValueObject(new ValueCompanyAddressProxy(getTable(), getValueProperty("address")));
    }

    /**
     * {@link ValueCompany#address}
     * 企业地址信息
     */
    public ValueCompanyLicenseProxy license() {
        return getValueObject(new ValueCompanyLicenseProxy(getTable(), getValueProperty("license")));
    }

    @Override
    public Class<ValueCompany> getEntityClass() {
        return entityClass;
    }

    public static class ValueCompanyAddressProxy extends AbstractValueObjectProxyEntity<ValueCompany1Proxy, ValueCompanyAddress> {

        private ValueCompanyAddressProxy(TableAvailable table, String propertyName) {
            super(table, propertyName);
        }

        public SQLColumn<ValueCompany1Proxy, String> province() {
            return get("province");
        }
    }

    public static class ValueCompanyLicenseProxy extends AbstractValueObjectProxyEntity<ValueCompany1Proxy, ValueCompanyLicense> {


        private ValueCompanyLicenseProxy(TableAvailable table, String propertyName) {
            super(table, propertyName);
        }

        public SQLColumn<ValueCompany1Proxy, String> licenseNo() {
            return get("licenseNo");
        }

        public ValueCompanyLicenseExtraProxy extra() {
            return getValueObject(new ValueCompanyLicenseExtraProxy(getTable(), getValueProperty("extra")));
        }

        public static class ValueCompanyLicenseExtraProxy extends AbstractValueObjectProxyEntity<ValueCompany1Proxy, ValueCompanyLicenseExtra> {

            private ValueCompanyLicenseExtraProxy(TableAvailable table, String propertyName) {
                super(table, propertyName);
            }

            public SQLColumn<ValueCompany1Proxy, String> licenseImage() {
                return get("licenseImage");
            }
        }
    }


    public ValueCompany1ProxyFetcher FETCHER =new ValueCompany1ProxyFetcher(this,null,SQLSelectAsExpression.empty);


    public static class ValueCompany1ProxyFetcher extends AbstractFetcher<ValueCompany1Proxy,ValueCompany, ValueCompany1ProxyFetcher> {


        public ValueCompany1ProxyFetcher(ValueCompany1Proxy proxy, ValueCompany1ProxyFetcher prev, SQLSelectAsExpression sqlSelectAsExpression) {
            super(proxy, prev, sqlSelectAsExpression);
        }

        public ValueCompany1ProxyFetcher id() {
            return add(getProxy().id());
        }

        public ValueCompany1ProxyFetcher name() {
            return add(getProxy().name());
        }
        public ValueCompany1ProxyFetcher address() {
            return add(getProxy().address());
        }

        @Override
        protected ValueCompany1ProxyFetcher createFetcher(ValueCompany1Proxy valueCompany1Proxy, AbstractFetcher<ValueCompany1Proxy, ValueCompany, ValueCompany1ProxyFetcher> prev, SQLSelectAsExpression sqlSelectExpression) {
            return new ValueCompany1ProxyFetcher(valueCompany1Proxy, this, sqlSelectExpression);
        }
    }
}