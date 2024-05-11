package com.easy.query.test.nop;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.nop.proxy.OtherTableProxy;
import lombok.Data;

/**
 * create time 2024/5/10 12:47
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("OtherTable")
@EntityProxy
public class OtherTable implements ProxyEntityAvailable<OtherTable , OtherTableProxy> {
    @Column(primaryKey = true)
    private String id;
    private String type;

    @Override
    public Class<OtherTableProxy> proxyTableClass() {
        return OtherTableProxy.class;
    }
}
