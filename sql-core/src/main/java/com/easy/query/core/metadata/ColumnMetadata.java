package com.easy.query.core.metadata;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.extension.increment.IncrementSQLColumnGenerator;
import com.easy.query.core.basic.extension.track.update.ValueUpdateAtomicTrack;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.util.EasyClassUtil;

import java.beans.PropertyDescriptor;

/**
 * @FileName: ColumnMetadata.java
 * @Description: 文件说明
 * @Date: 2023/2/11 15:13
 * @author xuejiaming
 */
public class ColumnMetadata {

    private final EntityMetadata entityMetadata;
    /**
     * 数据库列名
     */
    private final String name;


    private final PropertyDescriptor property;
    /**
     * property.getPropertyType()默认会加锁synchronized所以这边增加一个冗余字段
     */
    private final Class<?> propertyType;
    private final String propertyName;

    private final  boolean primary;
    private final  boolean increment;


//    private  boolean nullable=true;
    private final  boolean version;
    private final  boolean insertIgnore;
    private final  boolean updateIgnore ;

    private final EncryptionStrategy encryptionStrategy;
    private final  boolean supportQueryLike;
    private final  boolean large;
    private final  boolean primitive;

    private final ValueConverter<?, ?> valueConverter;
    private final ColumnValueSQLConverter columnValueSQLConverter;
    private final ValueUpdateAtomicTrack<Object> valueUpdateAtomicTrack;
    private final IncrementSQLColumnGenerator incrementSQLColumnGenerator;
    private final PropertySetterCaller<Object> setterCaller;
    private final Property<Object,?> getterCaller;
    private final JdbcTypeHandler jdbcTypeHandler;

    public ColumnMetadata(ColumnOption columnOption) {
        this.entityMetadata = columnOption.getEntityMetadata();
        this.name = columnOption.getName();
        this.property= columnOption.getProperty();
        this.propertyType = columnOption.getProperty().getPropertyType();
        this.propertyName = columnOption.getProperty().getName();
        this.primary = columnOption.isPrimary();
        this.increment= columnOption.isIncrement();
        this.version= columnOption.isVersion();
        this.insertIgnore= columnOption.isInsertIgnore();
        this.updateIgnore= columnOption.isUpdateIgnore();
        this.encryptionStrategy= columnOption.getEncryptionStrategy();
        this.supportQueryLike= columnOption.isSupportQueryLike();
        this.large= columnOption.isLarge();
        this.valueConverter = columnOption.getValueConverter();
        this.columnValueSQLConverter = columnOption.getColumnValueSQLConverter();
        this.valueUpdateAtomicTrack = columnOption.getValueUpdateAtomicTrack();
        this.incrementSQLColumnGenerator = columnOption.getIncrementSQLColumnGenerator();
        this.primitive = propertyType.isPrimitive();

        if(columnOption.getGetterCaller()==null){
            throw new IllegalArgumentException("not found "+ EasyClassUtil.getSimpleName(columnOption.getEntityMetadata().getEntityClass()) +"."+propertyName+" getter caller");
        }
        if(columnOption.getSetterCaller()==null){
            throw new IllegalArgumentException("not found "+ EasyClassUtil.getSimpleName(columnOption.getEntityMetadata().getEntityClass()) +"."+propertyName+" setter caller");
        }
        this.getterCaller = columnOption.getGetterCaller();
        this.setterCaller = columnOption.getSetterCaller();
        this.jdbcTypeHandler=columnOption.getJdbcTypeHandler();
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


    public boolean isIncrement() {
        return increment;
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

    public PropertyDescriptor getProperty() {
        return property;
    }


    public boolean isEncryption() {
        return encryptionStrategy!=null;
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


    public ValueConverter<?, ?> getValueConverter() {
        return valueConverter;
    }

    public ValueUpdateAtomicTrack<Object> getValueUpdateAtomicTrack() {
        return valueUpdateAtomicTrack;
    }

    public Class<?> getPropertyType() {
        return propertyType;
    }

    public String getPropertyName() {
        return propertyName;
    }
    public boolean isPrimitive(){
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

    public ColumnValueSQLConverter getColumnValueSQLConverter(){
        return columnValueSQLConverter;
    }

    public IncrementSQLColumnGenerator getIncrementSQLColumnGenerator() {
        return incrementSQLColumnGenerator;
    }
}
