package com.easy.query.test.pgsql;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.test.mysql8.entity.TopicJson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/12/8 08:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class JsonTest extends PgSQLBaseTest{
    @Before
    public void before(){
        DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(PgTopicJson.class));
        codeFirstCommand.executeWithTransaction(s->s.commit());
        {
            entityQuery.deletable(PgTopicJson.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
        }
        {

            PgTopicJson topicJson = new PgTopicJson();
            topicJson.setId("1");
            topicJson.setName("名称");
            topicJson.setExtraJson("{\"success\":true,\"code\":\"200\",\"age\":18}");
            topicJson.setExtraJsonArray("[{\"name\":\"Jack\",\"age\":18,\"success\":true,\"code\":\"200\"},{\"name\":\"Tom\",\"age\":20,\"success\":false,\"code\":\"200\"}]");
            entityQuery.insertable(topicJson).executeRows();
        }
        {

            PgTopicJson topicJson = new PgTopicJson();
            topicJson.setId("2");
            topicJson.setName("名称2");
            topicJson.setExtraJson("{\"success\":false,\"code\":\"100\",\"msg\":\"存在错误信息\"}");
            topicJson.setExtraJsonArray("[{\"name\":\"JackSon\",\"age\":18,\"success\":true,\"code\":\"200\"},{\"name\":\"Tom\",\"age\":20,\"success\":false,\"code\":\"200\"}]");
            entityQuery.insertable(topicJson).executeRows();
        }
    }
    @Test
    public void testJsonField4(){
        List<Draft1<Boolean>> list1 = entityQuery.queryable(PgTopicJson.class)
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
        List<PgTopicJson> ages = entityQuery.queryable(PgTopicJson.class)
                .where(t -> {
                    t.extraJson().asJSONObject().getInteger("age").eq(18);
                }).toList();
        Assert.assertEquals(1,ages.size());
    }
    @Test
    public void testJsonField6(){
        List<PgTopicJson> ages = entityQuery.queryable(PgTopicJson.class)
                .where(t -> {
                    t.extraJsonArray().asJSONArray().getJSONObject(0).getString("name").eq("Jack");
                }).toList();
        Assert.assertEquals(1,ages.size());
    }
}
