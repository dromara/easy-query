package com.easy.query.test.h2.vo;

import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.h2.vo.proxy.QueryVOProxy;
import lombok.Data;

/**
 * create time 2023/12/17 14:25
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityFileProxy
public class  QueryVO implements ProxyEntityAvailable<QueryVO , QueryVOProxy> {
    private String id;
    private String field1;
    private String field2;

    @Override
    public Class<QueryVOProxy> proxyTableClass() {
        return QueryVOProxy.class;
    }
}
