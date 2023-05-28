package com.easy.query.core.util;

import com.easy.query.core.common.LinkedCaseInsensitiveMap;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;

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

/**
 * @FileName: ClassUtil.java
 * @Description: 文件说明
 * @Date: 2023/2/11 13:03
 * @author xuejiaming
 */
public class EasyClassUtil {
    private final static Log log= LogFactory.getLog(EasyClassUtil.class);

    private EasyClassUtil() {
    }

    public static String getInstanceSimpleName(Object obj) {
        if(obj==null) {
            return EasyStringUtil.EMPTY;
        }
        return getSimpleName(obj.getClass());
    }
    public static String getSimpleName(Class<?> clazz) {
        if (clazz == null) {
            return EasyStringUtil.EMPTY;
        }
        return clazz.getSimpleName();
    }

    private static PropertyDescriptor find(PropertyDescriptor[] pd, String name) {
        for (PropertyDescriptor p : pd) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public static Method getWriteMethodNotNull(PropertyDescriptor prop, Class<?> type){
        Method writeMethod = getWriteMethodOrNull(prop, type);
        if(writeMethod==null){
            throw new EasyQueryException(EasyClassUtil.getSimpleName(type)+"."+prop.getName()+" cant get write method.");
        }
        return writeMethod;
    }

    /**
     * 代码参考beetlsql
     * <a href="https://gitee.com/xiandafu/beetlsql/blob/master/sql-util/src/main/java/org/beetl/sql/clazz/kit/BeanKit.java">beetlsql</a>
     * @param prop
     * @param type
     * @return
     */
    public static Method getWriteMethodOrNull(PropertyDescriptor prop, Class<?> type) {
        Method writeMethod = prop.getWriteMethod();
        //当使用lombok等链式编程方式时 有返回值的setter不被认为是writeMethod，需要自己去获取
        //lombok.experimental.Accessors (chain = true)注解影响
        if (writeMethod == null && !"class".equals(prop.getName())) {
            String propName = prop.getName();
            //符合JavaBean规范的set方法名称（userName=>setUserName,uName=>setuName）
            String setMethodName =
                    "set" + (propName.length() > 1 && propName.charAt(1) >= 'A' && propName.charAt(1) <= 'Z' ?
                            propName :
                            EasyStringUtil.toUpperCaseFirstOne(propName));
            try {
                writeMethod = type.getMethod(setMethodName, prop.getPropertyType());
            } catch (Exception e) {
                log.error("get write method error:"+prop.getName(),e);
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
    public static boolean isEnumType(Class<?> clazz) {
        return clazz.isEnum();
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new EasyQueryException(e);
        } catch (IllegalAccessException e) {
            throw new EasyQueryException(e);
        }
    }

    public static Map newMapInstanceOrNull(Class<?> clazz) {
        if(Map.class.equals(clazz)){
            return new LinkedCaseInsensitiveMap();
        }
        try {
            return (Map) clazz.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    public static PropertyDescriptor[] propertyDescriptors(Class<?> c) throws IntrospectionException {
        BeanInfo beanInfo = null;
        beanInfo = Introspector.getBeanInfo(c, Object.class);
        return beanInfo.getPropertyDescriptors();
    }
    public static PropertyDescriptor[] propertyDescriptorsRuntime(Class<?> c){
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(c, Object.class);
            return beanInfo.getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Field> getAllFields(Class<?> clazz) {
        ArrayList<Field> fields = new ArrayList<>();
        // 递归获取父类的所有Field
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            fields.addAll(getAllFields(superClass));
        }
        // 获取当前类的所有Field
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
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
     *
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


    /**
     * 代码参考beetlsql
     * @param result
     * @param requiredType
     * @return
     */
    public static Object convertValueToRequiredType(Object result, Class<?> requiredType) {
        if (result == null) {
            return null;
        }
        Class<?> type = result.getClass();
        if (type == result) {
            //大多数情况，都是这样
            return result;
        }
        if (String.class == requiredType) {
            return result.toString();
        }
        //判断Number对象所表示的类或接口是否与requiredType所表示的类或接口是否相同，或者是否是其超类或者超接口
        else if (Number.class.isAssignableFrom(requiredType)) {
            if (result instanceof Number) {
                return EasyNumberUtil.convertNumberToTargetClass((Number) result, requiredType);
            } else {
                return EasyNumberUtil.parseNumber(result.toString(), (Class<Number>) requiredType);
            }
        } else if (requiredType.isPrimitive()) {
            if (result instanceof Number) {
                return EasyNumberUtil.convertNumberToTargetClass((Number) result, requiredType);
            }
        }


        //TODO,增加一个扩展点，支持其他类型，比如JDK时间，Blob，Clob等


        throw new IllegalArgumentException("无法转化成期望类型:" + requiredType);
    }
}
