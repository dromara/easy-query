package com.easy.query.test.kt

import com.easy.query.api4j.sql.SQLWherePredicate
import com.easy.query.core.expression.lambda.Property
import com.easy.query.test.BaseTest
import com.easy.query.test.entity.BlogEntity
import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime

class KtTest : BaseKtTest() {

    @Test
    fun query1() {
        var toSQL = easyKtQuery!!.queryable(TopicKt::class.java)
            .where {
                it.eq(TopicKt::id, 1)
            }.toSQL()
        Assert.assertEquals("SELECT `id`,`stars` FROM `t_topic` WHERE `id` = ?", toSQL);
    }

    @Test
    fun query2() {
        var toSQL = easyKtQuery!!.queryable(TopicKt::class.java)
            .where {
                it.eq(TopicKt::id, 1)
            }.selectCount().toSQL()
        Assert.assertEquals("SELECT COUNT(*) FROM `t_topic` WHERE `id` = ?", toSQL);
    }

    @Test
    fun query3() {
        var maxCreateTimeQuery = easyKtQuery!!.queryable(Topic1Kt::class.java)
            .select(LocalDateTime::class.java) {
                it.columnMax(Topic1Kt::createTime)
            }

        var toSQL = easyKtQuery!!.queryable(Topic1Kt::class.java)
            .where {
                it.eq(Topic1Kt::createTime, maxCreateTimeQuery)
            }.toSQL()
        Assert.assertEquals(
            "SELECT t.`id`,t.`stars`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` = (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1)",
            toSQL
        )
    }

    @Test
    fun query4() {
        var sql = easyKtQuery!!.queryable(BlogKtEntity::class.java)
            .where {
                it.eq(BlogKtEntity::id, "123")
            }
            .toSQL()
        Assert.assertEquals(
            "SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` = ?",
            sql
        )
        val blogEntity = easyKtQuery!!.queryable(BlogKtEntity::class.java)
            .where {
                it.eq(BlogKtEntity::id, "123")
            }.firstOrNull()
        Assert.assertNull(blogEntity)
    }

    @Test
    fun query5() {
        var sql = easyKtQuery!!.queryable(BlogKtEntity::class.java)
            .where {
                it.eq(BlogKtEntity::id, "123")
            }
            .select {
                it.column(BlogKtEntity::content);
                it.column(BlogKtEntity::title);
            }
            .toSQL()
        Assert.assertEquals(
            "SELECT `content`,`title` FROM `t_blog` WHERE `deleted` = ? AND `id` = ?",
            sql
        )
        val blogEntity = easyKtQuery!!.queryable(BlogKtEntity::class.java)
            .where {
                it.eq(BlogKtEntity::id, "1")
            }
            .select {
                it.column(BlogKtEntity::content);
                it.column(BlogKtEntity::title);
            }.firstOrNull()
        Assert.assertNotNull(blogEntity)
        Assert.assertNull(blogEntity.id);
        Assert.assertNotNull(blogEntity.title);
    }

    @Test
    fun query6() {
        var sql = easyKtQuery!!.queryable(BlogKtEntity::class.java)
            .where {
                it.eq(BlogKtEntity::id, "123")
            }
            .orderByAsc {
                it.column(BlogKtEntity::score)
            }
            .orderByDesc {
                it.column(BlogKtEntity::star)
            }
            .select {
                it.column(BlogKtEntity::content);
                it.column(BlogKtEntity::title);
            }
            .toSQL()
        Assert.assertEquals(
            "SELECT `content`,`title` FROM `t_blog` WHERE `deleted` = ? AND `id` = ? ORDER BY `score` ASC,`star` DESC",
            sql
        )
        val blogEntity = easyKtQuery!!.queryable(BlogKtEntity::class.java)
            .where {
                it.eq(BlogKtEntity::id, "1")
            }
            .orderByAsc {
                it.column(BlogKtEntity::score)
            }
            .orderByDesc {
                it.column(BlogKtEntity::star)
            }
            .select {
                it.column(BlogKtEntity::content);
                it.column(BlogKtEntity::title);
            }.firstOrNull()
        Assert.assertNotNull(blogEntity)
        Assert.assertNull(blogEntity.id);
        Assert.assertNotNull(blogEntity.title);
    }

    @Test
    fun query7() {
        var sql = easyKtQuery!!.queryable(BlogKtEntity::class.java)
            .where {
                it.eq(BlogKtEntity::id, "123")
            }
            .orderByAsc {
                it.column(BlogKtEntity::score)
            }
            .orderByDesc {
                it.column(BlogKtEntity::star)
            }
            .select(BlogKtEntity::class.java) {
                it.column(BlogKtEntity::content);
                it.column(BlogKtEntity::title);
                it.sqlFuncAs(it.fx().dateTimeFormat(BlogKtEntity::createTime),BlogKtEntity::id)
            }
            .toSQL()
        Assert.assertEquals(
            "SELECT t.`content`,t.`title`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s.%f') AS `id` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? ORDER BY t.`score` ASC,t.`star` DESC",
            sql
        )
        val blogEntity = easyKtQuery!!.queryable(BlogKtEntity::class.java)
            .where {
                it.eq(BlogKtEntity::id, "1")
            }
            .orderByAsc {
                it.column(BlogKtEntity::score)
            }
            .orderByDesc {
                it.column(BlogKtEntity::star)
            }
            .select(BlogKtEntity::class.java) {
                it.column(BlogKtEntity::content);
                it.column(BlogKtEntity::title);
                it.sqlFuncAs(it.fx().dateTimeFormat(BlogKtEntity::createTime),BlogKtEntity::id)
            }.firstOrNull()
        Assert.assertNotNull(blogEntity)
        Assert.assertNotNull(blogEntity.id);
        Assert.assertEquals("2020-01-02 01:01:01.000000",blogEntity.id);
        Assert.assertNotNull(blogEntity.title);
    }
}