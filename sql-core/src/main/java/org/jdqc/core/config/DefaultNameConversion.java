package org.jdqc.core.config;

import org.jdqc.core.annotation.Column;
import org.jdqc.core.annotation.Table;
import org.jdqc.core.util.ClassUtil;
import org.jdqc.core.util.StringUtil;

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
            return clazz.getSimpleName();
        }
        String tableName = table.value();
        return StringUtil.isBlank(tableName)?clazz.getSimpleName():tableName;
    }

    @Override
    public String getColName(String attrName) {
        return attrName;
    }
}
