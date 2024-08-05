package com.easy.query.core.proxy.partition.proxy;

import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;

/**
 * create time 2024/1/26 22:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PartitionProxy extends SQLSelectAsExpression {
    <TProperty> void fetch(int index, PropTypeColumn<TProperty> column, TablePropColumn tablePropColumn);
    ResultColumnMetadata[] getPartitionByPropTypes();
}
