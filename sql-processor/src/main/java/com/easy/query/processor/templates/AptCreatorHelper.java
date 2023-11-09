package com.easy.query.processor.templates;

/**
 * create time 2023/11/8 16:46
 * 文件说明
 *
 * @author xuejiaming
 */
public class AptCreatorHelper {
     public static String createProxy(AptFileCompiler aptFileCompiler,AptValueObjectInfo aptValueObjectInfo){

         String propertyContent = renderPropertyUI(aptFileCompiler, aptValueObjectInfo);
         String valueObjectContent = renderValueObjectUI(aptFileCompiler, aptValueObjectInfo);
         String proxyTemplate = AptConstant.PROXY_TEMPLATE
                 .replace("@{package}", aptFileCompiler.getPackageName())
                 .replace("@{imports}", String.join("\n", aptFileCompiler.getImports()))
                 .replace("@{entityClass}", aptFileCompiler.getEntityClassName())
                 .replace("@{entityClassProxy}", aptFileCompiler.getEntityClassProxyName())
                 .replace("@{fieldContent}", propertyContent)
                 .replace("@{valueObjectContext}", valueObjectContent);
         return proxyTemplate;
     }
     private static String renderPropertyUI(AptFileCompiler aptFileCompiler,AptValueObjectInfo aptValueObjectInfo){
         StringBuilder filedContent = new StringBuilder();
         for (AptPropertyInfo property : aptValueObjectInfo.getProperties()) {
             if(property.isValueObject()){

                 String fieldString = AptConstant.FIELD_VALUE_OBJECT_TEMPLATE
                         .replace("@{entityClass}", property.getEntityName())
                         .replace("@{comment}", property.getComment())
                         .replace("@{propertyType}", property.getPropertyType())
                         .replace("@{property}", property.getPropertyName());
                 filedContent.append(fieldString);
             }else{
                 String fieldString = AptConstant.FIELD_TEMPLATE
                         .replace("@{entityClassProxy}", aptFileCompiler.getEntityClassProxyName())
                         .replace("@{comment}", property.getComment())
                         .replace("@{propertyType}", property.getPropertyType())
                         .replace("@{property}", property.getPropertyName());
                 filedContent.append(fieldString);
             }
         }
         return filedContent.toString();
     }

     private static String renderValueObjectUI(AptFileCompiler aptFileCompiler,AptValueObjectInfo aptValueObjectInfo){
         StringBuilder valueObjectContentBuilder = new StringBuilder();
         for (AptValueObjectInfo valueObject : aptValueObjectInfo.getChildren()) {
             String propertyContent = renderPropertyUI(aptFileCompiler, valueObject);
             String vc = renderValueObjectUI(aptFileCompiler,valueObject);
             String valueObjectContent = AptConstant.FIELD_VALUE_OBJECT_CLASS_TEMPLATE
                     .replace("@{entityClass}", valueObject.getEntityName())
                     .replace("@{mainEntityClassProxy}", aptFileCompiler.getEntityClassProxyName())
                     .replace("@{fieldContent}", propertyContent)
                     .replace("@{valueObjectContext}", vc);
             valueObjectContentBuilder.append(valueObjectContent);
             valueObjectContentBuilder.append("\n");
         }
         return valueObjectContentBuilder.toString();
     }
}
