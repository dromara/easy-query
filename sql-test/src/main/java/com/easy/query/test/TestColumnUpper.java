package com.easy.query.test;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.proxy.TestColumnUpperProxy;
import lombok.Data;

/**
 * create time 2025/2/12 15:13
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("m_table")
@EntityProxy
@Data
public class TestColumnUpper implements ProxyEntityAvailable<TestColumnUpper , TestColumnUpperProxy> {
    @Column(value = "ROLEID")
    private String roleid;

}
