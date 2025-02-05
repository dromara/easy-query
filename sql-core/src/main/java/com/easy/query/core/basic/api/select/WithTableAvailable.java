package com.easy.query.core.basic.api.select;

import com.easy.query.core.util.EasyClassUtil;

/**
 * create time 2025/2/4 15:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface WithTableAvailable {
    String getWithTableName();

    static String getDefaultClassWithTableName(Class<?> clazz){
        return "with_"+EasyClassUtil.getSimpleName(clazz);
    }
}
