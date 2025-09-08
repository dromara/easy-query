package com.easy.query.core.expression.predicate;

import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.util.Objects;

/**
 * create time 2025/9/8 16:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class SmartPredicateItem {
    public final TableAvailable table;
    public final String property;

    public SmartPredicateItem(TableAvailable table, String property){
        this.table = table;
        this.property = property;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        SmartPredicateItem that = (SmartPredicateItem) object;
        return Objects.equals(table, that.table) && Objects.equals(property, that.property);
    }

    @Override
    public int hashCode() {
        return Objects.hash(table, property);
    }
}
