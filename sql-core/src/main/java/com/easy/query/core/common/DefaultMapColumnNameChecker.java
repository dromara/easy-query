package com.easy.query.core.common;

import com.easy.query.core.util.EasyFieldCheckUtil;

/**
 * create time 2024/10/24 15:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultMapColumnNameChecker implements MapColumnNameChecker{
    @Override
    public String checkColumnName(String columnName) {
        return EasyFieldCheckUtil.toCheckField(columnName);
    }
}
