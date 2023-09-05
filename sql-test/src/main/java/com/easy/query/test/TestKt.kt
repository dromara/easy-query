package com.easy.query.test

import com.easy.query.api4kt.client.DefaultEasyKtQuery
import com.easy.query.test.kt.TopicKt

fun main(args: Array<String>){
    var kMutableProperty1 = TopicKt::id
    val defaultEasyKtQuery = DefaultEasyKtQuery(null)
    var toList = defaultEasyKtQuery.queryable(TopicKt::class.java)
        .where {
            it.eq(TopicKt::id, 1)
        }.toList()
}