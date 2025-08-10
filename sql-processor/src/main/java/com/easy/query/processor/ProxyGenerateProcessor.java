package com.easy.query.processor;


import com.easy.query.core.annotation.ColumnIgnore;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.ProxyProperty;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.ValueObject;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.processor.helper.AptCreatorHelper;
import com.easy.query.processor.helper.AptFileCompiler;
import com.easy.query.processor.helper.AptPropertyInfo;
import com.easy.query.processor.helper.AptSelectPropertyInfo;
import com.easy.query.processor.helper.AptSelectorInfo;
import com.easy.query.processor.helper.AptValueObjectInfo;
import com.easy.query.processor.helper.PropertyColumn;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create time 2023/6/24 14:17
 * 文件说明
 *
 * @author xuejiaming
 */
//@SupportedAnnotationTypes({"com.easy.query.core.annotation.EntityProxy"})
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ProxyGenerateProcessor extends AbstractProcessor {
    private static final Map<String, String> TYPE_MAPPING = new HashMap<>();
    private static final Map<String, PropertyColumn> TYPE_COLUMN_MAPPING = new HashMap<>();

    static {
        TYPE_MAPPING.put("float", "java.lang.Float");
        TYPE_MAPPING.put("double", "java.lang.Double");
        TYPE_MAPPING.put("short", "java.lang.Short");
        TYPE_MAPPING.put("int", "java.lang.Integer");
        TYPE_MAPPING.put("long", "java.lang.Long");
        TYPE_MAPPING.put("byte", "java.lang.Byte");
        TYPE_MAPPING.put("boolean", "java.lang.Boolean");
        TYPE_COLUMN_MAPPING.put("java.lang.Float", new PropertyColumn("SQLFloatTypeColumn", "java.lang.Float"));
        TYPE_COLUMN_MAPPING.put("java.lang.Double", new PropertyColumn("SQLDoubleTypeColumn", "java.lang.Double"));
        TYPE_COLUMN_MAPPING.put("java.lang.Short", new PropertyColumn("SQLShortTypeColumn", "java.lang.Short"));
        TYPE_COLUMN_MAPPING.put("java.lang.Integer", new PropertyColumn("SQLIntegerTypeColumn", "java.lang.Integer"));
        TYPE_COLUMN_MAPPING.put("java.lang.Long", new PropertyColumn("SQLLongTypeColumn", "java.lang.Long"));
        TYPE_COLUMN_MAPPING.put("java.lang.Byte", new PropertyColumn("SQLByteTypeColumn", "java.lang.Byte"));
        TYPE_COLUMN_MAPPING.put("java.math.BigDecimal", new PropertyColumn("SQLBigDecimalTypeColumn", "java.math.BigDecimal"));
        TYPE_COLUMN_MAPPING.put("java.lang.Boolean", new PropertyColumn("SQLBooleanTypeColumn", "java.lang.Boolean"));
        TYPE_COLUMN_MAPPING.put("java.lang.String", new PropertyColumn("SQLStringTypeColumn", "java.lang.String"));
        TYPE_COLUMN_MAPPING.put("java.util.UUID", new PropertyColumn("SQLUUIDTypeColumn", "java.util.UUID"));
        TYPE_COLUMN_MAPPING.put("java.sql.Timestamp", new PropertyColumn("SQLTimestampTypeColumn", "java.sql.Timestamp"));
        TYPE_COLUMN_MAPPING.put("java.sql.Time", new PropertyColumn("SQLTimeTypeColumn", "java.sql.Time"));
        TYPE_COLUMN_MAPPING.put("java.sql.Date", new PropertyColumn("SQLDateTypeColumn", "java.sql.Date"));
        TYPE_COLUMN_MAPPING.put("java.util.Date", new PropertyColumn("SQLUtilDateTypeColumn", "java.util.Date"));
        TYPE_COLUMN_MAPPING.put("java.time.LocalDate", new PropertyColumn("SQLLocalDateTypeColumn", "java.time.LocalDate"));
        TYPE_COLUMN_MAPPING.put("java.time.LocalDateTime", new PropertyColumn("SQLLocalDateTimeTypeColumn", "java.time.LocalDateTime"));
        TYPE_COLUMN_MAPPING.put("java.time.LocalTime", new PropertyColumn("SQLLocalTimeTypeColumn", "java.time.LocalTime"));
    }

    private static final String FIELD_DOC_COMMENT_TEMPLATE = "\n" +
            "    /**\n" +
            "     * {@link @{entityClass}#get@{property}}\n" +
            "     @{comment}\n" +
            "     */";
    private static final String FIELD_EMPTY_DOC_COMMENT_TEMPLATE = "\n" +
            "    /**\n" +
            "     * {@link @{entityClass}#get@{property}}\n" +
            "     */";
    private Filer filer;
    private Elements elementUtils;
    private Types typeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.filer = processingEnv.getFiler();
        this.elementUtils = processingEnv.getElementUtils();
        this.typeUtils = processingEnv.getTypeUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportedAnnotationTypes = new HashSet<>();
        supportedAnnotationTypes.add(EntityProxy.class.getCanonicalName());
        return supportedAnnotationTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
//            EasyQueryProxyProperties props = new EasyQueryProxyProperties(filer);
//
//            String enable = props.getProperties().getProperty("processor.enable", "");
//            if ("false".equalsIgnoreCase(enable)) {
//                return true;
//            }
            String basePath = "";
//
//            //upperCase, lowerCase, upperCamelCase, lowerCamelCase
//            String proxyNameStyle = props.getProperties().getProperty("processor.proxyInstanceNameStyle", "upperCase");

            //代理类后缀
//            String proxyClassSuffix = props.getProperties().getProperty("processor.proxyClassSuffix", "Proxy");
//            String defaultProxyInstanceName = props.getProperties().getProperty("processor.proxyInstanceName", "DEFAULT");

//            //待忽略对象后缀
//            String[] entityIgnoreSuffixes = props.getProperties().getProperty("processor.entity.ignoreSuffixes", "").split(",");


//            StringBuilder tablesContent = new StringBuilder();
            roundEnv.getElementsAnnotatedWith(EntityProxy.class)
                    .forEach((Consumer<Element>) entityClassElement -> {

//                String proxyEntityName = entityClassElement.getSimpleName().toString();

//                for (String entityIgnoreSuffix : entityIgnoreSuffixes) {
//                    if (proxyEntityName.endsWith(entityIgnoreSuffix.trim())) {
//                        proxyEntityName = proxyEntityName.substring(0, proxyEntityName.length() - entityIgnoreSuffix.length());
//                        break;
//                    }
//                }
                EntityFileProxy entityFileProxy = entityClassElement.getAnnotation(EntityFileProxy.class);
                if (entityFileProxy != null) {
                    return;
                }
                EntityProxy entityProxy = entityClassElement.getAnnotation(EntityProxy.class);

                Table tableAnnotation = entityClassElement.getAnnotation(Table.class);


                //每一个 entity 生成一个独立的文件

                String entityFullName = entityClassElement.toString();
                String realGenPackage = guessTablesPackage(entityFullName);
                String entityClassName = entityClassElement.getSimpleName().toString();
                String proxyInstanceName = EasyStringUtil.isBlank(entityProxy.value()) ? entityClassName + "Proxy" : entityProxy.value();
//                if (EasyStringUtil.isBlank(proxyInstanceName)) {
//                    proxyInstanceName = buildName(entityClassNameReference + "Proxy", "upperCase");
//                }
                HashSet<String> ignoreProperties = new HashSet<>(Arrays.asList(entityProxy.ignoreProperties()));


                TypeElement classElement = (TypeElement) entityClassElement;
                AptFileCompiler aptFileCompiler = new AptFileCompiler(realGenPackage, entityClassName, proxyInstanceName, tableAnnotation, new AptSelectorInfo(proxyInstanceName + "Fetcher"));
                aptFileCompiler.addImports("com.easy.query.core.proxy.fetcher.AbstractFetcher");
                aptFileCompiler.addImports("com.easy.query.core.proxy.SQLSelectAsExpression");
                aptFileCompiler.addImports("com.easy.query.core.proxy.core.EntitySQLContext");
                AptValueObjectInfo aptValueObjectInfo = new AptValueObjectInfo(entityClassName);
                aptFileCompiler.addImports(entityFullName);
                do {
                    fillPropertyAndColumns(aptFileCompiler, aptValueObjectInfo, classElement, ignoreProperties);
                    classElement = (TypeElement) typeUtils.asElement(classElement.getSuperclass());
                } while (classElement != null);

                String content = buildTablesClass(aptFileCompiler, aptValueObjectInfo);
                genClass(basePath, realGenPackage, proxyInstanceName, content);

            });
        }
        return false;
    }

    public boolean isAbsolutePath(String path) {
        return path != null && (path.startsWith("/") || path.indexOf(":") > 0);
    }

    /**
     * 获取项目的根目录，也就是根节点 pom.xml 所在的目录
     *
     * @return
     */
    private String getProjectRootPath(String genFilePath) {
        File file = new File(genFilePath);
        int count = 20;
        return getProjectRootPath(file, count);
    }

    private String getProjectRootPath(File file, int count) {
        if (count <= 0) {
            return null;
        }
        if (file.isFile()) {
            return getProjectRootPath(file.getParentFile(), --count);
        } else {
            if (new File(file, "pom.xml").exists() && !new File(file.getParentFile(), "pom.xml").exists()) {
                return file.getAbsolutePath();
            } else {
                return getProjectRootPath(file.getParentFile(), --count);
            }
        }
    }

    private boolean isFromTestSource(String path) {
        return path.contains("test-sources") || path.contains("test-annotations");
    }

    private void genClass(String basePath, String genPackageName, String className, String genContent) {
        Writer writer = null;
        try {
            JavaFileObject sourceFile = filer.createSourceFile(genPackageName + "." + className);
            if (basePath == null || basePath.trim().isEmpty()) {
                writer = sourceFile.openWriter();
                writer.write(genContent);
                writer.flush();
                return;
            }


            String defaultGenPath = sourceFile.toUri().getPath();

            //真实的生成代码的目录
            String realPath;

            //用户配置的路径为绝对路径
            if (isAbsolutePath(basePath)) {
                realPath = basePath;
            }
            //配置的是相对路径，那么则以项目根目录为相对路径
            else {
                String projectRootPath = getProjectRootPath(defaultGenPath);
                realPath = new File(projectRootPath, basePath).getAbsolutePath();
            }

            //通过在 test/java 目录下执行编译生成的
            boolean fromTestSource = isFromTestSource(defaultGenPath);
            if (fromTestSource) {
                realPath = new File(realPath, "src/test/java").getAbsolutePath();
            } else {
                realPath = new File(realPath, "src/main/java").getAbsolutePath();
            }

            File genJavaFile = new File(realPath, (genPackageName + "." + className).replace(".", "/") + ".java");
            if (!genJavaFile.getParentFile().exists() && !genJavaFile.getParentFile().mkdirs()) {
                System.out.println(">>>>>ERROR: can not mk dirs by easy-query processor for: " + genJavaFile.getParentFile());
                return;
            }
            int bufferSize = 32 * 1024; // 32KB缓冲区
            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(genJavaFile),
                            StandardCharsets.UTF_8
                    ),
                    bufferSize
            );
//            writer = new PrintWriter(new FileOutputStream(genJavaFile));
            writer.write(genContent);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public static String camelToUnderline(String string) {
        if (string == null || string.trim().isEmpty()) {
            return "";
        }
        int len = string.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = string.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append('_');
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }


    public static String firstCharToUpperCase(String string) {
        char firstChar = string.charAt(0);
        if (firstChar >= 'a' && firstChar <= 'z') {
            char[] arr = string.toCharArray();
            arr[0] -= ('a' - 'A');
            return new String(arr);
        }
        return string;
    }

    public static String firstCharToLowerCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            char[] arr = str.toCharArray();
            arr[0] += ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    //upperCase, lowerCase, upperCamelCase, lowerCamelCase
    private static String buildName(String name, String style) {
        if ("upperCase".equalsIgnoreCase(style)) {
            return camelToUnderline(name).toUpperCase();
        } else if ("lowerCase".equalsIgnoreCase(style)) {
            return camelToUnderline(name).toLowerCase();
        } else if ("upperCamelCase".equalsIgnoreCase(style)) {
            return firstCharToUpperCase(name);
        }
        //lowerCamelCase
        else {
            return firstCharToLowerCase(name);
        }
    }

    private String buildTablesClass(AptFileCompiler aptFileCompiler, AptValueObjectInfo aptValueObjectInfo) {
        return AptCreatorHelper.createProxy(aptFileCompiler, aptValueObjectInfo);
    }

    private String guessTablesPackage(String entityClassName) {
        StringBuilder guessPackage = new StringBuilder();
        if (!entityClassName.contains(".")) {
            guessPackage.append("proxy");// = "table";
        } else {
            guessPackage.append(entityClassName, 0, entityClassName.lastIndexOf(".")).append(".proxy");
        }
        return guessPackage.toString();
    }

    private void fillValueObject(String parentProperty, AptValueObjectInfo aptValueObjectInfo, Element fieldClassElement, AptFileCompiler aptFileCompiler, Set<String> ignoreProperties) {
        String entityName = aptValueObjectInfo.getEntityName();
        List<? extends Element> enclosedElements = fieldClassElement.getEnclosedElements();
        for (Element fieldElement : enclosedElements) {
            if (ElementKind.FIELD == fieldElement.getKind()) {

                Set<Modifier> modifiers = fieldElement.getModifiers();
                if (modifiers.contains(Modifier.STATIC)) {
                    //ignore static fields
                    continue;
                }

                String propertyName = fieldElement.toString();
                if (!ignoreProperties.isEmpty() && ignoreProperties.contains(parentProperty + "." + propertyName)) {
                    continue;
                }
                ColumnIgnore column = fieldElement.getAnnotation(ColumnIgnore.class);
                if (column != null) {
                    continue;
                }
                Navigate navigate = fieldElement.getAnnotation(Navigate.class);
                boolean includeProperty = navigate != null;
                boolean includeManyProperty = false;

                ProxyProperty proxyProperty = fieldElement.getAnnotation(ProxyProperty.class);
                String proxyPropertyName = proxyProperty != null ? proxyProperty.value() : propertyName;
                Boolean anyType = proxyProperty == null ? null : proxyProperty.generateAnyType();

                TypeMirror type = fieldElement.asType();
                boolean isGeneric = type.getKind() == TypeKind.TYPEVAR;
                boolean isDeclared = type.getKind() == TypeKind.DECLARED;
                String fieldGenericType = getGenericTypeString(isGeneric, isDeclared, includeProperty, type);
                String docComment = elementUtils.getDocComment(fieldElement);
                ValueObject valueObject = fieldElement.getAnnotation(ValueObject.class);
                boolean isValueObject = valueObject != null;
                String fieldName = isValueObject ? fieldGenericType.substring(fieldGenericType.lastIndexOf(".") + 1) : entityName;
                FieldComment fieldComment = getFiledComment(docComment, fieldName, propertyName);
                PropertyColumn propertyColumn = getPropertyColumn(fieldGenericType, anyType);
                aptFileCompiler.addImports(propertyColumn.getImport());
                if (includeProperty) {
                    aptFileCompiler.addImports("com.easy.query.core.proxy.columns.SQLNavigateColumn");
                    String navigatePropertyProxyFullName = getNavigatePropertyProxyFullName(propertyColumn.getPropertyType(), navigate.propIsProxy());
                    if (navigatePropertyProxyFullName != null) {
                        propertyColumn.setNavigateProxyName(navigatePropertyProxyFullName);
                    } else {
                        fieldComment.proxyComment += "\n//apt提示无法获取导航属性代理:" + propertyColumn.getPropertyType();
                    }
                    if (navigate.value() == RelationTypeEnum.OneToMany || navigate.value() == RelationTypeEnum.ManyToMany) {
                        includeManyProperty = true;
//                        aptFileCompiler.addImports("com.easy.query.core.proxy.columns.SQLQueryable");
                        aptFileCompiler.addImports("com.easy.query.core.proxy.columns.SQLManyQueryable");
                    }
                }

                aptValueObjectInfo.addProperties(new AptPropertyInfo(propertyName, propertyColumn, fieldComment, fieldName, isValueObject, includeProperty, includeManyProperty, proxyPropertyName));

                if (valueObject != null) {
                    aptFileCompiler.addImports(fieldGenericType);
                    String valueObjectClassName = fieldGenericType.substring(fieldGenericType.lastIndexOf(".") + 1);
                    Element fieldClass = ((DeclaredType) type).asElement();
                    AptValueObjectInfo fieldAptValueObjectInfo = new AptValueObjectInfo(valueObjectClassName);
                    aptValueObjectInfo.getChildren().add(fieldAptValueObjectInfo);
                    fillValueObject(parentProperty + "." + propertyName, fieldAptValueObjectInfo, fieldClass, aptFileCompiler, ignoreProperties);
                }
            }
        }
    }

    private void fillPropertyAndColumns(AptFileCompiler aptFileCompiler, AptValueObjectInfo aptValueObjectInfo, TypeElement classElement, Set<String> ignoreProperties) {

        List<? extends Element> enclosedElements = classElement.getEnclosedElements();
        for (Element fieldElement : enclosedElements) {

            //all fields
            if (ElementKind.FIELD == fieldElement.getKind()) {


                Set<Modifier> modifiers = fieldElement.getModifiers();
                if (modifiers.contains(Modifier.STATIC)) {
                    //ignore static fields
                    continue;
                }

                String propertyName = fieldElement.toString();
                if (!ignoreProperties.isEmpty() && ignoreProperties.contains(propertyName)) {
                    continue;
                }
                ColumnIgnore column = fieldElement.getAnnotation(ColumnIgnore.class);
                if (column != null) {
                    continue;
                }
                Navigate navigate = fieldElement.getAnnotation(Navigate.class);
                boolean includeProperty = navigate != null;
                boolean includeManyProperty = false;
                ProxyProperty proxyProperty = fieldElement.getAnnotation(ProxyProperty.class);
                String proxyPropertyName = proxyProperty != null ? proxyProperty.value() : propertyName;
                Boolean anyType = proxyProperty == null ? null : proxyProperty.generateAnyType();
//                if(Objects.equals("roles",propertyName)){
//                    System.out.println("111");
//                }
                TypeMirror type = fieldElement.asType();
                boolean isGeneric = type.getKind() == TypeKind.TYPEVAR;
                boolean isDeclared = type.getKind() == TypeKind.DECLARED;
                String fieldGenericType = getGenericTypeString(isGeneric, isDeclared, includeProperty, type);
                String docComment = elementUtils.getDocComment(fieldElement);
                ValueObject valueObject = fieldElement.getAnnotation(ValueObject.class);
                boolean isValueObject = valueObject != null;
                String fieldName = isValueObject ? fieldGenericType.substring(fieldGenericType.lastIndexOf(".") + 1) : aptFileCompiler.getEntityClassName();
                FieldComment fieldComment = getFiledComment(docComment, fieldName, propertyName);
                PropertyColumn propertyColumn = getPropertyColumn(fieldGenericType, anyType);
                aptFileCompiler.addImports(propertyColumn.getImport());

                if (!includeProperty) {
                    aptFileCompiler.getSelectorInfo().addProperties(new AptSelectPropertyInfo(propertyName, fieldComment, proxyPropertyName));
                } else {
                    aptFileCompiler.addImports("com.easy.query.core.proxy.columns.SQLNavigateColumn");
                    String navigatePropertyProxyFullName = getNavigatePropertyProxyFullName(propertyColumn.getPropertyType(), navigate.propIsProxy());
                    if (navigatePropertyProxyFullName != null) {
                        propertyColumn.setNavigateProxyName(navigatePropertyProxyFullName);
                    } else {
                        fieldComment.proxyComment += "\n//apt提示无法获取导航属性代理:" + propertyColumn.getPropertyType();
                    }
                    if (navigate.value() == RelationTypeEnum.OneToMany || navigate.value() == RelationTypeEnum.ManyToMany) {
                        includeManyProperty = true;
//                        aptFileCompiler.addImports("com.easy.query.core.proxy.columns.SQLQueryable");
                        aptFileCompiler.addImports("com.easy.query.core.proxy.columns.SQLManyQueryable");
                    }
                }
                //todo
                aptValueObjectInfo.addProperties(new AptPropertyInfo(propertyName, propertyColumn, fieldComment, fieldName, isValueObject, includeProperty, includeManyProperty, proxyPropertyName));


                if (valueObject != null) {
                    aptFileCompiler.addImports("com.easy.query.core.proxy.AbstractValueObjectProxyEntity");
                    aptFileCompiler.addImports(fieldGenericType);
                    String valueObjectClassName = fieldGenericType.substring(fieldGenericType.lastIndexOf(".") + 1);
                    Element fieldClass = ((DeclaredType) type).asElement();
                    AptValueObjectInfo fieldAptValueObjectInfo = new AptValueObjectInfo(valueObjectClassName);
                    aptValueObjectInfo.getChildren().add(fieldAptValueObjectInfo);
                    fillValueObject(propertyName, fieldAptValueObjectInfo, fieldClass, aptFileCompiler, ignoreProperties);
                }
            }
        }
    }

    private String getNavigatePropertyProxyFullName(String fullClassName, boolean propIsProxy) {
//        if(propertyColumn.getPropertyType().equals("com.easy.query.test.entity.school.MySchoolClass1")){
        TypeElement typeElement = elementUtils.getTypeElement(fullClassName);
        if (typeElement != null) {
            EntityProxy annotation = typeElement.getAnnotation(EntityProxy.class);
            if (annotation != null) {
                if (EasyStringUtil.isBlank(annotation.value())) {
                    return getDefaultClassProxyName(fullClassName);
                }
                return fullClassName.substring(0, fullClassName.lastIndexOf(".")) + ".proxy." + annotation.value();
            }
            EntityFileProxy annotationFile = typeElement.getAnnotation(EntityFileProxy.class);
            if (annotationFile != null) {
                if (EasyStringUtil.isBlank(annotationFile.value())) {
                    return getDefaultClassProxyName(fullClassName);
                }
                return fullClassName.substring(0, fullClassName.lastIndexOf(".")) + ".proxy." + annotationFile.value();
            }
        }
        if (propIsProxy) {
            return getDefaultClassProxyName(fullClassName);
        }
//        }
        return null;
    }

    private String getDefaultClassProxyName(String fullClassName) {
        return fullClassName.substring(0, fullClassName.lastIndexOf(".")) + ".proxy." + fullClassName.substring(fullClassName.lastIndexOf(".") + 1) + "Proxy";
    }

    private FieldComment getFiledComment(String docComment, String className, String propertyName) {
        if (docComment == null) {
            String proxyComment = FIELD_EMPTY_DOC_COMMENT_TEMPLATE
                    .replace("@{entityClass}", className)
                    .replace("@{property}", EasyStringUtil.toUpperCaseFirstOne(propertyName));
            return new FieldComment(proxyComment, "");
        }
        String[] commentLines = docComment.trim().split("\n");
        StringBuilder fieldComment = new StringBuilder();
        fieldComment.append("* ").append(commentLines[0]);
        for (int i = 1; i < commentLines.length; i++) {
            fieldComment.append("\n     *").append(commentLines[i]);
        }
        String fieldCommentString = fieldComment.toString();
        String proxyComment = FIELD_DOC_COMMENT_TEMPLATE
                .replace("@{comment}", fieldCommentString)
                .replace("@{entityClass}", className)
                .replace("@{property}", EasyStringUtil.toUpperCaseFirstOne(propertyName));
        return new FieldComment(proxyComment, fieldCommentString);
    }

    private String getGenericTypeString(boolean isGeneric, boolean isDeclared, boolean includeProperty, TypeMirror type) {
        if (isGeneric) {
            return "java.lang.Object";
        }

        if (type.getKind() == TypeKind.ARRAY) {
            // 处理数组类型，递归获取组件类型
            ArrayType arrayType = (ArrayType) type;
            String componentTypeName = getGenericTypeString(isGeneric, isDeclared, includeProperty, arrayType.getComponentType());
            return componentTypeName + "[]";
        } else {

            String typeString = defTypeString(isDeclared, includeProperty, type);
            if (typeString.contains("<") && typeString.contains(">")) {
                String trim = type.toString().trim();
                if (type.getAnnotationMirrors().size() > 0) {
                    if (trim.lastIndexOf(") ::") > -1) {
                        return type.toString().trim().substring(type.toString().trim().lastIndexOf(") ::") + 4).replaceAll(">\\)", ">");
                    } else {
                        for (AnnotationMirror annotationMirror : type.getAnnotationMirrors()) {
                            trim = trim.replace(annotationMirror.toString(), "").trim();
                        }
                    }
                }
                return trim;
            }

            return TYPE_MAPPING.getOrDefault(typeString, typeString);
        }
    }

    private String defTypeString(boolean isDeclared, boolean includeProperty, TypeMirror type) {

        if (includeProperty) {
            if (type instanceof DeclaredType) {
                DeclaredType declaredType = (DeclaredType) type;
                List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
                if (typeArguments != null && typeArguments.size() == 1) {
                    return typeArguments.get(0).toString().trim();
                }

                Element element = declaredType.asElement();
                if (element != null) {
                    TypeMirror elementType = element.asType();
                    if (elementType != null) {
                        return elementType.toString();
                    }
                }
            }
            String trim = type.toString().trim();
            return parseGenericType(trim);
        }
        Element element = typeUtils.asElement(type);
        if (element != null) {
            return element.asType().toString().trim();
        }
        return type.toString().trim();
    }

    public static String parseGenericType(String genericTypeString) {
        if (genericTypeString.contains(",")) {
            return genericTypeString;
        }
        // 正则表达式用于匹配泛型类型字符串
        String regex = "<(.+?)>$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(genericTypeString);

        // 如果匹配成功，返回内部类型字符串；否则返回空字符串
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return genericTypeString;
        }
    }

    public static PropertyColumn getPropertyColumn(String fieldGenericType, Boolean anyType) {
        return TYPE_COLUMN_MAPPING.getOrDefault(fieldGenericType, new PropertyColumn("SQLAnyTypeColumn", fieldGenericType, anyType));
    }

}
