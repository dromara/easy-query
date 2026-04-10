package com.easy.query.test.common;

import com.easy.query.core.common.DefaultMapColumnNameChecker;

/**
 * create time 2024/10/24 17:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class NoChecker extends DefaultMapColumnNameChecker {
    @Override
    public String checkColumnName(String columnName) {
        return columnName;
    }
}
