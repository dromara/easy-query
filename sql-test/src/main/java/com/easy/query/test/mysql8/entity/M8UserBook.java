package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
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
@Table("m8_user_book")
@EntityProxy
@FieldNameConstants
public class M8UserBook implements ProxyEntityAvailable<M8UserBook , M8UserBookProxy> {
    @Column(primaryKey = true)
    private String bookId;
    private String userId;
    private String bookName;
    private BigDecimal bookPrice;
}
