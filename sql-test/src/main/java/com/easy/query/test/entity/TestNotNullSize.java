package com.easy.query.test.entity;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.TestNotNullSizeProxy;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * create time 2025/11/22 16:19
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_test_not_null_size")
@EntityProxy
public class TestNotNullSize implements ProxyEntityAvailable<TestNotNullSize , TestNotNullSizeProxy> {
    private String id;

    @NotNull(message = "不能为空")
    @Size(min = 1,max = 10,message = "长度不能小于1")
    private List<String> rids;
}
