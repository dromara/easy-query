package com.easy.query.test.kt

import com.easy.query.core.enums.SQLRangeEnum
import com.sun.org.apache.xpath.internal.operations.String
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal
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
                it.sqlFuncAs(it.fx().dateTimeFormat(BlogKtEntity::createTime), BlogKtEntity::id)
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
                it.sqlFuncAs(it.fx().dateTimeFormat(BlogKtEntity::createTime), BlogKtEntity::id)
            }.firstOrNull()
        Assert.assertNotNull(blogEntity)
        Assert.assertNotNull(blogEntity.id);
        Assert.assertEquals("2020-01-02 01:01:01.000000", blogEntity.id);
        Assert.assertNotNull(blogEntity.title);
    }

    @Test
    fun query8() {


        val strings =  arrayOf("Hello", "kotlin");
        var sql = easyKtQuery!!.queryable(BlogKtEntity::class.java)
            .where {
                it.eq(BlogKtEntity::id, "123");
                it.eq(false, BlogKtEntity::id, "123");
                it.ne(BlogKtEntity::id, "123");
                it.ne(true, BlogKtEntity::id, "123");
                it.ge(BlogKtEntity::createTime, LocalDateTime.now())
                it.ge(false,BlogKtEntity::createTime, LocalDateTime.now())
                it.gt(BlogKtEntity::score, BigDecimal.ZERO)
                it.gt(true,BlogKtEntity::score, BigDecimal.ZERO)
                it.lt(BlogKtEntity::createTime, LocalDateTime.now())
                it.lt(true,BlogKtEntity::createTime, LocalDateTime.now())
                it.le(BlogKtEntity::score, BigDecimal.ZERO)
                it.le(false,BlogKtEntity::score, BigDecimal.ZERO)
                it.like(BlogKtEntity::title, "123")
                it.like(true,BlogKtEntity::title, "123")
                it.notLike(BlogKtEntity::title, "123")
                it.notLike(false,BlogKtEntity::title, "123")
                it.likeMatchLeft(BlogKtEntity::title, "123")
                it.likeMatchLeft(true,BlogKtEntity::title, "123")
                it.notLikeMatchLeft(BlogKtEntity::title, "123")
                it.notLikeMatchLeft(false,BlogKtEntity::title, "123")
                it.likeMatchRight(BlogKtEntity::title, "123")
                it.likeMatchRight(true,BlogKtEntity::title, "123")
                it.notLikeMatchRight(BlogKtEntity::title, "123")
                it.notLikeMatchRight(false,BlogKtEntity::title, "123")
                it.isNull(BlogKtEntity::id)
                it.isNull(false,BlogKtEntity::id)
                it.isNotNull(BlogKtEntity::id)
                it.isNotNull(false,BlogKtEntity::id)
                it.`in`(BlogKtEntity::id,strings)
                it.`in`(false,BlogKtEntity::id,strings)
                it.notIn(BlogKtEntity::id,strings)
                it.notIn(false,BlogKtEntity::id,strings)
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
                it.columnAs(BlogKtEntity::title,BlogKtEntity::content);
                it.sqlFuncAs(it.fx().dateTimeFormat(BlogKtEntity::createTime), BlogKtEntity::id)
            }
            .toSQL()
        Assert.assertEquals(
            "SELECT t.`content`,t.`title`,t.`title` AS `content`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s.%f') AS `id` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? AND t.`id` <> ? AND t.`id` <> ? AND t.`create_time` >= ? AND t.`score` > ? AND t.`score` > ? AND t.`create_time` < ? AND t.`create_time` < ? AND t.`score` <= ? AND t.`title` LIKE ? AND t.`title` LIKE ? AND t.`title` NOT LIKE ? AND t.`title` LIKE ? AND t.`title` LIKE ? AND t.`title` NOT LIKE ? AND t.`title` LIKE ? AND t.`title` LIKE ? AND t.`title` NOT LIKE ? AND t.`id` IS NULL AND t.`id` IS NOT NULL AND t.`id` IN (?,?) AND t.`id` NOT IN (?,?) ORDER BY t.`score` ASC,t.`star` DESC",
            sql
        )
    }
    @Test
    fun query9() {

        var sql = easyKtQuery!!.queryable(BlogKtEntity::class.java)
            .where {
                it.rangeClosed(BlogKtEntity::createTime, LocalDateTime.now(), LocalDateTime.now())
                it.rangeClosed(BlogKtEntity::createTime,false, LocalDateTime.now(),true, LocalDateTime.now())
                it.rangeClosed(false,BlogKtEntity::createTime,false, LocalDateTime.now(),true, LocalDateTime.now())


                it.rangeOpen(BlogKtEntity::createTime, LocalDateTime.now(), LocalDateTime.now())
                it.rangeOpen(BlogKtEntity::createTime,false, LocalDateTime.now(),true, LocalDateTime.now())
                it.rangeOpen(false,BlogKtEntity::createTime,false, LocalDateTime.now(),true, LocalDateTime.now())


                it.rangeOpenClosed(BlogKtEntity::createTime, LocalDateTime.now(), LocalDateTime.now())
                it.rangeOpenClosed(BlogKtEntity::createTime,false, LocalDateTime.now(),true, LocalDateTime.now())
                it.rangeOpenClosed(false,BlogKtEntity::createTime,false, LocalDateTime.now(),true, LocalDateTime.now())

                it.rangeClosedOpen(BlogKtEntity::createTime, LocalDateTime.now(), LocalDateTime.now())
                it.rangeClosedOpen(BlogKtEntity::createTime,false, LocalDateTime.now(),true, LocalDateTime.now())
                it.rangeClosedOpen(false,BlogKtEntity::createTime,false, LocalDateTime.now(),true, LocalDateTime.now())

                it.range(true,BlogKtEntity::createTime,true, LocalDateTime.now(),false, LocalDateTime.now(),SQLRangeEnum.CLOSED)

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
                it.columnAs(BlogKtEntity::title,BlogKtEntity::content);
                it.sqlFuncAs(it.fx().dateTimeFormat(BlogKtEntity::createTime), BlogKtEntity::id)
            }
            .toSQL()
        Assert.assertEquals(
            "SELECT t.`content`,t.`title`,t.`title` AS `content`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s.%f') AS `id` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`create_time` >= ? AND t.`create_time` <= ? AND t.`create_time` <= ? AND t.`create_time` > ? AND t.`create_time` < ? AND t.`create_time` < ? AND t.`create_time` > ? AND t.`create_time` <= ? AND t.`create_time` <= ? AND t.`create_time` >= ? AND t.`create_time` < ? AND t.`create_time` < ? AND t.`create_time` >= ? ORDER BY t.`score` ASC,t.`star` DESC",
            sql
        )
    }
}