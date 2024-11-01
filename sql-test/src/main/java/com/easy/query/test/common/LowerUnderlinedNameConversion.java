package com.easy.query.test.common;

import com.easy.query.core.configuration.nameconversion.impl.UnderlinedNameConversion;
import com.easy.query.core.util.EasyStringUtil;

/**
  * create time 2024/11/1 17:13
  * 文件说明
  * @author xuejiaming
  */
public class LowerUnderlinedNameConversion extends UnderlinedNameConversion {
    @Override
    public String convert(String name) {
        String convert = super.convert(name);
        return EasyStringUtil.toLowerCaseFirstOne(convert);
    }
}
