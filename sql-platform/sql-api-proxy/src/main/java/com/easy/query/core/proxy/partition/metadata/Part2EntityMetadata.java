package com.easy.query.core.proxy.partition.metadata;

import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.proxy.partition.Part2;

/**
 * create time 2024/8/6 11:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class Part2EntityMetadata extends Part1EntityMetadata {

    private final JdbcTypeHandler jdbcTypeHandler2;

    public Part2EntityMetadata(Class<?> entityClass, EntityMetadata entityMetadata, JdbcTypeHandler jdbcTypeHandler1, JdbcTypeHandler jdbcTypeHandler2) {
        super(entityClass, entityMetadata, jdbcTypeHandler1);
        this.jdbcTypeHandler2 = jdbcTypeHandler2;
    }

    @Override
    protected boolean isPartitionByColumn(String propertyName) {
        if(Part2.PART_COLUMN2.equals(propertyName)){
            return true;
        }
        return super.isPartitionByColumn(propertyName);
    }

    @Override
    protected JdbcTypeHandler getPartitionJdbcTypeHandler(String propertyName) {
        if(Part2.PART_COLUMN2.equals(propertyName)){
            return jdbcTypeHandler2;
        }
        return super.getPartitionJdbcTypeHandler(propertyName);
    }
}
