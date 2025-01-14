package com.easy.query.test.h2;

import com.easy.query.api.proxy.base.LocalDateTimeProxy;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.doc.dto.BankCardVO;
import com.easy.query.test.doc.dto.DocUserVO;
import com.easy.query.test.doc.dto.proxy.BankCardVOProxy;
import com.easy.query.test.doc.dto.proxy.DocUserVOProxy;
import com.easy.query.test.doc.entity.DocBank;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/1/4 14:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class H2DocTest extends H2BaseTest {

    @Test
    public void selectProxyTest1() {

        easyEntityQuery.queryable(DocUser.class)
                .selectAutoInclude(DocUserVO.class);

        easyEntityQuery.queryable(DocUser.class)
                .includes(user -> user.bankCards(),cq->cq.where(c->c.bankId().eq("招商银行")))
                .selectAutoInclude(DocUserVO.class);


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<BankCardVO> list = easyEntityQuery.queryable(DocBankCard.class)
                    .leftJoin(DocUser.class, (bank_card, user) -> bank_card.uid().eq(user.id()))
                    .leftJoin(DocBank.class, (bank_card, user, bank) -> bank_card.bankId().eq(bank.id()))
                    .where((bank_card, user, bank) -> {
                        user.name().like("小明");
                        bank_card.type().eq("储蓄卡");
                    })
                    .select((bank_card, user, bank) -> {
                        BankCardVOProxy r = new BankCardVOProxy();
                        r.selectAll(bank_card);//相当于是查询所有的bankCard字段
                        r.userName().set(user.name());
                        r.bankName().set(bank.name());
                        return r;
                    }).toList();
        } catch (Exception ex) {
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.id,t.uid,t.code,t.type,t1.name AS user_name,t2.name AS bank_name FROM doc_bank_card t LEFT JOIN doc_user t1 ON t.uid = t1.id LEFT JOIN doc_bank t2 ON t.bank_id = t2.id WHERE t1.name LIKE ? AND t.type = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明%(String),储蓄卡(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void selectProxyTest1_1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> bank_card.type().eq("储蓄卡"))
                    .select(bank_card -> bank_card.FETCHER.id().code())
                    .toList();
        } catch (Exception ex) {
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.id,t.code FROM doc_bank_card t WHERE t.type = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("储蓄卡(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void selectProxyTest2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<BankCardVO> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.user().name().like("小明");
                        bank_card.type().eq("储蓄卡");
                    })
                    .select(bank_card -> {
                        BankCardVOProxy r = new BankCardVOProxy();
                        r.selectAll(bank_card);//相当于是查询所有的bankCard字段
                        r.userName().set(bank_card.user().name());
                        r.bankName().set(bank_card.bank().name());
                        return r;
                    }).toList();
        } catch (Exception ex) {
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.id,t.uid,t.code,t.type,t1.name AS user_name,t2.name AS bank_name FROM doc_bank_card t LEFT JOIN doc_user t1 ON t1.id = t.uid LEFT JOIN doc_bank t2 ON t2.id = t.bank_id WHERE t1.name LIKE ? AND t.type = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明%(String),储蓄卡(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void selectProxyTest3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<DocUserVO> list = easyEntityQuery.queryable(DocUser.class)
                    .where(user -> {
                        user.name().like("小明");
                    })
                    .select(user -> {
                        DocUserVOProxy r = new DocUserVOProxy();
                        r.selectAll(user);//相当于是查询所有的bankCard字段
                        r.cardCount().set(user.bankCards().count());
                        return r;
                    }).toList();
        } catch (Exception ex) {
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.id,t.name,t.phone,t.age,(SELECT COUNT(*) FROM doc_bank_card t1 WHERE t1.uid = t.id) AS card_count FROM doc_user t WHERE t.name LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void selectProxyTest4() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<DocUserVO> list = easyEntityQuery.queryable(DocUser.class)
                    .where(user -> {
                        user.name().like("小明");
                    })
                    .select(user -> {
                        DocUserVOProxy r = new DocUserVOProxy();
                        r.selectAll(user);//相当于是查询所有的bankCard字段
                        Query<Long> longQuery = easyEntityQuery.queryable(DocBankCard.class)
                                .where(bank_card -> {
                                    bank_card.uid().eq(user.id());
                                }).selectCount();
                        r.cardCount().setSubQuery(longQuery);
                        return r;
                    }).toList();
        } catch (Exception ex) {
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.id,t.name,t.phone,t.age,(SELECT COUNT(*) FROM doc_bank_card t1 WHERE t1.uid = t.id) AS card_count FROM doc_user t WHERE t.name LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void selectProxyTest5() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<DocUserVO> list = easyEntityQuery.queryable(DocUser.class)
                    .where(user -> {
                        user.name().like("小明");
                    })
                    .select(user -> {
                        DocUserVOProxy r = new DocUserVOProxy();
                        r.selectAll(user);//相当于是查询所有的bankCard字段
                        r.cardCount().setSQL("IFNULL({0},1)", c -> c.expression(user.age()));
                        return r;
                    }).toList();
        } catch (Exception ex) {
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.id,t.name,t.phone,t.age,IFNULL(t.age,1) AS card_count FROM doc_user t WHERE t.name LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void selectClassTest1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<BankCardVO> list = easyEntityQuery.queryable(DocBankCard.class)
                    .leftJoin(DocUser.class, (bank_card, user) -> bank_card.uid().eq(user.id()))
                    .leftJoin(DocBank.class, (bank_card, user, bank) -> bank_card.bankId().eq(bank.id()))
                    .where((bank_card, user, bank) -> {
                        user.name().like("小明");
                        bank_card.type().eq("储蓄卡");
                    })
                    .select(BankCardVO.class, (bank_card, user, bank) -> Select.of(
                            //自动映射bank_card全属性等于select t.*但是以结果为主
                            bank_card.FETCHER.allFields(),
                            //添加FieldNameConstants,也可以使用方法引用BankCardVO::getUserName如果属性符合java的bean规范
                            user.name().as(BankCardVO.Fields.userName),
                            bank.name().as(BankCardVO.Fields.bankName)
                    )).toList();
        } catch (Exception ex) {
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.id,t.uid,t.code,t.type,t1.name AS user_name,t2.name AS bank_name FROM doc_bank_card t LEFT JOIN doc_user t1 ON t.uid = t1.id LEFT JOIN doc_bank t2 ON t.bank_id = t2.id WHERE t1.name LIKE ? AND t.type = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明%(String),储蓄卡(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void selectClassTest2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<BankCardVO> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.user().name().like("小明");
                        bank_card.type().eq("储蓄卡");
                    })
                    .select(BankCardVO.class, bank_card -> Select.of(
                            bank_card.FETCHER.allFields(),
                            bank_card.user().name().as(BankCardVO.Fields.userName),
                            bank_card.bank().name().as(BankCardVO.Fields.bankName)
                    )).toList();
        } catch (Exception ex) {
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.id,t.uid,t.code,t.type,t1.name AS user_name,t2.name AS bank_name FROM doc_bank_card t LEFT JOIN doc_user t1 ON t1.id = t.uid LEFT JOIN doc_bank t2 ON t2.id = t.bank_id WHERE t1.name LIKE ? AND t.type = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明%(String),储蓄卡(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void selectClassTest3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<DocUserVO> list = easyEntityQuery.queryable(DocUser.class)
                    .where(user -> {
                        user.name().like("小明");
                    })
                    .select(DocUserVO.class,user -> Select.of(
                            user.FETCHER.allFields(),
                            user.bankCards().count().as(DocUserVO.Fields.cardCount)
                    )).toList();
        } catch (Exception ex) {
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.id,t.name,t.phone,t.age,(SELECT COUNT(*) FROM doc_bank_card t1 WHERE t1.uid = t.id) AS card_count FROM doc_user t WHERE t.name LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void selectClassTest4() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<DocUserVO> list = easyEntityQuery.queryable(DocUser.class)
                    .where(user -> {
                        user.name().like("小明");
                    })
                    .select(DocUserVO.class,user -> Select.of(
                            user.FETCHER.allFields(),
                            user.expression().subQuery(()->{
                                return easyEntityQuery.queryable(DocBankCard.class)
                                        .where(bank_card -> {
                                            bank_card.uid().eq(user.id());
                                        }).selectCount();
                            }).as(DocUserVO.Fields.cardCount)
                    )).toList();
        } catch (Exception ex) {
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.id,t.name,t.phone,t.age,(SELECT COUNT(*) FROM doc_bank_card t1 WHERE t1.uid = t.id) AS card_count FROM doc_user t WHERE t.name LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void selectClassTest5() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<DocUserVO> list = easyEntityQuery.queryable(DocUser.class)
                    .where(user -> {
                        user.name().like("小明");
                    })
                    .select(DocUserVO.class,user -> Select.of(
                            user.FETCHER.allFields(),
                            user.expression().sqlSegment("IFNULL({0},1)", c -> c.expression(user.age()),Integer.class).as(DocUserVO.Fields.cardCount)
                    )).toList();
        } catch (Exception ex) {
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.id,t.name,t.phone,t.age,IFNULL(t.age,1) AS card_count FROM doc_user t WHERE t.name LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

}
