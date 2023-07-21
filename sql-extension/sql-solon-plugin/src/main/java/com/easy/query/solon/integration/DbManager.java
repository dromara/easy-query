package com.easy.query.solon.integration;

import com.easy.query.api4j.client.EasyQuery;
import org.noear.solon.core.BeanWrap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2023/7/19 21:53
 * 文件说明
 *
 * @author xuejiaming
 */

public class DbManager {
    private static DbManager _global = new DbManager();

    public static DbManager global() {
        return _global;
    }


    private Map<String, EasyQuery> dbMap = new ConcurrentHashMap<>();

    public EasyQuery get(BeanWrap bw) {
        EasyQuery db = dbMap.get(bw.name());

        if (db == null) {
//            synchronized (bw.name().intern()) {
//                db = dbMap.get(bw.name());
//                if (db == null) {
//                    DataSource ds = bw.raw();
//                    db = new DbContext(ds).nameSet(bw.name());
//
//                    dbMap.put(bw.name(), db);
//
//                    if (bw.typed()) {
//                        dbMap.put("", db);
//                    }
//
//                    //初始化元信息（可起到热链接的作用）
//                    db.initMetaData();
//                }
//            }

        }

        return db;
    }

    public void reg(BeanWrap bw) {
        get(bw);
    }
}

