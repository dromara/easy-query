package com.easy.query.core.configuration.column2mapkey;

import com.easy.query.core.util.EasyStringUtil;

/**
 * create time 2024/8/5 15:36
 * 文件说明
 *
 * @author xuejiaming
 */
public class UpperColumn2MapKeyConversion implements Column2MapKeyConversion{
    @Override
    public String convertToMapKey(String columnName) {
        return EasyStringUtil.fromAllUpperNoUnderlined(columnName);
    }
}
