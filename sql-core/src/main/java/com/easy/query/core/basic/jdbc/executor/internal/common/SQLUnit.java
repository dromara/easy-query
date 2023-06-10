//package com.easy.query.core.basic.jdbc.executor.internal.common;
//
//import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
//
//import java.util.List;
//
///**
// * create time 2023/5/13 09:40
// * 文件说明
// *
// * @author xuejiaming
// */
//public class SQLUnit {
//    private final String sql;
//    private final List<SQLParameter> parameters;
//    private final List<Object> entities;
//    private final boolean fillAutoIncrement;
//
//    public SQLUnit(String sql, List<SQLParameter> parameters, List<Object> entities, boolean fillAutoIncrement){
//
//        this.sql = sql;
//        this.parameters = parameters;
//        this.entities = entities;
//        this.fillAutoIncrement = fillAutoIncrement;
//    }
//    public String getSQL() {
//        return sql;
//    }
//
//    public List<SQLParameter> getParameters() {
//        return parameters;
//    }
//
//    public List<Object> getEntities() {
//        return entities;
//    }
//
//    public boolean isFillAutoIncrement() {
//        return fillAutoIncrement;
//    }
//}
