package org.easy.query.core.config;

import org.easy.query.core.annotation.Table;
import org.easy.query.core.util.ClassUtil;
import org.easy.query.core.util.StringUtil;

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
            return StringUtil.enCodeUnderlined(clazz.getSimpleName());
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
