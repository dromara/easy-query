package com.easy.query.core.expression;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.util.Objects;

/**
 * create time 2025/3/11 21:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultRelationTableKey implements RelationTableKey {

    private final TableAvailable table;
    private final String property;

    public DefaultRelationTableKey(TableAvailable table, String property){
        this.table = table;
        this.property = property;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DefaultRelationTableKey that = (DefaultRelationTableKey) o;
        return Objects.equals(table, that.table) && Objects.equals(property, that.property);
    }

    @Override
    public int hashCode() {
        return Objects.hash(table, property);
    }
}
