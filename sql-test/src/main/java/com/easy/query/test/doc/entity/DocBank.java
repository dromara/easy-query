package com.easy.query.test.doc.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.doc.entity.proxy.DocBankProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * create time 2025/1/4 11:14
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("doc_bank")
@EntityProxy
@Data
@FieldNameConstants
@EasyAlias("bank")
public class DocBank implements ProxyEntityAvailable<DocBank, DocBankProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;

    @Navigate(value = RelationTypeEnum.OneToMany, targetProperty = DocBankCard.Fields.bankId)
    private List<DocBankCard> bankCards;
}
