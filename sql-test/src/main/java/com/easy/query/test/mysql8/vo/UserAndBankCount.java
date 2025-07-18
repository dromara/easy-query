package com.easy.query.test.mysql8.vo;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.PartitionOrderEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import com.easy.query.test.mysql8.vo.proxy.UserAndBankCountProxy;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/7/18 13:22
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class UserAndBankCount {
    private String uid;
    private String bankId;

    private Long bankCount;

    @Navigate(value = RelationTypeEnum.OneToMany,
            selfProperty = {UserAndBankCountProxy.Fields.uid,
                    UserAndBankCountProxy.Fields.bankId},
            targetProperty = {SysBankCard.Fields.uid, SysBankCard.Fields.bankId}, subQueryToGroupJoin = true, supportNonEntity = true,partitionOrder = PartitionOrderEnum.IGNORE)
    private List<SysBankCard> bankUserCards;
}
