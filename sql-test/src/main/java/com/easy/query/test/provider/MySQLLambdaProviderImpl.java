package com.easy.query.test.provider;

import com.easy.query.api4j.sql.SQLWherePredicate;

/**
 * create time 2023/10/28 11:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQLLambdaProviderImpl<T> implements MySQLLambdaProvider<T>{
    private final SQLWherePredicate<T> sqlWherePredicate;

    public MySQLLambdaProviderImpl(SQLWherePredicate<T> sqlWherePredicate){
        this.sqlWherePredicate = sqlWherePredicate;
    }
    @Override
    public SQLWherePredicate<T> getSQLWherePredicate() {
        return sqlWherePredicate;
    }
}
