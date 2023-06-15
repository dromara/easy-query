package com.easy.query.core.metadata.bean;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.ColumnIgnore;
import com.easy.query.core.annotation.Encryption;
import com.easy.query.core.annotation.InsertIgnore;
import com.easy.query.core.annotation.LogicDelete;
import com.easy.query.core.annotation.ShardingDataSourceKey;
import com.easy.query.core.annotation.ShardingExtraDataSourceKey;
import com.easy.query.core.annotation.ShardingExtraTableKey;
import com.easy.query.core.annotation.ShardingTableKey;
import com.easy.query.core.annotation.UpdateIgnore;
import com.easy.query.core.annotation.Version;

import java.beans.PropertyDescriptor;

/**
 * create time 2023/6/15 10:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class PropertyMetadata {
    private final PropertyDescriptor propertyDescriptor;
    private Column columnAnnotation;
    private Encryption encryptionAnnotation;
    private InsertIgnore insertIgnoreAnnotation;
    private UpdateIgnore updateIgnoreAnnotation;
    private Version versionAnnotation;
    private LogicDelete logicDeleteAnnotation;
    private ShardingDataSourceKey shardingDataSourceKeyAnnotation;
    private ShardingExtraDataSourceKey shardingExtraDataSourceKeyAnnotation;
    private ShardingTableKey shardingTableKeyAnnotation;
    private ShardingExtraTableKey shardingExtraTableKeyAnnotation;
    private ColumnIgnore columnIgnoreAnnotation;

    public PropertyMetadata(PropertyDescriptor propertyDescriptor) {
        this.propertyDescriptor = propertyDescriptor;
    }

    public PropertyDescriptor getPropertyDescriptor() {
        return propertyDescriptor;
    }

    public Column getColumnAnnotation() {
        return columnAnnotation;
    }

    public void setColumnAnnotation(Column columnAnnotation) {
        this.columnAnnotation = columnAnnotation;
    }

    public Encryption getEncryptionAnnotation() {
        return encryptionAnnotation;
    }

    public void setEncryptionAnnotation(Encryption encryptionAnnotation) {
        this.encryptionAnnotation = encryptionAnnotation;
    }

    public InsertIgnore getInsertIgnoreAnnotation() {
        return insertIgnoreAnnotation;
    }

    public void setInsertIgnoreAnnotation(InsertIgnore insertIgnoreAnnotation) {
        this.insertIgnoreAnnotation = insertIgnoreAnnotation;
    }

    public UpdateIgnore getUpdateIgnoreAnnotation() {
        return updateIgnoreAnnotation;
    }

    public void setUpdateIgnoreAnnotation(UpdateIgnore updateIgnoreAnnotation) {
        this.updateIgnoreAnnotation = updateIgnoreAnnotation;
    }

    public Version getVersionAnnotation() {
        return versionAnnotation;
    }

    public void setVersionAnnotation(Version versionAnnotation) {
        this.versionAnnotation = versionAnnotation;
    }

    public LogicDelete getLogicDeleteAnnotation() {
        return logicDeleteAnnotation;
    }

    public void setLogicDeleteAnnotation(LogicDelete logicDeleteAnnotation) {
        this.logicDeleteAnnotation = logicDeleteAnnotation;
    }

    public ShardingDataSourceKey getShardingDataSourceKeyAnnotation() {
        return shardingDataSourceKeyAnnotation;
    }

    public void setShardingDataSourceKeyAnnotation(ShardingDataSourceKey shardingDataSourceKeyAnnotation) {
        this.shardingDataSourceKeyAnnotation = shardingDataSourceKeyAnnotation;
    }

    public ShardingExtraDataSourceKey getShardingExtraDataSourceKeyAnnotation() {
        return shardingExtraDataSourceKeyAnnotation;
    }

    public void setShardingExtraDataSourceKeyAnnotation(ShardingExtraDataSourceKey shardingExtraDataSourceKeyAnnotation) {
        this.shardingExtraDataSourceKeyAnnotation = shardingExtraDataSourceKeyAnnotation;
    }

    public ShardingTableKey getShardingTableKeyAnnotation() {
        return shardingTableKeyAnnotation;
    }

    public void setShardingTableKeyAnnotation(ShardingTableKey shardingTableKeyAnnotation) {
        this.shardingTableKeyAnnotation = shardingTableKeyAnnotation;
    }

    public ShardingExtraTableKey getShardingExtraTableKeyAnnotation() {
        return shardingExtraTableKeyAnnotation;
    }

    public void setShardingExtraTableKeyAnnotation(ShardingExtraTableKey shardingExtraTableKeyAnnotation) {
        this.shardingExtraTableKeyAnnotation = shardingExtraTableKeyAnnotation;
    }

    public ColumnIgnore getColumnIgnoreAnnotation() {
        return columnIgnoreAnnotation;
    }

    public void setColumnIgnoreAnnotation(ColumnIgnore columnIgnoreAnnotation) {
        this.columnIgnoreAnnotation = columnIgnoreAnnotation;
    }
}
