package com.easy.query.test.doc.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.PartitionOrderEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.doc.entity.proxy.DocUserProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * create time 2025/1/4 11:11
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("doc_user")
@EntityProxy
@Data
@FieldNameConstants
@EasyAlias("user")
public class DocUser implements ProxyEntityAvailable<DocUser , DocUserProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    private String phone;
    private Integer age;

    @Navigate(value = RelationTypeEnum.OneToMany,targetProperty = DocBankCard.Fields.uid,partitionOrder = PartitionOrderEnum.KEY_ASC)
    private List<DocBankCard> bankCards;

    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {DocUser.Fields.id}, targetProperty = {DocUserBook.Fields.uid})
    private List<DocUserBook> userBooks;
}
