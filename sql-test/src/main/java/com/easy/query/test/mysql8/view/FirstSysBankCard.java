package com.easy.query.test.mysql8.view;

import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.api.EntityCteViewer;
import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import com.easy.query.test.mysql8.entity.bank.proxy.SysBankCardProxy;
import com.easy.query.test.mysql8.view.proxy.FirstSysBankCardProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.function.Supplier;

/**
 * create time 2025/6/7 15:45
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("bank_card_first")
@EntityProxy
@Data
@FieldNameConstants
@EasyAlias("bank_card_first")
public class FirstSysBankCard implements ProxyEntityAvailable<FirstSysBankCard, FirstSysBankCardProxy>, Serializable, EntityCteViewer<FirstSysBankCard> {
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
    /**
     * 用户开户时间
     */
    private LocalDateTime openTime;

    @Override
    public Supplier<Query<FirstSysBankCard>> viewConfigure(QueryRuntimeContext runtimeContext) {
        return () -> {
            SQLClientApiFactory sqlClientApiFactory = runtimeContext.getSQLClientApiFactory();
            ClientQueryable<SysBankCard> queryable = sqlClientApiFactory.createQueryable(SysBankCard.class, runtimeContext);
            return new EasyEntityQueryable<>(SysBankCardProxy.createTable(), queryable).select(m -> Select.PART.of(
                            m,
                            m.expression().rowNumberOver().partitionBy(m.uid()).orderByDescending(m.openTime())
                    ))
                    .where(card -> {
                        card.partColumn1().eq(1L);
                    }).select(FirstSysBankCard.class, u -> Select.of(
                            u.entityTable().FETCHER.allFields()
                    ));
        };
    }
}
