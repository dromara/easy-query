package com.easy.query.core.configuration;

import com.easy.query.core.basic.plugin.logicdel.*;
import com.easy.query.core.basic.plugin.logicdel.impl.BooleanEasyEntityTypeConfiguration;
import com.easy.query.core.basic.plugin.logicdel.impl.DeleteLongTimestampEasyEntityTypeConfiguration;
import com.easy.query.core.basic.plugin.logicdel.impl.LocalDateEasyLogicDeleteStrategy;
import com.easy.query.core.basic.plugin.logicdel.impl.LocalDateTimeEasyEntityTypeConfiguration;
import com.easy.query.core.basic.plugin.version.EasyVersionIntStrategy;
import com.easy.query.core.basic.plugin.version.EasyVersionLongStrategy;
import com.easy.query.core.basic.plugin.version.EasyVersionStrategy;
import com.easy.query.core.basic.plugin.version.EasyVersionTimestampStrategy;
import com.easy.query.core.basic.plugin.version.EasyVersionUUIDStrategy;
import com.easy.query.core.configuration.dialect.Dialect;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.basic.plugin.encryption.EasyEncryptionStrategy;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.basic.enums.LogicDeleteStrategyEnum;
import com.easy.query.core.basic.plugin.interceptor.EasyInterceptor;
import com.easy.query.core.sharding.initializer.ShardingInitializer;
import com.easy.query.core.sharding.initializer.UnShardingInitializer;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName: JDQCConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/2/7 09:06
 * @author xuejiaming
 */
public class QueryConfiguration {
    private static final LogicDeleteStrategy BOOL_LOGIC_DELETE = new BooleanEasyEntityTypeConfiguration();
    private static final LogicDeleteStrategy TIMESTAMP_LOGIC_DELETE = new DeleteLongTimestampEasyEntityTypeConfiguration();
    private static final LogicDeleteStrategy LOCAL_DATE_TIME_LOGIC_DELETE = new LocalDateTimeEasyEntityTypeConfiguration();
    private static final LogicDeleteStrategy LOCAL_DATE_LOGIC_DELETE = new LocalDateEasyLogicDeleteStrategy();

    private final EasyQueryOption easyQueryOption;


    private final NameConversion nameConversion;
    private final Dialect dialect;
//    private Map<Class<?>, EntityTypeConfiguration<?>> entityTypeConfigurationMap = new HashMap<>();
    private Map<String, EasyInterceptor> interceptorMap =new ConcurrentHashMap<>();
    private Map<String, LogicDeleteStrategy> globalLogicDeleteStrategyMap = new ConcurrentHashMap<>();
    private Map<Class<? extends EasyEncryptionStrategy>, EasyEncryptionStrategy> easyEncryptionStrategyMap = new ConcurrentHashMap<>();
    private Map<Class<? extends EasyVersionStrategy>, EasyVersionStrategy> easyVersionStrategyMap = new ConcurrentHashMap<>();
    private Map<Class<? extends ShardingInitializer>, ShardingInitializer> shardingInitializerMap = new ConcurrentHashMap<>();

//    public EasyQueryConfiguration(Dialect dialect, NameConversion nameConversion) {
//       this(EasyQueryOption.defaultEasyQueryOption(),dialect,nameConversion);
//    }
    public QueryConfiguration(EasyQueryOption easyQueryOption, Dialect dialect, NameConversion nameConversion) {
        this.easyQueryOption = easyQueryOption;
        this.dialect = dialect;
        this.nameConversion = nameConversion;
        easyVersionStrategyMap.put(EasyVersionIntStrategy.class,new EasyVersionIntStrategy());
        easyVersionStrategyMap.put(EasyVersionLongStrategy.class,new EasyVersionLongStrategy());
        easyVersionStrategyMap.put(EasyVersionUUIDStrategy.class,new EasyVersionUUIDStrategy());
        easyVersionStrategyMap.put(EasyVersionTimestampStrategy.class,new EasyVersionTimestampStrategy());
        shardingInitializerMap.put(UnShardingInitializer.class,UnShardingInitializer.INSTANCE);
    }

    public boolean deleteThrow(){
        return easyQueryOption.isDeleteThrowError();
    }

    public NameConversion getNameConversion() {
        return nameConversion;
    }

    public Dialect getDialect() {
        return dialect;
    }

    public void applyEasyInterceptor(EasyInterceptor easyInterceptor){
        String interceptorName = easyInterceptor.name();
        if(EasyStringUtil.isBlank(interceptorName)){
            throw new EasyQueryException(EasyClassUtil.getInstanceSimpleName(easyInterceptor)+"cant get interceptor name");
        }
        if(interceptorMap.containsKey(interceptorName)){
            throw new EasyQueryException("global interceptor:" + interceptorName + ",repeat");
        }
        interceptorMap.put(interceptorName,easyInterceptor);
    }


    public EasyInterceptor getEasyInterceptor(String name){
        if(name==null){
            throw new IllegalArgumentException("cant get global interceptor,name is null");
        }
        return interceptorMap.get(name);
    }
    public Collection<EasyInterceptor> getEasyInterceptors(){
        return interceptorMap.values();
    }
//    public void applyEntityTypeConfiguration(EntityTypeConfiguration<?> entityTypeConfiguration) {
//        entityTypeConfigurationMap.put(entityTypeConfiguration.entityType(), entityTypeConfiguration);
//    }
//
//    public EntityTypeConfiguration<?> getEntityTypeConfiguration(Class<?> entityType) {
//        return entityTypeConfigurationMap.get(entityType);
//    }

    public void applyLogicDeleteStrategy(LogicDeleteStrategy globalLogicDeleteStrategy) {
        String strategy = globalLogicDeleteStrategy.getStrategy();
        if (globalLogicDeleteStrategyMap.containsKey(strategy)) {
            throw new EasyQueryException("global logic delete strategy:" + strategy + ",repeat");
        }
        globalLogicDeleteStrategyMap.put(strategy, globalLogicDeleteStrategy);
    }

    /**
     * 获取
     *
     * @param strategy
     * @return
     */
    public LogicDeleteStrategy getLogicDeleteStrategy(String strategy) {
        return globalLogicDeleteStrategyMap.get(strategy);
    }
    public LogicDeleteStrategy getLogicDeleteStrategyNotNull(String strategy) {
        LogicDeleteStrategy globalLogicDeleteStrategy = getLogicDeleteStrategy(strategy);
        if(globalLogicDeleteStrategy==null){
            throw new EasyQueryException("easy logic delete strategy not found. strategy:"+strategy);
        }
        return globalLogicDeleteStrategy;
    }

    public LogicDeleteStrategy getSysLogicDeleteStrategyNotNull(LogicDeleteStrategyEnum strategy) {
        if (Objects.equals(LogicDeleteStrategyEnum.BOOLEAN, strategy)) {
            return BOOL_LOGIC_DELETE;
        } else if (Objects.equals(LogicDeleteStrategyEnum.DELETE_LONG_TIMESTAMP, strategy)) {
            return TIMESTAMP_LOGIC_DELETE;
        } else if (Objects.equals(LogicDeleteStrategyEnum.LOCAL_DATE_TIME, strategy)) {
            return LOCAL_DATE_TIME_LOGIC_DELETE;
        } else if (Objects.equals(LogicDeleteStrategyEnum.LOCAL_DATE, strategy)) {
            return LOCAL_DATE_LOGIC_DELETE;
        }
        throw new EasyQueryException("easy logic delete strategy not found. strategy:"+strategy);
    }
    public void applyEasyEncryptionStrategy(EasyEncryptionStrategy encryptionStrategy) {
        Class<? extends EasyEncryptionStrategy> strategyClass = encryptionStrategy.getClass();
        if (easyEncryptionStrategyMap.containsKey(strategyClass)) {
            throw new EasyQueryException("easy encryption strategy:" + EasyClassUtil.getSimpleName(strategyClass) + ",repeat");
        }
        easyEncryptionStrategyMap.put(strategyClass, encryptionStrategy);
    }

    public EasyEncryptionStrategy getEasyEncryptionStrategy(Class<? extends EasyEncryptionStrategy> strategy){
        return easyEncryptionStrategyMap.get(strategy);
    }
    public boolean containEasyEncryptionStrategy(Class<? extends EasyEncryptionStrategy> strategy){
        return getEasyEncryptionStrategy(strategy)!=null;
    }
    public EasyEncryptionStrategy getEasyEncryptionStrategyNotNull(Class<? extends EasyEncryptionStrategy> strategy){
        EasyEncryptionStrategy easyEncryptionStrategy = getEasyEncryptionStrategy(strategy);
        if(easyEncryptionStrategy==null){
            throw new EasyQueryException("easy encryption strategy not found. strategy:"+ EasyClassUtil.getSimpleName(strategy));
        }
        return easyEncryptionStrategy;
    }
    //easyVersionStrategyMap

    public void applyEasyVersionStrategy(EasyVersionStrategy versionStrategy) {
        Class<? extends EasyVersionStrategy> strategyClass = versionStrategy.getClass();
        if (easyVersionStrategyMap.containsKey(strategyClass)) {
            throw new EasyQueryException("easy version strategy:" + EasyClassUtil.getSimpleName(strategyClass) + ",repeat");
        }
        easyVersionStrategyMap.put(strategyClass, versionStrategy);
    }

    public EasyVersionStrategy getEasyVersionStrategyOrNull(Class<? extends EasyVersionStrategy> strategy){
        return easyVersionStrategyMap.get(strategy);
    }
    public boolean containEasyVersionStrategy(Class<? extends EasyVersionStrategy> strategy){
        return getEasyVersionStrategyOrNull(strategy)!=null;
    }
    public EasyVersionStrategy getEasyVersionStrategyNotNull(Class<? extends EasyVersionStrategy> strategy){
        EasyVersionStrategy easyVersionStrategy = getEasyVersionStrategyOrNull(strategy);
        if(easyVersionStrategy==null){
            throw new EasyQueryException("easy version strategy not found. strategy:"+ EasyClassUtil.getSimpleName(strategy));
        }
        return easyVersionStrategy;
    }
    public EasyQueryOption getEasyQueryOption() {
        return easyQueryOption;
    }


    public void applyShardingInitializer(ShardingInitializer shardingInitializer){
        Class<? extends ShardingInitializer> initializerClass = shardingInitializer.getClass();
        if(shardingInitializerMap.containsKey(initializerClass)){
            throw new EasyQueryException("easy sharding initializer:" + EasyClassUtil.getSimpleName(initializerClass) + ",repeat");
        }
        shardingInitializerMap.put(initializerClass,shardingInitializer);
    }

    public ShardingInitializer getEasyShardingInitializerOrNull(Class<? extends ShardingInitializer> initializer){
        return shardingInitializerMap.get(initializer);
    }
}
