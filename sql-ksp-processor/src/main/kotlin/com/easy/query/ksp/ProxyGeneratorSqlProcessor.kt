package com.easy.query.ksp

import com.easy.query.core.annotation.*
import com.easy.query.core.enums.RelationTypeEnum
import com.easy.query.core.util.EasyStringUtil
import com.easy.query.processor.FieldComment
import com.easy.query.processor.helper.*
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.*


@OptIn(KspExperimental::class)
class ProxyGeneratorSqlProcessor(
    private val environment: SymbolProcessorEnvironment,
) : SymbolProcessor {

    companion object {
        private val TYPE_MAPPING = mapOf(
            "float" to "java.lang.Float",
            "double" to "java.lang.Double",
            "short" to "java.lang.Short",
            "int" to "java.lang.Integer",
            "long" to "java.lang.Long",
            "byte" to "java.lang.Byte",
            "boolean" to "java.lang.Boolean"
        )
        private val TYPE_COLUMN_MAPPING = mapOf(
            "kotlin.Float" to KspPropertyColumn("SQLFloatTypeColumn", "java.lang.Float"),
            "kotlin.Double" to KspPropertyColumn("SQLDoubleTypeColumn", "java.lang.Double"),
            "kotlin.Short" to KspPropertyColumn("SQLShortTypeColumn", "java.lang.Short"),
            "kotlin.Int" to KspPropertyColumn("SQLIntegerTypeColumn", "java.lang.Integer"),
            "kotlin.Long" to KspPropertyColumn("SQLLongTypeColumn", "java.lang.Long"),
            "kotlin.Byte" to KspPropertyColumn("SQLByteTypeColumn", "java.lang.Byte"),
            "kotlin.Boolean" to KspPropertyColumn("SQLBooleanTypeColumn", "java.lang.Boolean"),
            "kotlin.String" to KspPropertyColumn("SQLStringTypeColumn", "java.lang.String"),


            "java.lang.Float" to KspPropertyColumn("SQLFloatTypeColumn", "java.lang.Float"),
            "java.lang.Double" to KspPropertyColumn("SQLDoubleTypeColumn", "java.lang.Double"),
            "java.lang.Short" to KspPropertyColumn("SQLShortTypeColumn", "java.lang.Short"),
            "java.lang.Integer" to KspPropertyColumn("SQLIntegerTypeColumn", "java.lang.Integer"),
            "java.lang.Long" to KspPropertyColumn("SQLLongTypeColumn", "java.lang.Long"),
            "java.lang.Byte" to KspPropertyColumn("SQLByteTypeColumn", "java.lang.Byte"),
            "java.math.BigDecimal" to KspPropertyColumn("SQLBigDecimalTypeColumn", "java.math.BigDecimal"),
            "java.lang.Boolean" to KspPropertyColumn("SQLBooleanTypeColumn", "java.lang.Boolean"),
            "java.lang.String" to KspPropertyColumn("SQLStringTypeColumn", "java.lang.String"),
            "java.util.UUID" to KspPropertyColumn("SQLUUIDTypeColumn", "java.util.UUID"),
            "java.sql.Timestamp" to KspPropertyColumn("SQLTimestampTypeColumn", "java.sql.Timestamp"),
            "java.sql.Time" to KspPropertyColumn("SQLTimeTypeColumn", "java.sql.Time"),
            "java.sql.Date" to KspPropertyColumn("SQLDateTypeColumn", "java.sql.Date"),
            "java.util.Date" to KspPropertyColumn("SQLUtilDateTypeColumn", "java.util.Date"),
            "java.time.LocalDate" to KspPropertyColumn("SQLLocalDateTypeColumn", "java.time.LocalDate"),
            "java.time.LocalDateTime" to KspPropertyColumn("SQLLocalDateTimeTypeColumn", "java.time.LocalDateTime"),
            "java.time.LocalTime" to KspPropertyColumn("SQLLocalTimeTypeColumn", "java.time.LocalTime")
        )

        private const val FIELD_DOC_COMMENT_TEMPLATE = "\n" +
                "    /**\n" +
                "     * [@{entityClass}.@{property}]\n" +
                "     @{comment}\n" +
                "     */"
        private const val FIELD_EMPTY_DOC_COMMENT_TEMPLATE = "\n" +
                "    /**\n" +
                "     * [@{entityClass}.@{property}]\n" +
                "     */"

        fun getKspPropertyColumn(fieldGenericType: String?, anyType: Boolean?): PropertyColumn {
            return TYPE_COLUMN_MAPPING.getOrDefault(
                fieldGenericType,
                KspPropertyColumn("SQLAnyTypeColumn", fieldGenericType, anyType)
            )
        }

    }


    override fun process(resolver: Resolver): List<KSAnnotated> {
        resolver.getSymbolsWithAnnotation(EntityProxy::class.java.name).forEach {
            if (it is KSClassDeclaration) {
                environment.logger.info("entityClassElement:${it.qualifiedName?.asString()}")
                buildEntityProxy(it, resolver)
            }
        }
        return emptyList()
    }

    @OptIn(KspExperimental::class)
    private fun buildEntityProxy(entityClassElement: KSClassDeclaration, resolver: Resolver) {
        val basePath = ""
        val entityProxy = entityClassElement.getAnnotationsByType(EntityProxy::class).first()
        //dto相关类型无Table注解
        val tableAnnotation = entityClassElement.getAnnotationsByType(Table::class).firstOrNull()


        // 每一个 entity 生成一个独立的文件
        val entityFullName = entityClassElement.qualifiedName!!.asString()
        val realGenPackage = entityProxy.generatePackage
            .takeIf { it.isNotBlank() } ?: (entityClassElement.packageName.asString() + ".proxy")
//        val realGenPackage = entityClassElement.packageName.asString() + ".proxy"
        val entityClassName = entityClassElement.simpleName.asString()
        val proxyInstanceName = entityProxy.value.takeIf { it.isNotBlank() } ?: "${entityClassName}Proxy"

        val ignoreProperties = entityProxy.ignoreProperties.toHashSet()

        val aptFileCompiler =
            AptFileCompiler(
                realGenPackage,
                entityClassName,
                proxyInstanceName,
                tableAnnotation,
                AptSelectorInfo("${proxyInstanceName}Fetcher")
            )
        aptFileCompiler.addImports("com.easy.query.core.proxy.fetcher.AbstractFetcher")
        aptFileCompiler.addImports("com.easy.query.core.proxy.SQLSelectAsExpression")
        aptFileCompiler.addImports("com.easy.query.core.proxy.core.EntitySQLContext")
        val aptValueObjectInfo = AptValueObjectInfo(entityClassName)
        aptFileCompiler.addImports(entityFullName)

        var currentClassElement: KSClassDeclaration? = entityClassElement
        while (currentClassElement != null) {
            fillPropertyAndColumns(resolver, aptFileCompiler, aptValueObjectInfo, currentClassElement, ignoreProperties)
            currentClassElement =
                currentClassElement.superTypes.firstOrNull()?.resolve()?.declaration as KSClassDeclaration? ?: break
        }

        val content = buildTablesClass(aptFileCompiler, aptValueObjectInfo)
//        genClass(basePath, realGenPackage, proxyInstanceName, content)
        environment.codeGenerator.createNewFile(
            Dependencies(false, entityClassElement.containingFile!!),
            realGenPackage,
            proxyInstanceName,
            "kt"
        ).use {
            it.write(content.encodeToByteArray())
        }
    }

    @OptIn(KspExperimental::class)
    private fun fillPropertyAndColumns(
        resolver: Resolver,
        aptFileCompiler: AptFileCompiler,
        aptValueObjectInfo: AptValueObjectInfo,
        classElement: KSClassDeclaration,
        ignoreProperties: Set<String>,
    ) {
        classElement.declarations.filterIsInstance<KSPropertyDeclaration>().forEach { fieldElement ->
            val modifiers = fieldElement.modifiers
            if (modifiers.contains(Modifier.JAVA_STATIC)) {
                // Ignore static fields
                return@forEach
            }

            val propertyName = fieldElement.simpleName.asString()
            if (ignoreProperties.isNotEmpty() && ignoreProperties.contains(propertyName)) {
                return@forEach
            }
            val columnIgnore = fieldElement.getAnnotationsByType(ColumnIgnore::class).firstOrNull()
            if (columnIgnore != null) {
                return@forEach
            }
            val navigate = fieldElement.getAnnotationsByType(Navigate::class).firstOrNull()
            val includeProperty = navigate != null
            var includeManyProperty = false
            val proxyProperty = fieldElement.getAnnotationsByType(ProxyProperty::class).firstOrNull()
            val proxyPropertyName = proxyProperty?.value ?: propertyName
            val anyType = proxyProperty?.generateAnyType

            val type = fieldElement.type.resolve()
            val isGeneric = type.arguments.isNotEmpty()
            // TODO 似乎这个isDeclared没用上
            val isDeclared = type.declaration is KSClassDeclaration
            val fieldGenericType = getGenericTypeString(isGeneric, isDeclared, includeProperty, type)
            val docComment = fieldElement.docString
            val valueObject = fieldElement.getAnnotationsByType(ValueObject::class).firstOrNull()
            val isValueObject = valueObject != null
            val fieldName =
                if (isValueObject) fieldGenericType.substringAfterLast('.') else aptFileCompiler.entityClassName
            var fieldComment = getFiledComment(docComment, fieldName, propertyName)
            val propertyColumn = getKspPropertyColumn(fieldGenericType, anyType)
            aptFileCompiler.addImports(propertyColumn.import)

            if (!includeProperty) {
                aptFileCompiler.selectorInfo.addProperties(
                    AptSelectPropertyInfo(
                        propertyName,
                        fieldComment,
                        proxyPropertyName
                    )
                )
            } else {
                aptFileCompiler.addImports("com.easy.query.core.proxy.columns.SQLNavigateColumn")
                val navigatePropertyProxyFullName = getNavigatePropertyProxyFullName(
                    resolver,
                    propertyColumn.propertyType,
                    navigate?.propIsProxy == true
                )
                if (navigatePropertyProxyFullName != null) {
                    propertyColumn.navigateProxyName = navigatePropertyProxyFullName
                } else {
//                    fieldComment.proxyComment = "$fieldComment\n//apt提示无法获取导航属性代理:${propertyColumn.propertyType}"
                    fieldComment.proxyComment += "\n// apt提示无法获取导航属性代理: $propertyColumn.propertyType"
                }
                if (navigate?.value == RelationTypeEnum.OneToMany || navigate?.value == RelationTypeEnum.ManyToMany) {
                    includeManyProperty = true
                    aptFileCompiler.addImports("com.easy.query.core.proxy.columns.SQLManyQueryable")
                }

            }
            aptValueObjectInfo.addProperties(
                AptPropertyInfo(
                    propertyName,
                    propertyColumn,
                    fieldComment,
                    fieldName,
                    isValueObject,
                    includeProperty,
                    includeManyProperty,
                    proxyPropertyName
                )
            )
            valueObject?.let {
                aptFileCompiler.addImports("com.easy.query.core.proxy.AbstractValueObjectProxyEntity")
                aptFileCompiler.addImports(fieldGenericType)
                val valueObjectClassName = fieldGenericType.substringAfterLast('.')
                val fieldClass = type.declaration as KSClassDeclaration
                val fieldAptValueObjectInfo = AptValueObjectInfo(valueObjectClassName)
                aptValueObjectInfo.children.add(fieldAptValueObjectInfo)
                fillValueObject(
                    resolver,
                    propertyName,
                    fieldAptValueObjectInfo,
                    fieldClass,
                    aptFileCompiler,
                    ignoreProperties
                )
            }
        }
    }

    private fun getGenericTypeString(
        isGeneric: Boolean,
        isDeclared: Boolean,
        includeProperty: Boolean,
        type: KSType,
    ): String {
        val typeString = defTypeString(isDeclared, includeProperty, type)
        return if (typeString.contains(".")) {
            typeString
        } else {
            TYPE_MAPPING.getOrDefault(typeString, typeString)
        }
    }

    private fun KSType.toJavaString(): String {
        val type = this
        if (type.declaration.qualifiedName == null) {
            return "Any"
        }
        val declaration = type.declaration
        val className = declaration.qualifiedName!!.asString()
        // 将kotlin的类型转为java对应的类型
        val arguments = type.arguments
        return if (arguments.isNotEmpty()) { // 泛型
            "$className<${
                arguments.joinToString(", ") {
                    // 如果是可空的，增加问号
                    val resolve = it.type?.resolve()
                    val nullable = if (resolve?.isMarkedNullable == true) "?" else ""
                    resolve?.toJavaString()?.let { ktType -> ktType + nullable } ?: "*"
                }
            }>"
        } else {
            className
        }
    }

    private fun defTypeString(
        isDeclared: Boolean,
        includeProperty: Boolean,
        type: KSType,
    ): String {
        return if (includeProperty) {
            val genericType = type.arguments.firstOrNull()?.type?.resolve()
            genericType?.toJavaString() ?: type.toJavaString()
        } else {
            type.toJavaString()
        }
    }

    fun parseGenericType(genericTypeString: String): String {
        if (genericTypeString.contains(",")) {
            return genericTypeString
        }
        val regex = Regex("<(.+?)>$")
        val matchResult = regex.find(genericTypeString)
        return matchResult?.groupValues?.get(1) ?: genericTypeString
    }

    private fun getFiledComment(docComment: String?, className: String, propertyName: String): FieldComment {
        if (docComment == null) {
            val proxyComment = FIELD_EMPTY_DOC_COMMENT_TEMPLATE
                .replace("@{entityClass}", className)
                .replace("@{property}", propertyName)
            return FieldComment(proxyComment, "");
        }
        val commentLines = docComment.trim().split("\n".toRegex())
        val fieldComment = StringBuilder()
        fieldComment.append("* ").append(commentLines[0])
        for (i in 1..<commentLines.size) {
            fieldComment.append("\n     *").append(commentLines[i])
        }
        var entityComment = fieldComment.toString()
        val proxyComment = FIELD_DOC_COMMENT_TEMPLATE
            .replace("@{comment}", entityComment)
            .replace("@{entityClass}", className)
            .replace("@{property}", propertyName)
        return FieldComment(proxyComment, entityComment);
    }

    @OptIn(KspExperimental::class)
    private fun getNavigatePropertyProxyFullName(
        resolver: Resolver,
        fullClassName: String,
        propIsProxy: Boolean,
    ): String? {
        val typeElement = resolver.getClassDeclarationByName(fullClassName)
        if (typeElement != null) {
            val annotation = typeElement.getAnnotationsByType(EntityProxy::class).firstOrNull()
            if (annotation != null) {
                return if (EasyStringUtil.isBlank(annotation.value)) {
                    getDefaultClassProxyName(fullClassName)
                } else fullClassName.substring(0, fullClassName.lastIndexOf(".")) + ".proxy." + annotation.value
            }
            val annotationFile = typeElement.getAnnotationsByType(EntityFileProxy::class).firstOrNull()
            if (annotationFile != null) {
                return if (EasyStringUtil.isBlank(annotationFile.value)) {
                    getDefaultClassProxyName(fullClassName)
                } else fullClassName.substring(0, fullClassName.lastIndexOf(".")) + ".proxy." + annotationFile.value
            }
        }
        return if (propIsProxy) {
            getDefaultClassProxyName(fullClassName)
        } else null
        //        }
    }

    private fun getDefaultClassProxyName(fullClassName: String): String {
        return fullClassName.substring(0, fullClassName.lastIndexOf(".")) + ".proxy." + fullClassName.substring(
            fullClassName.lastIndexOf(".") + 1
        ) + "Proxy"
    }

    private fun fillValueObject(
        resolver: Resolver,
        parentProperty: String,
        aptValueObjectInfo: AptValueObjectInfo,
        fieldClassElement: KSClassDeclaration,
        aptFileCompiler: AptFileCompiler,
        ignoreProperties: Set<String>,
    ) {
        val entityName = aptValueObjectInfo.entityName
        val enclosedElements = fieldClassElement.declarations

        for (fieldElement in enclosedElements) {
            if (fieldElement is KSPropertyDeclaration) {
                val modifiers = fieldElement.modifiers
                if (modifiers.contains(Modifier.JAVA_STATIC)) {
                    // Ignore static fields
                    continue
                }

                val propertyName = fieldElement.simpleName.asString()
                if (ignoreProperties.isNotEmpty() && ignoreProperties.contains("$parentProperty.$propertyName")) {
                    continue
                }

                val columnIgnore = fieldElement.getAnnotationsByType(ColumnIgnore::class).firstOrNull()
                if (columnIgnore != null) {
                    continue
                }
                val navigate = fieldElement.getAnnotationsByType(Navigate::class).firstOrNull()
                val includeProperty = navigate != null
                var includeManyProperty = false

                val proxyProperty = fieldElement.getAnnotationsByType(ProxyProperty::class).firstOrNull()
                val proxyPropertyName = proxyProperty?.value ?: propertyName
                val anyType = proxyProperty?.generateAnyType

                val type = fieldElement.type.resolve()
                val isGeneric = type.arguments.isNotEmpty()
                val isDeclared = type.declaration is KSClassDeclaration
                val fieldGenericType = getGenericTypeString(isGeneric, isDeclared, includeProperty, type)
                val docComment = fieldElement.docString ?: ""
                val valueObject = fieldElement.getAnnotationsByType(ValueObject::class).firstOrNull() != null
                val fieldName = if (valueObject) fieldGenericType.substringAfterLast('.') else entityName
                var fieldComment = getFiledComment(docComment, fieldName, propertyName)
                val propertyColumn = getKspPropertyColumn(fieldGenericType, anyType)
                aptFileCompiler.addImports(propertyColumn.import)

                if (includeProperty) {
                    aptFileCompiler.addImports("com.easy.query.core.proxy.columns.SQLNavigateColumn")
                    val navigatePropertyProxyFullName = getNavigatePropertyProxyFullName(
                        resolver,
                        propertyColumn.propertyType,
                        navigate?.propIsProxy == true
                    )
                    if (navigatePropertyProxyFullName != null) {
                        propertyColumn.navigateProxyName = navigatePropertyProxyFullName
                    } else {
                        fieldComment.proxyComment += "\n// apt提示无法获取导航属性代理: $propertyColumn.propertyType"
                    }
                    if (navigate?.value == RelationTypeEnum.OneToMany || navigate?.value == RelationTypeEnum.ManyToMany) {
                        includeManyProperty = true
                        aptFileCompiler.addImports("com.easy.query.core.proxy.columns.SQLManyQueryable")
                    }
                }

                aptValueObjectInfo.addProperties(
                    AptPropertyInfo(
                        propertyName,
                        propertyColumn,
                        fieldComment,
                        fieldName,
                        valueObject,
                        includeProperty,
                        includeManyProperty,
                        proxyPropertyName
                    )
                )

                if (valueObject) {
                    aptFileCompiler.addImports(fieldGenericType)
                    val valueObjectClassName = fieldGenericType.substringAfterLast('.')
                    val fieldClass = type.declaration as KSClassDeclaration
                    val fieldAptValueObjectInfo = AptValueObjectInfo(valueObjectClassName)
                    aptValueObjectInfo.children.add(fieldAptValueObjectInfo)
                    fillValueObject(
                        resolver,
                        "$parentProperty.$propertyName",
                        fieldAptValueObjectInfo,
                        fieldClass,
                        aptFileCompiler,
                        ignoreProperties
                    )
                }
            }
        }
    }

    private fun buildTablesClass(aptFileCompiler: AptFileCompiler, aptValueObjectInfo: AptValueObjectInfo): String {
        return KspCreatorHelper.createProxy(aptFileCompiler, aptValueObjectInfo)
    }
}