package com.easy.query.test.pgsql;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.MyCategory;
import com.easy.query.test.entity.vo.MyCategoryVO3;
import com.easy.query.test.enums.NamedEnum;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2026/4/20 11:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class NamedEnumTest extends PgSQLBaseTest {
    @Test
    public void test1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        NamedEnumEntity namedEnumEntity = entityQuery.queryable(NamedEnumEntity.class)
                .where(n -> {
                    n.type().eq(NamedEnum.BOOK);
                }).singleNotNull();
        Assert.assertEquals(NamedEnum.BOOK, namedEnumEntity.getType());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT \"id\",\"name\",\"type\" FROM \"t_named_enum\" WHERE \"type\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("BOOK(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();


    }
}
