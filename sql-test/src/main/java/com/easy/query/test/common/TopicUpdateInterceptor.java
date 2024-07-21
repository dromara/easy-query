package com.easy.query.test.common;

import com.easy.query.core.basic.extension.interceptor.EntityInterceptor;
import com.easy.query.core.basic.extension.interceptor.UpdateEntityColumnInterceptor;
import com.easy.query.core.basic.extension.interceptor.UpdateSetInterceptor;
import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.segment.index.EntitySegmentComparer;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.test.entity.TopicUpdate;

import java.time.LocalDateTime;

/**
 * create time 2024/7/21 16:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class TopicUpdateInterceptor implements UpdateSetInterceptor, EntityInterceptor, UpdateEntityColumnInterceptor {
    @Override
    public void configure(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder, ColumnSetter<Object> columnSetter) {

        String createTime = "createTime";
        //是否已经set了
        if (!entityUpdateExpressionBuilder.getSetColumns().containsOnce(entityClass, createTime)) {
            columnSetter.set(createTime, LocalDateTime.now());
        }
    }

    @Override
    public String name() {
        return "123123";
    }

    @Override
    public boolean apply(Class<?> entityClass) {
        return TopicUpdate.class.equals(entityClass);
    }

    @Override
    public void configureInsert(Class<?> entityClass, EntityInsertExpressionBuilder entityInsertExpressionBuilder, Object entity) {
    }

    @Override
    public void configureUpdate(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder, Object entity) {
        TopicUpdate entity1 = (TopicUpdate) entity;
        entity1.setCreateTime(LocalDateTime.of(2024,1,1,1,1));
    }

    @Override
    public void configure(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder, ColumnOnlySelector<Object> columnSelector, Object entity) {
        EntitySegmentComparer createTime = new EntitySegmentComparer(entityClass, "createTime");
        columnSelector.getSQLSegmentBuilder().forEach(k->{
             createTime.visit(k);
             return createTime.isInSegment();
        });
        if(!createTime.isInSegment()){
            columnSelector.column("createTime");
        }
    }
}
