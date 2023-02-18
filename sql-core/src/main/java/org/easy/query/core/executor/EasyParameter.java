package org.easy.query.core.executor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @FileName: EasyParameter.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:09
 * @Created by xuejiaming
 */
public class EasyParameter {
    private final PreparedStatement ps;
    private final List<Object> values;
    private int index;

    public EasyParameter(PreparedStatement ps, List<Object> values){

        this.ps = ps;
        this.values = values;
    }
    public void setIndex(int i){
        index=i;
    }
    public int getIndex(){
        return index+1;
    }
    public Object getValue(){
        return values.get(index);
    }

    public PreparedStatement getPs() {
        return ps;
    }
    public void setDefaultParameter() throws SQLException {
        ps.setObject(getIndex(),getValue());
    }
}
