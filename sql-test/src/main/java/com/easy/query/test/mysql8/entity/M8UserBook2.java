package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8UserBook2Proxy;
import com.easy.query.test.mysql8.entity.proxy.M8UserBookProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

/**
 * create time 2025/3/15 19:29
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("m8_user_book2")
@EntityProxy
@FieldNameConstants
public class M8UserBook2 implements ProxyEntityAvailable<M8UserBook2, M8UserBook2Proxy> {
    @Column(primaryKey = true)
    private String bookId;
    private String bookName;
    private BigDecimal bookPrice;
}
