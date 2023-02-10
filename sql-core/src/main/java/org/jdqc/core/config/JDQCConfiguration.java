package org.jdqc.core.config;

import org.jdqc.core.exception.JDQCException;
import org.jdqc.core.metadata.TableInfo;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName: JDQCConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/2/7 09:06
 * @Created by xuejiaming
 */
public class JDQCConfiguration {
    private  final ConcurrentHashMap<Class<?>, TableInfo> cacheTableInfos;
    private final String driver;

    public JDQCConfiguration(String driver){
        this.driver = driver;
        cacheTableInfos= new ConcurrentHashMap<>();
    }

    /**
     * 根据字节返回对应的表信息
     * @param clazz
     * @return
     */
    public TableInfo getTableByEntity(Class<?> clazz) {
        TableInfo tableInfo = cacheTableInfos.get(clazz);
        if (tableInfo == null) {
            throw new JDQCException(JDQCException.CLASS_TABLE_MISS, "not found table " + clazz.getSimpleName());
        }
        return tableInfo;
    }

    /**
     * 添加表信息
     * @param tableInfo
     */
    public void addTableInfo(TableInfo tableInfo){
        cacheTableInfos.putIfAbsent(tableInfo.getTableType(),tableInfo);
    }

    public String getDriver(){
        return driver;
    }
}
