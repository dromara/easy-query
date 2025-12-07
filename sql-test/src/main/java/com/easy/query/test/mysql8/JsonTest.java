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
            topicJson.setExtraJson("{\"success\":true,\"code\":\"200\",\"age\":18}");
            topicJson.setExtraJsonArray("[{\"name\":\"Jack\",\"age\":18,\"success\":true,\"code\":\"200\"},{\"name\":\"Tom\",\"age\":20,\"success\":false,\"code\":\"200\"}]");
            easyEntityQuery.insertable(topicJson).executeRows();
        }
        {

            TopicJson topicJson = new TopicJson();
            topicJson.setId("2");
            topicJson.setName("名称2");
            topicJson.setExtraJson("{\"success\":false,\"code\":\"100\",\"msg\":\"存在错误信息\"}");
            topicJson.setExtraJsonArray("[{\"name\":\"JackSon\",\"age\":18,\"success\":true,\"code\":\"200\"},{\"name\":\"Tom\",\"age\":20,\"success\":false,\"code\":\"200\"}]");
            easyEntityQuery.insertable(topicJson).executeRows();
        }
    }

    @Test
    public void testJsonField(){
        List<TopicJson> list = easyEntityQuery.queryable(TopicJson.class)
                .where(t -> {
                    t.extraJson().asJSONObject().getField("code").eq("200");
                    t.extraJson().asJSONObject().getField("success").eq("true");
                }).toList();

        List<Draft1<Boolean>> list1 = easyEntityQuery.queryable(TopicJson.class)
                .where(t -> {
                }).select(t -> Select.DRAFT.of(
                        t.extraJson().asJSONObject().getBoolean("success")
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
                    t.extraJson().asJSONObject().getField("code").eq("200");
                }).toList();
        Assert.assertEquals(1,list.size());
        TopicJson topicJson = list.get(0);
        Map map = JSON.parseObject(topicJson.getExtraJson(), Map.class);
        Assert.assertEquals("200",map.get("code"));

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`name`,`extra_json`,`extra_json_array` FROM `t_test_json` WHERE JSON_UNQUOTE(JSON_EXTRACT(`extra_json`, '$.code')) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("200(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testJsonField4(){
        List<Draft1<Boolean>> list1 = easyEntityQuery.queryable(TopicJson.class)
                .where(t -> {
                    t.extraJson().asJSONObject().getBoolean("success").eq(true);
                }).select(t -> Select.DRAFT.of(
                        t.extraJson().asJSONObject().getBoolean("success")
                )).toList();
        Assert.assertEquals(1,list1.size());
        Draft1<Boolean> booleanDraft1 = list1.get(0);
        Assert.assertTrue(booleanDraft1.getValue1());
    }
    @Test
    public void testJsonField5(){
        List<TopicJson> ages = easyEntityQuery.queryable(TopicJson.class)
                .where(t -> {
                    t.extraJson().asJSONObject().getInteger("age").eq(18);
                }).toList();
        Assert.assertEquals(1,ages.size());
    }
    @Test
    public void testJsonField6(){
        List<TopicJson> ages = easyEntityQuery.queryable(TopicJson.class)
                .where(t -> {
                    t.extraJsonArray().asJSONArray().getJSONObject(0).getString("name").eq("Jack");
                }).toList();
        Assert.assertEquals(1,ages.size());
    }
}
