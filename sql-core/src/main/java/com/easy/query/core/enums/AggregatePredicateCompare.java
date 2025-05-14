package com.easy.query.core.enums;

/**
 * @FileName: EasyAggregate.java
 * @Description: 文件说明
 * create time 2023/2/18 22:18
 * @author xuejiaming
 */
public enum AggregatePredicateCompare implements SQLPredicateCompare {
    EQ("="),
    NE("<>"),
    /**
     * 大于 >
     */
    GT(">"),
    GE(">="),
    LT("<"),
    LE("<=");
    private final String sql;

     AggregatePredicateCompare(String sql){

        this.sql = sql;
    }

    @Override
    public String getSQL() {
        return sql;
    }

    @Override
    public SQLPredicateCompare toReverse() {
        switch (this){
            case EQ:return NE;
            case NE:return EQ;
            case GT:return LE;
            case LE:return GT;
            case GE:return LT;
            case LT:return GE;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isLike() {
        return false;
    }
}
