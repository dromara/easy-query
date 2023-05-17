package com.easy.query.core.expression.segment.builder;

import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLSegment;

import java.util.*;

/**
 * @FileName: DefaultSqlSegmentBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/13 21:30
 * @author xuejiaming
 */
public abstract class AbstractSqlBuilderSegment implements SQLBuilderSegment {
    private final List<SQLSegment> sqlSegments=new ArrayList<>();
    @Override
    public void append(SQLSegment sqlSegment) {
        sqlSegments.add(sqlSegment);
    }
    @Override
    public List<SQLSegment> getSQLSegments() {
        return sqlSegments;
    }

    @Override
    public boolean isEmpty() {
        return sqlSegments.isEmpty();
    }

    @Override
    public void copyTo(SQLBuilderSegment predicateSegment) {
        predicateSegment.getSQLSegments().addAll(sqlSegments);
    }

    @Override
    public boolean containsOnce(Class<?> entityClass, String propertyName) {
        for (SQLSegment sqlSegment : sqlSegments) {
            if(sqlSegment instanceof SQLEntitySegment){
                SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                if(Objects.equals(sqlEntitySegment.getTable().getEntityClass(),entityClass)&&Objects.equals(sqlEntitySegment.getPropertyName(), propertyName)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void clear() {
        sqlSegments.clear();
    }
}
