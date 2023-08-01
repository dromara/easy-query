package com.easy.query.solon;

import org.noear.solon.Solon;

public class Main {
    public static void main(String[] args) {
        Solon.start(Main.class,args,(app)->{
            app.cfg().loadAdd("application.yml");//app.yml或者application.yml默认加载可以忽略
        });
    }
}