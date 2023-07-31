package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.util.EasyCollectionUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * create time 2023/7/31 21:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultJdbcResult<TR> implements JdbcResult<TR> {
    private final JdbcStreamResult<TR> jdbcStreamResult;

    public DefaultJdbcResult(JdbcStreamResult<TR> jdbcStreamResult){

        this.jdbcStreamResult = jdbcStreamResult;
    }

    @Override
    public JdbcStreamResult<TR> getJdbcStreamResult() {
        return jdbcStreamResult;
    }
    @Override
    public List<TR> toList(){
        try(JdbcStreamResult<TR> jdbcStreamResultSet =jdbcStreamResult){
            StreamIterable<TR> streamResult = jdbcStreamResultSet.getStreamIterable();
            return EasyCollectionUtil.newArrayList(streamResult);
        } catch (SQLException e) {
            throw new EasyQuerySQLCommandException(e);
        }
    }
}
