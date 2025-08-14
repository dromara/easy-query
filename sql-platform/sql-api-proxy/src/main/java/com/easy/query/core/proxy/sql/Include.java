package com.easy.query.core.proxy.sql;

import com.easy.query.core.expression.parser.core.available.Include2Available;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/6/26 20:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class Include {
    public static List<Include2Available> of(Include2Available ...include2s) {
        if(include2s==null){
            return null;
        }
        return Arrays.asList(include2s);
    }
}
