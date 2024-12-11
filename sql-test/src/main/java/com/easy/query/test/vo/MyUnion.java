package com.easy.query.test.vo;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.vo.proxy.MyUnionProxy;
import lombok.Data;

/**
 * create time 2024/12/10 10:46
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class MyUnion implements ProxyEntityAvailable<MyUnion , MyUnionProxy> {
    private String taskStatus;
}
