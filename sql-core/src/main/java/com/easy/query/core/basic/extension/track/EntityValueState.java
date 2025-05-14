package com.easy.query.core.basic.extension.track;

import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Objects;

/**
 * @author xuejiaming
 * @FileName: EntryState.java
 * @Description: 文件说明
 * create time 2023/3/18 21:22
 */
public class EntityValueState {
    private final ColumnMetadata columnMetadata;
    private final Object original;
    private final Object current;
    private Boolean isChange;

    public EntityValueState(ColumnMetadata columnMetadata, Object original, Object current) {
        this.columnMetadata = columnMetadata;
        this.original = original;
        this.current = current;
    }

    public String getProperty() {
        return columnMetadata.getPropertyName();
    }

    public ColumnMetadata getColumnMetadata() {
        return columnMetadata;
    }

    public Object getOriginal() {
        return original;
    }

    public Object getCurrent() {
        return current;
    }

    public boolean isChanged() {
        return !notChanged();
    }

    public boolean notChanged() {
        if (isChange != null) {
            return isChange;
        }
        boolean nc = notChanged0();
        this.isChange = !nc;
        return nc;
    }

    private boolean notChanged0() {
        if (original == null && current == null) {
            return true;
        }
        if (original == null) {
            return false;
        }
        if (current == null) {
            return false;
        }
        if (Comparable.class.isAssignableFrom(columnMetadata.getPropertyType())) {
            return ((Comparable<?>) original).compareTo(EasyObjectUtil.typeCastNullable(current)) == 0;
        }
        return Objects.equals(original, current);
    }
}
