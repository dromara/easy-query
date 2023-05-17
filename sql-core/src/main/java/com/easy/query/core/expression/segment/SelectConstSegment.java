package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.jdbc.parameter.SQLParameterCollector;

/**
 * @FileName: SelectCountSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 12:52
 * @author xuejiaming
 */
public class SelectConstSegment implements SQLSegment {
    private final String projects;

    public SelectConstSegment(String projects){

        this.projects = projects;
    }
    @Override
    public String toSQL(SQLParameterCollector sqlParameterCollector) {
        return projects;
    }
}
