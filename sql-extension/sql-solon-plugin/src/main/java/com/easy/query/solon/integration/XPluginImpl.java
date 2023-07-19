package com.easy.query.solon.integration;

import org.noear.solon.core.AopContext;
import org.noear.solon.core.Plugin;

import javax.sql.DataSource;

/**
 * create time 2023/7/19 21:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class XPluginImpl implements Plugin {
    @Override
    public void start(AopContext context) throws Throwable {

        // 事件监听，用于时实初始化
        context.subWrapsOfType(DataSource.class, bw -> {
            DbManager.global().reg(bw);
        });
    }
}
