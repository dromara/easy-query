package com.easy.query.test.kt

import com.easy.query.core.annotation.Column
import com.easy.query.core.annotation.EntityProxy
import com.easy.query.core.annotation.Table

 class AddressKt{
    @Column(primaryKey = true)
    var province:String?=null;
    var city:String?=null;
    var area:String?=null;
}
