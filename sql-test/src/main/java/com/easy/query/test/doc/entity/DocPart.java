package com.easy.query.test.doc.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.doc.entity.proxy.DocPartProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * create time 2025/3/10 15:39
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("doc_part")
@EntityProxy
@Data
@FieldNameConstants
@EasyAlias("doc_part")
public class DocPart implements ProxyEntityAvailable<DocPart, DocPartProxy> {

    @Column(primaryKey = true)
    private String id;
    private String cardId;
    private String name;
}
