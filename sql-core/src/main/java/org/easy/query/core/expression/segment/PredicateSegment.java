package org.easy.query.core.expression.segment;

import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.expression.segment.predicate.node.Predicate;
import org.easy.query.core.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: PredicateSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:05
 * @Created by xuejiaming
 */
public class PredicateSegment implements SqlSegment {
    private List<PredicateSegment> children;
    private Predicate predicate;
    private final boolean root;

    public boolean isEmpty() {
        return this.predicate == null && this.children == null;
    }
    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public PredicateSegment() {
        this(false);
    }

    public PredicateSegment(boolean root) {
        this.root = root;
    }

    public PredicateSegment(Predicate predicate) {
        setPredicate(predicate);
        this.root = false;
    }

    private boolean isPredicate() {
        return predicate != null && children == null;
    }

    public void setPredicate(Predicate predicate) {
        if (!isEmpty()) {
            throw new EasyQueryException("sql segment cant set predicate.");
        }
        this.predicate = predicate;
    }

    public void addPredicateSegment(PredicateSegment predicateSegment) {
        if (isPredicate()) {
            throw new EasyQueryException("sql segment is predicate can't add predicate segment");
        }
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(predicateSegment);
    }

    @Override
    public String getSql() {
        if (isPredicate()) {
            return predicate.getSql();
        } else {
            StringBuilder sql = new StringBuilder();
            boolean allAnd = true;
            boolean allOr = true;
            for (PredicateSegment child : children) {
                if (child instanceof AndPredicateSegment) {
                    allOr = false;
                    if (sql.length() == 0) {
                        sql.append(child.getSql());
                    } else {
                        sql.append(AndPredicateSegment.AND).append(child.getSql());
                    }
                } else if (child instanceof OrPredicateSegment) {
                    allAnd = false;
                    if (sql.length() == 0) {
                        sql.append(child.getSql());
                    } else {
                        sql.append(OrPredicateSegment.OR).append(child.getSql());
                    }
                }
            }
            if (sql.length() != 0) {
                if (root && (allAnd || allOr)) {
                    return sql.toString();
                } else {
                    return "(" + sql + ")";
                }
            }
            return StringUtil.EMPTY;
        }
    }

}
