package com.easy.query.core.expression.segment.core;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.visitor.TableVisitor;

/**
 * create time 2023/8/6 14:22
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableSQLSegment extends CloneableSQLSegment {
   @Nullable
   TableAvailable getTable();
    default void accept(TableVisitor visitor){
        visitor.visit(getTable());
    }
}
