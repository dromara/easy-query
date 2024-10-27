package com.easy.query.test.common;

import com.easy.query.core.common.DefaultMapColumnNameChecker;
import com.easy.query.core.common.MapColumnNameChecker;

/**
 * create time 2024/10/24 17:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyChecker extends DefaultMapColumnNameChecker {
    @Override
    public String checkColumnName(String columnName) {
        if("下拉-2-下拉".equals(columnName)){
            return columnName;
        }
        return super.checkColumnName(columnName);
    }
}
