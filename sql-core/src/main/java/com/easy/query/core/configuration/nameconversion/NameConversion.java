package com.easy.query.core.configuration.nameconversion;

/**
 * create time 2023/2/12 08:57
 * 属性名表名转换成列名统一转换
 *
 * @author xuejiaming
 */
public interface NameConversion {

   /**
    * 注解的后置处理包括@Table和@Column
    * @param name
    * @return
    */
   default String annotationCovert(Class<?> entityClass,String name){
      return name;
   }
   /**
    * 属性名或者表名转成列名
    * @param name 属性名或者表名
    * @return 转换后的列名
    */
   String convert(String name);
}
