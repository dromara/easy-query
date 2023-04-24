package com.easy.query.core.expression.segment.condition;

import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.sharding.route.RoutePredicateExpression;
import com.easy.query.core.util.StringUtil;

import java.util.*;

/**
 * @author xuejiaming
 * @FileName: PredicateSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:05
 */
public abstract class AbstractPredicateSegment implements PredicateSegment,ShardingPredicateSegment {
    protected List<PredicateSegment> children;
    protected Predicate predicate;
    protected final boolean root;

    @Override
    public List<PredicateSegment> getChildren() {
        return children;
    }

    @Override
    public Predicate getPredicate() {
        return predicate;
    }

    @Override
    public boolean isRoot() {
        return root;
    }

    public boolean isEmpty() {
        return this.predicate == null && this.children == null;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public AbstractPredicateSegment() {
        this(false);
    }

    public AbstractPredicateSegment(boolean root) {
        this(null, root);
    }

    public AbstractPredicateSegment(Predicate predicate) {
       this(predicate,false);
    }
    public AbstractPredicateSegment(Predicate predicate, boolean root) {
        setPredicate(predicate);
        this.root = root;
    }

    @Override
    public boolean isPredicate() {
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
    public boolean containsOnce(Class<?> entityClass, String propertyName) {
        if (isPredicate()) {
            return Objects.equals(predicate.getTable().getEntityClass(), entityClass) && Objects.equals(predicate.getPropertyName(), propertyName);
        } else {
            if (children != null) {
                for (PredicateSegment child : children) {
                    if (child.containsOnce(entityClass, propertyName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public PredicateIndex buildPredicateIndex() {
        EasyPredicateContext easyPredicateContext = new EasyPredicateContext();
        buildPredicateIndex(easyPredicateContext);
        return easyPredicateContext;
    }


    protected void buildPredicateIndex(EasyPredicateContext easyPredicateContext) {
        if (isPredicate()) {
            easyPredicateContext.add(predicate);
        } else {
            if (children != null) {
                for (PredicateSegment child : children) {
                    ((AbstractPredicateSegment) child).buildPredicateIndex(easyPredicateContext);
                }
            }
        }
    }

    @Override
    public void copyTo(PredicateSegment predicateSegment) {
        if (isPredicate()) {
            predicateSegment.setPredicate(predicate);
        } else {
            if (children != null) {

                for (PredicateSegment child : children) {
                    boolean isRoot = child.isRoot();
                    if (child instanceof AndPredicateSegment) {
                        AndPredicateSegment andPredicateSegment = new AndPredicateSegment(isRoot);
                        predicateSegment.addPredicateSegment(andPredicateSegment);
                        child.copyTo(andPredicateSegment);
                    } else if (child instanceof OrPredicateSegment) {
                        OrPredicateSegment orPredicateSegment = new OrPredicateSegment(isRoot);
                        predicateSegment.addPredicateSegment(orPredicateSegment);
                        child.copyTo(orPredicateSegment);
                    }
                }
            }
        }
    }

    @Override
    public String toSql(SqlParameterCollector sqlParameterCollector) {
        if (isPredicate()) {
            return predicate.toSql(sqlParameterCollector);
        } else {
            if (children != null) {
                StringBuilder sql = new StringBuilder();
                boolean allAnd = true;
                boolean allOr = true;

                for (PredicateSegment child : children) {
                    if (child instanceof AndPredicateSegment) {
                        allOr = false;
                        if (sql.length() != 0) {
                            sql.append(AndPredicateSegment.AND);
                        }
                        sql.append(child.toSql(sqlParameterCollector));
                    } else if (child instanceof OrPredicateSegment) {
                        allAnd = false;
                        if (sql.length() != 0) {
                            sql.append(OrPredicateSegment.OR);
                        }
                        sql.append(child.toSql(sqlParameterCollector));
                    }
                }
                if (sql.length() != 0) {
                    if (root && (allAnd || allOr)) {
                        return sql.toString();
                    } else {
                        return "(" + sql + ")";
                    }
                }
            }
            return StringUtil.EMPTY;
        }
    }

}
