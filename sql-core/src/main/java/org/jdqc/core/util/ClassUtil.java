package org.jdqc.core.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @FileName: ClassUtil.java
 * @Description: 文件说明
 * @Date: 2023/2/11 13:03
 * @Created by xuejiaming
 */
public class ClassUtil {
    private ClassUtil(){}

    public static PropertyDescriptor[] propertyDescriptors(Class<?> c) throws IntrospectionException {

        BeanInfo beanInfo = null;
        beanInfo = Introspector.getBeanInfo(c);
        return beanInfo.getPropertyDescriptors();

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
