package com.easy.query.test;

import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.test.entity.Company;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.function.Supplier;

/**
 * create time 2023/11/6 08:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class ValueObjectTest extends BaseTest{

    @Test
    public void voTest1(){
        Supplier<Exception> f = () -> {
            try {
                List<Company> list = easyQuery.queryable(Company.class)
                        .asTable("company_a")
                        .select(o->o.columnAll())
                        .toList();
            }catch (Exception ex){
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("SELECT `id`,`name`,`province`,`city`,`area` FROM `company_a`",sql);
    }
    @Test
    public void voTest2(){
        Supplier<Exception> f = () -> {
            try {
                List<Company> list = easyQuery.queryable(Company.class)
                        .asTable("company_a")
                        .toList();
            }catch (Exception ex){
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("SELECT `id`,`name`,`province`,`city`,`area` FROM `company_a`",sql);
    }
    @Test
    public void voTest3(){
        Supplier<Exception> f = () -> {
            try {
                List<Company> list = easyQueryClient.queryable(Company.class)
                        .asTable("company_a")
                        //.select(o->o.column(Company::getAddress))
                        .select(o->{
                            o.column("address.province");
                        })
                        .toList();
            }catch (Exception ex){
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("SELECT `province` FROM `company_a`",sql);
    }
    @Test
    public void voTest4(){
        Supplier<Exception> f = () -> {
            try {
                List<Company> list = easyQueryClient.queryable(Company.class)
                        .asTable("company_a")
                        //.select(o->o.column(Company::getAddress))
                        .select(o->{
                            o.column("id");
                            o.column("address.province").columnIgnore("address");
                        })
                        .toList();
            }catch (Exception ex){
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("SELECT `id` FROM `company_a`",sql);
    }
    @Test
    public void voTest5(){
        Supplier<Exception> f = () -> {
            try {
                List<Company> list = easyQueryClient.queryable(Company.class)
                        .asTable("company_a")
                        //.select(o->o.column(Company::getAddress))
                        .select(o->{
                            o.columnAll().columnIgnore("address");
                        })
                        .toList();
            }catch (Exception ex){
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("SELECT `id`,`name` FROM `company_a`",sql);
    }
    @Test
    public void voTest6(){
        Supplier<Exception> f = () -> {
            try {
                List<Company> list = easyQueryClient.queryable(Company.class)
                        .asTable("company_a")
                        //.select(o->o.column(Company::getAddress))
                        .select(o->{
                            o.column("address");
                        })
                        .toList();
            }catch (Exception ex){
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("SELECT `province`,`city`,`area` FROM `company_a`",sql);
    }
//    @Test
//    public void voTest7(){
//        Supplier<Exception> f = () -> {
//            try {
//                List<Company> list = easyQuery.queryable(Company.class)
//                        .asTable("company_a")
//                        //.select(o->o.column(Company::getAddress))
//                        .select(o->{
//                            o.column();
//                        })
//                        .toList();
//            }catch (Exception ex){
//                return ex;
//            }
//            return null;
//        };
//        Exception exception = f.get();
//        Assert.assertNotNull(exception);
//        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//        String sql = easyQuerySQLStatementException.getSQL();
//        Assert.assertEquals("SELECT `province`,`city`,`area` FROM `company_a`",sql);
//    }
    @Test
    public void voTest7(){

        Supplier<Exception> f = () -> {
            try {
                List<Company> list = easyQuery.queryable(Company.class)
                        .asTable("company_a")
                        //.select(o->o.column(Company::getAddress))
                        .select(o->{
                            o.column(x->x.getAddress().getProvince());
                        })
                        .toList();
            }catch (Exception ex){
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("SELECT `province` FROM `company_a`",sql);
    }
    @Test
    public void voTest9(){

        Supplier<Exception> f = () -> {
            try {
                List<Company> list = easyQuery.queryable(Company.class)
                        .asTable("company_a")
                        .select(o->{
                            o.column(Company::getAddress);
                        })
                        .toList();
            }catch (Exception ex){
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("SELECT `province`,`city`,`area` FROM `company_a`",sql);
    }
    @Test
    public void voTest10(){

        Supplier<Exception> f = () -> {
            try {
                List<Company> list = easyQuery.queryable(Company.class)
                        .asTable("company_a")
                        .where(o->o.eq(x->x.getAddress().getArea(),1))
                        .where(o->o.eq(x->x.getAddress().getProvince(),2))
                        .select(o->{
                            o.column(Company::getAddress);
                        })
                        .toList();
            }catch (Exception ex){
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("SELECT `province`,`city`,`area` FROM `company_a` WHERE `area` = ? AND `province` = ?",sql);
    }

    @Test
    public void voTest8(){

        Property<Company,String> property=x->x.getAddress().getProvince();
//        Property<Company, ?> property1 = columTest1(Company::getAddress, CompanyAddress::getProvince);
//        {
//
//            String propertyName = EasyLambdaUtil.getPropertyName(property1);
//            System.out.println(propertyName);
//        }
        {

            String propertyName = EasyLambdaUtil.getPropertyName(Company::getName);
            System.out.println(propertyName);
        }
//        String propertyName = EasyLambdaUtil.getPropertyName(property);
////        Type ret = Type.getReturnType(desc)
//        String propertyPath = PropertyAnalyzer.analyzePropertyPath(company, property);
//        System.out.println(propertyPath); // 输出 "province"
        for (int i = 0; i < 100; i++) {
            String propertyName2 = EasyLambdaUtil.getPropertyName(property);
            System.out.println(propertyName2);
        }

    }
//    public static  <T,T1,T2> Property<T,?> columTest1(Property<T,T1> column,Property<T1,T2> column1){
//        StringBuilder sb = new StringBuilder();
//        String propertyName = EasyLambdaUtil.getPropertyName(column);
//        sb.append(propertyName);
//        sb.append(".");
//        String propertyName1 = EasyLambdaUtil.getPropertyName(column1);
//        sb.append(propertyName1);
//        return new MultiPropertyImpl<>(sb.toString());
//    }
//
//    private String classFilePath(String className) {
//        return className.replace('.', '/') + ".class";
//    }
//
//    public static void extractLambdaMethod(Property<Company, String> lambda) {
//        try {
//            Class<?> lambdaClass = lambda.getClass();
//            Method declaredMethod = lambdaClass.getDeclaredMethod("writeReplace");
//            declaredMethod.setAccessible(Boolean.TRUE);
//            SerializedLambda serializedLambda = (SerializedLambda) declaredMethod.invoke(lambda);
//
//            String className = serializedLambda.getImplClass();
//            ClassReader classReader = new ClassReader(className);
//            classReader.accept(new ClassVisitor(Opcodes.ASM4) {
//                @Override
//                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
//                    System.out.println(name);
//                    System.out.println(descriptor);
//                    System.out.println(signature);
//                    if (name.equals("apply") && descriptor.equals("(Ljava/lang/Object;)Ljava/lang/Object;")) {
//                        return new MethodVisitor(Opcodes.ASM4) {
//                            @Override
//                            public void visitLdcInsn(Object value) {
//                                if (value instanceof String) {
//                                    System.out.println("Lambda Expression Output: " + value);
//                                }
//                                super.visitLdcInsn(value);
//                            }
//                        };
//                    }
//                    return super.visitMethod(access, name, descriptor, signature, exceptions);
//                }
//            }, 0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
