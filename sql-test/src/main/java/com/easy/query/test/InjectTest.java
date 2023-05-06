package com.easy.query.test;

import com.easy.query.core.exception.EasyQueryInjectBeanCurrentlyInCreationException;
import com.easy.query.core.inject.ServiceCollection;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.inject.impl.ServiceCollectionImpl;
import com.easy.query.inject.BeanCurrent1;
import com.easy.query.inject.BeanCurrent2;
import com.easy.query.inject.Car;
import com.easy.query.inject.Car1;
import com.easy.query.inject.Driver;
import com.easy.query.inject.Driver1;
import org.junit.Assert;
import org.junit.Test;

/**
 * create time 2023/5/5 22:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class InjectTest {
    @Test
    public void injectTest1(){
        ServiceCollection serviceCollection = new ServiceCollectionImpl();
        serviceCollection.addService(Car.class, Car1.class);
        serviceCollection.addService(Driver.class, Driver1.class);
        ServiceProvider serviceProvider = serviceCollection.build();
        Car service = serviceProvider.getService(Car.class);
        Assert.assertEquals(Car1.class,service.getClass());
        service.run();
    }

    /**
     * 如果已经被build那么重新替换也是不行的
     */
    @Test
    public void injectTest2(){
        ServiceCollection serviceCollection = new ServiceCollectionImpl();
        serviceCollection.addService(Car.class, Car1.class);
        serviceCollection.addService(Driver.class, Driver1.class);
        ServiceProvider serviceProvider = serviceCollection.build();

        serviceCollection.addServiceFactory(Car.class, sp->{
            Car1 car1 = new Car1(sp.getService(Driver.class));
            car1.setName("111");
            return car1;
        });
        Car service1 = serviceProvider.getService(Car.class);
        Assert.assertEquals(Car1.class,service1.getClass());
        Car1 service11 = (Car1) service1;
        Assert.assertEquals("car1",service11.getName());
    }
    @Test
    public void injectTest3(){
        ServiceCollection serviceCollection = new ServiceCollectionImpl();
        serviceCollection.addService(BeanCurrent1.class);
        serviceCollection.addService(BeanCurrent2.class);
        ServiceProvider serviceProvider = serviceCollection.build();
        try{
            BeanCurrent1 service = serviceProvider.getService(BeanCurrent1.class);
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQueryInjectBeanCurrentlyInCreationException);
        }
    }
}
