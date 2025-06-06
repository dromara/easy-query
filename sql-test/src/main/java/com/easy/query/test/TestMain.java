//package com.easy.query;
//
//import com.easy.query.core.expression.lambda.Property;
//import com.easy.query.core.util.EasyUtil;
//import com.easy.query.core.util.LambdaUtil;
//import com.easy.query.test.TestUserMysql;
//
//import java.lang.invoke.*;
//import java.util.function.Function;
//
///**
// * @FileName: TestMain.java
// * @Description: 文件说明
// * create time 2023/3/20 14:44
// * @author xuejiaming
// */
//public class TestMain {
//    public static void main(String[] args) throws Throwable {
////        Property<Object, ?> id = EasyUtil.getLambda1(TestUserMysql.class, "id", String.class);
//        Property<Object, ?> myMethod = EasyUtil.getPropertyLambda(TestUserMysql.class, "id", String.class);
//        TestUserMysql testUserMysql = new TestUserMysql();
//        testUserMysql.setId("234");
//        Object apply = id.apply(testUserMysql);
//        String propertyName = LambdaUtil.getPropertyName(id);
//        MethodHandles.Lookup lookup = MethodHandles.lookup();
//        MethodType methodType = MethodType.methodType(String.class, String.class);
//        MethodHandle methodHandle = lookup.findVirtual(MyInterfaceImpl.class, "myMethod", methodType);
//        CallSite callSite = LambdaMetafactory.altMetafactory(
//                lookup,
//                "apply",
//                MethodType.methodType(Function.class),
//                MethodType.methodType(String.class, MyInterface.class),
//                methodHandle,
//                MethodType.methodType(String.class, MyInterface.class),
//                MethodType.methodType(String.class, MyInterface.class));
//        Function<MyInterface, String> function = (Function<MyInterface, String>) callSite.getTarget().invokeExact();
//        MyInterface myInterface = new MyInterfaceImpl();
//        String result = function.apply(myInterface);
//        System.out.println(result); // Output: Test
//    }
//
//    interface MyInterface {
//        String myMethod();
//    }
//   public static  class MyInterfaceImpl implements MyInterface {
//        @Override
//        public String myMethod() {
//            return "Test";
//        }
//    }
//}
//
//
////    public static void main(String[] args) throws Throwable {
////        MethodHandles.Lookup lookup = MethodHandles.lookup();
////        MethodType methodType = MethodType.methodType(String.class, String.class); // 目标方法的返回类型和参数类型
////        MethodHandle methodHandle = lookup.findVirtual(MyInterfaceImpl.class, "myMethod", methodType); // 目标方法的句柄
////        MethodType instantiatedMethodType = MethodType.methodType(MyInterface.class); // Lambda表达式的目标方法类型
////        CallSite callSite = LambdaMetafactory.altMetafactory(
////                lookup,
////                "apply",
////                instantiatedMethodType,
////                methodType.generic(),
////                methodHandle,
////                methodType,
////                MethodType.methodType(Object.class, MyInterface.class)); // 传入的参数分别是：当前类的查找对象，Lambda表达式要实现的方法的名称，Lambda表达式的目标方法类型，目标方法的返回类型和参数类型，目标方法的句柄，目标方法的类型，实现Lambda表达式的方法类型
////        Function<MyInterface, String> function = (Function<MyInterface, String>) callSite.getTarget().invokeExact();
////
////
////
////
////
////
////        MyInterface myInterface = new MyInterfaceImpl();
////        String result = function.apply(myInterface);
////        System.out.println(result); // Output: Test Output
////    }
////}
////
////interface MyInterface {
////    String myMethod(String str);
////}
////
////class MyInterfaceImpl implements MyInterface {
////    @Override
////    public String myMethod(String str) {
////        return "Test " + str;
////    }
////}