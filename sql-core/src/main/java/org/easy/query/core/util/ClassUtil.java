package org.easy.query.core.util;

import org.easy.query.core.basic.bean.BeanMethodInvoker;
import org.easy.query.core.basic.bean.MethodInvoker;
import org.easy.query.core.exception.EasyQueryException;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName: ClassUtil.java
 * @Description: 文件说明
 * @Date: 2023/2/11 13:03
 * @Created by xuejiaming
 */
public class ClassUtil {
    public final static Map<Class, Map<String, MethodInvoker>> methodInvokerCache = new ConcurrentHashMap<>();
    private ClassUtil(){}


    public static Object getPropertyValue(Object o, String attrName) {

        try {
            MethodInvoker inv = getInvoker(o.getClass(), attrName);
            return inv.get(o);
        } catch (Exception ex) {
            throw new RuntimeException("POJO属性访问出错:" + attrName, ex);
        }
    }
    public static MethodInvoker getInvoker(Class<?> clazz, String name){

        MethodInvoker invoker = null;
        Map<String, MethodInvoker> map = methodInvokerCache.get(clazz);
        if (map != null) {
            invoker = map.get(name);
            if (invoker != null) {
                return invoker;
            }
        }

        PropertyDescriptor property = null;
        PropertyDescriptor[] pd = null;
        try {
            pd = propertyDescriptors(clazz);
            property = find(pd, name);
        } catch (IntrospectionException e) {
            throw new EasyQueryException( "获取类属性错", e);
        }

        if (property != null) {
            invoker = new BeanMethodInvoker(property);
            return invoker;
        }

//        /**
//         * 检测2.0兼容，就是isXXX对应的PropertyDescriptor，本来应该只能按照xxx访问，但2.0错误支持了
//         * isXxx来访问此属性，3.0继续支持
//         *
//         */
//
//        if (name.startsWith("is")) {
//            property = findIsMethod(pd, name);
//        }
//        if (property != null) {
//            invoker = new PojoMethodInvoker(property);
//            return invoker;
//        }
//
//        // General Get
//        Method method = getGetMethod(c, "get", Object.class);
//        if (method != null) {
//            invoker = new GeneralGetMethodInvoker(method, name);
//        } else {
//            method = getGetMethod(c, "get", String.class);
//            if (method != null) {
//                invoker = new GeneralGetMethodInvoker(method, name);
//            }
//        }
//
        if (invoker == null) {

            return null;
        }
//
        if (map == null) {
            map = new ConcurrentHashMap<>();
            map.putIfAbsent(name, invoker);
            methodInvokerCache.putIfAbsent(clazz, map);
        } else {
            map.putIfAbsent(name, invoker);
        }

        return invoker;
    }

    private static PropertyDescriptor find(PropertyDescriptor[] pd, String name) {
        for (PropertyDescriptor p : pd) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }
    public static Method getWriteMethod(PropertyDescriptor prop, Class<?> type) {
        Method writeMethod = prop.getWriteMethod();
        //当使用lombok等链式编程方式时 有返回值的setter不被认为是writeMethod，需要自己去获取
        if (writeMethod == null && !"class".equals(prop.getName())) {
            String propName = prop.getName();
            //符合JavaBean规范的set方法名称（userName=>setUserName,uName=>setuName）
            String setMethodName =
                    "set" + (propName.length() > 1 && propName.charAt(1) >= 'A' && propName.charAt(1) <= 'Z' ?
                            propName :
                            StringUtil.toUpperCaseFirstOne(propName));
            try {
                writeMethod = type.getMethod(setMethodName, prop.getPropertyType());
            } catch (Exception e) {
                //不存在set方法
                return null;
            }
        }
        return writeMethod;
    }

    public static boolean isBasicType(Class<?> clazz) {
        if (clazz.isPrimitive()) {
            return true;
        }

        if (clazz.getName().startsWith("java.")) {
            return ((clazz == String.class) || clazz == Integer.class || clazz == Byte.class || clazz == Long.class
                    || clazz == Double.class || clazz == Float.class || clazz == Character.class || clazz == Short.class
                    || clazz == BigDecimal.class || clazz == BigInteger.class || clazz == Boolean.class
                    || clazz == java.util.Date.class || clazz == java.sql.Date.class
                    || clazz == java.sql.Timestamp.class || clazz == java.time.LocalDateTime.class
                    || clazz == java.time.LocalDate.class);
        } else {
            return false;
        }

    }
    public static  <T> T newInstance(Class<T> clazz){
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new EasyQueryException(e);
        } catch (IllegalAccessException e) {
            throw new EasyQueryException(e);
        }
    }
    public static PropertyDescriptor[] propertyDescriptors(Class<?> c) throws IntrospectionException {

        BeanInfo beanInfo = null;
        beanInfo = Introspector.getBeanInfo(c,Object.class);
        return beanInfo.getPropertyDescriptors();

    }
    public static List<Field> getAllFields(Class clazz){
        ArrayList<Field> fields = new ArrayList<>();
        Class currentClazz = clazz;
        while(currentClazz!=null){
            fields.addAll(Arrays.asList(currentClazz.getDeclaredFields()));
            currentClazz=currentClazz.getSuperclass();
        }
        return fields;
    }

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    /**
     * 得到某个类的类注解，如果没有，则寻找父类
     * @param c
     * @param expect
     * @return
     */
    public static <T extends Annotation> T getAnnotation(Class c, Class<T> expect) {
        do {
            T an = (T) c.getAnnotation(expect);
            if (an != null) {
                return an;
            }
            c = c.getSuperclass();
        } while (c != null && c != Object.class);
        return null;

    }
    public static <T extends Annotation> T getAnnotation(Class c, String property, Method getter,
                                                         Class<T> annotationClass) {
        if (getter == null) {
            throw new NullPointerException("期望POJO类符合javabean规范，" + c + " 没有getter方法");
        }
        T t = getter.getAnnotation(annotationClass);
        if (t != null) {
            return t;
        } else {
            try {
                while (c != null) {
                    Field[] fs = c.getDeclaredFields();
                    for (Field f : fs) {
                        if (!f.getName().equals(property)) {
                            continue;
                        }
                        t = f.getAnnotation(annotationClass);
                        return t;

                    }
                    c = c.getSuperclass();
                }
                return t;
            } catch (Exception e) {
                return null;
            }

        }
    }
}
