package org.easy.query.core.config;

import org.easy.query.core.configuration.EntityTypeConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: JDQCConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/2/7 09:06
 * @Created by xuejiaming
 */
public class EasyQueryConfiguration {


    private NameConversion nameConversion = new DefaultNameConversion();
    private EasyQueryDialect dialect = new DefaultEasyQueryDialect();
    private Map<Class<?>,EntityTypeConfiguration<?>> entityTypeConfigurationMap=new HashMap<>();

    public EasyQueryConfiguration() {
    }

    public NameConversion getNameConversion() {
        return nameConversion;
    }

    public void setNameConversion(NameConversion nameConversion) {
        this.nameConversion = nameConversion;
    }

    public EasyQueryDialect getDialect() {
        return dialect;
    }

    public void setDialect(EasyQueryDialect dialect) {
        this.dialect = dialect;
    }


    public void applyEntityTypeConfiguration(EntityTypeConfiguration<?> entityTypeConfiguration){
        entityTypeConfigurationMap.put(entityTypeConfiguration.entityType(),entityTypeConfiguration);
    }
    public EntityTypeConfiguration<?> getEntityTypeConfiguration(Class<?> entityType){
        return entityTypeConfigurationMap.get(entityType);
    }
}
