package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.SelectConstSegment;

/**
 * @FileName: SelectCountSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 12:52
 * @author xuejiaming
 */
public class SelectConstSegmentImpl implements SelectConstSegment {
    private final String projects;

    public SelectConstSegmentImpl(String projects){

        this.projects = projects;
    }
    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return projects;
    }
}
