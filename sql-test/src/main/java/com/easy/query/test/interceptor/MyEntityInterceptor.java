package com.easy.query.test.interceptor;

import com.easy.query.core.basic.extension.interceptor.EntityInterceptor;
import com.easy.query.core.basic.extension.interceptor.UpdateSetInterceptor;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.segment.index.EntitySegmentComparer;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.test.entity.TopicInterceptor;
import com.easy.query.test.logicdel.CurrentUserHelper;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

/**
 * create time 2023/4/3 21:13
 * 如果是spring项目添加@Component，如果是非spring项目直接添加到EasyQueryConfiguration.applyEasyInterceptor
 *
 * @author xuejiaming
 */
public class MyEntityInterceptor implements EntityInterceptor, UpdateSetInterceptor {
    @Override
    public void configureInsert(Class<?> entityClass, EntityInsertExpressionBuilder entityInsertExpressionBuilder, Object entity) {
        TopicInterceptor topicInterceptor = (TopicInterceptor) entity;
        if (topicInterceptor.getCreateTime() == null) {
            topicInterceptor.setCreateTime(LocalDateTime.now());
        }
        if (topicInterceptor.getCreateBy() == null) {
            topicInterceptor.setCreateBy(CurrentUserHelper.getUserId());
        }
        if (topicInterceptor.getUpdateTime() == null) {
            topicInterceptor.setUpdateTime(LocalDateTime.now());
        }
        if (topicInterceptor.getUpdateBy() == null) {
            topicInterceptor.setUpdateBy(CurrentUserHelper.getUserId());
        }
    }

    @Override
    public void configureUpdate(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder, Object entity) {

        TopicInterceptor topicInterceptor = (TopicInterceptor) entity;
        topicInterceptor.setUpdateTime(LocalDateTime.now());
        topicInterceptor.setUpdateBy(CurrentUserHelper.getUserId());
    }

    @Override
    public String name() {
        return "MyEntityInterceptor";
    }

    @Override
    public boolean apply(@NotNull Class<?> entityClass) {
        return TopicInterceptor.class.isAssignableFrom(entityClass);
    }

    @Override
    public void configure(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder, ColumnSetter<Object> columnSetter) {

        String updateBy = "updateBy";//属性名用来动态创建lambda
        String updateTime = "updateTime";//属性名用来动态创建lambda
        EntitySegmentComparer updateByComparer = new EntitySegmentComparer(entityClass, updateBy);
        EntitySegmentComparer updateTimeComparer = new EntitySegmentComparer(entityClass, updateTime);
        columnSetter.getSQLBuilderSegment().forEach(sqlSegment->{
            updateByComparer.visit(sqlSegment);
            updateTimeComparer.visit(sqlSegment);
            return updateByComparer.isInSegment()&&updateTimeComparer.isInSegment();
        });
        //是否已经set了
        if (!updateByComparer.isInSegment()) {
            String userId = CurrentUserHelper.getUserId();
            columnSetter.set(updateBy, userId);
        }
        if (!updateTimeComparer.isInSegment()) {
            columnSetter.set(updateTime, LocalDateTime.now());
        }
    }
}
