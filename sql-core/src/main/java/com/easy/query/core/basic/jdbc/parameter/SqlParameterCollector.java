package com.easy.query.core.basic.jdbc.parameter;

import java.util.List;

/**
 * create time 2023/4/23 08:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SqlParameterCollector {
    void addParameter(SQLParameter sqlParameter);
    List<SQLParameter> getParameters();
}
