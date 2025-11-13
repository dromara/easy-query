package com.easy.query.core.expression.include;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.sql.include.IncludeParserResult;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.NavigateFlatMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * create time 2024/5/15 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyFlatIncludeProcessor extends EasyIncludeProcess {
    private final NavigateFlatMetadata navigateFlatMetadata;
    private Property<Object, Collection<?>> navigateFlatGetter;
    private final boolean flatClassObject;

    public EasyFlatIncludeProcessor(NavigateFlatMetadata navigateFlatMetadata, IncludeParserResult includeParserResult, QueryRuntimeContext runtimeContext) {
        super(includeParserResult, runtimeContext);
        this.navigateFlatMetadata = navigateFlatMetadata;
        this.flatClassObject = !navigateFlatMetadata.isToMany() && !navigateFlatMetadata.isBasicType();
        //生成多个targetNaviaget在process的时候for调用
        initNavigateFlatGetter(includeParserResult, includeParserResult.getFlatQueryEntityMetadata(), navigateFlatMetadata, runtimeContext);

    }

    public void initNavigateFlatGetter(IncludeParserResult includeParserResult, EntityMetadata flatQueryEntityMetadata, NavigateFlatMetadata navigateFlatMetadata, QueryRuntimeContext runtimeContext) {
        if (navigateFlatMetadata == null) {
            return;
        }
        boolean basicType = navigateFlatMetadata.isBasicType();
        EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
        Iterator<String> mappingPathIterator = Arrays.stream(navigateFlatMetadata.getMappingPath()).iterator();
        String firstNavValue = mappingPathIterator.next();
        List<Property<Object, ?>> replyExpressions = new ArrayList<>();
        NavigateMetadata currentNavigateMetadata = flatQueryEntityMetadata.getNavigateNotNull(firstNavValue);
        EntityMetadata currentEntityMetadata = entityMetadataManager.getEntityMetadata(currentNavigateMetadata.getNavigatePropertyType());
//        replyExpressions.add(currentNavigateMetadata.getGetter());
        while (mappingPathIterator.hasNext()) {
            String currentNavValue = mappingPathIterator.next();
            if (!mappingPathIterator.hasNext()) {
                if (basicType) {
                    ColumnMetadata columnMetadata = currentEntityMetadata.getColumnNotNull(currentNavValue);
                    replyExpressions.add(columnMetadata.getGetterCaller());
                    break;
                }
            }
            currentNavigateMetadata = currentEntityMetadata.getNavigateNotNull(currentNavValue);
            currentEntityMetadata = entityMetadataManager.getEntityMetadata(currentNavigateMetadata.getNavigatePropertyType());
            replyExpressions.add(currentNavigateMetadata.getGetter());
        }
        this.navigateFlatGetter = obj -> {
            if (obj == null) {
                return null;
            }
            Iterator<Property<Object, ?>> iterator = replyExpressions.iterator();
            Property<Object, ?> first = iterator.next();
            Collection<Object> collectionValues = getCollectionValue(obj, first);
            while (iterator.hasNext()) {
                Property<Object, ?> getter = iterator.next();
                collectionValues = collectionValues.stream().map(o -> {
                            return getCollectionValue(o, getter);
                        }).flatMap(o -> o.stream()).filter(o -> o != null)
                        .distinct().collect(Collectors.toList());
            }
            return collectionValues;
        };
    }

    private Collection<Object> getCollectionValue(Object obj, Property<Object, ?> getter) {
        Object value = getter.apply(obj);
        if (value == null) {
            return Collections.emptyList();
        }
        if (value instanceof Collection) {
            return (Collection<Object>) value;
        } else {
            return Collections.singletonList(value);
        }
    }

    @Override
    protected <T> void setEntityValue(T entity, Object value) {
        if (navigateFlatMetadata != null) {
            Collection<?> values = null;
            if (value instanceof Collection) {
                values = ((Collection<?>) value).stream().map(o -> navigateFlatGetter.apply(o)).flatMap(o -> o.stream()).distinct().collect(Collectors.toList());
            } else {
                values = navigateFlatGetter.apply(value);
            }
            if (navigateFlatMetadata.isToMany()) {
                navigateFlatMetadata.getBeanSetter().call(entity, values);
            } else {
                Object val = EasyCollectionUtil.firstOrNull(values);
                if (flatClassObject) {
                    Object target = includeParserResult.getFlatClassMap().get(val);
                    navigateFlatMetadata.getBeanSetter().call(entity, target);
                } else {
                    ValueConverter<?, ?> valueConverter = navigateFlatMetadata.getValueConverter();
                    if (valueConverter != null) {
                        Object deserialize = valueConverter.deserialize(EasyObjectUtil.typeCastNullable(val), navigateFlatMetadata.getColumnMetadata());
                        navigateFlatMetadata.getBeanSetter().call(entity, deserialize);
                    } else {
                        navigateFlatMetadata.getBeanSetter().call(entity, val);
                    }
                }
            }
        } else {
            super.setEntityValue(entity, value);
        }
    }
}
