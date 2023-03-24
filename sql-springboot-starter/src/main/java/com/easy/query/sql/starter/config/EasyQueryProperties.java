package com.easy.query.sql.starter.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @FileName: EasyQueryProperties.java
 * @Description: 文件说明
 * @Date: 2023/3/11 14:25
 * @author xuejiaming
 */
@ConfigurationProperties(prefix = "easy-query")
public class EasyQueryProperties {

    private Boolean enable =false;

    private String logClass="com.easy.query.sql.starter.logging.Slf4jImpl";

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }


    public String getLogClass() {
        return logClass;
    }

    public void setLogClass(String logClass) {
        this.logClass = logClass;
    }

    public EasyQueryProperties() {
    }
}
