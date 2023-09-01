package com.easy.query.core.expression.segment.builder;

import com.easy.query.core.expression.lambda.BreakConsumer;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.SubQueryColumnSegment;
import com.easy.query.core.expression.segment.index.SegmentIndex;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @Description: 文件说明
 * @Date: 2023/2/13 21:30
 * @author xuejiaming
 */
public abstract class AbstractSQLBuilderSegment implements SQLBuilderSegment {
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
        for (SQLSegment sqlSegment : sqlSegments) {
            if(sqlSegment instanceof SubQueryColumnSegment){
                SubQueryColumnSegment subQueryColumnSegment = (SubQueryColumnSegment) sqlSegment;
                predicateSegment.getSQLSegments().add(subQueryColumnSegment.cloneSQLColumnSegment());

            }else{
                predicateSegment.getSQLSegments().add(sqlSegment);
            }
        }
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
    public SegmentIndex buildSegmentIndex() {
        EasySegmentIndexContext easySegmentIndexContext = new EasySegmentIndexContext();
        for (SQLSegment sqlSegment : sqlSegments) {
            if(sqlSegment instanceof SQLEntitySegment){
                easySegmentIndexContext.add((SQLEntitySegment) sqlSegment);
            }
        }
        return easySegmentIndexContext;
    }

    @Override
    public boolean forEach(BreakConsumer<SQLSegment> consumer) {

        for (SQLSegment sqlSegment : sqlSegments) {
            if(consumer.accept(sqlSegment)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        sqlSegments.clear();
    }
}
