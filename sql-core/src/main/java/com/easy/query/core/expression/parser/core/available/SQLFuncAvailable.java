package com.easy.query.core.expression.parser.core.available;

import com.easy.query.core.func.SQLFunc;

/**
 * create time 2023/10/11 16:22
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFuncAvailable extends RuntimeContextAvailable {
    default SQLFunc fx(){
        return getRuntimeContext().fx();
    }
}
