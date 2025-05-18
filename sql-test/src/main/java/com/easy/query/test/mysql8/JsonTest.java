package com.easy.query.test.mysql8;

import com.alibaba.fastjson2.JSON;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.TopicJson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * create time 2025/3/4 08:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class JsonTest extends BaseTest{
    @Before
    public void before(){
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(TopicJson.class));
        codeFirstCommand.executeWithTransaction(s->s.commit());
        {
            easyEntityQuery.deletable(TopicJson.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
        }
        {

            TopicJson topicJson = new TopicJson();
            topicJson.setId("1");
            topicJson.setName("名称");
            topicJson.setExtraJson("{\"success\":true,\"code\":\"200\"}");
            easyEntityQuery.insertable(topicJson).executeRows();
        }
        {

            TopicJson topicJson = new TopicJson();
            topicJson.setId("2");
            topicJson.setName("名称2");
            topicJson.setExtraJson("{\"success\":false,\"code\":\"100\",\"msg\":\"存在错误信息\"}");
            easyEntityQuery.insertable(topicJson).executeRows();
        }
    }

    @Test
    public void testJsonField(){
//        List<Map<String, Object>> maps = easyEntityQuery.sqlQueryMap("SELECT `id`,`name`,`extra_json` FROM `t_test_json` WHERE (`extra_json`->\"$.success\") =  CAST(? AS JSON)",Collections.singletonList(true));
//        List<Map<String, Object>> maps1 = easyEntityQuery.sqlQueryMap("SELECT `id`,`name`,`extra_json` FROM `t_test_json` WHERE (`extra_json`->\"$.success\") =  ?",Collections.singletonList("true"));
//        System.out.println(maps);
//        System.out.println(maps1);
        List<TopicJson> list = easyEntityQuery.queryable(TopicJson.class)
                .where(t -> {
//                    t.extraJson().asAny().getJsonField("success").eq(true);
//                    t.id().eq(t.expression().sqlSegment(" CAST({0} AS JSON)",c->c.format(true)));
//                    t.extraJson().asAny().getJsonField("success").eq(t.expression().sqlSegment(" CAST({0} AS JSON)",c->c.value("true")));
                    t.extraJson().asJsonMap().getField("code").eq("200");
                    t.extraJson().asJsonMap().getField("success").eq("true");
                }).toList();

        List<Draft1<Boolean>> list1 = easyEntityQuery.queryable(TopicJson.class)
                .where(t -> {
                }).select(t -> Select.DRAFT.of(
                        t.extraJson().asAny().getField("success", Boolean.class)
                )).toList();
       System.out.println(list);
        System.out.println(list1);
    }

    @Test
    public void testJsonField2(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<TopicJson> list = easyEntityQuery.queryable(TopicJson.class)
                .where(t -> {
                    t.extraJson().asJsonMap().getField("code").eq("200");
                }).toList();
        Assert.assertEquals(1,list.size());
        TopicJson topicJson = list.get(0);
        Map map = JSON.parseObject(topicJson.getExtraJson(), Map.class);
        Assert.assertEquals("200",map.get("code"));

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`name`,`extra_json` FROM `t_test_json` WHERE (`extra_json`->'$.code') = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("200(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testJsonField4(){
        List<Draft1<Boolean>> list1 = easyEntityQuery.queryable(TopicJson.class)
                .where(t -> {
                    t.extraJson().asJsonMap().getBooleanField("success").eq(true);
                }).select(t -> Select.DRAFT.of(
                        t.extraJson().asAny().getField("success", Boolean.class)
                )).toList();
        Assert.assertEquals(1,list1.size());
        Draft1<Boolean> booleanDraft1 = list1.get(0);
        Assert.assertTrue(booleanDraft1.getValue1());
    }
}
