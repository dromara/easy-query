//package com.easy.query.core.expression.parser.core.base;
//
//import com.easy.query.core.expression.builder.GroupSelector;
//import com.easy.query.core.expression.func.ColumnPropertyFunction;
//import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
//import com.easy.query.core.expression.parser.core.base.core.SQLPropertyNative;
//
///**
// * create time 2023/9/27 19:41
// * 文件说明
// *
// * @author xuejiaming
// */
//public interface ColumnGroupAsSelector<T1> extends EntitySQLTableOwner<T1>, SQLPropertyNative<ColumnGroupAsSelector<T1>> {
//
//    GroupSelector getGroupSelector();
//
//    ColumnGroupAsSelector<T1> column(String property);
//
//    ColumnGroupAsSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction);
//
//    default <T2> ColumnGroupAsSelector<T2> then(ColumnGroupAsSelector<T2> sub) {
//        return sub;
//    }
//}
