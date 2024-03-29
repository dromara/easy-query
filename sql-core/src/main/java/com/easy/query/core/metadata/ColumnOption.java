package com.easy.query.core.metadata;

import com.easy.query.core.basic.extension.complex.ComplexPropType;
import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultValueConverter;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.extension.generated.GeneratedKeySQLColumnGenerator;
import com.easy.query.core.basic.extension.track.update.ValueUpdateAtomicTrack;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * create time 2023/5/20 22:41
 * 文件说明
 *
 * @author xuejiaming
 */
public final class ColumnOption {

    private final boolean tableEntity;
    private final EntityMetadata entityMetadata;
    private final String name;


    private PropertyDescriptor property;

    private boolean primary = false;
    private boolean generatedKey = false;


    //    private  boolean nullable=true;
    private boolean version = false;
    private boolean insertIgnore = false;
    private boolean updateIgnore = false;
    private boolean updateSetInTrackDiff = false;

    private EncryptionStrategy encryptionStrategy;
    private boolean supportQueryLike = false;
    private boolean large = false;
    private boolean autoSelect = true;
    private boolean realColumn = true;

    private ValueConverter<?, ?> valueConverter;
    private ComplexPropType complexPropType;
    private ColumnValueSQLConverter columnValueSQLConverter;
    private ValueUpdateAtomicTrack<Object> valueUpdateAtomicTrack;
    private GeneratedKeySQLColumnGenerator generatedKeySQLColumnGenerator;
    private PropertySetterCaller<Object> setterCaller;
    private Property<Object, ?> getterCaller;
    private JdbcTypeHandler jdbcTypeHandler;

    private boolean valueObject;
    private Supplier<Object> beanConstructorCreator;
    private final List<ColumnOption> valueObjectColumnOptions;
    private String fullPropertyName;


    public ColumnOption(boolean tableEntity,EntityMetadata entityMetadata, String name) {
        this.tableEntity = tableEntity;
        this.entityMetadata = entityMetadata;
        this.name = name;
        this.valueConverter = DefaultValueConverter.INSTANCE;
        this.valueUpdateAtomicTrack = null;
        this.valueObject = false;
        this.valueObjectColumnOptions = new ArrayList<>();
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

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isGeneratedKey() {
        return generatedKey;
    }

    public void setGeneratedKey(boolean generatedKey) {
        this.generatedKey = generatedKey;
    }

    public boolean isVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

    public boolean isInsertIgnore() {
        return insertIgnore;
    }

    public void setInsertIgnore(boolean insertIgnore) {
        this.insertIgnore = insertIgnore;
    }

    public boolean isUpdateIgnore() {
        return updateIgnore;
    }

    public void setUpdateIgnore(boolean updateIgnore) {
        this.updateIgnore = updateIgnore;
    }

    public boolean isUpdateSetInTrackDiff() {
        return updateSetInTrackDiff;
    }

    public void setUpdateSetInTrackDiff(boolean updateSetInTrackDiff) {
        this.updateSetInTrackDiff = updateSetInTrackDiff;
    }

    public PropertyDescriptor getProperty() {
        return property;
    }

    public void setProperty(PropertyDescriptor property) {
        this.property = property;
    }

    public boolean isEncryption() {
        return encryptionStrategy != null;
    }

    public EncryptionStrategy getEncryptionStrategy() {
        return encryptionStrategy;
    }

    public void setEncryptionStrategy(EncryptionStrategy encryptionStrategy) {
        this.encryptionStrategy = encryptionStrategy;
    }

    public boolean isSupportQueryLike() {
        return supportQueryLike;
    }

    public void setSupportQueryLike(boolean supportQueryLike) {
        this.supportQueryLike = supportQueryLike;
    }

    public boolean isLarge() {
        return large;
    }

    public void setLarge(boolean large) {
        this.large = large;
    }

    public boolean isAutoSelect() {
        return autoSelect;
    }

    public void setAutoSelect(boolean autoSelect) {
        this.autoSelect = autoSelect;
    }

    public ValueConverter<?, ?> getValueConverter() {
        return valueConverter;
    }

    public void setValueConverter(ValueConverter<?, ?> valueConverter) {
        this.valueConverter = valueConverter;
    }

    public ColumnValueSQLConverter getColumnValueSQLConverter() {
        return columnValueSQLConverter;
    }

    public void setColumnValueSQLConverter(ColumnValueSQLConverter columnValueSQLConverter) {
        this.columnValueSQLConverter = columnValueSQLConverter;
    }

    public ValueUpdateAtomicTrack<Object> getValueUpdateAtomicTrack() {
        return valueUpdateAtomicTrack;
    }

    public void setValueUpdateAtomicTrack(ValueUpdateAtomicTrack<Object> valueUpdateAtomicTrack) {
        this.valueUpdateAtomicTrack = valueUpdateAtomicTrack;
    }

    public PropertySetterCaller<Object> getSetterCaller() {
        return setterCaller;
    }

    public void setSetterCaller(PropertySetterCaller<Object> setterCaller) {
        this.setterCaller = setterCaller;
    }

    public Property<Object, ?> getGetterCaller() {
        return getterCaller;
    }

    public void setGetterCaller(Property<Object, ?> getterCaller) {
        this.getterCaller = getterCaller;
    }

    public JdbcTypeHandler getJdbcTypeHandler() {
        return jdbcTypeHandler;
    }

    public void setJdbcTypeHandler(JdbcTypeHandler jdbcTypeHandler) {
        this.jdbcTypeHandler = jdbcTypeHandler;
    }

    public GeneratedKeySQLColumnGenerator getGeneratedKeySQLColumnGenerator() {
        return generatedKeySQLColumnGenerator;
    }

    public void setGeneratedKeySQLColumnGenerator(GeneratedKeySQLColumnGenerator generatedKeySQLColumnGenerator) {
        this.generatedKeySQLColumnGenerator = generatedKeySQLColumnGenerator;
    }

    public ComplexPropType getComplexPropType() {
        return complexPropType;
    }

    public void setComplexPropType(ComplexPropType complexPropType) {
        this.complexPropType = complexPropType;
    }

    public boolean isValueObject() {
        return valueObject;
    }

    public void setValueObject(boolean valueObject) {
        this.valueObject = valueObject;
    }

    public List<ColumnOption> getValueObjectColumnOptions() {
        return valueObjectColumnOptions;
    }

    public boolean isTableEntity() {
        return tableEntity;
    }

    public String getFullPropertyName() {
        return fullPropertyName;
    }

    public void setFullPropertyName(String fullPropertyName) {
        this.fullPropertyName = fullPropertyName;
    }

    public Supplier<Object> getBeanConstructorCreator() {
        return beanConstructorCreator;
    }

    public void setBeanConstructorCreator(Supplier<Object> beanConstructorCreator) {
        this.beanConstructorCreator = beanConstructorCreator;
    }

    public boolean isRealColumn() {
        return realColumn;
    }

    public void setRealColumn(boolean realColumn) {
        this.realColumn = realColumn;
    }
}
