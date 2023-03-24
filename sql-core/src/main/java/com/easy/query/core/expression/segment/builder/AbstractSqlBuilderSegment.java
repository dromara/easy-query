package com.easy.query.core.expression.segment.builder;

import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.segment.SqlSegment;

import java.util.*;

/**
 * @FileName: DefaultSqlSegmentBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/13 21:30
 * @author xuejiaming
 */
public abstract class AbstractSqlBuilderSegment implements SqlBuilderSegment {
    private final List<SqlSegment> sqlSegments=new ArrayList<>();
    @Override
    public void append(SqlSegment sqlSegment) {
        sqlSegments.add(sqlSegment);
    }

    @Override
    public abstract String toSql();
    @Override
    public List<SqlSegment> getSqlSegments() {
        return sqlSegments;
    }

    @Override
    public boolean isEmpty() {
        return sqlSegments.isEmpty();
    }

    @Override
    public void copyTo(SqlBuilderSegment predicateSegment) {
        predicateSegment.getSqlSegments().addAll(sqlSegments);
    }

    @Override
    public boolean containsOnce(Class<?> entityClass, String propertyName) {
        for (SqlSegment sqlSegment : sqlSegments) {
            if(sqlSegment instanceof SqlEntitySegment){
                SqlEntitySegment sqlEntitySegment = (SqlEntitySegment) sqlSegment;
                if(Objects.equals(sqlEntitySegment.getTable().entityClass(),entityClass)&&Objects.equals(sqlEntitySegment.getPropertyName(), propertyName)){
                    return true;
                }
            }
        }
        return false;
    }
}
