//package com.easy.query.test.interceptor;
//
//import com.easy.query.core.api.SQLClientApiFactory;
//import com.easy.query.core.basic.api.select.ClientQueryable;
//import com.easy.query.core.basic.extension.interceptor.PredicateFilterInterceptor;
//import com.easy.query.core.context.QueryRuntimeContext;
//import com.easy.query.core.expression.parser.core.base.WherePredicate;
//import com.easy.query.core.expression.sql.builder.LambdaEntityExpressionBuilder;
//import com.easy.query.test.entity.Company;
//import com.easy.query.test.entity.blogtest.SysUser;
//
///**
// * create time 2024/6/14 17:29
// * 文件说明
// *
// * @author xuejiaming
// */
//public class DataScopeInterceptor implements PredicateFilterInterceptor {
//    @Override
//    public void configure(Class<?> entityClass, LambdaEntityExpressionBuilder lambdaEntityExpressionBuilder, WherePredicate<Object> wherePredicate) {
//        if(true){
//            QueryRuntimeContext runtimeContext = lambdaEntityExpressionBuilder.getRuntimeContext();
//            SQLClientApiFactory sqlClientApiFactory = runtimeContext.getSQLClientApiFactory();
//            ClientQueryable<Company> queryable = sqlClientApiFactory.createQueryable(Company.class, runtimeContext);
//            wherePredicate.in("userId",
//                    queryable.where(x->{
//                        x.eq("comId","上下文comId");
//                    }).select(x->x.column("id"))
//            );
//        }
//    }
//
//    @Override
//    public boolean enable() {
//        return //ThreadLocal.xxxxxx
//    }
//
//    @Override
//    public String name() {
//        return "xxxx";
//    }
//
//    @Override
//    public boolean apply(Class<?> entityClass) {
//        return SysUser.class.isAssignableFrom(entityClass);
//    }
//}
