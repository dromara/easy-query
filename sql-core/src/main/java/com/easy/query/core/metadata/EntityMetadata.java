package com.easy.query.core.metadata;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.ColumnIgnore;
import com.easy.query.core.annotation.ColumnSQLExpression;
import com.easy.query.core.annotation.EasyAssertMessage;
import com.easy.query.core.annotation.EasyTree;
import com.easy.query.core.annotation.Encryption;
import com.easy.query.core.annotation.InsertIgnore;
import com.easy.query.core.annotation.LogicDelete;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.NavigateFlat;
import com.easy.query.core.annotation.NavigateJoin;
import com.easy.query.core.annotation.NavigateSetter;
import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.annotation.ShardingDataSourceKey;
import com.easy.query.core.annotation.ShardingExtraDataSourceKey;
import com.easy.query.core.annotation.ShardingExtraTableKey;
import com.easy.query.core.annotation.ShardingTableKey;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.UpdateIgnore;
import com.easy.query.core.annotation.ValueObject;
import com.easy.query.core.annotation.Version;
import com.easy.query.core.api.EntityCteViewer;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.extension.complex.ComplexPropType;
import com.easy.query.core.basic.extension.complex.DefaultComplexPropType;
import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultValueConverter;
import com.easy.query.core.basic.extension.conversion.EasyColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.EnumValueAutoConverter;
import com.easy.query.core.basic.extension.conversion.ExpArg;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.extension.generated.DefaultGeneratedKeySQLColumnGenerator;
import com.easy.query.core.basic.extension.generated.GeneratedKeySQLColumnGenerator;
import com.easy.query.core.basic.extension.generated.PrimaryKeyGenerator;
import com.easy.query.core.basic.extension.generated.UnsupportPrimaryKeyGenerator;
import com.easy.query.core.basic.extension.interceptor.EntityInterceptor;
import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.extension.interceptor.PredicateFilterInterceptor;
import com.easy.query.core.basic.extension.interceptor.UpdateEntityColumnInterceptor;
import com.easy.query.core.basic.extension.interceptor.UpdateSetInterceptor;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteBuilder;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategy;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategyEnum;
import com.easy.query.core.basic.extension.navigate.DefaultNavigateExtraFilterStrategy;
import com.easy.query.core.basic.extension.navigate.NavigateBuilder;
import com.easy.query.core.basic.extension.navigate.NavigateExtraFilterStrategy;
import com.easy.query.core.basic.extension.navigate.NavigateValueSetter;
import com.easy.query.core.basic.extension.version.VersionStrategy;
import com.easy.query.core.basic.jdbc.executor.impl.def.EntityResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.reader.BeanDataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.DataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.EmptyDataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.PropertyDataReader;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.UnKnownTypeHandler;
import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.common.bean.FastBeanProperty;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.configuration.bean.EasyMatcher;
import com.easy.query.core.configuration.bean.PropertyDescriptorResult;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.configuration.bean.PropertyDescriptorMatcher;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EntityMetadataTypeEnum;
import com.easy.query.core.enums.OrderByPropertyModeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQueryPropertyNotFoundException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.implicit.EntityRelationPropertyProvider;
import com.easy.query.core.expression.parser.core.available.MappingPath;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.extra.ExtraAutoIncludeConfigure;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.logging.nologging.NoLoggingImpl;
import com.easy.query.core.sharding.initializer.ShardingEntityBuilder;
import com.easy.query.core.sharding.initializer.ShardingInitOption;
import com.easy.query.core.sharding.initializer.ShardingInitializer;
import com.easy.query.core.sharding.router.table.TableUnit;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyBeanUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyNavigateUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.core.util.EasyUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * create time 2023/2/11 10:17
 * 对象元信息
 *
 * @author xuejiaming
 */
public class EntityMetadata {
    private static final Log log = LogFactory.getLog(EntityMetadata.class);
    private final Class<?> entityClass;
    private String tableName;
    private String oldTableName;
    private String treeName;
    private String schema;
    private String comment;
    private ExtraAutoIncludeConfigure extraAutoIncludeConfigure;
    private ErrorMessage errorMessage;

    public boolean isMultiTableMapping() {
        return shardingTablePropertyName != null;
    }

    public boolean isMultiDataSourceMapping() {
        return shardingDataSourcePropertyName != null;
    }


    private LogicDeleteMetadata logicDeleteMetadata;
    private VersionMetadata versionMetadata;
    private String shardingDataSourcePropertyName;
    private final Set<String> shardingDataSourcePropertyNames = new LinkedHashSet<>();
    private String shardingTablePropertyName;
    private final Set<String> shardingTablePropertyNames = new LinkedHashSet<>();
    private ShardingInitConfig shardingInitConfig;
    private boolean hasValueObject;
    //    private boolean aliasQuery;
    private boolean hasPrimaryKeyGenerator = false;

    /**
     * 查询过滤器
     */
    private final List<PredicateFilterInterceptor> predicateFilterInterceptors = new ArrayList<>();
    private final List<EntityInterceptor> entityInterceptors = new ArrayList<>();
    private final List<UpdateSetInterceptor> updateSetInterceptors = new ArrayList<>();
    private final List<UpdateEntityColumnInterceptor> updateEntityColumnInterceptors = new ArrayList<>();
    private final Map<String, ColumnMetadata> property2ColumnMap = new LinkedHashMap<>();
    private final Map<String, NavigateMetadata> property2NavigateMap = new LinkedHashMap<>();
    private final Map<String, NavigateFlatMetadata> property2NavigateFlatMap = new LinkedHashMap<>();
    private final Map<String, NavigateJoinMetadata> property2NavigateJoinMap = new LinkedHashMap<>();
    private final Map<String/*property name*/, String/*column name*/> keyPropertiesMap = new LinkedHashMap<>();
    private final List<String/*column name*/> generatedKeyColumns = new ArrayList<>(2);
    private final Map<String/*column name*/, ColumnMetadata> column2PropertyMap = new HashMap<>();

    private final Set<ActualTable> actualTables = new CopyOnWriteArraySet<>();
    private final Set<String> dataSources = new CopyOnWriteArraySet<>();
    protected EntityMetadataTypeEnum entityMetadataType = EntityMetadataTypeEnum.BEAN;
    protected Supplier<Object> beanConstructorCreator;

    private Supplier<Query<?>> cteViewerCreator;

    private DataReader dataReader;

    public EntityMetadata(Class<?> entityClass) {
        this.entityClass = entityClass;
    }


    public void init(ServiceProvider serviceProvider) {

        if (EasyClassUtil.isBasicType(entityClass)) {
            entityMetadataType = EntityMetadataTypeEnum.BASIC_TYPE;
            return;
        }
        if (EasyClassUtil.isEnumType(entityClass)) {
            entityMetadataType = EntityMetadataTypeEnum.BASIC_TYPE;
            return;
        }

        QueryConfiguration configuration = serviceProvider.getService(QueryConfiguration.class);
        EasyQueryOption easyQueryOption = configuration.getEasyQueryOption();
        PropertyDescriptorMatcher propertyDescriptorMatcher = serviceProvider.getService(PropertyDescriptorMatcher.class);
        JdbcTypeHandlerManager jdbcTypeHandlerManager = serviceProvider.getService(JdbcTypeHandlerManager.class);
        NameConversion nameConversion = configuration.getNameConversion();

        Table table = EasyClassUtil.getAnnotation(entityClass, Table.class);
        if (table != null) {
            this.tableName = EasyStringUtil.defaultIfBank(nameConversion.annotationCovert(entityClass, table.value(), true), nameConversion.convert(EasyClassUtil.getSimpleName(entityClass)));
            this.oldTableName = EasyStringUtil.defaultIfBank(nameConversion.annotationCovert(entityClass, table.
                    renameFrom(), true), nameConversion.convert(EasyClassUtil.getSimpleName(entityClass)));
            if (easyQueryOption.isSaveComment()) {
                this.comment = table.comment();
            }
            this.schema = table.schema();
            if (EasyStringUtil.isBlank(this.schema)) {
                //如果存在默认的schema那么就用这个
                boolean hasDefaultSchema = EasyStringUtil.isNotBlank(easyQueryOption.getDefaultSchema());
                if (hasDefaultSchema) {
                    this.schema = easyQueryOption.getDefaultSchema();
                }
            }

            EasyTree easyTree = EasyClassUtil.getAnnotation(entityClass, EasyTree.class);
            if (easyTree != null) {
                if (EasyStringUtil.isNotBlank(easyTree.value())) {
                    this.treeName = easyTree.value();
                }
            }

            if (EntityCteViewer.class.isAssignableFrom(entityClass)) {
                EntityCteViewer<?> entityCteViewer = EasyObjectUtil.typeCastNotNull(EasyClassUtil.newInstance(entityClass));
                QueryRuntimeContext runtimeContext = serviceProvider.getService(QueryRuntimeContext.class);
                this.cteViewerCreator = EasyObjectUtil.typeCastNotNull(entityCteViewer.viewConfigure(runtimeContext));
            }
        }

        EasyAssertMessage easyAssertMessage = EasyClassUtil.getAnnotation(entityClass, EasyAssertMessage.class);
        if (easyAssertMessage != null) {
            this.errorMessage = new ErrorMessage(easyAssertMessage.value());
        } else {
            this.errorMessage = new ErrorMessage(EasyUtil.NOT_NULL);
        }
        HashSet<String> ignoreProperties = table != null ? new HashSet<>(Arrays.asList(table.ignoreProperties())) : new HashSet<>();
        Map<String, Field> staticFields = new HashMap<>();

        Collection<Field> allFields = EasyClassUtil.getAllFields(this.entityClass, staticFields);

        PropertyDescriptor[] ps = EasyClassUtil.propertyDescriptors(entityClass);
        EasyMatcher easyMatcher = propertyDescriptorMatcher.create(ps);
//        PropertyDescriptorFinder propertyDescriptorFinder = new PropertyDescriptorFinder(ps);
        FastBean fastBean = EasyBeanUtil.getFastBean(entityClass);
        this.beanConstructorCreator = fastBean.getBeanConstructorCreator();
        boolean tableEntity = EasyStringUtil.isNotBlank(tableName);

        //dto、vo支持额外的selectAutoInclude的配置
        if (!tableEntity) {
            Field extra_auto_include_configure = staticFields.get("EXTRA_AUTO_INCLUDE_CONFIGURE");
            if (extra_auto_include_configure != null) {
                Object extra_auto_include_configure_value = EasyClassUtil.getStaticFieldValue(extra_auto_include_configure);
                if (extra_auto_include_configure_value instanceof ExtraAutoIncludeConfigure) {
                    this.extraAutoIncludeConfigure = (ExtraAutoIncludeConfigure) extra_auto_include_configure_value;
                } else {
                    throw new EasyQueryInvalidOperationException(String.format("[%s] static field [EXTRA_AUTO_INCLUDE_CONFIGURE] not ExtraAutoIncludeConfigure.class", EasyClassUtil.getSimpleName(entityClass)));
                }
            }
        }

        this.dataReader = tableEntity ? EmptyDataReader.EMPTY : null;
        ColumnAllIndex columnAllIndex = new ColumnAllIndex();
        for (Field field : allFields) {
            if (Modifier.isStatic(field.getModifiers()) || ignoreProperties.contains(field.getName())) {
                continue;
            }
            PropertyDescriptorResult matchResult = easyMatcher.match(entityClass, field);
            if (matchResult == null) {
                continue;
            }
            String property = matchResult.getPropertyName();
            //未找到bean属性就直接忽略
            PropertyDescriptor propertyDescriptor = matchResult.getPropertyDescriptor();
            Type genericType = field.getGenericType();
            FastBeanProperty fastBeanProperty = new FastBeanProperty(isGenericType(genericType), propertyDescriptor);
            ColumnIgnore columnIgnore = field.getAnnotation(ColumnIgnore.class);
            if (columnIgnore != null) {
                continue;
            }
            Navigate navigate = field.getAnnotation(Navigate.class);
            if (navigate != null) {
                createNavigateMetadata(tableEntity, navigate, field, fastBean, fastBeanProperty, property, configuration);
                continue;
            } else {
                ValueObject valueObject = field.getAnnotation(ValueObject.class);
                if (valueObject != null) {
                    hasValueObject = true;
                    ColumnOption columnOption = createColumnOption(field, propertyDescriptor, tableEntity, property, fastBeanProperty, configuration, fastBean, jdbcTypeHandlerManager, true, field.getName(), easyQueryOption);
                    FastBean valueObjectFastBean = EasyBeanUtil.getFastBean(propertyDescriptor.getPropertyType());
                    columnOption.setValueObject(true);
                    columnOption.setBeanConstructorCreator(valueObjectFastBean.getBeanConstructorCreator());
                    parseValueObject(columnOption, configuration, jdbcTypeHandlerManager);
                    acceptColumnOption(null, columnOption, columnAllIndex);
                    continue;
                }
            }
            if (!tableEntity) {
                NavigateFlat navigateFlat = field.getAnnotation(NavigateFlat.class);
                if (navigateFlat != null) {
                    createNavigateFlatMappingMetadata(navigateFlat, staticFields, field, fastBean, fastBeanProperty, property, configuration);
                    continue;
                }
                NavigateJoin navigateJoin = field.getAnnotation(NavigateJoin.class);
                if (navigateJoin != null) {
                    createNavigateJoinMappingMetadata(navigateJoin, staticFields, property);
                }
            }

            ColumnOption columnOption = createColumnOption(field, propertyDescriptor, tableEntity, property, fastBeanProperty, configuration, fastBean, jdbcTypeHandlerManager, true, field.getName(), easyQueryOption);
            acceptColumnOption(null, columnOption, columnAllIndex);
        }
        if (EasyCollectionUtil.isEmpty(property2ColumnMap.keySet())) {
            if (log instanceof NoLoggingImpl) {
                System.out.println("NoLogging:" + EasyClassUtil.getSimpleName(entityClass) + " not found property bean, plz add get set method");
            } else {
                log.warn(EasyClassUtil.getSimpleName(entityClass) + " not found property, plz add bean get set method");
            }
        }
        //初始化拦截器
        entityGlobalInterceptorConfigurationInit(configuration);

        if (table != null && isSharding()) {
            Class<? extends ShardingInitializer> initializer = table.shardingInitializer();
            initSharding(configuration, initializer);
        }
    }


    private OrderByModeEnum getOrderByMode(OrderByPropertyModeEnum orderByPropertyMode) {
        if (orderByPropertyMode == OrderByPropertyModeEnum.DEFAULT) {
            return null;
        }
        if (orderByPropertyMode == OrderByPropertyModeEnum.NULLS_FIRST) {
            return OrderByModeEnum.NULLS_FIRST;
        } else {
            return OrderByModeEnum.NULLS_LAST;
        }
    }

    private @Nullable NavigateExtraFilterStrategy getNavigateExtraFilterStrategy(QueryConfiguration configuration, Navigate navigate) {
        Class<? extends NavigateExtraFilterStrategy> extraFilterStrategyClass = navigate.extraFilter();
        if (!Objects.equals(DefaultNavigateExtraFilterStrategy.class, extraFilterStrategyClass)) {
            NavigateExtraFilterStrategy navigateExtraFilterStrategy = configuration.getNavigateExtraFilterStrategy(extraFilterStrategyClass);
            if (navigateExtraFilterStrategy == null) {
                throw new EasyQueryInvalidOperationException("not found navigate extra filter strategy:[" + EasyClassUtil.getSimpleName(extraFilterStrategyClass) + "]");
            }
            return navigateExtraFilterStrategy;
        }
        return null;
    }

    private @Nullable EntityRelationPropertyProvider getEntityRelationPropertyProvider(QueryConfiguration configuration, Navigate navigate) {

        EntityRelationPropertyProvider entityRelationPropertyProvider = configuration.getRelationPropertyProvider(navigate.relationPropertyStrategy());
        if (entityRelationPropertyProvider == null) {
            throw new EasyQueryInvalidOperationException("not found navigate to many subquery sql strategy:[" + navigate.relationPropertyStrategy() + "]");
        }
        return entityRelationPropertyProvider;
    }

    private void createNavigateMetadata(boolean tableEntity, Navigate navigate, Field field, FastBean fastBean, FastBeanProperty fastBeanProperty, String property, QueryConfiguration configuration) {

        RelationTypeEnum relationType = navigate.value();
        boolean readMetadata = tableEntity || navigate.supportNonEntity();
        boolean toMany = relationType.equals(RelationTypeEnum.OneToMany) || relationType.equals(RelationTypeEnum.ManyToMany);
        //toOne并且是table对象
        boolean hasDirectMapping = !toMany && readMetadata && EasyArrayUtil.isNotEmpty(navigate.directMapping());
        if (hasDirectMapping) {
            if (navigate.directMapping().length < 2) {
                throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(entityClass) + " navigate directMapping must have a length of at least 2, property:[" + property + "]");
            }
        }
        boolean required = navigate.required();

        String[] selfProperties = readMetadata && !hasDirectMapping ? navigate.selfProperty() : EasyArrayUtil.EMPTY;
        String[] targetProperties = readMetadata && !hasDirectMapping ? navigate.targetProperty() : EasyArrayUtil.EMPTY;
        String[] selfMappingProperties = readMetadata && !hasDirectMapping ? navigate.selfMappingProperty() : EasyArrayUtil.EMPTY;
        String[] targetMappingProperties = readMetadata && !hasDirectMapping ? navigate.targetMappingProperty() : EasyArrayUtil.EMPTY;

        EasyNavigateUtil.checkProperties(entityClass, property, selfProperties, selfMappingProperties, navigate.mappingClass(), targetMappingProperties, targetProperties);
        //获取导航类型如果是单个对象则为对象类型如果是集合属性那么为集合内泛型类型
        Class<?> navigateType = getNavigateType(toMany, field, fastBeanProperty);
        if (navigateType == null) {
            StringBuilder msg = new StringBuilder();
            msg.append("entity:[").append(EasyClassUtil.getSimpleName(entityClass)).append("] not found navigate type, property:[").append(property).append("]");
            if (toMany) {
                //请确认属性property是使用了List<...>或者Set<...>等集合类型
                msg.append("Please confirm that the property [").append(property).append("] is defined as a collection type, such as List<...> or Set<...>.");
            }
            throw new EasyQueryInvalidOperationException(msg.toString());
        }

        List<NavigateOrderProp> orderProps = toMany
                ? Arrays.stream(navigate.orderByProps()).map(orderByProperty -> new NavigateOrderProp(orderByProperty.property(), orderByProperty.asc(), getOrderByMode(orderByProperty.mode()))).collect(Collectors.toList())
                : EasyCollectionUtil.emptyList();

        NavigateOption navigateOption = new NavigateOption(this, property, fastBeanProperty.getPropertyType(), navigateType, relationType, selfProperties, targetProperties, orderProps, navigate.offset(), navigate.limit(), navigate.directMapping());
        navigateOption.setIgnoreAutoInclude(navigate.ignoreAutoInclude());
        navigateOption.setPartitionOrder(navigate.partitionOrder());
        if (readMetadata) {
            navigateOption.setSubQueryToGroupJoin(toMany && navigate.subQueryToGroupJoin());
            navigateOption.setRequired(required);
            NavigateExtraFilterStrategy navigateExtraFilterStrategy = getNavigateExtraFilterStrategy(configuration, navigate);
            if (navigateExtraFilterStrategy != null) {
                SQLActionExpression1<WherePredicate<?>> predicateFilterExpression = navigateExtraFilterStrategy.getPredicateFilterExpression(new NavigateBuilder(navigateOption));
                if (predicateFilterExpression != null) {
                    navigateOption.setPredicateFilterExpression(predicateFilterExpression);
                }
            }

            EntityRelationPropertyProvider entityRelationPropertyProvider = getEntityRelationPropertyProvider(configuration, navigate);
            navigateOption.setEntityRelationPropertyProvider(entityRelationPropertyProvider);


            if (RelationTypeEnum.ManyToMany == relationType) {
                //有中间表多对多
                if (!Objects.equals(Object.class, navigate.mappingClass())) {
                    if (EasyArrayUtil.isEmpty(navigate.selfMappingProperty())) {
                        throw new IllegalArgumentException("relation type many to many self mapping property is empty");
                    }
                    if (EasyArrayUtil.isEmpty(navigate.targetMappingProperty())) {
                        throw new IllegalArgumentException("relation type many to many target mapping property is empty");
                    }
                    navigateOption.setMappingClass(navigate.mappingClass());
                    navigateOption.setSelfMappingProperties(navigate.selfMappingProperty());
                    navigateOption.setTargetMappingProperties(navigate.targetMappingProperty());
                    if (navigateExtraFilterStrategy != null) {
                        SQLActionExpression1<WherePredicate<?>> predicateFilterExpression = navigateExtraFilterStrategy.getPredicateMappingClassFilterExpression(new NavigateBuilder(navigateOption));
                        if (predicateFilterExpression != null) {
                            navigateOption.setPredicateMappingClassFilterExpression(predicateFilterExpression);
                        }
                    }
                }
            }
        }
        Property<Object, ?> beanGetter = fastBean.getBeanGetter(fastBeanProperty);
        PropertySetterCaller<Object> beanSetter = getBeanSetter(field, fastBean, fastBeanProperty, configuration);

        NavigateMetadata navigateMetadata = new NavigateMetadata(navigateOption, beanGetter, beanSetter);
        property2NavigateMap.put(property, navigateMetadata);
    }

    private String[] getFlatMappingPath(NavigateFlat navigateFlat, Map<String, Field> staticFields, String property) {
        String[] path0 = getFlatMappingPath0(navigateFlat, staticFields);
        if (navigateFlat.prefix()) {
            return EasyArrayUtil.concat(path0, new String[]{property});
        }
        return path0;
    }

    private String[] getFlatMappingPath0(NavigateFlat navigateFlat, Map<String, Field> staticFields) {
        return getMappingPath(navigateFlat.pathAlias(), staticFields);
    }

    private String[] getJoinMappingPath(NavigateJoin navigateJoin, Map<String, Field> staticFields, String property) {
        String[] path0 = getJoinMappingPath0(navigateJoin, staticFields);
        if (navigateJoin.prefix()) {
            return EasyArrayUtil.concat(path0, new String[]{property});
        }
        return path0;
    }

    private String[] getJoinMappingPath0(NavigateJoin navigateJoin, Map<String, Field> staticFields) {
        return getMappingPath(navigateJoin.pathAlias(), staticFields);
    }

    private String[] getMappingPath(String mapping, Map<String, Field> staticFields) {
        if (EasyStringUtil.isNotBlank(mapping)) {
            Field field = staticFields.get(mapping);
            if (field != null) {
                Object mappingPathObject = EasyClassUtil.getStaticFieldValue(field);
                if (mappingPathObject instanceof MappingPath) {
                    MappingPath mappingPath = (MappingPath) mappingPathObject;
                    String mappingPathValue = mappingPath.__getMappingPath();
                    if (EasyStringUtil.isNotBlank(mappingPathValue)) {
                        return mappingPathValue.split("\\.");
                    }
                }
            } else if (mapping.contains(".")) {
                return mapping.split("\\.");
            } else {
                return new String[]{mapping};
            }
        }
        throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(entityClass) + " mapping path:[" + mapping + "] cant parse");
    }

    private void createNavigateFlatMappingMetadata(NavigateFlat navigateFlat, Map<String, Field> staticFields, Field field, FastBean fastBean, FastBeanProperty fastBeanProperty, String property, QueryConfiguration configuration) {
        String[] mappingPath = getFlatMappingPath(navigateFlat, staticFields, property);
        if (mappingPath.length <= 1) {
            throw new EasyQueryInvalidOperationException("navigate flat, mappingPath at least two path");
        }
        Class<?> propertyType = fastBeanProperty.getPropertyType();
        boolean toMany = Collection.class.isAssignableFrom(propertyType);
        Class<?> navigateType = getNavigateType(toMany, field, fastBeanProperty);
        if (navigateType == null) {
            throw new EasyQueryInvalidOperationException("not found navigate flat type, property:[" + property + "]");
        }
//        Property<Object, ?> beanGetter = fastBean.getBeanGetter(fastBeanProperty);
        PropertySetterCaller<Object> beanSetter = getBeanSetter(field, fastBean, fastBeanProperty, configuration);

        NavigateFlatMetadata navigateFlatMetadata = new NavigateFlatMetadata(this, toMany, mappingPath, navigateType, EasyClassUtil.isBasicTypeOrEnum(navigateType), beanSetter, property);

        property2NavigateFlatMap.put(property, navigateFlatMetadata);
    }

    protected PropertySetterCaller<Object> getBeanSetter(Field field, FastBean fastBean, FastBeanProperty fastBeanProperty, QueryConfiguration configuration) {
        PropertySetterCaller<Object> beanSetter = fastBean.getBeanSetter(fastBeanProperty);
        NavigateSetter navigateSetter = field.getAnnotation(NavigateSetter.class);
        if (navigateSetter != null) {
            Class<? extends NavigateValueSetter> setter = navigateSetter.value();
            NavigateValueSetter navigateValueSetter = configuration.getNavigateValueSetter(setter);
            if (navigateValueSetter == null) {
                throw new EasyQueryInvalidOperationException("not found navigate value setter:[" + EasyClassUtil.getSimpleName(setter) + "]");
            }
            return (obj, val) -> {
                Object ov = navigateValueSetter.beforeSet(entityClass, field.getName(), EasyObjectUtil.typeCastNullable(val));
                beanSetter.call(obj, ov);
            };
        } else {
            return beanSetter;
        }
    }

    private void createNavigateJoinMappingMetadata(NavigateJoin navigateJoin, Map<String, Field> staticFields, String property) {
        String[] mappingPath = getJoinMappingPath(navigateJoin, staticFields, property);
        if (mappingPath.length <= 1) {
            throw new EasyQueryInvalidOperationException("navigate join, mappingPath at least two path");
        }
        NavigateJoinMetadata navigateJoinMetadata = new NavigateJoinMetadata(this, mappingPath, property);

        property2NavigateJoinMap.put(property, navigateJoinMetadata);
    }

    private ColumnMetadata acceptColumnOption(String parentPropertyName, ColumnOption columnOption, ColumnAllIndex columnAllIndex) {

        String propertyName = parentPropertyName == null ? columnOption.getPropertyName() : parentPropertyName + "." + columnOption.getPropertyName();
        columnOption.setFullPropertyName(propertyName);
        ColumnMetadata columnMetadata = new ColumnMetadata(columnOption);
        ColumnMetadata oldValue = property2ColumnMap.put(columnMetadata.getPropertyName(), columnMetadata);
        if (oldValue != null) {
            throw new EasyQueryInvalidOperationException("propertyName:" + propertyName + ", repeat.");
        }
        if (columnOption.isValueObject()) {
            for (ColumnOption valueObjectColumnOption : columnOption.getValueObjectColumnOptions()) {
                ColumnMetadata valueObjectColumnMetadata = acceptColumnOption(propertyName, valueObjectColumnOption, columnAllIndex);
                columnMetadata.getValueObjectColumnMetadataList().add(valueObjectColumnMetadata);
            }
        } else {
            ColumnMetadata oldColumnName = column2PropertyMap.put(columnOption.getName(), columnMetadata);
            if (oldColumnName != null) {
                throw new EasyQueryInvalidOperationException("columnName:" + columnOption.getName() + ", repeat.");
            }
            if (columnOption.isTableEntity() && columnOption.isAutoSelect()) {
                dataReader = new BeanDataReader(dataReader, new PropertyDataReader(new EntityResultColumnMetadata(columnAllIndex.incrementAndGet(), this, columnMetadata)));
            }
        }
        return columnMetadata;
    }


    private void processEnumValueConverter(ColumnOption columnOption, Class<?> propertyType, QueryConfiguration configuration) {
        //如果是默认的那么就通过自动关联的值转换处进行寻找
        if (Enum.class.isAssignableFrom(propertyType)) {
            List<EnumValueAutoConverter<?, ?>> enumValueAutoConverters = configuration.getEnumValueAutoConverters();
            for (EnumValueAutoConverter<?, ?> enumValueAutoConverter : enumValueAutoConverters) {
                if (enumValueAutoConverter.apply(entityClass, EasyObjectUtil.typeCastNotNull(propertyType))) {
                    columnOption.setValueConverter(enumValueAutoConverter);
                    break;
                }
            }
        }
    }

    private ColumnOption createColumnOption(Field field, PropertyDescriptor propertyDescriptor, boolean tableEntity, String property, FastBeanProperty fastBeanProperty, QueryConfiguration configuration, FastBean fastBean, JdbcTypeHandlerManager jdbcTypeHandlerManager, boolean defaultAutoSelect, String fieldName, EasyQueryOption easyQueryOption) {
        NameConversion nameConversion = configuration.getNameConversion();
        Column column = field.getAnnotation(Column.class);
        boolean hasColumnName = column != null && EasyStringUtil.isNotBlank(column.value());
        boolean autoSelect = column == null ? defaultAutoSelect : column.autoSelect();
        if (column != null && !column.exist()) {
            autoSelect = false;
        }
        String columnName = hasColumnName ? nameConversion.annotationCovert(entityClass, column.value(), false) : nameConversion.convert(property);
        ColumnOption columnOption = new ColumnOption(tableEntity, this, columnName, property, fieldName);
//        if (column != null) {
//            columnOption.setNullable(column.nullable());
//        }
        columnOption.setPropertyDescriptor(propertyDescriptor);
        columnOption.setAutoSelect(autoSelect);


        Encryption encryption = field.getAnnotation(Encryption.class);
        if (encryption != null) {
            Class<? extends EncryptionStrategy> strategy = encryption.strategy();
            EncryptionStrategy easyEncryptionStrategy = configuration.getEasyEncryptionStrategy(strategy);
            if (easyEncryptionStrategy == null) {
                throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + "." + property + " Encryption strategy unknown, plz register this component");
            }
            columnOption.setEncryptionStrategy(easyEncryptionStrategy);
            columnOption.setSupportQueryLike(encryption.supportQueryLike());
        }
        if (column != null) {
            columnOption.setNullable(column.nullable());
            if (easyQueryOption.isSaveComment()) {
                columnOption.setComment(column.comment());
            }
            columnOption.setJdbcType(column.jdbcType());
            //获取默认的属性值转换
            Class<? extends ValueConverter<?, ?>> conversionClass = column.conversion();
            //如果不是默认的就代表添加了
            if (!Objects.equals(DefaultValueConverter.class, conversionClass)) {
                ValueConverter<?, ?> valueConverter = configuration.getValueConverter(conversionClass);
                if (valueConverter == null) {
                    throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + "." + property + " conversion unknown, plz register this component");
                }
                columnOption.setValueConverter(valueConverter);
            } else {
                //如果是默认的那么就通过自动关联的值转换处进行寻找
                processEnumValueConverter(columnOption, propertyDescriptor.getPropertyType(), configuration);
            }
            Class<? extends ComplexPropType> complexPropTypeClass = column.complexPropType();
            if (Objects.equals(DefaultComplexPropType.class, complexPropTypeClass)) {
                ComplexPropType complexPropType = new DefaultComplexPropType(fastBeanProperty.getPropertyType());
                columnOption.setComplexPropType(complexPropType);
            } else {
                ComplexPropType complexPropType = EasyClassUtil.newInstance(complexPropTypeClass);
                columnOption.setComplexPropType(new DefaultComplexPropType(complexPropType.complexType()));
            }
            Class<? extends JdbcTypeHandler> typeHandlerClass = column.typeHandler();
            if (!Objects.equals(typeHandlerClass, UnKnownTypeHandler.class)) {
                JdbcTypeHandler handlerByHandlerClass = jdbcTypeHandlerManager.getHandlerByHandlerClass(typeHandlerClass);
                columnOption.setJdbcTypeHandler(handlerByHandlerClass);
            }


        } else {
            //如果是默认的那么就通过自动关联的值转换处进行寻找
            processEnumValueConverter(columnOption, propertyDescriptor.getPropertyType(), configuration);
        }
        boolean exist = true;

        if (tableEntity) {

            if (column != null) {
                exist = column.exist();
                if (column.primaryKey()) {
                    keyPropertiesMap.put(property, columnName);
                }
                columnOption.setPrimary(column.primaryKey());
                boolean generatedKey = column.generatedKey();
                if (generatedKey) {
                    generatedKeyColumns.add(columnName);
                    Class<? extends GeneratedKeySQLColumnGenerator> generatedKeySQLColumnGeneratorClass = column.generatedSQLColumnGenerator();
                    if (!Objects.equals(DefaultGeneratedKeySQLColumnGenerator.class, generatedKeySQLColumnGeneratorClass)) {
                        GeneratedKeySQLColumnGenerator generatedKeySQLColumnGenerator = configuration.getGeneratedKeySQLColumnGenerator(generatedKeySQLColumnGeneratorClass);
                        if (generatedKeySQLColumnGenerator == null) {
                            throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + "." + property + " generated key sql column generator unknown, plz register this component");
                        }
                        columnOption.setGeneratedKeySQLColumnGenerator(generatedKeySQLColumnGenerator);
                    }
                } else if (column.primaryKey()) {
                    Class<? extends PrimaryKeyGenerator> primaryKeyGeneratorClass = column.primaryKeyGenerator();
                    //非默认的主键生成器
                    if (!Objects.equals(UnsupportPrimaryKeyGenerator.class, primaryKeyGeneratorClass)) {

                        PrimaryKeyGenerator primaryKeyGenerator = configuration.getPrimaryKeyGenerator(primaryKeyGeneratorClass);
                        if (primaryKeyGenerator == null) {
                            throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + "." + property + " primary key generator unknown, plz register this component");
                        }
                        columnOption.setPrimaryKeyGenerator(primaryKeyGenerator);
                        this.hasPrimaryKeyGenerator = true;
                    }
                }
                columnOption.setGeneratedKey(generatedKey);


                Class<? extends ColumnValueSQLConverter> columnValueSQLConverterClass = column.sqlConversion();
                boolean hasSQLConversion = !Objects.equals(DefaultColumnValueSQLConverter.class, columnValueSQLConverterClass);
                ColumnSQLExpression columnSQLExpression = column.sqlExpression();
                boolean hasSQLExpression = EasyStringUtil.isNotBlank(columnSQLExpression.sql());
                if (hasSQLConversion && hasSQLExpression) {
                    throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(entityClass) + "." + property + " conflict with sqlConversion and sqlExpression");
                }
                if (hasSQLConversion) {
                    //配置列值数据库转换器
                    ColumnValueSQLConverter columnValueSQLConverter = configuration.getColumnValueSQLConverter(columnValueSQLConverterClass);
                    if (columnValueSQLConverter == null) {
                        throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + "." + property + " column value sql converter unknown, plz register this component");
                    }
                    columnOption.setColumnValueSQLConverter(columnValueSQLConverter);
//                    if (columnValueSQLConverter.isMergeSubQuery()) {
//                        this.aliasQuery = true;
//                    }
                }
                if (hasSQLExpression) {
                    List<ExpArg> expArgs = EasyUtil.getExpArgs(columnSQLExpression.args());
                    EasyColumnValueSQLConverter easyColumnValueSQLConverter = new EasyColumnValueSQLConverter(columnSQLExpression.sql(), columnSQLExpression.realColumn(), expArgs);
                    columnOption.setColumnValueSQLConverter(easyColumnValueSQLConverter);
                }
            }
            if (!exist) {
                columnOption.setInsertIgnore(true);
                columnOption.setUpdateIgnore(true);
                columnOption.setUpdateSetInTrackDiff(false);
            } else {
                InsertIgnore insertIgnore = field.getAnnotation(InsertIgnore.class);
                if (insertIgnore != null) {
                    columnOption.setInsertIgnore(true);
                }

                UpdateIgnore updateIgnore = field.getAnnotation(UpdateIgnore.class);
                if (updateIgnore != null) {
                    columnOption.setUpdateIgnore(true);
                    columnOption.setUpdateSetInTrackDiff(updateIgnore.updateSetInTrackDiff());
                }
            }

            Version version = field.getAnnotation(Version.class);
            if (version != null) {

                Class<? extends VersionStrategy> strategy = version.strategy();
                VersionStrategy easyVersionStrategy = configuration.getEasyVersionStrategyOrNull(strategy);
                if (easyVersionStrategy == null) {
                    throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + "." + property + " Version strategy unknown, plz register this component");
                }
                columnOption.setVersion(true);

                if (versionMetadata == null) {
                    versionMetadata = new VersionMetadata(property, easyVersionStrategy);
                } else {
                    throw new EasyQueryException("multi version not support");
                }
            }
            ShardingDataSourceKey shardingDataSourceKey = field.getAnnotation(ShardingDataSourceKey.class);
            if (shardingDataSourceKey != null) {
                this.setShardingDataSourcePropertyName(property);
            }
            ShardingExtraDataSourceKey shardingExtraDataSourceKey = field.getAnnotation(ShardingExtraDataSourceKey.class);
            if (shardingExtraDataSourceKey != null) {
                this.addExtraShardingDataSourcePropertyName(property);
            }
            ShardingTableKey shardingTableKey = field.getAnnotation(ShardingTableKey.class);
            if (shardingTableKey != null) {
                this.setShardingTablePropertyName(property);
            }
            ShardingExtraTableKey shardingExtraTableKey = field.getAnnotation(ShardingExtraTableKey.class);
            if (shardingExtraTableKey != null) {
                this.addExtraShardingTablePropertyName(property);
            }

            LogicDelete logicDelete = field.getAnnotation(LogicDelete.class);
            if (logicDelete != null) {
                if (this.logicDeleteMetadata != null) {
                    throw new EasyQueryException("multi logic delete not support");
                }
                LogicDeleteStrategyEnum strategy = logicDelete.strategy();
                String param = logicDelete.param();
                if (Objects.equals(LogicDeleteStrategyEnum.CUSTOM, strategy)) {//使用自定义
                    String strategyName = logicDelete.strategyName();
                    if (EasyStringUtil.isBlank(strategyName)) {
                        throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + "." + property + " logic delete strategy is empty");
                    }
                    LogicDeleteStrategy globalLogicDeleteStrategy = configuration.getLogicDeleteStrategyNotNull(strategyName);
                    LogicDeleteBuilder logicDeleteBuilder = new LogicDeleteBuilder(entityClass, property, field.getType(), param);
                    this.logicDeleteMetadata = globalLogicDeleteStrategy.configureBuild(logicDeleteBuilder);
                } else {//使用系统默认的
                    LogicDeleteStrategy sysGlobalLogicDeleteStrategy = configuration.getSysLogicDeleteStrategyNotNull(strategy);
                    LogicDeleteBuilder logicDeleteBuilder = new LogicDeleteBuilder(entityClass, property, field.getType(), param);
                    this.logicDeleteMetadata = sysGlobalLogicDeleteStrategy.configureBuild(logicDeleteBuilder);
                }
            }
        }
        Property<Object, ?> beanGetter = fastBean.getBeanGetter(fastBeanProperty);
        columnOption.setGetterCaller(beanGetter);
        PropertySetterCaller<Object> beanSetter = fastBean.getBeanSetter(fastBeanProperty);
        columnOption.setSetterCaller(beanSetter);
        if (columnOption.getJdbcTypeHandler() == null) {
            JdbcTypeHandler jdbcTypeHandler = jdbcTypeHandlerManager.getHandler(columnOption.getPropertyDescriptor().getPropertyType());
            columnOption.setJdbcTypeHandler(jdbcTypeHandler);
        }
        return columnOption;

    }

    private void parseValueObject(ColumnOption parentColumnOption, QueryConfiguration configuration, JdbcTypeHandlerManager jdbcTypeHandlerManager) {
        PropertyDescriptor parentProperty = parentColumnOption.getPropertyDescriptor();
        EasyQueryOption easyQueryOption = configuration.getEasyQueryOption();
        Class<?> valueObjectClass = parentProperty.getPropertyType();
        Collection<Field> allFields = EasyClassUtil.getAllFields(valueObjectClass);
        PropertyDescriptor[] ps = EasyClassUtil.propertyDescriptors(valueObjectClass);
        PropertyDescriptorFinder propertyDescriptorFinder = new PropertyDescriptorFinder(ps);
        FastBean fastBean = EasyBeanUtil.getFastBean(valueObjectClass);
        for (Field field : allFields) {
            String property = EasyStringUtil.toLowerCaseFirstOne(field.getName());
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            //未找到bean属性就直接忽略
            PropertyDescriptor propertyDescriptor = propertyDescriptorFinder.find(property);
            if (propertyDescriptor == null) {
                continue;
            }
            Type genericType = field.getGenericType();
            FastBeanProperty fastBeanProperty = new FastBeanProperty(isGenericType(genericType), propertyDescriptor);
            ColumnIgnore columnIgnore = field.getAnnotation(ColumnIgnore.class);
            if (columnIgnore != null) {
                continue;
            }
            Navigate navigate = field.getAnnotation(Navigate.class);
            if (navigate != null) {
                createNavigateMetadata(true, navigate, field, fastBean, fastBeanProperty, property, configuration);
                continue;
            } else {
                ValueObject valueObject = field.getAnnotation(ValueObject.class);
                if (valueObject != null) {
                    ColumnOption columnOption = createColumnOption(field, propertyDescriptor, true, property, fastBeanProperty, configuration, fastBean, jdbcTypeHandlerManager, parentColumnOption.isAutoSelect(), field.getName(), easyQueryOption);
                    FastBean valueObjectFastBean = EasyBeanUtil.getFastBean(propertyDescriptor.getPropertyType());
                    columnOption.setValueObject(true);
                    columnOption.setBeanConstructorCreator(valueObjectFastBean.getBeanConstructorCreator());
                    parentColumnOption.getValueObjectColumnOptions().add(columnOption);
                    parseValueObject(columnOption, configuration, jdbcTypeHandlerManager);
                    continue;
                }
            }

            ColumnOption columnOption = createColumnOption(field, propertyDescriptor, true, property, fastBeanProperty, configuration, fastBean, jdbcTypeHandlerManager, parentColumnOption.isAutoSelect(), field.getName(), easyQueryOption);
            parentColumnOption.getValueObjectColumnOptions().add(columnOption);
        }
    }

    /**
     * 检查是否是泛型类型
     *
     * @param type
     * @return
     */
    private static boolean isGenericType(Type type) {
        if (type instanceof TypeVariable) {
            return true;
        } else if (type instanceof Class) {
            Class<?> clazz = (Class<?>) type;
            if (clazz.getTypeParameters().length > 0) {
                return true;
            }
        }
        return false;
    }

    private Class<?> getNavigateType(boolean toMany, Field field, FastBeanProperty fastBeanProperty) {

        if (toMany) {
            Type genericType = field.getGenericType();

            if (genericType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                Type[] typeArguments = parameterizedType.getActualTypeArguments();

                if (typeArguments.length > 0) {
                    Type elementType = typeArguments[0];
                    if (elementType instanceof Class) {
                        return (Class<?>) elementType;
                    }
                }
            }
            return null;
        }
        return fastBeanProperty.getPropertyType();
    }

    private void initSharding(QueryConfiguration configuration, Class<? extends ShardingInitializer> initializer) {

        ShardingInitializer easyShardingInitializer = configuration.getEasyShardingInitializerOrNull(initializer);
        if (easyShardingInitializer == null) {
            throw new EasyQueryInvalidOperationException("not found sharding initializer:" + EasyClassUtil.getSimpleName(initializer));
        }
        ShardingEntityBuilder<Object> shardingInitializerBuilder = new ShardingEntityBuilder<Object>(this, configuration.getEasyQueryOption());
        easyShardingInitializer.initialize(shardingInitializerBuilder);
        ShardingInitOption shardingInitOption = shardingInitializerBuilder.build();
        Map<String, Collection<String>> initializeTables = shardingInitOption.getActualTableNames();
        if (initializeTables != null && !initializeTables.isEmpty()) {
            Set<String> dataSources = initializeTables.keySet();
            for (String dataSource : dataSources) {
                Collection<String> tableNames = initializeTables.get(dataSource);
                if (EasyCollectionUtil.isNotEmpty(tableNames)) {
                    for (String name : tableNames) {
                        addActualTableWithDataSource(dataSource, name);
                    }
                } else {
                    addActualTableWithDataSource(dataSource, tableName);
                }
            }
        }
        ShardingSequenceConfig shardingSequenceConfig = null;
        //如果有配置
        Comparator<TableUnit> defaultTableNameComparator = shardingInitOption.getDefaultTableNameComparator();
        if (defaultTableNameComparator != null) {

            shardingSequenceConfig = new ShardingSequenceConfig(defaultTableNameComparator
                    , shardingInitOption.getSequenceProperties()
                    , shardingInitOption.getMaxShardingQueryLimit()
                    , shardingInitOption.getSequenceCompareMethods()
                    , shardingInitOption.getSequenceCompareAscMethods()
                    , shardingInitOption.getSequenceLimitMethods()
                    , shardingInitOption.getSequenceConnectionModeMethods()
                    , shardingInitOption.getConnectionMode());

        }
        this.shardingInitConfig = new ShardingInitConfig(shardingInitOption.getReverseFactor(), shardingInitOption.getMinReverseTotal(), shardingSequenceConfig);
    }

    protected void entityGlobalInterceptorConfigurationInit(QueryConfiguration configuration) {

        if (EasyStringUtil.isNotBlank(tableName)) {
            List<Interceptor> globalInterceptors = configuration.getEasyInterceptors().stream().sorted(Comparator.comparingInt(Interceptor::order)).collect(Collectors.toList());
            for (Interceptor globalInterceptor : globalInterceptors) {
                if (globalInterceptor.apply(entityClass)) {
                    if (globalInterceptor instanceof PredicateFilterInterceptor) {
                        predicateFilterInterceptors.add((PredicateFilterInterceptor) globalInterceptor);
                    }
                    if (globalInterceptor instanceof EntityInterceptor) {
                        entityInterceptors.add((EntityInterceptor) globalInterceptor);
                    }
                    if (globalInterceptor instanceof UpdateSetInterceptor) {
                        updateSetInterceptors.add((UpdateSetInterceptor) globalInterceptor);
                    }
                    if (globalInterceptor instanceof UpdateEntityColumnInterceptor) {
                        updateEntityColumnInterceptors.add((UpdateEntityColumnInterceptor) globalInterceptor);
                    }
                }
            }
        }
    }


    public Class<?> getEntityClass() {
        return entityClass;
    }

    public @Nullable String getTableName() {
        return tableName;
    }

    public @Nullable String getOldTableName() {
        return oldTableName;
    }

    public @Nullable String getTreeName() {
        return treeName;
    }

    public String getComment() {
        return comment;
    }

    public String getSchemaOrNull() {
        return schema;
    }

    public String getSchemaOrDefault(String defSchema) {
        if (schema == null) {
            return defSchema;
        }
        return schema;
    }

    public String getColumnName(String propertyName) {
        ColumnMetadata columnMetadata = property2ColumnMap.get(propertyName);
        if (columnMetadata == null) {
            throw new EasyQueryException(String.format("%s not found property:[%s] mapping column", EasyClassUtil.getSimpleName(entityClass), propertyName));
        }
        return columnMetadata.getName();
    }

    public String getPropertyNameOrNull(String columnName) {
        return getPropertyNameOrNull(columnName, null);
    }

    public String getPropertyNameNotNull(String columnName) {
        String propertyName = getPropertyNameOrNull(columnName, null);

        if (propertyName == null) {
            throw new EasyQueryException(String.format("not found column:[%s] mapping property", columnName));
        }
        return propertyName;
    }

    /**
     * 忽略大小写
     *
     * @param columnName
     * @param def
     * @return
     */
    public String getPropertyNameOrNull(String columnName, String def) {
        ColumnMetadata columnMetadata = getColumnMetadataOrNull(columnName);
        if (columnMetadata == null) {
            return def;
        }
        return columnMetadata.getPropertyName();
    }

    public ColumnMetadata getColumnMetadataOrNull(String columnName) {
        ColumnMetadata columnMetadata = null;
        if (null == (columnMetadata = column2PropertyMap.get(columnName))) {
            columnMetadata = column2PropertyMap.get(columnName.toLowerCase(Locale.ENGLISH));
        }
        return columnMetadata;

    }

    public Collection<ColumnMetadata> getColumns() {
        return property2ColumnMap.values();
    }

    public Collection<String> getProperties() {
        return property2ColumnMap.keySet();
    }

    public Map<String, ColumnMetadata> getProperty2ColumnMap() {
        return property2ColumnMap;
    }

    public Map<String, NavigateMetadata> getProperty2NavigateMap() {
        return property2NavigateMap;
    }

    /**
     * 获取所有的key
     *
     * @return
     */
    public Collection<String> getKeyProperties() {
        return keyPropertiesMap.keySet();
    }

    public boolean isKeyProperty(String propertyName) {
        if (propertyName == null) {
            return false;
        }
        return keyPropertiesMap.containsKey(propertyName);
    }

    public NavigateMetadata getNavigateNotNull(String propertyName) {
        NavigateMetadata navigateMetadata = getNavigateOrNull(propertyName);
        if (navigateMetadata == null) {
            throw new EasyQueryException(String.format(EasyClassUtil.getSimpleName(entityClass) + " not found property:[%s] mapping navigate", propertyName));
        }
        return navigateMetadata;
    }

    public NavigateFlatMetadata getNavigateFlatNotNull(String propertyName) {
        NavigateFlatMetadata navigateFlatMetadata = getNavigateFlatOrNull(propertyName);
        if (navigateFlatMetadata == null) {
            throw new EasyQueryException(String.format(EasyClassUtil.getSimpleName(entityClass) + " not found property:[%s] mapping navigate flat", propertyName));
        }
        return navigateFlatMetadata;
    }

    public NavigateMetadata getNavigateOrNull(String propertyName) {
        return property2NavigateMap.get(propertyName);
    }

    public NavigateFlatMetadata getNavigateFlatOrNull(String propertyName) {
        return property2NavigateFlatMap.get(propertyName);
    }

    public Collection<NavigateMetadata> getNavigateMetadatas() {
        return property2NavigateMap.values();
    }

    public Collection<NavigateFlatMetadata> getNavigateFlatMetadatas() {
        return property2NavigateFlatMap.values();
    }

    public NavigateJoinMetadata getNavigateJoinOrNull(String propertyName) {
        return property2NavigateJoinMap.get(propertyName);
    }

    public Collection<NavigateJoinMetadata> getNavigateJoinMetadatas() {
        return property2NavigateJoinMap.values();
    }

    public ColumnMetadata getColumnNotNull(String propertyName) {
        ColumnMetadata columnMetadata = getColumnOrNull(propertyName);
        if (columnMetadata == null) {
            throw new EasyQueryPropertyNotFoundException(entityClass, propertyName, String.format("%s not found property:[%s] mapping column name, please confirm that the field exists in the Java bean. if you want to use a non-standard Java bean, please set [propertyMode] to [same_as_entity]", EasyClassUtil.getSimpleName(entityClass), propertyName));
        }
        return columnMetadata;
    }

    public ColumnMetadata getColumnOrNull(String propertyName) {
        return property2ColumnMap.get(propertyName);
    }

    public void checkTable() {
        if (entityMetadataType != EntityMetadataTypeEnum.MAP) {
            if (EasyStringUtil.isEmpty(tableName)) {
                throw new EasyQueryException("current entity not mapping table name," + EasyClassUtil.getSimpleName(entityClass));
            }
        }
    }

    public void setLogicDeleteMetadata(LogicDeleteMetadata logicDeleteMetadata) {
        this.logicDeleteMetadata = logicDeleteMetadata;
    }

    public LogicDeleteMetadata getLogicDeleteMetadata() {
        return logicDeleteMetadata;
    }

    /**
     * 是否启用逻辑删除
     *
     * @return
     */
    public boolean enableLogicDelete() {
        return logicDeleteMetadata != null;
    }


    public List<PredicateFilterInterceptor> getPredicateFilterInterceptors() {
        return predicateFilterInterceptors;
    }

    public List<EntityInterceptor> getEntityInterceptors() {
        return entityInterceptors;
    }

    public List<UpdateSetInterceptor> getUpdateSetInterceptors() {
        return updateSetInterceptors;
    }

    public List<UpdateEntityColumnInterceptor> getUpdateEntityColumnInterceptors() {
        return updateEntityColumnInterceptors;
    }

    public List<String> getGeneratedKeyColumns() {
        return generatedKeyColumns;
    }


    public boolean hasVersionColumn() {
        return versionMetadata != null;
    }

    public VersionMetadata getVersionMetadata() {
        return versionMetadata;
    }

    public boolean isSharding() {
        return isMultiTableMapping() || isMultiDataSourceMapping();
    }

    public String getShardingDataSourcePropertyName() {
        return shardingDataSourcePropertyName;
    }

    public void setShardingDataSourcePropertyName(String shardingDataSourcePropertyName) {
        if (shardingDataSourcePropertyNames.contains(shardingDataSourcePropertyName)) {
            throw new EasyQueryInvalidOperationException("same sharding data source property name:[" + shardingDataSourcePropertyName + "]");
        }
        this.shardingDataSourcePropertyName = shardingDataSourcePropertyName;
        shardingDataSourcePropertyNames.add(shardingDataSourcePropertyName);
    }

    public void addExtraShardingDataSourcePropertyName(String shardingExtraDataSourcePropertyName) {
        if (shardingDataSourcePropertyNames.contains(shardingExtraDataSourcePropertyName)) {
            throw new EasyQueryInvalidOperationException("same sharding data source property name:[" + shardingExtraDataSourcePropertyName + "]");
        }
        shardingDataSourcePropertyNames.add(shardingExtraDataSourcePropertyName);
    }

    public String getShardingTablePropertyName() {
        return shardingTablePropertyName;
    }

    public void setShardingTablePropertyName(String shardingTablePropertyName) {
        if (shardingTablePropertyNames.contains(shardingTablePropertyName)) {
            throw new EasyQueryInvalidOperationException("same sharding table property name:[" + shardingTablePropertyName + "]");
        }
        this.shardingTablePropertyName = shardingTablePropertyName;
        shardingTablePropertyNames.add(shardingTablePropertyName);
    }

    public void addExtraShardingTablePropertyName(String shardingExtraTablePropertyName) {
        if (shardingTablePropertyNames.contains(shardingExtraTablePropertyName)) {
            throw new EasyQueryInvalidOperationException("same sharding table property name:[" + shardingExtraTablePropertyName + "]");
        }
        shardingTablePropertyNames.add(shardingExtraTablePropertyName);
    }

    public Collection<ActualTable> getActualTables() {
        return Collections.unmodifiableCollection(actualTables);
    }

    /**
     * 添加实际表和表所在的数据源
     *
     * @param dataSource
     * @param actualTableName
     */
    public void addActualTableWithDataSource(String dataSource, String actualTableName) {
        if (EasyStringUtil.isBlank(dataSource)) {
            throw new IllegalArgumentException("data source");
        }
        if (EasyStringUtil.isBlank(actualTableName)) {
            throw new IllegalArgumentException("actual table name");
        }
        dataSources.add(dataSource);
        actualTables.add(new ActualTable(dataSource, actualTableName));
    }

    public Collection<String> getDataSources() {
        return Collections.unmodifiableCollection(dataSources);
    }

    public Set<String> getShardingDataSourcePropertyNames() {
        return shardingDataSourcePropertyNames;
    }

    public Set<String> getShardingTablePropertyNames() {
        return shardingTablePropertyNames;
    }

    public ShardingInitConfig getShardingInitConfig() {
        return shardingInitConfig;
    }

    public Supplier<Object> getBeanConstructorCreator() {
        return beanConstructorCreator;
    }

    public EntityMetadataTypeEnum getEntityMetadataType() {
        return entityMetadataType;
    }

    public String getSingleKeyProperty() {
        Collection<String> keyProperties = getKeyProperties();
        if (EasyCollectionUtil.isNotSingle(keyProperties)) {
            throw new EasyQueryInvalidOperationException("entity :" + EasyClassUtil.getSimpleName(entityClass) + " not single key size :" + keyProperties.size() + ", and confirm whether the @Table annotation is present.");
        }
        return EasyCollectionUtil.first(keyProperties);
    }

    public DataReader getDataReader() {
        return dataReader;
    }

    public boolean isHasValueObject() {
        return hasValueObject;
    }

//    public boolean isAliasQuery() {
//        return aliasQuery;
//    }

    public @NotNull ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public boolean isHasPrimaryKeyGenerator() {
        return hasPrimaryKeyGenerator;
    }

    /**
     * 额外查询配置
     *
     * @return
     */
    public ExtraAutoIncludeConfigure getExtraAutoIncludeConfigure() {
        return extraAutoIncludeConfigure;
    }

    public Supplier<Query<?>> getCteViewerCreator() {
        return cteViewerCreator;
    }
}