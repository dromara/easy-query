package com.easy.query.test.pgsql;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import org.postgresql.util.PGobject;

import java.sql.SQLException;

/**
 * @FileName: BigDecimalTypeHandler.java
 * @Description: 文件说明
 * create time 2023/2/17 21:21
 * @author xuejiaming
 */
public class PgSQLJsonbTypeHandler implements JdbcTypeHandler {
    public static final PgSQLJsonbTypeHandler INSTANCE=new PgSQLJsonbTypeHandler();
    @Override
    public Object getValue(JdbcProperty jdbcProperty, StreamResultSet streamResultSet) throws SQLException {
        return streamResultSet.getString(jdbcProperty.getJdbcIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        PGobject pGobject = new PGobject();
        pGobject.setType("jsonb");
        pGobject.setValue((String) parameter.getValue());
        parameter.getPs().setObject(parameter.getIndex(),pGobject);
    }
}
