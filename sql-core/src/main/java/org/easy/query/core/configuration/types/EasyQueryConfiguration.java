package org.easy.query.core.configuration.types;

import org.easy.query.core.basic.enums.LogicDeleteStrategyEnum;
import org.easy.query.core.config.DefaultEasyQueryDialect;
import org.easy.query.core.config.DefaultNameConversion;
import org.easy.query.core.config.EasyQueryDialect;
import org.easy.query.core.config.NameConversion;
import org.easy.query.core.configuration.global.*;
import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.util.ClassUtil;
import org.easy.query.core.util.StringUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName: JDQCConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/2/7 09:06
 * @Created by xuejiaming
 */
public class EasyQueryConfiguration {
    private static final GlobalLogicDeleteStrategy BOOL_LOGIC_DELETE = new BoolGlobalEntityTypeConfiguration();
    private static final GlobalLogicDeleteStrategy TIMESTAMP_LOGIC_DELETE = new TimestampGlobalEntityTypeConfiguration();
    private static final GlobalLogicDeleteStrategy LOCAL_DATE_TIME_LOGIC_DELETE = new LocalDateTimeGlobalEntityTypeConfiguration();
    private static final GlobalLogicDeleteStrategy LOCAL_DATE_LOGIC_DELETE = new LocalDateGlobalLogicDeleteStrategy();


    private NameConversion nameConversion = new DefaultNameConversion();
    private EasyQueryDialect dialect = new DefaultEasyQueryDialect();
//    private Map<Class<?>, EntityTypeConfiguration<?>> entityTypeConfigurationMap = new HashMap<>();

    private Map<String,GlobalQueryFilterConfiguration> globalQueryFilterConfigurationMap=new ConcurrentHashMap<>();
    private Map<String, GlobalLogicDeleteStrategy> globalLogicDeleteStrategyMap = new ConcurrentHashMap<>();
    private final boolean deleteThrowError;

    public EasyQueryConfiguration() {
       this(false);
    }
    public EasyQueryConfiguration(boolean deleteThrowError) {
        this.deleteThrowError=deleteThrowError;
    }

    public boolean cantDelete(){
        return deleteThrowError;
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


    public void applyGlobalQueryFilterConfiguration(GlobalQueryFilterConfiguration globalQueryFilterConfiguration){
        String name = globalQueryFilterConfiguration.queryFilterName();
        if(StringUtil.isBlank(name)){
            throw new EasyQueryException(ClassUtil.getInstanceSimpleName(globalQueryFilterConfiguration)+"cant get query filter name");
        }
        if(globalQueryFilterConfigurationMap.containsKey(name)){
            throw new EasyQueryException("global query filter:" + name + ",repeat");
        }
        globalQueryFilterConfigurationMap.put(name,globalQueryFilterConfiguration);
    }
    public GlobalQueryFilterConfiguration getGlobalQueryFilterConfiguration(String name){
        if(name==null){
            throw new IllegalArgumentException("cant get global query filter,name is null");
        }
        return globalQueryFilterConfigurationMap.get(name);
    }
    public Collection<GlobalQueryFilterConfiguration> getGlobalQueryFilterConfigurations(){
        return globalQueryFilterConfigurationMap.values();
    }
//    public void applyEntityTypeConfiguration(EntityTypeConfiguration<?> entityTypeConfiguration) {
//        entityTypeConfigurationMap.put(entityTypeConfiguration.entityType(), entityTypeConfiguration);
//    }
//
//    public EntityTypeConfiguration<?> getEntityTypeConfiguration(Class<?> entityType) {
//        return entityTypeConfigurationMap.get(entityType);
//    }

    public void applyGlobalLogicDeleteStrategy(GlobalLogicDeleteStrategy globalLogicDeleteStrategy) {
        String strategy = globalLogicDeleteStrategy.getStrategy();
        if (globalLogicDeleteStrategyMap.containsKey(strategy)) {
            throw new EasyQueryException("global strategy:" + strategy + ",repeat");
        }
        globalLogicDeleteStrategyMap.put(strategy, globalLogicDeleteStrategy);
    }

    /**
     * 获取
     *
     * @param strategy
     * @return
     */
    public GlobalLogicDeleteStrategy getGlobalLogicDeleteStrategy(String strategy) {
        return globalLogicDeleteStrategyMap.get(strategy);
    }

    public GlobalLogicDeleteStrategy getSysGlobalLogicDeleteStrategy(LogicDeleteStrategyEnum strategy) {
        if (Objects.equals(LogicDeleteStrategyEnum.BOOLEAN, strategy)) {
            return BOOL_LOGIC_DELETE;
        } else if (Objects.equals(LogicDeleteStrategyEnum.LONG_TIMESTAMP, strategy)) {
            return TIMESTAMP_LOGIC_DELETE;
        } else if (Objects.equals(LogicDeleteStrategyEnum.LOCAL_DATE_TIME, strategy)) {
            return LOCAL_DATE_TIME_LOGIC_DELETE;
        } else if (Objects.equals(LogicDeleteStrategyEnum.LOCAL_DATE, strategy)) {
            return LOCAL_DATE_LOGIC_DELETE;
        }
        return null;
    }
}
