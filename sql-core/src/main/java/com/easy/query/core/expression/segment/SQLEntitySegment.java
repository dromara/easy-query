package com.easy.query.core.expression.segment;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/3/4 23:48
 */
public interface SQLEntitySegment extends SQLColumnSegment {
    TableAvailable getTable();

    String getPropertyName();

}
