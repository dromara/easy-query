package com.easy.query.test

import com.easy.query.api4kt.client.DefaultEasyKtQuery
import com.easy.query.api4kt.sql.SQLKtWherePredicate
import com.easy.query.test.entity.BlogEntity
import java.time.LocalDateTime

fun main(args: Array<String>){

    val defaultEasyKtQuery = DefaultEasyKtQuery(null)
    var toList = defaultEasyKtQuery.queryable(TopicKt::class.java)
        .where {
            it.eq(TopicKt::id, 1)
        }.toList()
}