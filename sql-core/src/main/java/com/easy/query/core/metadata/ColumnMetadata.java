package com.easy.query.core.metadata;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.basic.extension.complex.ComplexPropType;
import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.extension.generated.GeneratedKeySQLColumnGenerator;
import com.easy.query.core.basic.extension.generated.PrimaryKeyGenerator;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.util.EasyClassUtil;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * create time 2023/2/11 18:13
 * 解析对象后获得的列元信息
 *
 * @author xuejiaming
 */
public class ColumnMetadata {
    /**
     * 所属对象元信息
     */

    private final EntityMetadata entityMetadata;
    /**
     * 数据库列名
     */
    private final String name;
    private final String fieldName;


    /**
     * 属性信息
     */
    private final PropertyDescriptor property;
    /**
     * property.getPropertyType()默认会加锁synchronized所以这边增加一个冗余字段
     */
    private final Class<?> propertyType;
    /**
     * 属性名
     */
    private final String propertyName;

    /**
     * 是否是主键
     */
    private final boolean primary;
    /**
     * 是否是数据库生成列 比如自增键
     */
    private final boolean generatedKey;


//    private  boolean nullable=true;
    /**
     * 是否是乐观锁版本号
     */
    private final boolean version;
    /**
     * 是否插入时忽略
     */
    private final boolean insertIgnore;
    /**
     * 是否更新时忽略
     */
    private final boolean updateIgnore;
    /**
     * 如果更新时忽略当前列存在track的diff中是否也要更新
     */
    private final boolean updateSetInTrackDiff;
//    /**
//     * 并发更新在追踪下
//     */
//    private final boolean concurrentUpdateInTrack;

    /**
     * 加密策略
     */
    private final EncryptionStrategy encryptionStrategy;
    /**
     * 加密后查询是否支持like
     */
    private final boolean supportQueryLike;
    /**
     * 是否是大列
     */
    private final boolean large;
    /**
     * 是否自动查询结果
     */
    private final boolean autoSelect;
    /**
     * 是否是基本类型 int long double 而不是Integer Long...
     */
    private final boolean primitive;

    /**
     * 数据库和对象值转换器
     */
    private final ValueConverter<?, ?> valueConverter;
    /**
     * 对象数据库列转换器
     */
    private final ColumnValueSQLConverter columnValueSQLConverter;
    /**
     * 数据库生成键生成器
     */
    private final GeneratedKeySQLColumnGenerator generatedSQLColumnGenerator;
    private final PrimaryKeyGenerator primaryKeyGenerator;
    /**
     * 当前对象属性setter调用方法
     */
    private final PropertySetterCaller<Object> setterCaller;
    /**
     * 当前对象属性getter调用方法
     */
    private final Property<Object, ?> getterCaller;
    /**
     * 当前属性对应的jdbc处理器
     */
    private final JdbcTypeHandler jdbcTypeHandler;
    private final ComplexPropType complexPropType;
    private final boolean valueObject;
    private final List<ColumnMetadata> valueObjectColumnMetadataList;
    private final Supplier<Object> beanConstructorCreator;

    public ColumnMetadata(ColumnOption columnOption) {
        this.entityMetadata = columnOption.getEntityMetadata();
        this.name = columnOption.getName();
        this.property = columnOption.getPropertyDescriptor();
        if (property != null) {
            this.propertyType = property.getPropertyType();
            this.primitive = propertyType.isPrimitive();
        } else {
            this.propertyType = Object.class;
            this.primitive = false;
        }
        this.propertyName = columnOption.getFullPropertyName();
        this.fieldName = columnOption.getFieldName();
        this.primary = columnOption.isPrimary();
        this.generatedKey = columnOption.isGeneratedKey();
        this.version = columnOption.isVersion();
        this.insertIgnore = columnOption.isInsertIgnore();
        this.updateIgnore = columnOption.isUpdateIgnore();
        this.updateSetInTrackDiff = columnOption.isUpdateSetInTrackDiff();
        this.encryptionStrategy = columnOption.getEncryptionStrategy();
        this.supportQueryLike = columnOption.isSupportQueryLike();
        this.large = columnOption.isLarge();
//        this.nullable=columnOption.isNullable();
        this.autoSelect = columnOption.isAutoSelect();
        this.valueConverter = columnOption.getValueConverter();
        this.columnValueSQLConverter = columnOption.getColumnValueSQLConverter();
//        this.concurrentUpdateInTrack = columnOption.isConcurrentUpdateInTrack();
        this.generatedSQLColumnGenerator = columnOption.getGeneratedKeySQLColumnGenerator();
        this.primaryKeyGenerator = columnOption.getPrimaryKeyGenerator();

        if (columnOption.getGetterCaller() == null) {
            throw new IllegalArgumentException("not found " + EasyClassUtil.getSimpleName(columnOption.getEntityMetadata().getEntityClass()) + "." + propertyName + " getter caller");
        }
        if (columnOption.getSetterCaller() == null) {
            throw new IllegalArgumentException("not found " + EasyClassUtil.getSimpleName(columnOption.getEntityMetadata().getEntityClass()) + "." + propertyName + " setter caller");
        }
        this.getterCaller = columnOption.getGetterCaller();
        this.setterCaller = columnOption.getSetterCaller();
        this.jdbcTypeHandler = columnOption.getJdbcTypeHandler();
        this.complexPropType = columnOption.getComplexPropType();
        this.valueObject = columnOption.isValueObject();
        this.beanConstructorCreator = columnOption.getBeanConstructorCreator();
        if (this.valueObject) {
            this.valueObjectColumnMetadataList = new ArrayList<>(columnOption.getValueObjectColumnOptions().size());
        } else {
            this.valueObjectColumnMetadataList = Collections.emptyList();
        }
    }

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    public String getName() {
        return name;
    }

    public boolean isPrimary() {
        return primary;
    }


    public boolean isGeneratedKey() {
        return generatedKey;
    }


    public boolean isVersion() {
        return version;
    }


    public boolean isInsertIgnore() {
        return insertIgnore;
    }


    public boolean isUpdateIgnore() {
        return updateIgnore;
    }

    public boolean isUpdateSetInTrackDiff() {
        return updateSetInTrackDiff;
    }

    public PropertyDescriptor getProperty() {
        return property;
    }


    public boolean isEncryption() {
        return encryptionStrategy != null;
    }

    public EncryptionStrategy getEncryptionStrategy() {
        return encryptionStrategy;
    }

    public boolean isSupportQueryLike() {
        return supportQueryLike;
    }


    public boolean isLarge() {
        return large;
    }

    public boolean isAutoSelect() {
        return autoSelect;
    }

    public ValueConverter<?, ?> getValueConverter() {
        return valueConverter;
    }

    public Class<?> getPropertyType() {
        return propertyType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public PropertySetterCaller<Object> getSetterCaller() {
        return setterCaller;
    }

    public Property<Object, ?> getGetterCaller() {
        return getterCaller;
    }

    public JdbcTypeHandler getJdbcTypeHandler() {
        return jdbcTypeHandler;
    }

    public ColumnValueSQLConverter getColumnValueSQLConverter() {
        return columnValueSQLConverter;
    }

    public GeneratedKeySQLColumnGenerator getGeneratedSQLColumnGenerator() {
        return generatedSQLColumnGenerator;
    }

    public @Nullable PrimaryKeyGenerator getPrimaryKeyGenerator() {
        return primaryKeyGenerator;
    }

    /**
     * 表示当前属性的复杂类型
     * 如果当前属性为基本类型那么就是基本类型
     * 如果当前属性是{@link List<Object>} 这种泛型类型通过添加 {@link ComplexPropType}来表示为正确的真实类型
     *
     * @return
     */
    public ComplexPropType getComplexPropType() {
        return complexPropType;
    }

    public boolean isValueObject() {
        return valueObject;
    }

    public List<ColumnMetadata> getValueObjectColumnMetadataList() {
        return valueObjectColumnMetadataList;
    }

    public Supplier<Object> getBeanConstructorCreatorOrNull() {
        return beanConstructorCreator;
    }

    public String getFieldName() {
        return fieldName;
    }

    //    public boolean isNullable() {
//        return nullable;
//    }
    //    public boolean isConcurrentUpdateInTrack() {
//        return concurrentUpdateInTrack;
//    }
}
