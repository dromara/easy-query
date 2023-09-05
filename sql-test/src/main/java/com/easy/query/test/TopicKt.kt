package com.easy.query.test

import com.easy.query.core.annotation.Column
import com.easy.query.core.annotation.EntityProxy
import com.easy.query.core.annotation.Table

@Table("t_topic")
 class TopicKt{
    @Column(primaryKey = true)
    var id:String?=null;
    var stars:Int?=null;
    override fun toString(): String {
        return "Topic(id=$id, stars=$stars)"
    }
    abstract class ITable {
        abstract var id:String
    }
}
