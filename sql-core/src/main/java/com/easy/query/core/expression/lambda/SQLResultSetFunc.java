package com.easy.query.core.expression.lambda;

import java.sql.SQLException;

/**
 * create time 2023/6/23 21:22
 * 文件说明
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface SQLResultSetFunc<T1,TR> {

    TR apply(T1 p1) throws SQLException;
}