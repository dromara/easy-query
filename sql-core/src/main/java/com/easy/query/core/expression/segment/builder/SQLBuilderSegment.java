package com.easy.query.core.expression.segment.builder;

import com.easy.query.core.expression.lambda.BreakConsumer;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.index.SegmentIndex;
import com.easy.query.core.expression.segment.index.EntitySegmentComparer;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * create time 2023/2/13 12:49
 */
public interface SQLBuilderSegment extends SQLSegment {
    List<SQLSegment> getSQLSegments();

    void append(SQLSegment sqlSegment);

    boolean isEmpty();

    default boolean isNotEmpty() {
        return !isEmpty();
    }

    void copyTo(SQLBuilderSegment predicateSegment);

    SQLBuilderSegment cloneSQLBuilder();

//    /**
//     * <blockquote><pre>
//     *     {@code
//     *   EntitySegmentComparer updateByComparer = new EntitySegmentComparer(entityClass, updateBy);
//     *         EntitySegmentComparer updateTimeComparer = new EntitySegmentComparer(entityClass, updateTime);
//     *         columnSetter.getSQLBuilderSegment().forEach(sqlSegment->{
//     *             updateByComparer.sameWith(sqlSegment);
//     *             updateTimeComparer.sameWith(sqlSegment);
//     *             return updateByComparer.isInSegment()&&updateTimeComparer.isInSegment();
//     *         });
//     *
//     *         //是否已经set了
//     *         if (!updateByComparer.isInSegment()) {
//     *             String userId = CurrentUserHelper.getUserId();
//     *             columnSetter.set(updateBy, userId);
//     *         }
//     *         if (!updateTimeComparer.isInSegment()) {
//     *             columnSetter.set(updateTime, LocalDateTime.now());
//     *         }
//     *      }
//     * </pre></blockquote>
//     * @param entityClass
//     * @param propertyName
//     * @return
//     */
//    @Deprecated
//    boolean containsOnce(Class<?> entityClass, String propertyName);

    @Deprecated
    SegmentIndex buildSegmentIndex();
     void visit(Consumer<EntitySegmentComparer> visitorConsumer);

    /**
     * <blockquote><pre>
     *     {@code
     *   EntitySegmentComparer updateByComparer = new EntitySegmentComparer(entityClass, updateBy);
     *         EntitySegmentComparer updateTimeComparer = new EntitySegmentComparer(entityClass, updateTime);
     *         columnSetter.getSQLBuilderSegment().forEach(sqlSegment->{
     *             updateByComparer.visit(sqlSegment);
     *             updateTimeComparer.visit(sqlSegment);
     *             return updateByComparer.isInSegment()&&updateTimeComparer.isInSegment();
     *         });
     *
     *         //是否已经set了
     *         if (!updateByComparer.isInSegment()) {
     *             String userId = CurrentUserHelper.getUserId();
     *             columnSetter.set(updateBy, userId);
     *         }
     *         if (!updateTimeComparer.isInSegment()) {
     *             columnSetter.set(updateTime, LocalDateTime.now());
     *         }
     *      }
     * </pre></blockquote>
     * @param consumer
     * @return
     */
    boolean forEach(BreakConsumer<SQLSegment> consumer);
    default boolean contains(EntitySegmentComparer entitySegmentComparer){
        forEach(s->{
            entitySegmentComparer.visit(s);
            return entitySegmentComparer.isInSegment();
        });
        return entitySegmentComparer.isInSegment();
    }
    void clear();
}
