package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;

/**
 * @FileName: SelectCountSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 12:52
 * @author xuejiaming
 */
public class SelectConstSegment implements SqlSegment{
    private final String projects;

    public SelectConstSegment(String projects){

        this.projects = projects;
    }
    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        return projects;
    }
}
