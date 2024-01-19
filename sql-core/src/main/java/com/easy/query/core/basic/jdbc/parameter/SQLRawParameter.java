package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.enums.SQLLikeEnum;

/**
 * create time 2024/1/19 09:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLRawParameter{
    private final Object val;
    private final SQLLikeEnum sqlLike;
//    private SQLLikeEnum sqlLike;

    public SQLRawParameter(Object val, SQLLikeEnum sqlLike){

        this.val = val;
        this.sqlLike = sqlLike;
    }

    public Object getVal() {
        if(sqlLike!=null){
            switch (sqlLike) {
                case LIKE_PERCENT_RIGHT:
                    return val + "%";
                case LIKE_PERCENT_LEFT:
                    return "%" + val;
                default:
                    return "%" + val + "%";
            }
        }
        return val;
    }

//    public SQLLikeEnum getSqlLike() {
//        return sqlLike;
//    }
//
//    public void setSqlLike(SQLLikeEnum sqlLike) {
//        this.sqlLike = sqlLike;
//    }
}
