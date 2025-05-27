package com.easy.query.ksp

import com.easy.query.processor.helper.PropertyColumn

class KspPropertyColumn(
    sqlColumnName: String?,
    propertyType: String?,
    private val anyType: Boolean? = false
): PropertyColumn(sqlColumnName, propertyType, anyType) {

    override fun getPropertyTypeClass(includeProperty: Boolean): String {
        if (!includeProperty) {
            if ("SQLAnyTypeColumn" == sqlColumnName) {
                return "__cast(Object::class.java)"
            }
        }
        if (anyType != null && anyType) {
            return "__cast(Object::class.java)"
        }
        return "$propertyType::class.java"
    }
}