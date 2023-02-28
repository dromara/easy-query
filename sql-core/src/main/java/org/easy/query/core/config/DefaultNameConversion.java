package org.easy.query.core.config;

import org.easy.query.core.annotation.Table;
import org.easy.query.core.util.ClassUtil;
import org.easy.query.core.util.StringUtil;

/**
 * @FileName: DeefaultNameConversion.java
 * @Description: 文件说明
 * @Date: 2023/2/11 13:22
 * @Created by xuejiaming
 */
public class DefaultNameConversion extends NameConversion {

    @Override
    public String getTableName(Class clazz) {

        Table table = ClassUtil.getAnnotation(clazz, Table.class);
        if(table==null){
            return ClassUtil.getSimpleName(clazz);
        }
        String tableName = table.value();
        return StringUtil.isBlank(tableName)?ClassUtil.getSimpleName(clazz):tableName;
    }

    @Override
    public String getColName(String attrName) {
        return attrName;
    }

    @Override
    public String getPropertyName(String colName) {
        return colName;
    }
}
