package org.easy.query.core.config;

/**
 *
 * @FileName: NameConversion.java
 * @Description: 文件说明
 * @Date: 2023/2/7 09:00
 * @Created by xuejiaming
 */
public abstract class NameConversion {
    /****
     * 根据实体class获取表名
     * @return String 对应表名
     */
    public abstract String getTableName(Class clazz);


    /**
     * Java实体类字段名称转数据表列名
     * @param attrName 属性名称
     * @return String 列名
     */
    public abstract String getColName(String attrName);
}
