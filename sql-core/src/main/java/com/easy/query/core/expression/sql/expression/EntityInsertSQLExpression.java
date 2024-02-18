package com.easy.query.core.expression.sql.expression;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;

import java.util.List;

/**
 * create time 2023/4/22 22:25
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityInsertSQLExpression extends EntitySQLExpression {
    SQLBuilderSegment getColumns();
    SQLBuilderSegment getDuplicateKeyUpdateColumns();
    @Nullable  List<String> getDuplicateKeys();
    void addDuplicateKey(String duplicateKey);

    @Override
    EntityInsertSQLExpression cloneSQLExpression();
}
