package com.easy.query.core.metadata;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.extension.track.update.TrackValueUpdate;

import java.beans.PropertyDescriptor;

/**
 * @FileName: ColumnMetadata.java
 * @Description: 文件说明
 * @Date: 2023/2/11 15:13
 * @author xuejiaming
 */
public class ColumnMetadata {

    private final EntityMetadata entityMetadata;
    private final String name;


    private final PropertyDescriptor property;
    /**
     * property.getPropertyType()默认会加锁synchronized所以这边增加一个冗余字段
     */
    private final Class<?> propertyType;

    private final  boolean primary;
    private final  boolean increment;


//    private  boolean nullable=true;
    private final  boolean version;
    private final  boolean insertIgnore;
    private final  boolean updateIgnore ;

    private final EncryptionStrategy encryptionStrategy;
    private final  boolean supportQueryLike;
    private final  boolean large;

    private final ValueConverter<?, ?> valueConverter;
    private final TrackValueUpdate<Object> trackValueUpdate;

    public ColumnMetadata(ColumnOption columnOption) {
        this.entityMetadata = columnOption.getEntityMetadata();
        this.name = columnOption.getName();
        this.property= columnOption.getProperty();
        this.propertyType= columnOption.getProperty().getPropertyType();
        this.primary= columnOption.isPrimary();
        this.increment= columnOption.isIncrement();
        this.version= columnOption.isVersion();
        this.insertIgnore= columnOption.isInsertIgnore();
        this.updateIgnore= columnOption.isUpdateIgnore();
        this.encryptionStrategy= columnOption.getEncryptionStrategy();
        this.supportQueryLike= columnOption.isSupportQueryLike();
        this.large= columnOption.isLarge();
        this.valueConverter = columnOption.getValueConverter();
        this.trackValueUpdate = columnOption.getTrackValueUpdate();
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

    public TrackValueUpdate<Object> getTrackValueUpdate() {
        return trackValueUpdate;
    }

    public Class<?> getPropertyType() {
        return propertyType;
    }
}
