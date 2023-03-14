package com.easy.query.core.expression.segment.condition;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.util.StringUtil;

import java.util.*;

/**
 * @FileName: PredicateSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:05
 * @Created by xuejiaming
 */
public class AbstractPredicateSegment implements PredicateSegment {
    private List<PredicateSegment> children;
    private Predicate predicate;
    private Map<Class<?>, Set<String>> entityProperties;
    private final boolean root;

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
        this.root = root;
    }

    public AbstractPredicateSegment(Predicate predicate) {
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

//    @Override
//    public boolean contains(Class<?> entityClass,String propertyName) {
//        if (isPredicate()) {
//            return Objects.equals(predicate.getPropertyName(), propertyName);
//        } else {
//            for (PredicateSegment child : children) {
//                if(child.contains(propertyName)){
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    @Override
    public Map<Class<?>, Set<String>> getEntityProperties() {
        if(entityProperties==null){
            entityProperties= new HashMap<>();
            extractEntitySegment(entityProperties);
        }

        return entityProperties;
    }

    protected void extractEntitySegment(Map<Class<?>,Set<String>> entityProperties){
        if (isPredicate()) {
            Set<String> propertySet = entityProperties.computeIfAbsent(predicate.getTable().entityClass(), key -> new HashSet<>());
            propertySet.add(predicate.getPropertyName());
        } else {
            if(children!=null){
                for (PredicateSegment child : children) {
                    ((AbstractPredicateSegment)child).extractEntitySegment(entityProperties);
                }
            }
        }
    }

    @Override
    public void copyTo(PredicateSegment predicateSegment) {
        if (isPredicate()) {
            predicateSegment.setPredicate(predicate);
        } else {
            if(children!=null){

                for (PredicateSegment child : children) {
                    if (child instanceof AndPredicateSegment) {
                        AndPredicateSegment andPredicateSegment = new AndPredicateSegment();
                        predicateSegment.addPredicateSegment(andPredicateSegment);
                        child.copyTo(andPredicateSegment);
                    } else if (child instanceof OrPredicateSegment) {
                        OrPredicateSegment orPredicateSegment = new OrPredicateSegment();
                        predicateSegment.addPredicateSegment(orPredicateSegment);
                        child.copyTo(orPredicateSegment);
                    }
                }
            }
        }
    }

    @Override
    public String toSql() {
        if (isPredicate()) {
            return predicate.toSql();
        } else {
            StringBuilder sql = new StringBuilder();
            if(children!=null){
                boolean allAnd = true;
                boolean allOr = true;

                for (PredicateSegment child : children) {
                    if (child instanceof AndPredicateSegment) {
                        allOr = false;
                        if (sql.length() == 0) {
                            sql.append(child.toSql());
                        } else {
                            sql.append(AndPredicateSegment.AND).append(child.toSql());
                        }
                    } else if (child instanceof OrPredicateSegment) {
                        allAnd = false;
                        if (sql.length() == 0) {
                            sql.append(child.toSql());
                        } else {
                            sql.append(OrPredicateSegment.OR).append(child.toSql());
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
            }
            return StringUtil.EMPTY;
        }
    }

}
