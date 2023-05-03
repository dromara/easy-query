package com.easy.query.core.expression.segment;

/**
 * create time 2023/4/29 22:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnSegment extends SqlEntityAliasSegment{
    @Override
    ColumnSegment cloneSqlEntitySegment();
}
