package com.easy.query.test.mysql8;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.implicit.EntityRelationPropertyProvider;
import com.easy.query.core.expression.include.getter.RelationIncludeGetter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.test.mysql8.entity.M8UserBook2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * create time 2025/3/19 16:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class FindInSetRelationToImplicitProvider implements EntityRelationPropertyProvider {
    public static final EntityRelationPropertyProvider INSTANCE = new FindInSetRelationToImplicitProvider();


    @Override
    public String getName() {
        return "find_in_set";
    }

    @Override
    public <T> ClientQueryable<T> toImplicitSubQuery(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, NavigateMetadata navigateMetadata, QueryRuntimeContext runtimeContext) {

        ClientQueryable<?> clientQueryable = runtimeContext.getSQLClientApiFactory().createQueryable(navigateMetadata.getNavigatePropertyType(), runtimeContext);
        if (navigateMetadata.getRelationType() == RelationTypeEnum.ManyToMany && navigateMetadata.getMappingClass() != null) {
            throw new UnsupportedOperationException("many to many not support find_in_set");
        } else {
            clientQueryable.where(t -> {
                String[] targetPropertiesOrPrimary = navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext);
                String[] selfPropertiesOrPrimary = navigateMetadata.getSelfPropertiesOrPrimary();
                t.and(() -> {
                    for (int i = 0; i < targetPropertiesOrPrimary.length; i++) {
                        String property = targetPropertiesOrPrimary[i];
                        String selfProperty = selfPropertiesOrPrimary[i];
                        t.sqlNativeSegment("FIND_IN_SET({0},{1})", c -> {
                            c.expression(t.getTable(), property)
                                    .expression(leftTable, selfProperty);
                        });
                    }
//                    t.multiEq(true, new SimpleEntitySQLTableOwner<>(leftTable), navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), navigateMetadata.getSelfPropertiesOrPrimary());
                    navigateMetadata.predicateFilterApply(t);
                });
            });
        }
        return EasyObjectUtil.typeCastNullable(clientQueryable);
    }

    @Override
    public TableAvailable toImplicitJoin(EntityExpressionBuilder entityExpressionBuilder, TableAvailable leftTable, String property, String fullName) {
        return null;
    }

    @Override
    public void relationMultiIdsFetcherPredicate(WherePredicate<?> targetWherePredicate, String[] targetProps, List<List<Object>> relationIds) {
        //仅支持单个属性多个属性自己去实现
        String targetProp = targetProps[0];
        String collect = relationIds.stream().filter(o -> o.get(0) != null && EasyStringUtil.isNotBlank(o.get(0).toString()))
                .flatMap(o -> Arrays.stream(o.get(0).toString().split(",")))
                .distinct()
                .collect(Collectors.joining(","));

        targetWherePredicate.sqlNativeSegment("FIND_IN_SET({0},{1})", c -> {
            c.expression(targetProp).value(collect);
        });
//        targetWherePredicate.and(() -> {
//
//            for (List<Object> relationId : relationIds) {
//                Object o = relationId.get(0);
//
//                targetWherePredicate.sqlNativeSegment("FIND_IN_SET({0},{1})", c -> {
//                    c.expression(targetProp).value(o);
//                }).or();
//            }
//        });
    }

    @Override
    public void relationMultiIdFetcherPredicate(WherePredicate<?> targetWherePredicate, String[] targetProps, List<Object> relationIds) {

        String targetProp = targetProps[0];
        Object o = relationIds.get(0);
        targetWherePredicate.sqlNativeSegment("FIND_IN_SET({0},{1})", c -> {
            c.expression(targetProp).value(o);
        });
    }

    @Override
    public RelationIncludeGetter getOneToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] selfRelationColumn, Collection<RelationExtraEntity> entities) {
        return null;
    }

    @Override
    public RelationIncludeGetter getDirectToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, List<RelationExtraEntity> includes, List<Object> mappingRow) {
        return null;
    }

    @Override
    public RelationIncludeGetter getManyToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes) {
        return null;
    }

    @Override
    public RelationIncludeGetter getOneToManyGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes) {
        return null;
    }

    @Override
    public RelationIncludeGetter getManyToManyGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes, List<Object> mappingRows) {
        return new MyFindInSetManyToMany(includes);
    }

    public static class MyFindInSetManyToMany implements RelationIncludeGetter {
        private final List<RelationExtraEntity> includes;

        public MyFindInSetManyToMany(List<RelationExtraEntity> includes) {
            this.includes = includes;
        }

        @Override
        public boolean include() {
            return true;
        }

        @Override
        public Object getIncludeValue(RelationValue relationValue) {
            ArrayList<Object> objects = new ArrayList<>();
            if (relationValue.isNull()) {
                return objects;
            }
            for (RelationExtraEntity include : includes) {
                //如果要通用自己去实现参考RelationIncludeGetter的equals实现我这边是给专用的直接强转
                M8UserBook2 entity = (M8UserBook2) include.getEntity();
                List<Object> values = relationValue.getValues();
                Object o = values.get(0);
                String[] split = o.toString().split(",");
                for (String s : split) {
                    if (entity.getBookId().equals(s)) {
                        objects.add(entity);
                        ;
                    }
                }
            }
            return objects;
        }
    }
}
