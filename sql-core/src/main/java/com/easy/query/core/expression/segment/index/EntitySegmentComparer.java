package com.easy.query.core.expression.segment.index;

import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLSegment;

import java.util.Objects;

/**
 * create time 2024/7/21 20:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntitySegmentComparer {
    private final Class<?> entityClass;
    private final String propertyName;
    private SQLSegment segment;

    public EntitySegmentComparer(Class<?> entityClass, String propertyName) {
        this.entityClass = entityClass;
        this.propertyName = propertyName;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void visit(SQLSegment other) {
        if (segment == null) {
            if (other instanceof SQLEntitySegment) {
                SQLEntitySegment otherSegment = (SQLEntitySegment) other;
                EntitySegmentComparer otherSegmentComparer = new EntitySegmentComparer(otherSegment.getTable().getEntityClass(), otherSegment.getPropertyName());
                if (Objects.equals(this, otherSegmentComparer)) {
                    this.segment = otherSegment;
                }
            }
        }
    }

    public SQLSegment getSegment() {
        return segment;
    }

    public boolean isInSegment() {
        return this.segment != null;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        EntitySegmentComparer that = (EntitySegmentComparer) object;
        return Objects.equals(entityClass, that.entityClass) && Objects.equals(propertyName, that.propertyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityClass, propertyName);
    }
}
