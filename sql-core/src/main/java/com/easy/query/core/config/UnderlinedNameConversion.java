package com.easy.query.core.config;

import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.StringUtil;
import com.easy.query.core.annotation.Table;

/**
 * @FileName: UnderlinedNameConversion.java
 * @Description: 文件说明
 * @Date: 2023/2/11 13:29
 * @Created by xuejiaming
 */
public class UnderlinedNameConversion extends NameConversion {
    @Override
    public String getTableName(Class clazz) {
        Table table = ClassUtil.getAnnotation(clazz, Table.class);
        if(table==null){
            return null;
        }
        String tableName = table.value();
        if(StringUtil.isBlank(tableName)){
            return StringUtil.enCodeUnderlined(ClassUtil.getSimpleName(clazz));
        }
        return tableName;
    }

    @Override
    public String getColName(String attrName) {
        return StringUtil.enCodeUnderlined(attrName);
    }

    @Override
    public String getPropertyName(String colName) {
        return colName;
    }
}
