package com.easy.query.core.configuration;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.EnumValueAutoConverter;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.extension.generated.GeneratedKeySQLColumnGenerator;
import com.easy.query.core.basic.extension.generated.PrimaryKeyGenerator;
import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategy;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategyEnum;
import com.easy.query.core.basic.extension.logicdel.impl.BooleanLogicDeleteStrategy;
import com.easy.query.core.basic.extension.logicdel.impl.LocalDateLogicDeleteStrategy;
import com.easy.query.core.basic.extension.logicdel.impl.LocalDateTimeLogicDeleteStrategy;
import com.easy.query.core.basic.extension.logicdel.impl.LongTimestampLogicDeleteStrategy;
import com.easy.query.core.basic.extension.navigate.NavigateExtraFilterStrategy;
import com.easy.query.core.basic.extension.navigate.NavigateValueSetter;
import com.easy.query.core.basic.extension.version.VersionIntStrategy;
import com.easy.query.core.basic.extension.version.VersionLongStrategy;
import com.easy.query.core.basic.extension.version.VersionStrategy;
import com.easy.query.core.basic.extension.version.VersionTimestampStrategy;
import com.easy.query.core.basic.extension.version.VersionUUIDStrategy;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.implicit.GenericEntityRelationToImplicitProvider;
import com.easy.query.core.expression.implicit.EntityRelationPropertyProvider;
import com.easy.query.core.job.EasyTimeJobManager;
import com.easy.query.core.job.TimeJob;
import com.easy.query.core.sharding.initializer.ShardingInitializer;
import com.easy.query.core.sharding.initializer.UnShardingInitializer;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author xuejiaming
 * @FileName: JDQCConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/2/7 09:06
 */
public class QueryConfiguration {
    private static final LogicDeleteStrategy BOOL_LOGIC_DELETE = new BooleanLogicDeleteStrategy();
    private static final LogicDeleteStrategy TIMESTAMP_LOGIC_DELETE = new LongTimestampLogicDeleteStrategy();
    private static final LogicDeleteStrategy LOCAL_DATE_TIME_LOGIC_DELETE = new LocalDateTimeLogicDeleteStrategy();
    private static final LogicDeleteStrategy LOCAL_DATE_LOGIC_DELETE = new LocalDateLogicDeleteStrategy();

    private final EasyQueryOption easyQueryOption;


    private final NameConversion nameConversion;
    private final EasyTimeJobManager easyTimeJobManager;
    private final SQLKeyword SQLKeyWord;
    //    private Map<Class<?>, EntityTypeConfiguration<?>> entityTypeConfigurationMap = new HashMap<>();
    private Map<String, Interceptor> interceptorMap = new ConcurrentHashMap<>();
    private Map<String, LogicDeleteStrategy> globalLogicDeleteStrategyMap = new ConcurrentHashMap<>();
    private Map<Class<? extends NavigateExtraFilterStrategy>, NavigateExtraFilterStrategy> navigateExtraFilterStrategyMap = new ConcurrentHashMap<>();
    private Map<Class<? extends NavigateValueSetter>, NavigateValueSetter> navigateValueSetterMap = new ConcurrentHashMap<>();
    private Map<Class<? extends EncryptionStrategy>, EncryptionStrategy> easyEncryptionStrategyMap = new ConcurrentHashMap<>();
    private Map<Class<? extends VersionStrategy>, VersionStrategy> easyVersionStrategyMap = new ConcurrentHashMap<>();
    private Map<Class<? extends ShardingInitializer>, ShardingInitializer> shardingInitializerMap = new ConcurrentHashMap<>();
    private Map<Class<? extends ValueConverter<?, ?>>, ValueConverter<?, ?>> valueConverterMap = new ConcurrentHashMap<>();
    private List<EnumValueAutoConverter<?, ?>> enumValueAutoConverters = new CopyOnWriteArrayList<>();
    private Map<Class<? extends ColumnValueSQLConverter>, ColumnValueSQLConverter> columnValueSQLConverterMap = new ConcurrentHashMap<>();
    private Map<Class<? extends GeneratedKeySQLColumnGenerator>, GeneratedKeySQLColumnGenerator> generatedSQLColumnGeneratorMap = new ConcurrentHashMap<>();
    private Map<Class<? extends PrimaryKeyGenerator>, PrimaryKeyGenerator> primaryKeyGeneratorMap = new ConcurrentHashMap<>();
    private Map<String, EntityRelationPropertyProvider> toManySubquerySQLStrategyMap = new ConcurrentHashMap<>();

    //    public EasyQueryConfiguration(Dialect dialect, NameConversion nameConversion) {
//       this(EasyQueryOption.defaultEasyQueryOption(),dialect,nameConversion);
//    }
    public QueryConfiguration(EasyQueryOption easyQueryOption, SQLKeyword SQLKeyWord, NameConversion nameConversion, EasyTimeJobManager easyTimeJobManager) {
        this.easyQueryOption = easyQueryOption;
        this.SQLKeyWord = SQLKeyWord;
        this.nameConversion = nameConversion;
        this.easyTimeJobManager = easyTimeJobManager;
        easyVersionStrategyMap.put(VersionIntStrategy.class, new VersionIntStrategy());
        easyVersionStrategyMap.put(VersionLongStrategy.class, new VersionLongStrategy());
        easyVersionStrategyMap.put(VersionUUIDStrategy.class, new VersionUUIDStrategy());
        easyVersionStrategyMap.put(VersionTimestampStrategy.class, new VersionTimestampStrategy());
        shardingInitializerMap.put(UnShardingInitializer.class, UnShardingInitializer.INSTANCE);
        toManySubquerySQLStrategyMap.put("", GenericEntityRelationToImplicitProvider.INSTANCE);
//        primaryKeyGeneratorMap.put(UnsupportPrimaryKeyGenerator.class, UnsupportPrimaryKeyGenerator.INSTANCE);
    }

    public boolean deleteThrow() {
        return easyQueryOption.isDeleteThrowError();
    }

    public NameConversion getNameConversion() {
        return nameConversion;
    }

    public SQLKeyword getDialect() {
        return SQLKeyWord;
    }

    public void applyInterceptor(Interceptor easyInterceptor) {
        String interceptorName = easyInterceptor.name();
        if (EasyStringUtil.isBlank(interceptorName)) {
            throw new EasyQueryException("interceptor :[" + EasyClassUtil.getInstanceSimpleName(easyInterceptor) + "] cant get name");
        }
        if (interceptorMap.containsKey(interceptorName)) {
            throw new EasyQueryException("global interceptor:" + interceptorName + ",repeat");
        }
        interceptorMap.put(interceptorName, easyInterceptor);
    }


    public Interceptor getEasyInterceptor(String name) {
        if (name == null) {
            throw new IllegalArgumentException("cant get global interceptor,name is null");
        }
        return interceptorMap.get(name);
    }

    public Collection<Interceptor> getEasyInterceptors() {
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

    public void applyNavigateExtraFilterStrategy(NavigateExtraFilterStrategy navigateExtraFilterStrategy) {
        Objects.requireNonNull(navigateExtraFilterStrategy, "navigateExtraFilterStrategy is null");
        if (navigateExtraFilterStrategyMap.containsKey(navigateExtraFilterStrategy.getClass())) {
            throw new EasyQueryException("navigate extra filter strategy:" + EasyClassUtil.getSimpleName(navigateExtraFilterStrategy.getClass()) + ",repeat");
        }
        navigateExtraFilterStrategyMap.put(navigateExtraFilterStrategy.getClass(), navigateExtraFilterStrategy);
    }

    public NavigateExtraFilterStrategy getNavigateExtraFilterStrategy(Class<? extends NavigateExtraFilterStrategy> strategy) {
        return navigateExtraFilterStrategyMap.get(strategy);
    }
    public void applyNavigateValueSetter(NavigateValueSetter navigateValueSetter) {
        Objects.requireNonNull(navigateValueSetter, "navigateValueSetter is null");
        if (navigateValueSetterMap.containsKey(navigateValueSetter.getClass())) {
            throw new EasyQueryException("navigate value setter:" + EasyClassUtil.getSimpleName(navigateValueSetter.getClass()) + ",repeat");
        }
        Class<? extends NavigateValueSetter> nvClass = EasyObjectUtil.typeCastNullable(navigateValueSetter.getClass());
        navigateValueSetterMap.put(nvClass, navigateValueSetter);
    }

    public NavigateValueSetter getNavigateValueSetter(Class<? extends NavigateValueSetter> valueSetter) {
        return navigateValueSetterMap.get(valueSetter);
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
        if (globalLogicDeleteStrategy == null) {
            throw new EasyQueryException("easy logic delete strategy not found. strategy:" + strategy);
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
        throw new EasyQueryException("easy logic delete strategy not found. strategy:" + strategy);
    }

    public void applyEncryptionStrategy(EncryptionStrategy encryptionStrategy) {
        Class<? extends EncryptionStrategy> strategyClass = encryptionStrategy.getClass();
        if (easyEncryptionStrategyMap.containsKey(strategyClass)) {
            throw new EasyQueryException("easy encryption strategy:" + EasyClassUtil.getSimpleName(strategyClass) + ",repeat");
        }
        easyEncryptionStrategyMap.put(strategyClass, encryptionStrategy);
    }

    public EncryptionStrategy getEasyEncryptionStrategy(Class<? extends EncryptionStrategy> strategy) {
        return easyEncryptionStrategyMap.get(strategy);
    }

    public boolean containEasyEncryptionStrategy(Class<? extends EncryptionStrategy> strategy) {
        return getEasyEncryptionStrategy(strategy) != null;
    }

    public EncryptionStrategy getEasyEncryptionStrategyNotNull(Class<? extends EncryptionStrategy> strategy) {
        EncryptionStrategy easyEncryptionStrategy = getEasyEncryptionStrategy(strategy);
        if (easyEncryptionStrategy == null) {
            throw new EasyQueryException("easy encryption strategy not found. strategy:" + EasyClassUtil.getSimpleName(strategy));
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

    public VersionStrategy getEasyVersionStrategyOrNull(Class<? extends VersionStrategy> strategy) {
        return easyVersionStrategyMap.get(strategy);
    }

    public boolean containEasyVersionStrategy(Class<? extends VersionStrategy> strategy) {
        return getEasyVersionStrategyOrNull(strategy) != null;
    }

    public VersionStrategy getEasyVersionStrategyNotNull(Class<? extends VersionStrategy> strategy) {
        VersionStrategy easyVersionStrategy = getEasyVersionStrategyOrNull(strategy);
        if (easyVersionStrategy == null) {
            throw new EasyQueryException("easy version strategy not found. strategy:" + EasyClassUtil.getSimpleName(strategy));
        }
        return easyVersionStrategy;
    }

    public EasyQueryOption getEasyQueryOption() {
        return easyQueryOption;
    }


    public void applyShardingInitializer(ShardingInitializer shardingInitializer) {
        Class<? extends ShardingInitializer> initializerClass = shardingInitializer.getClass();
        if (shardingInitializerMap.containsKey(initializerClass)) {
            throw new EasyQueryException("easy sharding initializer:" + EasyClassUtil.getSimpleName(initializerClass) + ",repeat");
        }
        shardingInitializerMap.put(initializerClass, shardingInitializer);
        if (shardingInitializer instanceof TimeJob) {
            easyTimeJobManager.add((TimeJob) shardingInitializer);
        }
    }

    public ShardingInitializer getEasyShardingInitializerOrNull(Class<? extends ShardingInitializer> initializer) {
        return shardingInitializerMap.get(initializer);
    }

    public void applyValueConverter(ValueConverter<?, ?> valueConverter) {
        Class<? extends ValueConverter<?, ?>> converterClass = EasyObjectUtil.typeCastNullable(valueConverter.getClass());
        if (valueConverterMap.containsKey(converterClass)) {
            throw new EasyQueryException("ValueConverter:" + EasyClassUtil.getSimpleName(converterClass) + ",repeat");
        }
        valueConverterMap.put(converterClass, valueConverter);
        if (EnumValueAutoConverter.class.isAssignableFrom(converterClass)) {
            EnumValueAutoConverter<?, ?> enumValueAutoConverter = EasyObjectUtil.typeCastNullable(valueConverter);
            enumValueAutoConverters.add(enumValueAutoConverter);
        }
    }

    public List<EnumValueAutoConverter<?, ?>> getEnumValueAutoConverters() {
        return enumValueAutoConverters;
    }

    public ValueConverter<?, ?> getValueConverter(Class<? extends ValueConverter<?, ?>> converterClass) {
        return valueConverterMap.get(converterClass);
    }

    public void applyColumnValueSQLConverter(ColumnValueSQLConverter columnValueSQLConverter) {
        Class<? extends ColumnValueSQLConverter> columnValueSQLConverterClass = columnValueSQLConverter.getClass();
        if (columnValueSQLConverterMap.containsKey(columnValueSQLConverterClass)) {
            throw new EasyQueryException("column value sql converter:" + EasyClassUtil.getSimpleName(columnValueSQLConverterClass) + ",repeat");
        }
        columnValueSQLConverterMap.put(columnValueSQLConverterClass, columnValueSQLConverter);
    }

    public ColumnValueSQLConverter getColumnValueSQLConverter(Class<? extends ColumnValueSQLConverter> columnValueSQLConverterClass) {
        return columnValueSQLConverterMap.get(columnValueSQLConverterClass);
    }

    public void applyGeneratedKeySQLColumnGenerator(GeneratedKeySQLColumnGenerator generatedKeySQLColumnGenerator) {
        Class<? extends GeneratedKeySQLColumnGenerator> generatedKeySQLColumnGeneratorClass = generatedKeySQLColumnGenerator.getClass();
        if (generatedSQLColumnGeneratorMap.containsKey(generatedKeySQLColumnGeneratorClass)) {
            throw new EasyQueryException("generated key sql column generator:" + EasyClassUtil.getSimpleName(generatedKeySQLColumnGeneratorClass) + ",repeat");
        }
        generatedSQLColumnGeneratorMap.put(generatedKeySQLColumnGeneratorClass, generatedKeySQLColumnGenerator);
    }

    public GeneratedKeySQLColumnGenerator getGeneratedKeySQLColumnGenerator(Class<? extends GeneratedKeySQLColumnGenerator> generatedKeySQLColumnGenerator) {
        return generatedSQLColumnGeneratorMap.get(generatedKeySQLColumnGenerator);
    }
    public void applyPrimaryKeyGenerator(PrimaryKeyGenerator primaryKeyGenerator) {
        Class<? extends PrimaryKeyGenerator> primaryKeyGeneratorClass = primaryKeyGenerator.getClass();
        if (primaryKeyGeneratorMap.containsKey(primaryKeyGeneratorClass)) {
            throw new EasyQueryException("primary key generator:" + EasyClassUtil.getSimpleName(primaryKeyGeneratorClass) + ",repeat");
        }
        primaryKeyGeneratorMap.put(primaryKeyGeneratorClass, primaryKeyGenerator);
    }

    public PrimaryKeyGenerator getPrimaryKeyGenerator(Class<? extends PrimaryKeyGenerator> primaryKeyGenerator) {
        return primaryKeyGeneratorMap.get(primaryKeyGenerator);
    }
    public void applyRelationPropertyProvider(EntityRelationPropertyProvider toManySubquerySQLStrategy) {
        if (toManySubquerySQLStrategyMap.containsKey(toManySubquerySQLStrategy.getName())) {
            throw new EasyQueryException("entity relation property provider:" + toManySubquerySQLStrategy.getName() + ",repeat");
        }
        toManySubquerySQLStrategyMap.put(toManySubquerySQLStrategy.getName(), toManySubquerySQLStrategy);
    }

    public EntityRelationPropertyProvider getRelationPropertyProvider(String name) {
        return toManySubquerySQLStrategyMap.get(name);
    }
}
