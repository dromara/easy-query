package com.easy.query.test.mysql8.entity.bank;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.ForeignKey;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.OrderByProperty;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.OrderByPropertyModeEnum;
import com.easy.query.core.enums.PartitionOrderEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.bank.proxy.SysUserProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/4/3 20:03
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_sys_user")
@EntityProxy
@Data
@FieldNameConstants
@EasyAlias("user")
public class SysUser implements ProxyEntityAvailable<SysUser, SysUserProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    private String phone;
    private Integer age;
    private LocalDateTime createTime;
    /**
     * 用户拥有的银行卡数
     */
    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {"id"}, targetProperty = {"uid"}
            , partitionOrder = PartitionOrderEnum.NAVIGATE, limit = 1, orderByProps = {
            @OrderByProperty(property = "openTime", asc = true)
    })
    private SysBankCard firstCard;
    /**
     * 用户拥有的银行卡数
     */
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {"id"}, targetProperty = {"uid"}, partitionOrder = PartitionOrderEnum.IGNORE)
    private List<SysBankCard> bankCards;
    /**
     * 用户拥有的银行卡数
     */
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {"id"}, targetProperty = {"uid"}, partitionOrder = PartitionOrderEnum.IGNORE)
    private List<SysBankCardInt> bankCardIntList;
    /**
     * 用户拥有的银行卡数
     */
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {"id"}, targetProperty = {"uid"}, subQueryToGroupJoin = true)
    private List<SysBankCard> bankCards2;
    /**
     * 用户拥有的银行卡数
     */
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {"id"}, targetProperty = {"uid"}, subQueryToGroupJoin = true, required = true)
    private List<SysBankCard> bankCards3;


    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {"id"}, targetProperty = {"uid"})
    private List<SysUserBook> userBooks;
    /**
     * 用户拥有的银行卡数
     */
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {"id"}, targetProperty = {"uid"}, orderByProps = {
            @OrderByProperty(property = "openTime", asc = true),
            @OrderByProperty(property = "code", asc = false, mode = OrderByPropertyModeEnum.NULLS_FIRST),
    }, partitionOrder = PartitionOrderEnum.NAVIGATE)
    private List<SysBankCard> bankCard4s;

}
