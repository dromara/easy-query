package com.easy.query.core.api.dynamic.executor.search.meta;

import com.easy.query.core.annotation.EasyCond;
import com.easy.query.core.api.dynamic.executor.search.EasySearchConfiguration;
import com.easy.query.core.api.dynamic.executor.search.exception.EasySearchInternalException;
import com.easy.query.core.api.dynamic.executor.search.match.EasyTableMatch;
import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用于存储搜索条件的元数据，包含所有的条件元数据，便于在查询时进行条件解析和执行
 *
 * @author bkbits
 */
public class EasySearchMetaData {
    private final Map<String, EasyCondMetaData> conds;

    private EasySearchMetaData(Map<String, EasyCondMetaData> conds) {
        this.conds = Collections.unmodifiableMap(conds);
    }

    public static EasySearchMetaData of(
            @NotNull EasySearchConfiguration configuration,
            @NotNull EntityMetadataManager entityMetadataManager,
            @NotNull Class<?> searchClass
    ) {
        EntityMetadata dtoEntityMetadata = entityMetadataManager.getEntityMetadata(searchClass);
        EasyTableMatch defaultTableMatch = null;
        EasyCond typeCond = searchClass.getAnnotation(EasyCond.class);
        if (typeCond != null) {//类型有@EasyCond注解，根据注解来获取匹配器
            defaultTableMatch = EasyTableMatch.of(
                    typeCond.table(),
                    typeCond.tableAlias(),
                    typeCond.tableIndex()
            );
        }
        if (defaultTableMatch == null) {//默认匹配器为null，直接将当前类当做实体类
            defaultTableMatch = EasyTableMatch.of(searchClass);
        }

        boolean defaultEnabled = configuration.getOption().isDefaultEnabled();

        try {
            Map<String, EasyCondMetaData> fields = new LinkedHashMap<>();
            Class<?> fieldClass = searchClass;
            do {
                for (Field field : fieldClass.getDeclaredFields()) {
                    EasyCond cond = field.getDeclaredAnnotation(EasyCond.class);

                    //判断属性是否启用搜索
                    if (cond == null) { //未明确指定属性搜索开关
                        if (typeCond == null) { //未明确指定类型搜索开关
                            if (!defaultEnabled) { //默认禁用
                                continue;
                            }
                        }
                        else if (!typeCond.value()) { //明确指定类型搜索禁用
                            continue;
                        }
                    }
                    else if (!cond.value()) { //明确指定属性搜索禁用
                        continue;
                    }

                    EntityMetadata entityMetadata;
                    if (cond != null && !cond.table().equals(Object.class)) {
                        entityMetadata = entityMetadataManager.getEntityMetadata(cond.table());
                    }
                    else if (typeCond != null && !typeCond.table().equals(Object.class)) {
                        entityMetadata = entityMetadataManager.getEntityMetadata(typeCond.table());
                    }
                    else {
                        entityMetadata = dtoEntityMetadata;
                    }

                    String propertyName = null;
                    if (cond != null) {
                        propertyName = cond.property();
                    }
                    if (propertyName == null || (propertyName = propertyName.trim()).isEmpty()) {
                        propertyName = field.getName();
                    }

                    ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
                    EasyCondMetaData condMetaData = EasyCondMetaData.of(
                            columnMetadata,
                            field,
                            defaultTableMatch
                    );
                    fields.put(condMetaData.getName(), condMetaData);
                }

            } while ((fieldClass = fieldClass.getSuperclass()) != null);

            return new EasySearchMetaData(fields);
        } catch (Exception e) {
            throw new EasySearchInternalException("创建元数据失败: " + searchClass.getName(), e);
        }
    }

    /**
     * @param name 条件参数名
     * @return 条件元数据
     */
    public EasyCondMetaData getCond(String name) {
        return conds.get(name);
    }

    /**
     * @return 所有条件元数据
     */
    public Collection<EasyCondMetaData> getConds() {
        return conds.values();
    }
}
