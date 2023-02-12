package org.easy.query.mysql.base;

import org.easy.query.core.enums.SelectTableInfoTypeEnum;
import org.easy.query.core.impl.AbstractSelect1;
import org.easy.query.core.impl.AbstractSelect2;
import org.easy.query.mysql.util.MySQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: MySqlSelect1.java
 * @Description: 文件说明
 * @Date: 2023/2/7 13:04
 * @Created by xuejiaming
 */
public class MySQLSelect1<T1> extends AbstractSelect1<T1> {

    private final MySQLSelectContext selectContext;

    public MySQLSelect1(Class<T1> t1Class, MySQLSelectContext selectContext) {
        super(t1Class, selectContext);
        this.selectContext = selectContext;
    }

    @Override
    protected <T2> AbstractSelect2<T1, T2> createSelect2(Class<T2> joinClass, SelectTableInfoTypeEnum selectTableInfoType) {
        return new MySQLSelect2<>(t1Class,joinClass,selectContext,selectTableInfoType);
    }


    @Override
    public int count() {
        return 0;
    }

    @Override
    public boolean any() {
        return false;
    }

    @Override
    protected List<T1> toInternalList(String columns) {
        String s = toSql(columns);
        System.out.println(s);

        Connection conn = selectContext.getConn();
        try {
            try(PreparedStatement preparedStatement = conn.prepareStatement(s)){

                int paramSize = selectContext.getParams().size();
                for (int i = 0; i < paramSize; i++) {
                    preparedStatement.setObject(i+1,selectContext.getParams().get(i));
                }
                try(ResultSet rs = preparedStatement.executeQuery()){
                    while(rs.next()){
                        System.out.println(rs.getObject(1)+"---"+rs.getObject(2));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public String toSql(String columns) {
        return MySQLUtil.toSql(selectContext,columns);
    }


}
