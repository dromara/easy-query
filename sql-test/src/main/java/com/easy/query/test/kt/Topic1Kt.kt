package com.easy.query.test.kt

import com.easy.query.core.annotation.Column
import com.easy.query.core.annotation.EntityProxy
import com.easy.query.core.annotation.Table
import java.time.LocalDateTime

@Table("t_topic")
@EntityProxy
 class Topic1Kt{
    @Column(primaryKey = true)
    var id:String?=null;
    var stars:Int?=null;
    var createTime:LocalDateTime?=null;
}
