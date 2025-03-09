package com.easy.query.test.doc.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.doc.entity.proxy.DocUserBookProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

/**
 * create time 2025/3/9 22:15
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("doc_user_book")
@EntityProxy
@Data
@FieldNameConstants
@EasyAlias("userBook")
public class DocUserBook implements ProxyEntityAvailable<DocUserBook, DocUserBookProxy> {
    @Column(primaryKey = true)
    private String id;
    private String uid;
    private String name;
    private BigDecimal price;
}
