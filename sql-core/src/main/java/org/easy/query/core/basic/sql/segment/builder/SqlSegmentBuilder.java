package org.easy.query.core.basic.sql.segment.builder;

import org.easy.query.core.basic.sql.segment.segment.SqlSegment;

import java.util.List;

/**
 * @FileName: SqlSegment0Builder.java
 * @Description: 文件说明
 * @Date: 2023/2/13 12:49
 * @Created by xuejiaming
 */
public interface SqlSegmentBuilder {
    List<SqlSegment> getSqlSegments();
    void append(SqlSegment sqlSegment);
    boolean isEmpty();
   default boolean isNotEmpty(){
       return !isEmpty();
   }
    String toSql();
}
