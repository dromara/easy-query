package com.easy.query.test.kt

import org.junit.Assert
import org.junit.Test

class KtTest : BaseKtTest() {

    @Test
    fun query1(){
        var toSQL = easyKtQuery!!.queryable(TopicKt::class.java)
            .where {
                it.eq(TopicKt::id, 1)
            }.toSQL()
        Assert.assertEquals("SELECT `id`,`stars` FROM `t_topic` WHERE `id` = ?",toSQL);
    }
}