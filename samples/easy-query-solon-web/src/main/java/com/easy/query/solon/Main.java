package com.easy.query.solon;

import org.noear.solon.Solon;

public class Main {
    public static void main(String[] args) {
        Solon.start(Main.class,args,(app)->{
            app.cfg().loadAdd("application.yml");
        });
    }
}