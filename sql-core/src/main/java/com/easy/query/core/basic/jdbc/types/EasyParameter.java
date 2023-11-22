package com.easy.query.core.basic.jdbc.types;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @FileName: EasyParameter.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:09
 * @author xuejiaming
 */
public class EasyParameter {
    private final PreparedStatement ps;
    private final List<SQLParameter> sqlParameters;
    private int index;

    public EasyParameter(PreparedStatement ps, List<SQLParameter> sqlParameters){

        this.ps = ps;
        this.sqlParameters = sqlParameters;
    }
    public void setIndex(int i){
        index=i;
    }
    public int getIndex(){
        return index+1;
    }
    public Object getValue(){
        return getSQLParameter().getValue();
    }
    public SQLParameter getSQLParameter(){
        return sqlParameters.get(index);
    }

    /**
     * value type
     * @return
     */
    public Class<?> getValueType(){
        Object value = getValue();
        if(value==null){
            return null;
        }
        return value.getClass();
    }

    public PreparedStatement getPs() {
        return ps;
    }
    public void setDefaultParameter() throws SQLException {
        ps.setObject(getIndex(),getValue());
    }
}
