package com.easy.query.core.proxy.fetcher;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * create time 2023/12/6 23:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityFetcher<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity, TChain extends EntityFetcher<TProxy, TEntity, TChain>> extends SQLSelectAsExpression {//SQLFetcherExpression {

    TProxy getProxy();

    TChain allFields();

    default TChain allFieldsExclude(SQLColumn<TProxy, ?> ignoreColumn) {
        return allFieldsExclude(Collections.singletonList(ignoreColumn));
    }

    default TChain allFieldsExclude(SQLColumn<TProxy, ?> ignoreColumn1, SQLColumn<TProxy, ?> ignoreColumn2) {
        return allFieldsExclude(Arrays.asList(ignoreColumn1, ignoreColumn2));
    }

    default TChain allFieldsExclude(SQLColumn<TProxy, ?> ignoreColumn1, SQLColumn<TProxy, ?> ignoreColumn2, SQLColumn<TProxy, ?> ignoreColumn3) {
        return allFieldsExclude(Arrays.asList(ignoreColumn1, ignoreColumn2, ignoreColumn3));
    }

    default TChain allFieldsExclude(SQLColumn<TProxy, ?> ignoreColumn1, SQLColumn<TProxy, ?> ignoreColumn2, SQLColumn<TProxy, ?> ignoreColumn3, SQLColumn<TProxy, ?> ignoreColumn4) {
        return allFieldsExclude(Arrays.asList(ignoreColumn1, ignoreColumn2, ignoreColumn3, ignoreColumn4));
    }

    default TChain allFieldsExclude(SQLColumn<TProxy, ?> ignoreColumn1, SQLColumn<TProxy, ?> ignoreColumn2, SQLColumn<TProxy, ?> ignoreColumn3, SQLColumn<TProxy, ?> ignoreColumn4, SQLColumn<TProxy, ?> ignoreColumn5) {
        return allFieldsExclude(Arrays.asList(ignoreColumn1, ignoreColumn2, ignoreColumn3, ignoreColumn4, ignoreColumn5));
    }

    default TChain allFieldsExclude(SQLColumn<TProxy, ?> ignoreColumn1, SQLColumn<TProxy, ?> ignoreColumn2, SQLColumn<TProxy, ?> ignoreColumn3, SQLColumn<TProxy, ?> ignoreColumn4, SQLColumn<TProxy, ?> ignoreColumn5, SQLColumn<TProxy, ?> ignoreColumn6) {
        return allFieldsExclude(Arrays.asList(ignoreColumn1, ignoreColumn2, ignoreColumn3, ignoreColumn4, ignoreColumn5, ignoreColumn6));
    }

    TChain allFieldsExclude(Collection<SQLColumn<TProxy, ?>> ignoreColumns);


    default TChain valueObjectColumnExclude(SQLColumn<TProxy, ?> column, SQLColumn<TProxy, ?> ignoreColumn) {
        return valueObjectColumnExclude(column, Collections.singletonList(ignoreColumn));
    }

    default TChain valueObjectColumnExclude(SQLColumn<TProxy, ?> column, SQLColumn<TProxy, ?> ignoreColumn1, SQLColumn<TProxy, ?> ignoreColumn2) {
        return valueObjectColumnExclude(column, Arrays.asList(ignoreColumn1, ignoreColumn2));
    }

    default TChain valueObjectColumnExclude(SQLColumn<TProxy, ?> column, SQLColumn<TProxy, ?> ignoreColumn1, SQLColumn<TProxy, ?> ignoreColumn2, SQLColumn<TProxy, ?> ignoreColumn3) {
        return valueObjectColumnExclude(column, Arrays.asList(ignoreColumn1, ignoreColumn2, ignoreColumn3));
    }

    default TChain valueObjectColumnExclude(SQLColumn<TProxy, ?> column, SQLColumn<TProxy, ?> ignoreColumn1, SQLColumn<TProxy, ?> ignoreColumn2, SQLColumn<TProxy, ?> ignoreColumn3, SQLColumn<TProxy, ?> ignoreColumn4) {
        return valueObjectColumnExclude(column, Arrays.asList(ignoreColumn1, ignoreColumn2, ignoreColumn3, ignoreColumn4));
    }

    default TChain valueObjectColumnExclude(SQLColumn<TProxy, ?> column, SQLColumn<TProxy, ?> ignoreColumn1, SQLColumn<TProxy, ?> ignoreColumn2, SQLColumn<TProxy, ?> ignoreColumn3, SQLColumn<TProxy, ?> ignoreColumn4, SQLColumn<TProxy, ?> ignoreColumn5) {
        return valueObjectColumnExclude(column, Arrays.asList(ignoreColumn1, ignoreColumn2, ignoreColumn3, ignoreColumn4, ignoreColumn5));
    }

    default TChain valueObjectColumnExclude(SQLColumn<TProxy, ?> column, SQLColumn<TProxy, ?> ignoreColumn1, SQLColumn<TProxy, ?> ignoreColumn2, SQLColumn<TProxy, ?> ignoreColumn3, SQLColumn<TProxy, ?> ignoreColumn4, SQLColumn<TProxy, ?> ignoreColumn5, SQLColumn<TProxy, ?> ignoreColumn6) {
        return valueObjectColumnExclude(column, Arrays.asList(ignoreColumn1, ignoreColumn2, ignoreColumn3, ignoreColumn4, ignoreColumn5, ignoreColumn6));
    }

    TChain valueObjectColumnExclude(SQLColumn<TProxy, ?> column, Collection<SQLColumn<TProxy, ?>> ignoreColumns);

    @Override
    default TChain as(TablePropColumn propColumn) {
        return as(propColumn.getValue());
    }

    @Override
    TChain as(String propertyAlias);

    @Override
    <TEntity, TR> TChain as(Property<TEntity, TR> propertyAlias);

    TProxy fetchProxy();

//    @Override
//    TChain asc(boolean condition);
//
//    @Override
//    TChain desc(boolean condition);
}
