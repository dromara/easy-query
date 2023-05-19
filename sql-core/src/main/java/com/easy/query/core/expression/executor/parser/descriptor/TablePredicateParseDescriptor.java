package com.easy.query.core.expression.executor.parser.descriptor;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.util.EasyClassUtil;

import java.util.List;
import java.util.function.Supplier;

/**
 * create time 2023/5/18 13:30
 * 解析表片段描述内部可能存在多个条件或者单个对象
 *
 * @author xuejiaming
 */
public interface TablePredicateParseDescriptor extends TableParseDescriptor{
    void addTable(TableAvailable table);
    void addTablePredicate(TableAvailable table,PredicateSegment predicateSegment);
    List<PredicateSegment> getPredicatesOrNull(TableAvailable table);
    boolean isShardingTable(TableAvailable table);
}
