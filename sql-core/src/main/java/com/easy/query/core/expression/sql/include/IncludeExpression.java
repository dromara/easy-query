//package com.easy.query.core.expression.sql.include;
//
//import com.easy.query.core.basic.api.select.ClientQueryable;
//import com.easy.query.core.expression.lambda.SQLFuncExpression1;
//import com.easy.query.core.expression.parser.core.available.TableAvailable;
//import com.easy.query.core.metadata.IncludeNavigateParams;
//
///**
// * create time 2023/7/20 10:43
// * 文件说明
// *
// * @author xuejiaming
// */
//public class IncludeExpression {
//    private final TableAvailable table;
//    private final SQLFuncExpression1<IncludeNavigateParams, ClientQueryable<?>> includeQueryableFunction;
//
//    public IncludeExpression(TableAvailable table, SQLFuncExpression1<IncludeNavigateParams, ClientQueryable<?>> includeQueryableFunction) {
//        this.table = table;
//
//        this.includeQueryableFunction = includeQueryableFunction;
//    }
//
//    public TableAvailable getTable() {
//        return table;
//    }
//
//    public SQLFuncExpression1<IncludeNavigateParams, ClientQueryable<?>> getIncludeQueryableFunction() {
//        return includeQueryableFunction;
//    }
//}
