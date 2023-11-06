package com.easy.query.test.kt

import com.easy.query.core.annotation.Column
import com.easy.query.core.annotation.EntityProxy
import com.easy.query.core.annotation.Table

@Table("t_company")
 class CompanyKt{
    @Column(primaryKey = true)
    var id:String?=null;
    var name:String?=null;
    var address:AddressKt?=null;
}
