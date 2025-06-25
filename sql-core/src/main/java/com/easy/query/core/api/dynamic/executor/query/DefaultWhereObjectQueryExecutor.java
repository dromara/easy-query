package com.easy.query.core.api.dynamic.executor.query;

import com.easy.query.core.annotation.EasyWhereCondition;
import com.easy.query.core.common.tuple.MergeTuple2;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQueryWhereInvalidOperationException;
import com.easy.query.core.expression.builder.impl.FilterImpl;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * create time 2023/9/26 08:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultWhereObjectQueryExecutor implements WhereObjectQueryExecutor {


    private WhereObjectEntry checkStrict(EntityQueryExpressionBuilder entityQueryExpressionBuilder, boolean strictMode, String property, int tableIndex) {
        if (tableIndex < 0 || tableIndex > entityQueryExpressionBuilder.getTables().size() - 1) {
            if (strictMode) {
                throw new EasyQueryWhereInvalidOperationException("table index:[" + tableIndex + "] not found in query context");
            }
            return null;
        }
        TableAvailable entityTable = entityQueryExpressionBuilder.getTable(tableIndex).getEntityTable();
        EntityMetadata entityMetadata = entityTable.getEntityMetadata();

        ColumnMetadata columnMetadata = entityMetadata.getColumnOrNull(property);
        if (columnMetadata == null) {
            if (strictMode) {
                throw new EasyQueryWhereInvalidOperationException("property name:[" + property + "] not found query entity class:" + EasyClassUtil.getSimpleName(entityMetadata.getEntityClass()));
            }
            return null;
        }
        return new WhereObjectEntry(entityTable, property);
    }

    /**
     * element为null
     *
     * @param entityQueryExpressionBuilder
     * @param q
     * @param field
     * @return
     */
    private List<WhereObjectEntry> getQueryPropertiesOrNull(EntityQueryExpressionBuilder entityQueryExpressionBuilder, EasyWhereCondition q, Field field) {
        EasyWhereCondition.Mode mode = q.mode();
        boolean strictMode = q.strict();
        switch (mode) {
            case SINGLE: {
                int tableIndex = q.tableIndex();

                //获取映射的对象名称
                String queryPropertyName = EasyStringUtil.isNotBlank(q.propName()) ? q.propName() : EasyStringUtil.toLowerCaseFirstOne(field.getName());

                WhereObjectEntry whereObjectEntry = checkStrict(entityQueryExpressionBuilder, strictMode, queryPropertyName, tableIndex);
                if (whereObjectEntry == null) {
                    return Collections.emptyList();
                }
                return Collections.singletonList(whereObjectEntry);
            }
            case MULTI_OR: {
                String[] propNames = q.propNames();
                if (propNames == null || propNames.length == 0) {
                    throw new EasyQueryInvalidOperationException("where object mode: multi or,propNames can not be empty.");
                }
                int[] tablesIndex = buildTablesIndex(propNames, q.tablesIndex());
                List<WhereObjectEntry> whereObjectEntries = new ArrayList<>(propNames.length);
                for (int i = 0; i < propNames.length; i++) {
                    WhereObjectEntry whereObjectEntry = checkStrict(entityQueryExpressionBuilder, strictMode, propNames[i], tablesIndex[i]);
                    if (whereObjectEntry == null) {
                        continue;
                    }
                    whereObjectEntries.add(whereObjectEntry);
                }
                return whereObjectEntries;
            }
        }
        throw new EasyQueryInvalidOperationException("cant support where object mode:" + mode);

    }

    /**
     * 返回大于等于propNames长度的tableIndex索引数组
     *
     * @param propNames
     * @param tablesIndex
     * @return
     */
    private int[] buildTablesIndex(String[] propNames, int[] tablesIndex) {
        if (tablesIndex == null) {
            return new int[propNames.length];
        } else if (propNames.length != tablesIndex.length) {
            if (propNames.length < tablesIndex.length) {
                return tablesIndex;
            }
            int[] mergedIndex = new int[propNames.length];
            System.arraycopy(tablesIndex, 0, mergedIndex, 0, tablesIndex.length);
            return mergedIndex;
        }
        return tablesIndex;
    }

    @Override
    public void whereObject(Object object, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {

        Collection<Field> allFields = EasyClassUtil.getAllFields(object.getClass());


        FilterImpl filter = new FilterImpl(entityQueryExpressionBuilder.getRuntimeContext(), entityQueryExpressionBuilder.getExpressionContext(), entityQueryExpressionBuilder.getWhere(), false, entityQueryExpressionBuilder.getExpressionContext().getValueFilter());

        for (Field field : allFields) {
            boolean accessible = field.isAccessible();

            try {
                field.setAccessible(true);

                EasyWhereCondition q = field.getAnnotation(EasyWhereCondition.class);
                if (q == null) {
                    continue;
                }

                Object val = field.get(object);

                if (Objects.isNull(val)) {
                    continue;
                }
                if (val instanceof String) {
                    if (EasyStringUtil.isBlank(String.valueOf(val)) && !q.allowEmptyStrings()) {
                        continue;
                    }
                }
                List<WhereObjectEntry> queries = getQueryPropertiesOrNull(entityQueryExpressionBuilder, q, field);
                if (EasyCollectionUtil.isEmpty(queries)) {
                    continue;
                }

                switch (q.type()) {
                    case EQUAL: {
                        if (queries.size() > 1) {
                            filter.and(x -> {
                                for (WhereObjectEntry whereObjectEntry : queries) {
                                    x.eq(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), val).or();
                                }
                            });
                        } else {
                            filter.eq(queries.get(0).getTable(), queries.get(0).getProperty(), val);
                        }
                    }
                    break;
                    case GREATER_THAN:
                    case RANGE_LEFT_OPEN: {
                        if (queries.size() > 1) {
                            filter.and(x -> {
                                for (WhereObjectEntry whereObjectEntry : queries) {
                                    x.gt(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), val).or();
                                }
                            });
                        } else {
                            filter.gt(queries.get(0).getTable(), queries.get(0).getProperty(), val);
                        }
                    }
                    break;
                    case LESS_THAN:
                    case RANGE_RIGHT_OPEN: {
                        if (queries.size() > 1) {
                            filter.and(x -> {
                                for (WhereObjectEntry whereObjectEntry : queries) {
                                    x.lt(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), val).or();
                                }
                            });
                        } else {
                            filter.lt(queries.get(0).getTable(), queries.get(0).getProperty(), val);
                        }
                    }
                    break;
                    case LIKE:
                    case LIKE_MATCH_LEFT:
                    case LIKE_MATCH_RIGHT: {
                        SQLLikeEnum sqlLike = getSQLLike(q.type());
                        if (queries.size() > 1) {
                            filter.and(x -> {
                                for (WhereObjectEntry whereObjectEntry : queries) {
                                    x.like(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), val, sqlLike).or();
                                }
                            });
                        } else {
                            filter.like(queries.get(0).getTable(), queries.get(0).getProperty(), val, sqlLike);
                        }
                    }
                    break;
                    case GREATER_THAN_EQUAL:
                    case RANGE_LEFT_CLOSED: {
                        if (queries.size() > 1) {
                            filter.and(x -> {
                                for (WhereObjectEntry whereObjectEntry : queries) {
                                    x.ge(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), val).or();
                                }
                            });
                        } else {
                            filter.ge(queries.get(0).getTable(), queries.get(0).getProperty(), val);
                        }
                    }
                    break;
                    case LESS_THAN_EQUAL:
                    case RANGE_RIGHT_CLOSED: {
                        if (queries.size() > 1) {
                            filter.and(x -> {
                                for (WhereObjectEntry whereObjectEntry : queries) {
                                    x.le(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), val).or();
                                }
                            });
                        } else {
                            filter.le(queries.get(0).getTable(), queries.get(0).getProperty(), val);
                        }
                    }
                    break;
                    case IN:
                        if (val.getClass().isArray()) {
                            if (EasyArrayUtil.isNotEmpty((Object[]) val)) {
                                if (queries.size() > 1) {
                                    filter.and(x -> {
                                        for (WhereObjectEntry whereObjectEntry : queries) {
                                            x.in(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), (Object[]) val).or();
                                        }
                                    });
                                } else {
                                    filter.in(queries.get(0).getTable(), queries.get(0).getProperty(), (Object[]) val);
                                }
                            }
                        } else {
                            if (EasyCollectionUtil.isNotEmpty((Collection<?>) val)) {
                                if (queries.size() > 1) {
                                    filter.and(x -> {
                                        for (WhereObjectEntry whereObjectEntry : queries) {
                                            x.in(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), (Collection<?>) val).or();
                                        }
                                    });
                                } else {
                                    filter.in(queries.get(0).getTable(), queries.get(0).getProperty(), (Collection<?>) val);
                                }
                            }
                        }
                        break;
                    case NOT_IN:
                        if (val.getClass().isArray()) {
                            if (EasyArrayUtil.isNotEmpty((Object[]) val)) {
                                if (queries.size() > 1) {
                                    filter.and(x -> {
                                        for (WhereObjectEntry whereObjectEntry : queries) {
                                            x.notIn(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), (Object[]) val).or();
                                        }
                                    });
                                } else {
                                    filter.notIn(queries.get(0).getTable(), queries.get(0).getProperty(), (Object[]) val);
                                }
                            }
                        } else {
                            if (EasyCollectionUtil.isNotEmpty((Collection<?>) val)) {
                                if (queries.size() > 1) {
                                    filter.and(x -> {
                                        for (WhereObjectEntry whereObjectEntry : queries) {
                                            x.notIn(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), (Collection<?>) val).or();
                                        }
                                    });
                                } else {
                                    filter.notIn(queries.get(0).getTable(), queries.get(0).getProperty(), (Collection<?>) val);
                                }
                            }
                        }
                        break;
                    case NOT_EQUAL: {
                        if (queries.size() > 1) {
                            filter.and(x -> {
                                for (WhereObjectEntry whereObjectEntry : queries) {
                                    x.ne(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), val).or();
                                }
                            });
                        } else {
                            filter.ne(queries.get(0).getTable(), queries.get(0).getProperty(), val);
                        }
                    }
                    break;
                    case COLLECTION_EQUAL_OR: {
                        if (val.getClass().isArray()) {
                            if (EasyArrayUtil.isNotEmpty((Object[]) val)) {

                                if (queries.size() > 1) {
                                    filter.and(x -> {
                                        for (WhereObjectEntry whereObjectEntry : queries) {
                                            for (Object o : (Object[]) val) {
                                                x.eq(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), o).or();
                                            }
                                        }
                                    });
                                } else {
                                    filter.and(f -> {
                                        for (Object o : (Object[]) val) {
                                            f.eq(queries.get(0).getTable(), queries.get(0).getProperty(), o).or();
                                        }
                                    });
                                }
                            }
                        } else {
                            if (EasyCollectionUtil.isNotEmpty((Collection<?>) val)) {
                                if (queries.size() > 1) {
                                    filter.and(x -> {
                                        for (WhereObjectEntry whereObjectEntry : queries) {
                                            for (Object o : (Collection<?>) val) {
                                                x.eq(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), o).or();
                                            }
                                        }
                                    });
                                } else {
                                    filter.and(f -> {
                                        for (Object o : (Collection<?>) val) {
                                            f.eq(queries.get(0).getTable(), queries.get(0).getProperty(), o).or();
                                        }
                                    });
                                }
                            }
                        }
                    }
                    break;
                    case RANGE_OPEN:
                    case RANGE_CLOSED:
                    case RANGE_CLOSED_OPEN:
                    case RANGE_OPEN_CLOSED: {
                        MergeTuple2<SQLPredicateCompareEnum, SQLPredicateCompareEnum> sqlPredicateCompareEnum = EasyWhereCondition.Condition.getSQLPredicateCompareEnum(q.type());
                        if (val.getClass().isArray()) {
                            Object[] valArray = (Object[]) val;
                            if (EasyArrayUtil.isNotEmpty(valArray)) {
                                Object[] pairArray = {valArray[0], null};
                                if (valArray.length > 1) {
                                    pairArray[1] = valArray[1];
                                }

                                rangePairArray(pairArray, queries, filter, sqlPredicateCompareEnum);
                            }
                        } else {
                            Collection<?> valCollection = (Collection<?>) val;
                            if (EasyCollectionUtil.isNotEmpty(valCollection)) {
                                Object[] pairArray = getPairArray(valCollection);
                                rangePairArray(pairArray, queries, filter, sqlPredicateCompareEnum);
                            }
                        }
                    }
                    break;
                    default:
                        break;
                }

            } catch (Exception e) {
                throw new EasyQueryException(e);
            } finally {
                field.setAccessible(accessible);
            }

        }
    }

    //数组处理
    private void rangePairArray(Object[] pairArray, List<WhereObjectEntry> queries, FilterImpl filter, MergeTuple2<SQLPredicateCompareEnum, SQLPredicateCompareEnum> sqlPredicateCompareEnum) {

        if (queries.size() > 1) {
            filter.and(x -> {
                for (WhereObjectEntry whereObjectEntry : queries) {
                    if (pairArray[1] != null) {
                        x.compare(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), pairArray[0], sqlPredicateCompareEnum.t1)
                                .and()
                                .compare(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), pairArray[1], sqlPredicateCompareEnum.t2);
                    } else {
                        x.compare(whereObjectEntry.getTable(), whereObjectEntry.getProperty(), pairArray[0], sqlPredicateCompareEnum.t1);
                    }
                }
            });
        } else {
            filter.and(f -> {
                if (pairArray[1] != null) {
                    f.compare(queries.get(0).getTable(), queries.get(0).getProperty(), pairArray[0], sqlPredicateCompareEnum.t1)
                            .and()
                            .compare(queries.get(0).getTable(), queries.get(0).getProperty(), pairArray[1], sqlPredicateCompareEnum.t2);
                } else {
                    f.compare(queries.get(0).getTable(), queries.get(0).getProperty(), pairArray[0], sqlPredicateCompareEnum.t1);
                }
            });
        }
    }

    private Object[] getPairArray(Collection<?> collection) {
        Iterator<?> iterator = collection.iterator();
        Object first = iterator.next();
        Object[] array = {first, null};
        if (iterator.hasNext()) {
            array[1] = iterator.next();
        }
        return array;
    }

    private SQLLikeEnum getSQLLike(EasyWhereCondition.Condition like) {
        switch (like) {
            case LIKE:
                return SQLLikeEnum.LIKE_PERCENT_ALL;
            case LIKE_MATCH_LEFT:
                return SQLLikeEnum.LIKE_PERCENT_RIGHT;
            case LIKE_MATCH_RIGHT:
                return SQLLikeEnum.LIKE_PERCENT_LEFT;
        }
        throw new UnsupportedOperationException("where object cant get sql like");
    }
}
