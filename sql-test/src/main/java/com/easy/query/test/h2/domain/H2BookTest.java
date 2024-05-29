package com.easy.query.test.h2.domain;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.h2.domain.proxy.H2BookTestProxy;
import lombok.Data;

/**
 * create time 2023/7/30 00:11
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_book_test")
@Data
@EntityProxy
public class H2BookTest implements ProxyEntityAvailable<H2BookTest , H2BookTestProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    private String edition;
    private String price;
    private String storeId;

}
