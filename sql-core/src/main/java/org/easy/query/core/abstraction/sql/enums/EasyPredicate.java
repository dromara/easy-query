package org.easy.query.core.abstraction.sql.enums;

/**
 * @FileName: EasyAggregate.java
 * @Description: 文件说明
 * @Date: 2023/2/18 22:18
 * @Created by xuejiaming
 */
public enum EasyPredicate implements IEasyPredicate {
    EQ("="),
    NE("<>"),
    /**
     * 大于 >
     */
    GT(">"),
    GE(">="),
    LT("<"),
    LE("<=");
    private final String predicate;

     EasyPredicate(String predicate){

        this.predicate = predicate;
    }

    public String getPredicate() {
        return predicate;
    }
}
