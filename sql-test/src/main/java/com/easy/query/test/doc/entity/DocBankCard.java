package com.easy.query.test.doc.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.ForeignKey;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.doc.entity.proxy.DocBankCardProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/1/4 11:12
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("doc_bank_card")
@EntityProxy
@Data
@FieldNameConstants
@EasyAlias("bank_card")
public class DocBankCard implements ProxyEntityAvailable<DocBankCard, DocBankCardProxy> {
    @Column(primaryKey = true)
    private String id;
    private String uid;
    /**
     * 银行卡号
     */
    private String code;
    /**
     * 银行卡类型借记卡 储蓄卡
     */
    private String type;
    /**
     * 所属银行
     */
    private String bankId;

    @Navigate(value = RelationTypeEnum.ManyToOne, selfProperty = Fields.uid)
    private DocUser user;

    @NotNull
    @Valid
    @Navigate(value = RelationTypeEnum.ManyToOne, selfProperty = Fields.bankId)
    @ForeignKey
    private DocBank bank;


    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {DocBankCard.Fields.id}, targetProperty = {DocPart.Fields.cardId})
    private List<DocPart> parts;
}
