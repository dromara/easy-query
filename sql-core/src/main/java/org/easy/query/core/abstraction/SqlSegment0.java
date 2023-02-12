package org.easy.query.core.abstraction;

import org.easy.query.core.abstraction.sql.base.IndexAware;
import org.easy.query.core.enums.SqlSegmentCompareEnum;
import org.easy.query.core.enums.SqlSegmentLinkEnum;
import org.easy.query.core.enums.SqlSegmentTypeEnum;

/**
 * @FileName: SqlSegment0.java
 * @Description: 文件说明
 * @Date: 2023/2/12 22:39
 * @Created by xuejiaming
 */
public interface SqlSegment0 extends IndexAware {
    String column();
    int targetIndex();
    String targetColumn();
    SqlSegmentLinkEnum link();
    SqlSegmentCompareEnum compare();
    SqlSegmentTypeEnum type();
}
