package com.easy.query.sql.starter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * create time 2023/8/27 16:51
 * 文件说明
 *
 * @author xuejiaming
 */
@ConfigurationProperties(prefix = "easy-query-track")
public class EasyQueryTrackProperties {
    private Boolean enable = true;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
