package com.easy.query.test.pgsql;

import com.easy.query.api.proxy.entity.insert.EntityInsertable;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.TopicAuto;
import com.easy.query.test.entity.proxy.TopicAutoProxy;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * create time 2023/7/6 22:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class InsertTest extends PgSQLBaseTest {
    @Test
    public void insertTest1() {
        try {
            TopicAuto topicAuto = new TopicAuto();
            topicAuto.setStars(999);
            topicAuto.setTitle("title" + 999);
            topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
            Assert.assertNull(topicAuto.getId());
            EntityInsertable<TopicAutoProxy, TopicAuto> insertable = entityQuery.insertable(topicAuto).onConflictThen(null).noInterceptor().asTable(o -> o + "aaa").asSchema("xxx");
            long l = insertable.executeRows();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
            Assert.assertEquals("INSERT INTO \"xxx\".\"t_topic_autoaaa\" (\"stars\",\"title\",\"create_time\") VALUES (?,?,?) ON CONFLICT DO NOTHING", sql);
        }
    }

    @Test
    public void insertTest2() {
        entityQuery.deletable(BlogEntity.class)
                .whereById("200")
                .executeRows();
        LocalDateTime begin = LocalDateTime.of(2000, 1, 1, 1, 1, 1);
        String indexStr = "200";
        BlogEntity blog = new BlogEntity();
        blog.setId(indexStr);
        blog.setCreateBy(indexStr);
        blog.setCreateTime(begin.plusDays(1));
        blog.setUpdateBy(indexStr);
        blog.setUpdateTime(begin.plusDays(1));
        blog.setTitle("title" + indexStr);
        blog.setContent("content" + indexStr);
        blog.setUrl("http://blog.easy-query.com/" + indexStr);
        blog.setStar(1);
        blog.setScore(new BigDecimal("1.2"));
        blog.setStatus(1 % 3 == 0 ? 0 : 1);
        blog.setOrder(new BigDecimal("1.2").multiply(BigDecimal.valueOf(1)));
        blog.setIsTop(1 % 2 == 0);
        blog.setTop(1 % 2 == 0);
        blog.setDeleted(false);
        long l = entityQuery.insertable(blog)
                .onConflictThen(s->s.FETCHER.allFields())
                .executeRows();
        Assert.assertEquals(1, l);
        blog.setContent("abc");
        long l2 = entityQuery.insertable(blog)
                .onConflictThen(s->s.FETCHER.allFields())
                .executeRows();
        Assert.assertEquals(1, l2);
        BlogEntity blogEntity = entityQuery.queryable(BlogEntity.class)
                .whereById("200")
                .firstNotNull("xxx");
        Assert.assertEquals("abc", blogEntity.getContent());
        entityQuery.deletable(BlogEntity.class)
                .whereById("200")
                .executeRows();
    }

    @Test
    public void insertDuplicateKeyIgnore2() {

        TopicAuto topicAuto = new TopicAuto();
        topicAuto.setStars(999);
        topicAuto.setTitle("title" + 999);
        topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
        Assert.assertNull(topicAuto.getId());
        EntityInsertable<TopicAutoProxy, TopicAuto> insertable = entityQuery.insertable(topicAuto).onConflictThen(null);
        String sql = insertable.toSQL(topicAuto);
        Assert.assertEquals("INSERT INTO \"t_topic_auto\" (\"stars\",\"title\",\"create_time\") VALUES (?,?,?) ON CONFLICT DO NOTHING", sql);
    }

    @Test
    public void insertDuplicateKeyUpdate2() {

        TopicAuto topicAuto = new TopicAuto();
        topicAuto.setStars(999);
        topicAuto.setTitle("title" + 999);
        topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
        Assert.assertNull(topicAuto.getId());
        EntityInsertable<TopicAutoProxy, TopicAuto> insertable = entityQuery.insertable(topicAuto)
                .onConflictThen(s -> s.FETCHER.stars().createTime(), s -> s.title());
        String sql = insertable.toSQL(topicAuto);
        Assert.assertEquals("INSERT INTO \"t_topic_auto\" (\"stars\",\"title\",\"create_time\") VALUES (?,?,?) ON CONFLICT (\"title\") DO UPDATE SET \"stars\" = EXCLUDED.\"stars\", \"create_time\" = EXCLUDED.\"create_time\"", sql);
    }

    @Test
    public void insertDuplicateKeyUpdate3() {

        TopicAuto topicAuto = new TopicAuto();
        topicAuto.setStars(999);
        topicAuto.setTitle("title" + 999);
        topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
        Assert.assertNull(topicAuto.getId());
        EntityInsertable<TopicAutoProxy, TopicAuto> insertable = entityQuery.insertable(topicAuto).onConflictThen(s->s.FETCHER.allFields(), s -> s.title());
        String sql = insertable.toSQL(topicAuto);
        Assert.assertEquals("INSERT INTO \"t_topic_auto\" (\"stars\",\"title\",\"create_time\") VALUES (?,?,?) ON CONFLICT (\"title\") DO UPDATE SET \"stars\" = EXCLUDED.\"stars\", \"create_time\" = EXCLUDED.\"create_time\"", sql);

        {

            com.easy.query.api.proxy.entity.insert.EntityInsertable<TopicAutoProxy, TopicAuto> topicAutoProxyTopicAutoEntityInsertable =
                    entityQuery.insertable(topicAuto)
                            .onConflictThen(o -> o.FETCHER.id().title(),o -> o.FETCHER.id().stars());
            String sql1 = topicAutoProxyTopicAutoEntityInsertable.toSQL(topicAuto);

            Assert.assertEquals("INSERT INTO \"t_topic_auto\" (\"stars\",\"title\",\"create_time\") VALUES (?,?,?) ON CONFLICT (\"stars\") DO UPDATE SET \"title\" = EXCLUDED.\"title\"", sql1);

        }
        {

            com.easy.query.api.proxy.entity.insert.EntityInsertable<TopicAutoProxy, TopicAuto> topicAutoProxyTopicAutoEntityInsertable =
                    entityQuery.insertable(topicAuto)
                            .onConflictThen(o -> o.FETCHER.allFields(),o -> o.FETCHER.id().stars());
            String sql1 = topicAutoProxyTopicAutoEntityInsertable.toSQL(topicAuto);

            Assert.assertEquals("INSERT INTO \"t_topic_auto\" (\"stars\",\"title\",\"create_time\") VALUES (?,?,?) ON CONFLICT (\"stars\") DO UPDATE SET \"title\" = EXCLUDED.\"title\", \"create_time\" = EXCLUDED.\"create_time\"", sql1);

//            long l = entityQuery.insertable(topicAuto)
//                    .asTable("xxxaaa")
//                    .onConflictDoUpdate(o -> o.FETCHER.id().stars(), o -> o.FETCHER.allFields())
//                    .executeRows();
        }
    }
}
