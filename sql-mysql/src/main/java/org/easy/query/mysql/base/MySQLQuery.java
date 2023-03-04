//package org.easy.query.mysql.base;
//
//import org.easy.query.core.basic.api.select.Query;
//import org.easy.query.core.expression.context.SelectContext;
//
///**
// * @FileName: MySQLQuery.java
// * @Description: 文件说明
// * @Date: 2023/3/3 17:00
// * @Created by xuejiaming
// */
//public class MySQLQuery<T> implements Query<T> {
//    private final Class<T> entityClass;
//    private final SelectContext selectContext;
//
//    public MySQLQuery(Class<T> entityClass, SelectContext selectContext){
//        this.entityClass = entityClass;
//
//        this.selectContext = selectContext;
//    }
//    @Override
//    public Class<T> queryClass() {
//        return entityClass;
//    }
//
//    @Override
//    public SelectContext getSqlEntityExpression() {
//        return selectContext;
//    }
//}
