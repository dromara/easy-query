package com.easy.query.test.inject;

/**
 * create time 2023/5/5 21:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class Car1 implements Car{
    private final Driver driver;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Car1(Driver driver){

        this.driver = driver;
        this.name="car1";
    }
    @Override
    public void run() {
        driver.drive();
        System.out.println("Car1:"+name);
    }
}
