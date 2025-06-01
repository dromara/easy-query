package com.easy.query.ksp;

import com.easy.query.processor.FieldRenderVal;
import com.easy.query.processor.helper.*;

import java.util.HashMap;
import java.util.Map;

/**
 * create time 2023/11/8 16:46
 * 文件说明
 * {@link AptCreatorHelper}
 *
 * @author xuejiaming
 */
public class KspCreatorHelper {
    public static String createProxy(AptFileCompiler aptFileCompiler, AptValueObjectInfo aptValueObjectInfo) {

        String selectorContent = renderSelectorUI(aptFileCompiler);
        FieldRenderVal fieldRenderVal = renderStaticFieldCommentUI(aptFileCompiler);
        String propertyContent = renderPropertyUI(aptFileCompiler, aptValueObjectInfo);
        String valueObjectContent = renderValueObjectUI(aptFileCompiler, aptValueObjectInfo);
        // 准备替换参数
        Map<String, String> replacements = new HashMap<>(8);
        replacements.put("package", aptFileCompiler.getPackageName());
        replacements.put("imports", String.join("\n", aptFileCompiler.getImports()));
        replacements.put("entityClass", aptFileCompiler.getEntityClassName());
        replacements.put("entityClassProxy", aptFileCompiler.getEntityClassProxyName());
        replacements.put("fieldContent", propertyContent);
        replacements.put("valueObjectContext", valueObjectContent);
        replacements.put("selectorContext", selectorContent);
        replacements.put("fieldStaticContext", fieldRenderVal.staticField.toString());
        return KspConstant.PROXY_TEMPLATE_GENERATOR.generate(replacements);
    }

    private static String renderPropertyUI(AptFileCompiler aptFileCompiler, AptValueObjectInfo aptValueObjectInfo) {
        StringBuilder filedContent = new StringBuilder();
        Map<String, String> replacements = new HashMap<>();
        for (AptPropertyInfo property : aptValueObjectInfo.getProperties()) {
            if (property.isValueObject()) {

                replacements.put("entityClass", property.getEntityName());
                replacements.put("comment", property.getComment());
                replacements.put("propertyType", property.getPropertyType());
                replacements.put("property", property.getPropertyName());
                replacements.put("proxyProperty", property.getProxyPropertyName());

                String fieldString = KspConstant.FIELD_VALUE_OBJECT_TEMPLATE_GENERATOR.generate(replacements);
                filedContent.append(fieldString);
            } else {
                if (property.isIncludeProperty() && property.getNavigateProxyName() != null) {
                    if (property.isIncludeManyProperty()) {

                        replacements.put("entityClassProxy", aptFileCompiler.getEntityClassProxyName());
                        replacements.put("propertyProxy", property.getNavigateProxyName());
                        replacements.put("comment", property.getComment());
                        replacements.put("propertyType", property.getPropertyType());
                        replacements.put("property", property.getPropertyName());
                        replacements.put("proxyProperty", property.getProxyPropertyName());


                        String fieldString = KspConstant.FIELD_NAVIGATES_TEMPLATE_GENERATOR.generate(replacements);
                        filedContent.append(fieldString);
                    } else {
                        replacements.put("propertyProxy", property.getNavigateProxyName());
                        replacements.put("comment", property.getComment());
                        replacements.put("property", property.getPropertyName());
                        replacements.put("proxyProperty", property.getProxyPropertyName());

                        String fieldString = KspConstant.FIELD_NAVIGATE_TEMPLATE_GENERATOR.generate(replacements);
                        filedContent.append(fieldString);
                    }
                } else {
                    if (property.isAnyType()) {

                        replacements.put("entityClassProxy", aptFileCompiler.getEntityClassProxyName());
                        replacements.put("comment", property.getComment());
                        replacements.put("propertyType", property.getPropertyType());
                        replacements.put("propertyTypeClass", property.getPropertyTypeClass());
                        replacements.put("property", property.getPropertyName());
                        replacements.put("proxyProperty", property.getProxyPropertyName());
                        replacements.put("SQLColumn", property.getSqlColumn());
                        replacements.put("sqlColumnMethod", property.getSqlColumnMethod());;


                        String fieldString = KspConstant.ANY_FIELD_TEMPLATE_GENERATOR.generate(replacements);
                        filedContent.append(fieldString);
                    } else {


                        replacements.put("entityClassProxy", aptFileCompiler.getEntityClassProxyName());
                        replacements.put("comment", property.getComment());
                        replacements.put("property", property.getPropertyName());
                        replacements.put("proxyProperty", property.getProxyPropertyName());
                        replacements.put("SQLColumn", property.getSqlColumn());
                        replacements.put("sqlColumnMethod", property.getSqlColumnMethod());

                        String fieldString = KspConstant.FIELD_TEMPLATE_GENERATOR.generate(replacements);
                        filedContent.append(fieldString);
                    }
                }
            }
            replacements.clear();
        }
        return filedContent.toString();
    }

    private static String renderSelectorUI(AptFileCompiler aptFileCompiler) {
        String fieldSelectorContent = renderSelectorPropertyUI(aptFileCompiler);
        AptSelectorInfo selectorInfo = aptFileCompiler.getSelectorInfo();
        Map<String, String> replacements = new HashMap<>(4);
        replacements.put("entityClass", aptFileCompiler.getEntityClassName());
        replacements.put("selectorName", selectorInfo.getName());
        replacements.put("entityClassProxy", aptFileCompiler.getEntityClassProxyName());
        replacements.put("fieldSelectorContent", fieldSelectorContent);


        return KspConstant.PROXY_SELECTOR_TEMPLATE_GENERATOR.generate(replacements);
    }


    private static FieldRenderVal renderStaticFieldCommentUI(AptFileCompiler aptFileCompiler) {
//        boolean ignoreComment = !aptFileCompiler.isTableEntity();
        FieldRenderVal fieldRenderVal = new FieldRenderVal();
        AptSelectorInfo selectorInfo = aptFileCompiler.getSelectorInfo();
//        StringBuilder fieldCase = new StringBuilder();
        Map<String, String> replacements = new HashMap<>(1);
        for (AptSelectPropertyInfo property : selectorInfo.getProperties()) {


            replacements.put("property", property.getPropertyName());
            String staticFiled = KspConstant.FIELD_STATIC_TEMPLATE_GENERATOR.generate(replacements);
            fieldRenderVal.staticField.append(staticFiled);
            replacements.clear();

        }
        return fieldRenderVal;
    }

    private static String renderSelectorPropertyUI(AptFileCompiler aptFileCompiler) {
        AptSelectorInfo selectorInfo = aptFileCompiler.getSelectorInfo();
        StringBuilder filedContent = new StringBuilder();
        Map<String, String> replacements = new HashMap<>(3);
        for (AptSelectPropertyInfo property : selectorInfo.getProperties()) {
            replacements.put("selectorName", selectorInfo.getName());
            replacements.put("comment", property.getComment());
            replacements.put("proxyProperty", property.getProxyPropertyName());

            String fieldString = KspConstant.FIELD_SELECTOR_PROPERTY_TEMPLATE_GENERATOR.generate(replacements);
            filedContent.append(fieldString);
            replacements.clear();
        }
        return filedContent.toString();
    }

    private static String renderValueObjectUI(AptFileCompiler aptFileCompiler, AptValueObjectInfo aptValueObjectInfo) {
        StringBuilder valueObjectContentBuilder = new StringBuilder();
        Map<String, String> replacements = new HashMap<>(4);
        for (AptValueObjectInfo valueObject : aptValueObjectInfo.getChildren()) {
            String propertyContent = renderPropertyUI(aptFileCompiler, valueObject);
            String vc = renderValueObjectUI(aptFileCompiler, valueObject);
            replacements.put("entityClass", valueObject.getEntityName());
            replacements.put("mainEntityClassProxy", aptFileCompiler.getEntityClassProxyName());
            replacements.put("fieldContent", propertyContent);
            replacements.put("valueObjectContext", vc);



            String valueObjectContent = KspConstant.FIELD_VALUE_OBJECT_CLASS_TEMPLATE_GENERATOR.generate(replacements);
            valueObjectContentBuilder.append(valueObjectContent);
            valueObjectContentBuilder.append("\n");
            replacements.clear();
        }
        return valueObjectContentBuilder.toString();
    }
}
