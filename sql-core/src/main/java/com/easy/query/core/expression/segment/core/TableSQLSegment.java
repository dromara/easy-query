package com.easy.query.core.expression.segment.core;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.CloneableSQLSegment;

/**
 * create time 2023/8/6 14:22
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableSQLSegment extends CloneableSQLSegment {
    TableAvailable getTable();
}
