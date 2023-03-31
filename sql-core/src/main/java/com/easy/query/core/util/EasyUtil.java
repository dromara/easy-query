package com.easy.query.core.util;

import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.expression.segment.SqlEntityAliasSegment;
import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.metadata.ColumnMetadata;

import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuejiaming
 * @FileName: EasyUtil.java
 * @Description: 文件说明
 * @Date: 2023/3/4 13:12
 */
public class EasyUtil {


    private static final int FLAG_SERIALIZABLE = 1;

    private EasyUtil() {
    }

    public static EntityTableExpression getPredicateTableByOffset(EntityQueryExpression sqlEntityExpression, int offsetForward) {
        List<EntityTableExpression> tables = sqlEntityExpression.getTables();
        if (tables.isEmpty()) {
            throw new EasyQueryException("cant get current join table");
        }
        int i = getNextTableIndex(sqlEntityExpression) - 1 - offsetForward;
        return tables.get(i);
    }

    public static EntityTableExpression getCurrentPredicateTable(EntityQueryExpression sqlEntityExpression) {
        return getPredicateTableByOffset(sqlEntityExpression, 0);
    }

    public static EntityTableExpression getPreviewPredicateTable(EntityQueryExpression sqlEntityExpression) {
        return getPredicateTableByOffset(sqlEntityExpression, 1);
    }

    public static ColumnMetadata getColumnMetadata(EntityTableExpression tableExpression, String propertyName) {
        return tableExpression.getEntityMetadata().getColumnNotNull(propertyName);
    }

    public static int getNextTableIndex(EntityQueryExpression sqlEntityExpression) {
        return sqlEntityExpression.getTables().size();
    }

    public static String getAnonymousColumnName(SqlEntityAliasSegment sqlEntityProject) {
        String alias = sqlEntityProject.getAlias();
        if (StringUtil.isBlank(alias)) {
            return sqlEntityProject.getTable().getColumnName(sqlEntityProject.getPropertyName());
        }
        return alias;
    }

    private static Map<Class<?>, FastBean> CLASS_PROPERTY_FAST_BEAN_CACHE = new ConcurrentHashMap<>();

    public static FastBean getFastBean(Class<?> entityClass) {
        return CLASS_PROPERTY_FAST_BEAN_CACHE.computeIfAbsent(entityClass, key -> new FastBean(entityClass));
    }
    public static Property<Object, ?> getPropertyGetterLambda(Class<?> entityClass, String propertyName, Class<?> fieldType) {
        return getFastBean(entityClass).getBeanGetter(propertyName, fieldType);
    }
    public static PropertySetterCaller<Object> getPropertySetterLambda(Class<?> entityClass, PropertyDescriptor prop) {
        return getFastBean(entityClass).getBeanSetter(prop);
    }
}

