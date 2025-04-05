package com.easy.query.core.metadata;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.IncludeRelationIdAvailable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/7/15 20:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class IncludeNavigateParams implements IncludeRelationIdAvailable {
    private final List<List<Object>> relationIds;
    private NavigateMetadata navigateMetadata;
    private SQLFuncExpression<ClientQueryable<?>> mappingQueryableFunction;
    private SQLExpression1<ClientQueryable<?>> adapterExpression;
    private TableAvailable table;
    private Integer relationGroupSize;
    private boolean limit;
    private boolean replace=true;
    private boolean mappingFlat=false;
    private List<NavigateFlatMetadata> navigateFlatMetadataList;
    private EntityMetadata flatQueryEntityMetadata;
    private Class<?> flatClassType;
    public IncludeNavigateParams(){
        relationIds=new ArrayList<>();
    }

    public @NotNull List<List<Object>> getRelationIds() {
        return relationIds;
    }

    public NavigateMetadata getNavigateMetadata() {
        return navigateMetadata;
    }

    public void setNavigateMetadata(NavigateMetadata navigateMetadata) {
        this.navigateMetadata = navigateMetadata;
    }

    public SQLFuncExpression<ClientQueryable<?>> getMappingQueryableFunction() {
        return mappingQueryableFunction;
    }

    public void setMappingQueryableFunction(SQLFuncExpression<ClientQueryable<?>> mappingQueryableFunc) {
        this.mappingQueryableFunction = mappingQueryableFunc;
    }

    public TableAvailable getTable() {
        return table;
    }

    public void setTable(TableAvailable table) {
        this.table = table;
    }

    public void setRelationGroupSize(Integer relationGroupSize) {
        this.relationGroupSize = relationGroupSize;
    }

    public Integer getQueryRelationGroupSize(){
        return this.relationGroupSize;
    }

    public boolean isLimit() {
        return limit;
    }

    public void setLimit(boolean limit) {
        this.limit = limit;
    }

    public boolean isReplace() {
        return replace;
    }

    public void setReplace(boolean replace) {
        this.replace = replace;
    }

    public boolean isMappingFlat() {
        return mappingFlat;
    }

    public void setMappingFlat(boolean mappingFlat) {
        this.mappingFlat = mappingFlat;
    }

    public List<NavigateFlatMetadata> getNavigateFlatMetadataList() {
        return navigateFlatMetadataList;
    }

    public void setNavigateFlatMetadataList(List<NavigateFlatMetadata> navigateFlatMetadataList) {
        this.navigateFlatMetadataList = navigateFlatMetadataList;
    }

    public EntityMetadata getFlatQueryEntityMetadata() {
        return flatQueryEntityMetadata;
    }

    public void setFlatQueryEntityMetadata(EntityMetadata flatQueryEntityMetadata) {
        this.flatQueryEntityMetadata = flatQueryEntityMetadata;
    }

    public Class<?> getFlatClassType() {
        return flatClassType;
    }

    public void setFlatClassType(Class<?> flatClassType) {
        this.flatClassType = flatClassType;
    }

    public SQLExpression1<ClientQueryable<?>> getAdapterExpression() {
        return adapterExpression;
    }

    public void setAdapterExpression(SQLExpression1<ClientQueryable<?>> adapterExpression) {
        this.adapterExpression = adapterExpression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncludeNavigateParams that = (IncludeNavigateParams) o;
        return Objects.equals(navigateMetadata, that.navigateMetadata) && Objects.equals(table, that.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(navigateMetadata, table);
    }
}
