package com.easy.query.core.lambda.common;

import com.easy.query.core.common.bean.DefaultFastBean;
import com.easy.query.core.common.bean.FastBeanProperty;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.expression.lambda.PropertyVoidSetter;
import com.easy.query.core.util.EasyClassUtil;
import sun.misc.Unsafe;

import java.lang.invoke.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Supplier;

public class UnsafeFastBean extends DefaultFastBean
{
    private static final Unsafe unsafe;

    static
    {
        try
        {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        }
        catch (IllegalAccessException | NoSuchFieldException e)
        {
            throw new RuntimeException(e);
        }
    }

    private final Class<?> beanClass;
    private final boolean isAnonymousClass;
    private final MethodHandles.Lookup lookup;

    public UnsafeFastBean(Class<?> beanClass, MethodHandles.Lookup lookup)
    {
        super(beanClass);
        this.beanClass = beanClass;
        this.lookup = lookup;
        this.isAnonymousClass = beanClass.isAnonymousClass();
    }

    @Override
    public Supplier<Object> getBeanConstructorCreator()
    {
        if (isAnonymousClass)
        {
            return () ->
            {
                try
                {
                    return unsafe.allocateInstance(beanClass);
                }
                catch (InstantiationException e)
                {
                    throw new RuntimeException(e);
                }
            };
        }
        else
        {
            return super.getBeanConstructorCreator();
        }
    }

    @Override
    public Property<Object, ?> getBeanGetter(FastBeanProperty prop)
    {
        if (isAnonymousClass)
        {
            Class<?> propertyType = prop.getPropertyType();
            Method readMethod = prop.getReadMethod();
            String getFunName = readMethod.getName();
            MethodType methodType = MethodType.methodType(propertyType, beanClass);
            CallSite site;
            try
            {
                MethodHandle virtual = lookup.findVirtual(beanClass, getFunName, MethodType.methodType(propertyType));
//                MethodHandle virtual = lookup.findGetter(beanClass, getFunName, propertyType);
                site = LambdaMetafactory.altMetafactory(
                        lookup,
                        "apply",
                        MethodType.methodType(Property.class),
                        methodType.erase().generic(),
                        virtual,
                        methodType,
                        1
                );
                return (Property<Object, ?>) site.getTarget().invokeExact();
            }
            catch (Throwable e)
            {
                throw new EasyQueryException(e);
            }

        }
        else
        {
            return super.getBeanGetter(prop);
        }
    }

    @Override
    public PropertySetterCaller<Object> getBeanSetter(FastBeanProperty prop)
    {
        if (isAnonymousClass)
        {
            Class<?> propertyType = prop.getPropertyType();

            Method writeMethod = EasyClassUtil.getWriteMethodNotNull(prop, beanClass);
            MethodType setter = MethodType.methodType(writeMethod.getReturnType(), propertyType);

            Class<?> lambdaPropertyType = EasyClassUtil.getObjectTypeWhenPrimitive(propertyType);
            String getFunName = writeMethod.getName();
            try
            {

                MethodType instantiatedMethodType = MethodType.methodType(void.class, beanClass, lambdaPropertyType);
                MethodHandle target = lookup.findVirtual(beanClass, getFunName, setter);
                MethodType samMethodType = MethodType.methodType(void.class, Object.class, Object.class);
                CallSite site = LambdaMetafactory.metafactory(
                        lookup,
                        "apply",
                        MethodType.methodType(PropertyVoidSetter.class),
                        samMethodType,
                        target,
                        instantiatedMethodType
                );

                PropertyVoidSetter<Object, Object> objectPropertyVoidSetter = (PropertyVoidSetter<Object, Object>) site.getTarget().invokeExact();
                return objectPropertyVoidSetter::apply;
            }
            catch (Throwable e)
            {
                throw new EasyQueryException(e);
            }
        }
        else
        {
            return super.getBeanSetter(prop);
        }
    }

//    public static void main(String[] args) throws IntrospectionException
//    {
//        Result result = new Result()
//        {
//            String id = "id";
//
//            public String getId()
//            {
//                return id;
//            }
//
//            public void setId(String id)
//            {
//                this.id = id;
//            }
//
//            @Override
//            public String toString()
//            {
//                return "匿名Result{" +
//                        "id='" + id + '\'' +
//                        '}';
//            }
//        };
//        FastBeanProperty fastBeanProperty = new FastBeanProperty(false, new PropertyDescriptor("id", result.getClass()));
//        UnsafeFastBean unsafeFastBean = new UnsafeFastBean(result.getClass(), MethodHandles.lookup());
//        PropertySetterCaller<Object> beanSetter = unsafeFastBean.getBeanSetter(fastBeanProperty);
//        beanSetter.call(result, "new");
//        System.out.println(result);
//    }
}
