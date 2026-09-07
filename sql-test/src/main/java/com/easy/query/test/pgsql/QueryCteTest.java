package com.easy.query.test.pgsql;

import com.easy.query.api.proxy.base.ClassProxy;
import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.api.proxy.base.MapTypeProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.key.MapKey;
import com.easy.query.api.proxy.key.MapKeys;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.proxy.columns.types.SQLBigDecimalTypeColumn;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.proxy.Draft2Proxy;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.grouping.Grouping1;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.dto.MyCategoryDTO;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.MyCategory;
import com.easy.query.test.entity.UUIDEntity;
import com.easy.query.test.entity.vo.MyCategoryVO2;
import com.easy.query.test.entity.vo.MyCategoryVO3;
import com.easy.query.test.entity.vo.MyCategoryVO4;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.view.TreeC;
import com.easy.query.test.pgsql.proxy.PgItemProxy;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * create time 2024/11/9 09:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryCteTest extends PgSQLBaseTest {
    @Test
    public void tree7() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MyCategoryVO3> list = entityQuery1.queryable(MyCategory.class)
                .where(m -> {
                    m.id().eq("1");
                })
                .asTreeCTE(op -> {
                    op.setDeepColumnName("deep1");
                })
                .selectAutoInclude(MyCategoryVO3.class).toTreeList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH RECURSIVE \"as_tree_cte\" AS ( (SELECT 0 AS \"deep1\",t1.\"id\",t1.\"parent_id\",t1.\"name\" FROM \"public\".\"category\" t1 WHERE t1.\"id\" = ?)  UNION ALL  (SELECT t2.\"deep1\" + 1 AS \"deep1\",t3.\"id\",t3.\"parent_id\",t3.\"name\" FROM \"as_tree_cte\" t2 INNER JOIN \"public\".\"category\" t3 ON t3.\"parent_id\" = t2.\"id\") ) SELECT t.\"id\",t.\"parent_id\",t.\"name\",t.\"deep1\" FROM \"as_tree_cte\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
}
