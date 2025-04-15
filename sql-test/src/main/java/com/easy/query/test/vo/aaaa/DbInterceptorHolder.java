package com.easy.query.test.vo.aaaa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * create time 2025/4/15 14:41
 * 文件说明
 *
 * @author xuejiaming
 */
@Component
public class DbInterceptorHolder {
    @Autowired
    private List<DbInterceptor>  dbInterceptors;

    public List<DbInterceptor> getDbInterceptors(){
        return dbInterceptors;
    }
}
