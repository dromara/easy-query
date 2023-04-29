package com.easy.query.core.util;

import com.easy.query.core.exception.EasyQueryUnexpectedException;

/**
 * create time 2023/4/29 21:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyCheck {
    public static void assertElse(boolean expected,String unexpectedMsg){
        if(!expected){
            throw new EasyQueryUnexpectedException(unexpectedMsg);
        }
    }
}
