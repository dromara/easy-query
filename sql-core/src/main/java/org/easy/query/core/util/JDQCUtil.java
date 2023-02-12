package org.easy.query.core.util;

import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.config.EasyQueryConfiguration;
import org.easy.query.core.config.NameConversion;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @FileName: JDQCUtil.java
 * @Description: 文件说明
 * @Date: 2023/2/11 13:38
 * @Created by xuejiaming
 */
public class JDQCUtil {
    public static List<EntityMetadata> loadPackage(String packageName, EasyQueryConfiguration configuration){
        LinkedList<EntityMetadata> entityMetadataList = new LinkedList<>();
        NameConversion nameConversion = configuration.getNameConversion();
        try {
            Class[] classes = ClassUtil.getClasses(packageName);
            for (Class clazz : classes) {
                String tableName = nameConversion.getTableName(clazz);
                if(tableName==null){
                    continue;
                }
                EntityMetadata entityMetadata = new EntityMetadata(clazz, tableName);
                entityMetadataList.add(entityMetadata);

            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return entityMetadataList;
    }
}
