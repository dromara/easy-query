package com.easy.query.core.sharding.merge.result.impl.memory.row;

import com.easy.query.core.sharding.merge.result.StreamResultSet;
import com.easy.query.core.util.EasyCheck;

import java.sql.SQLException;

/**
 * create time 2023/5/3 09:55
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MemoryResultSetRow {

     Object getValue(final int columnIndex);

     void setValue(final int columnIndex, final Object value);
}
