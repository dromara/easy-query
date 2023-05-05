package com.easy.query.inject;

/**
 * create time 2023/5/5 21:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class Car1 implements Car{
    private final Driver driver;

    public Car1(Driver driver){

        this.driver = driver;
    }
    @Override
    public void run() {
        driver.drive();
        System.out.println("Car1");
    }
}
