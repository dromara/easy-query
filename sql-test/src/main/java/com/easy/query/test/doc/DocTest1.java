package com.easy.query.test.doc;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.BaseTest;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

/**
 * create time 2023/11/21 21:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class DocTest1 extends BaseTest {
    @Test
    public void test1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        BlogEntity blog = easyEntityQuery.queryable(BlogEntity.class)
                .findNotNull("1");
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void test2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        BlogEntity blog = easyEntityQuery.queryable(BlogEntity.class)
                .whereById("1")
                .firstNotNull();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();


    }


//    @Test
//    public void test3() {
//        MapKey<String> id = MapKeys.stringKey("id");
//        MapKey<String> phone = MapKeys.stringKey("phone");
//        MapKey<Integer> star = MapKeys.integerKey("star");
//        List<Draft3<String, Integer, LocalDateTime>> list = easyEntityQuery.queryable(SysUser.class)
//                .leftJoin(BlogEntity.class, (s, b2) -> s.phone().eq(b2.title()))
//                .where((s1, b2) -> {
//                    s1.departName().like("123");
//                    b2.star().gt(1);
//                })
//                .select((s1, b2) -> {
//                    MapTypeProxy map = new MapTypeProxy();
//                    map.put(id, s1.id());
//                    map.put(phone, s1.phone());
//                    map.put(star, b2.star());
//                    return map;
//                })
//                .leftJoin(Topic.class, (m, t) -> m.get(id).eq(t.id()))
//                .select((m, t) -> Select.DRAFT.of(
//                        m.get(phone),
//                        m.get(star),
//                        t.createTime()
//                )).toList();
//
//    }
//
}
