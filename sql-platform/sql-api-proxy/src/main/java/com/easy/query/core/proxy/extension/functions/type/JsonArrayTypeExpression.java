//package com.easy.query.core.proxy.extension.functions.type;
//
//import com.easy.query.core.proxy.extension.functions.ColumnJsonMapFunctionAvailable;
//import com.easy.query.core.util.EasyObjectUtil;
//
///**
// * create time 2023/12/21 09:19
// * 文件说明
// *
// * @author xuejiaming
// */
//public interface JsonArrayTypeExpression<T> extends ObjectTypeExpression<T>,
//        ColumnJsonMapFunctionAvailable<T> {
//    @Override
//    default <TR> JsonArrayTypeExpression<TR> asAnyType(Class<TR> clazz) {
//        ObjectTypeExpression.super.asAnyType(clazz);
//        return EasyObjectUtil.typeCastNullable(this);
//    }
//}
