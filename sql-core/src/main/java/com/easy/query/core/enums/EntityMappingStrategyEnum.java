package com.easy.query.core.enums;


import com.easy.query.core.basic.entity.ColumnEntityMappingRule;
import com.easy.query.core.basic.entity.PropertyEntityMappingRule;
import com.easy.query.core.basic.entity.PropertyFirstEntityMappingRule;
import com.easy.query.core.basic.entity.TryColumnAndPropertyEntityMappingRule;

/**
 * create time 2025/1/6 19:51 <br/>
 * 对象映射策略 <a href="http://www.easy-query.com/easy-query-doc/startup/mapping-rule.html">对象映射规则</a> <br/>
 * 如果你无法理解那么请在新项目的时候选择 PROPERTY_FIRST❗️<br/>
 * 如果你无法理解那么请在新项目的时候选择 PROPERTY_FIRST❗️<br/>
 * 如果你无法理解那么请在新项目的时候选择 PROPERTY_FIRST❗️<br/>
 *
 * @author xuejiaming
 * @author link2fun add doc
 */
public enum EntityMappingStrategyEnum {
    /**
     * 默认策略:表示实体的对应的列名和映射对象的列名相同能映射
     *
     * @see ColumnEntityMappingRule
     */
    COLUMN_ONLY,

    /**
     * 表示实体的对应的属性名和映射对象的属性名相同能映射
     *
     * @see PropertyEntityMappingRule
     */
    PROPERTY_ONLY,

    /**
     * 表示先使用实体对应的列名匹配如果无法映射则使用属性名进行匹配
     *
     * @see TryColumnAndPropertyEntityMappingRule
     */
    COLUMN_AND_PROPERTY,

    /**
     * 表示实体的对应的属性名和映射对象的属性名相同能映射,和PROPERTY_ONLY的区别就是如果是函数式片段没有property通过alias来匹配
     *
     * @see PropertyFirstEntityMappingRule
     */
    PROPERTY_FIRST;
}
