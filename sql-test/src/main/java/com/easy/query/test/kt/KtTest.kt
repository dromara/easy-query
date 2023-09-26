package com.easy.query.test.kt

import com.easy.query.api4j.sql.SQLColumnAsSelector
import com.easy.query.api4j.sql.SQLWherePredicate
import com.easy.query.core.expression.lambda.Property
import com.easy.query.test.BaseTest
import com.easy.query.test.entity.Topic
import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime
import kotlin.math.max

class KtTest : BaseKtTest() {

    @Test
    fun query1(){
        var toSQL = easyKtQuery!!.queryable(TopicKt::class.java)
            .where {
                it.eq(TopicKt::id, 1)
            }.toSQL()
        Assert.assertEquals("SELECT `id`,`stars` FROM `t_topic` WHERE `id` = ?",toSQL);
    }
    @Test
    fun query2(){
        var toSQL = easyKtQuery!!.queryable(TopicKt::class.java)
            .where {
                it.eq(TopicKt::id, 1)
            }.selectCount().toSQL()
        Assert.assertEquals("SELECT COUNT(*) FROM `t_topic` WHERE `id` = ?",toSQL);
    }
    @Test
    fun query3(){
        var maxCreateTimeQuery = easyKtQuery!!.queryable(Topic1Kt::class.java)
            .select(LocalDateTime::class.java) {
                it.columnMax(Topic1Kt::createTime)
            }

        var toSQL = easyKtQuery!!.queryable(Topic1Kt::class.java)
            .where {
                it.eq(Topic1Kt::createTime, maxCreateTimeQuery)
            }.toSQL()
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` = (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1)",toSQL)


    }
}