package com.easy.query.test.pgsql;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.sql.Select;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/12/8 08:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class Json2Test extends PgSQLBaseTest {
    @Before
    public void before() {
        DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(PgTopicJson2.class));
        codeFirstCommand.executeWithTransaction(s -> s.commit());
        {
            entityQuery.deletable(PgTopicJson2.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
        }
        {

            PgTopicJson2 topicJson = new PgTopicJson2();
            topicJson.setId("1");
            topicJson.setName("名称");
            {

                TopicExtraJson topicExtraJson = new TopicExtraJson();
                topicExtraJson.setSuccess(true);
                topicExtraJson.setCode("200");
                topicExtraJson.setAge(18);
                topicJson.setExtraJson(topicExtraJson);
            }
            ArrayList<TopicExtraJson> topicExtraJsons = new ArrayList<>();
            {

                TopicExtraJson topicExtraJson = new TopicExtraJson();
                topicExtraJson.setSuccess(true);
                topicExtraJson.setName("Jack");
                topicExtraJson.setCode("202");
                topicExtraJson.setAge(18);
                topicExtraJsons.add(topicExtraJson);
            }
            {

                TopicExtraJson topicExtraJson = new TopicExtraJson();
                topicExtraJson.setSuccess(false);
                topicExtraJson.setName("Tom");
                topicExtraJson.setCode("200");
                topicExtraJson.setAge(20);
                topicExtraJsons.add(topicExtraJson);
            }
            topicJson.setExtraJsonArray(topicExtraJsons);
            entityQuery.insertable(topicJson).executeRows();
        }
    }

    @Test
    public void testJsonField4() {
        List<Draft1<Boolean>> list1 = entityQuery.queryable(PgTopicJson2.class)
                .where(t -> {
                    t.extraJson().asJSONObject().getBoolean("success").eq(true);
                }).select(t -> Select.DRAFT.of(
                        t.extraJson().asJSONObject().getBoolean("success")
                )).toList();
        Assert.assertEquals(1, list1.size());
        Draft1<Boolean> booleanDraft1 = list1.get(0);
        Assert.assertTrue(booleanDraft1.getValue1());
    }

    @Test
    public void testJsonField5() {
        List<PgTopicJson2> ages = entityQuery.queryable(PgTopicJson2.class)
                .where(t -> {
                    t.extraJson().asJSONObject().getInteger("age").eq(18);
                }).toList();
        Assert.assertEquals(1, ages.size());
    }

    @Test
    public void testJsonField6() {
        List<PgTopicJson2> ages = entityQuery.queryable(PgTopicJson2.class)
                .where(t -> {
                    t.extraJsonArray().asJSONArray().getJSONObject(0).getString("name").eq("Jack");
                }).toList();
        Assert.assertEquals(1, ages.size());
    }
}
