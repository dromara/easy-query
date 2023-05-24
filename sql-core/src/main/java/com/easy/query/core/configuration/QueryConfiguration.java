package com.easy.query.core.configuration;

import com.easy.query.core.basic.plugin.conversion.ValueConverter;
import com.easy.query.core.basic.plugin.logicdel.*;
import com.easy.query.core.basic.plugin.logicdel.impl.BooleanEasyEntityTypeConfiguration;
import com.easy.query.core.basic.plugin.logicdel.impl.DeleteLongTimestampEasyEntityTypeConfiguration;
import com.easy.query.core.basic.plugin.logicdel.impl.LocalDateEasyLogicDeleteStrategy;
import com.easy.query.core.basic.plugin.logicdel.impl.LocalDateTimeEasyEntityTypeConfiguration;
import com.easy.query.core.basic.plugin.version.VersionIntStrategy;
import com.easy.query.core.basic.plugin.version.VersionLongStrategy;
import com.easy.query.core.basic.plugin.version.VersionStrategy;
import com.easy.query.core.basic.plugin.version.VersionTimestampStrategy;
import com.easy.query.core.basic.plugin.version.VersionUUIDStrategy;
import com.easy.query.core.configuration.dialect.Dialect;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.basic.plugin.encryption.EncryptionStrategy;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.basic.plugin.logicdel.LogicDeleteStrategyEnum;
import com.easy.query.core.basic.plugin.interceptor.Interceptor;
import com.easy.query.core.sharding.initializer.ShardingInitializer;
import com.easy.query.core.sharding.initializer.UnShardingInitializer;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyObjectUtil;
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
    private Map<String, Interceptor> interceptorMap =new ConcurrentHashMap<>();
    private Map<String, LogicDeleteStrategy> globalLogicDeleteStrategyMap = new ConcurrentHashMap<>();
    private Map<Class<? extends EncryptionStrategy>, EncryptionStrategy> easyEncryptionStrategyMap = new ConcurrentHashMap<>();
    private Map<Class<? extends VersionStrategy>, VersionStrategy> easyVersionStrategyMap = new ConcurrentHashMap<>();
    private Map<Class<? extends ShardingInitializer>, ShardingInitializer> shardingInitializerMap = new ConcurrentHashMap<>();
    private Map<Class<? extends ValueConverter>, ValueConverter<?,?>> valueConverterMap = new ConcurrentHashMap<>();

//    public EasyQueryConfiguration(Dialect dialect, NameConversion nameConversion) {
//       this(EasyQueryOption.defaultEasyQueryOption(),dialect,nameConversion);
//    }
    public QueryConfiguration(EasyQueryOption easyQueryOption, Dialect dialect, NameConversion nameConversion) {
        this.easyQueryOption = easyQueryOption;
        this.dialect = dialect;
        this.nameConversion = nameConversion;
        easyVersionStrategyMap.put(VersionIntStrategy.class,new VersionIntStrategy());
        easyVersionStrategyMap.put(VersionLongStrategy.class,new VersionLongStrategy());
        easyVersionStrategyMap.put(VersionUUIDStrategy.class,new VersionUUIDStrategy());
        easyVersionStrategyMap.put(VersionTimestampStrategy.class,new VersionTimestampStrategy());
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

    public void applyInterceptor(Interceptor easyInterceptor){
        String interceptorName = easyInterceptor.name();
        if(EasyStringUtil.isBlank(interceptorName)){
            throw new EasyQueryException(EasyClassUtil.getInstanceSimpleName(easyInterceptor)+"cant get interceptor name");
        }
        if(interceptorMap.containsKey(interceptorName)){
            throw new EasyQueryException("global interceptor:" + interceptorName + ",repeat");
        }
        interceptorMap.put(interceptorName,easyInterceptor);
    }


    public Interceptor getEasyInterceptor(String name){
        if(name==null){
            throw new IllegalArgumentException("cant get global interceptor,name is null");
        }
        return interceptorMap.get(name);
    }
    public Collection<Interceptor> getEasyInterceptors(){
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
    public void applyEncryptionStrategy(EncryptionStrategy encryptionStrategy) {
        Class<? extends EncryptionStrategy> strategyClass = encryptionStrategy.getClass();
        if (easyEncryptionStrategyMap.containsKey(strategyClass)) {
            throw new EasyQueryException("easy encryption strategy:" + EasyClassUtil.getSimpleName(strategyClass) + ",repeat");
        }
        easyEncryptionStrategyMap.put(strategyClass, encryptionStrategy);
    }

    public EncryptionStrategy getEasyEncryptionStrategy(Class<? extends EncryptionStrategy> strategy){
        return easyEncryptionStrategyMap.get(strategy);
    }
    public boolean containEasyEncryptionStrategy(Class<? extends EncryptionStrategy> strategy){
        return getEasyEncryptionStrategy(strategy)!=null;
    }
    public EncryptionStrategy getEasyEncryptionStrategyNotNull(Class<? extends EncryptionStrategy> strategy){
        EncryptionStrategy easyEncryptionStrategy = getEasyEncryptionStrategy(strategy);
        if(easyEncryptionStrategy==null){
            throw new EasyQueryException("easy encryption strategy not found. strategy:"+ EasyClassUtil.getSimpleName(strategy));
        }
        return easyEncryptionStrategy;
    }
    //easyVersionStrategyMap

    public void applyEasyVersionStrategy(VersionStrategy versionStrategy) {
        Class<? extends VersionStrategy> strategyClass = versionStrategy.getClass();
        if (easyVersionStrategyMap.containsKey(strategyClass)) {
            throw new EasyQueryException("easy version strategy:" + EasyClassUtil.getSimpleName(strategyClass) + ",repeat");
        }
        easyVersionStrategyMap.put(strategyClass, versionStrategy);
    }

    public VersionStrategy getEasyVersionStrategyOrNull(Class<? extends VersionStrategy> strategy){
        return easyVersionStrategyMap.get(strategy);
    }
    public boolean containEasyVersionStrategy(Class<? extends VersionStrategy> strategy){
        return getEasyVersionStrategyOrNull(strategy)!=null;
    }
    public VersionStrategy getEasyVersionStrategyNotNull(Class<? extends VersionStrategy> strategy){
        VersionStrategy easyVersionStrategy = getEasyVersionStrategyOrNull(strategy);
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

    public void applyValueConverter(ValueConverter<?,?> valueConverter){
        Class<? extends ValueConverter<?,?>> converterClass = EasyObjectUtil.typeCastNullable(valueConverter.getClass());
        if(valueConverterMap.containsKey(converterClass)){
            throw new EasyQueryException("value converter:" + EasyClassUtil.getSimpleName(converterClass) + ",repeat");
        }
        valueConverterMap.put(converterClass,valueConverter);
    }
    public  ValueConverter<?,?> getValueConverter(Class<? extends ValueConverter> converterClass){
        return valueConverterMap.get(converterClass);
    }
}
