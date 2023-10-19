package com.easy.query.core.func.def;

import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/10/18 14:20
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DistinctDefaultSQLFunction extends SQLFunction {
    DistinctDefaultSQLFunction distinct(boolean dist);
    DistinctDefaultSQLFunction nullDefault(Object value);
}
