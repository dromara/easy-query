package com.easy.query.test;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft4;
import com.easy.query.core.proxy.core.draft.proxy.Draft4Proxy;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.doc.entity.DocUser;
import org.junit.Test;

import java.util.List;

/**
 * create time 2025/3/7 16:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest22 extends BaseTest{
    @Test
    public void testManyGroup(){

        List<Draft2<Long, Long>> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().count().rangeOpenClosed(1L, 2L);
                })
                .select(user -> Select.DRAFT.of(
                        user.bankCards().where(bc -> bc.type().eq("1")).count(),
                        user.bankCards().where(bc -> bc.type().eq("2")).count()
                )).toList();
    }
    @Test
    public void testManyGroup2(){

        List<Draft2<Long, Long>> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().count().rangeOpenClosed(1L, 2L);
                })
                .select(user -> Select.DRAFT.of(
                        user.bankCards().where(bc -> bc.type().eq("1")).count(),
                        user.bankCards().where(bc -> bc.type().eq("2")).count()
                )).toList();
    }
    @Test
    public void testManyGroup3(){

        EntityQueryable<Draft4Proxy<String, Long, Long, Long>, Draft4<String, Long, Long, Long>> select = easyEntityQuery.queryable(DocBankCard.class)
                .groupBy(bank_card -> GroupKeys.of(bank_card.uid()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.count(),
                        group.where(g -> g.type().eq("1")).count(),
                        group.where(g -> g.type().eq("2")).count()
                ));
        List<Draft2<Long, Long>> list = easyEntityQuery.queryable(DocUser.class)
                .leftJoin(select,(user, l2) -> user.id().eq(l2.value1()))
                .where((user, l2) -> {
                    l2.value2().rangeOpenClosed(1L, 2L);
                })
                .select((user, l2) -> Select.DRAFT.of(
                        l2.value3(),
                        l2.value4()
                )).toList();
    }
}
