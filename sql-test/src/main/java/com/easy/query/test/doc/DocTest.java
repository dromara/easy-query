package com.easy.query.test.doc;

import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.test.BaseTest;
import com.easy.query.test.doc.dto.SysUserQueryRequest;
import com.easy.query.test.doc.entity.SysUser;
import com.easy.query.test.doc.entity.proxy.SysUserProxy;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

/**
 * create time 2023/11/21 21:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class DocTest extends BaseTest {
    @Test
    public void test1() {
        SysUserQueryRequest sysUserQueryRequest = new SysUserQueryRequest();
        sysUserQueryRequest.setName("小明");
        sysUserQueryRequest.setCreateTimeBegin(LocalDateTime.now().plusDays(-10));
        sysUserQueryRequest.setCreateTimeEnd(LocalDateTime.now());
        sysUserQueryRequest.setPhone("180");


        SysUserProxy sysUserTable = SysUserProxy.createTable();
        {

            Supplier<Exception> f = () -> {
                try {
                    EasyPageResult<SysUser> pageResult = easyProxyQuery.queryable(sysUserTable)
                            .whereObject(sysUserQueryRequest)
                            .toPageResult(1, 10);
                } catch (Exception ex) {
                    return ex;
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNotNull(exception);
            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
            Assert.assertEquals("SELECT COUNT(*) FROM `t_sys_user` WHERE `name` LIKE ? AND `phone` LIKE ? AND `create_time` >= ? AND `create_time` <= ?", easyQuerySQLStatementException.getSQL());

        }
        {

            Supplier<Exception> f = () -> {
                try {
                    List<SysUser> users = easyProxyQuery.queryable(sysUserTable)
                            .whereObject(sysUserQueryRequest)
                            .limit(0, 10).toList();
                } catch (Exception ex) {
                    return ex;
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNotNull(exception);
            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
            Assert.assertEquals("SELECT `id`,`name`,`account`,`depart_name`,`phone`,`create_time` FROM `t_sys_user` WHERE `name` LIKE ? AND `phone` LIKE ? AND `create_time` >= ? AND `create_time` <= ? LIMIT 10", easyQuerySQLStatementException.getSQL());

        }
        {



            Supplier<Exception> f = () -> {
                try {
                    List<SysUser> pageResult = easyProxyQuery.queryable(sysUserTable)
                            .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                            .where(o -> o
                                    .like(sysUserTable.name(), sysUserQueryRequest.getName())
                                    .like(sysUserTable.account(), sysUserQueryRequest.getAccount())
                                    .like(sysUserTable.phone(), sysUserQueryRequest.getPhone())
                                    .like(sysUserTable.departName(), sysUserQueryRequest.getDepartName())
                                    .rangeClosed(sysUserTable.createTime(), sysUserQueryRequest.getCreateTimeBegin(), sysUserQueryRequest.getCreateTimeEnd())
                            )
                            .toList();
                } catch (Exception ex) {
                    return ex;
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNotNull(exception);
            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
            Assert.assertEquals("SELECT `id`,`name`,`account`,`depart_name`,`phone`,`create_time` FROM `t_sys_user` WHERE `name` LIKE ? AND `phone` LIKE ? AND `create_time` >= ? AND `create_time` <= ?", easyQuerySQLStatementException.getSQL());

        }
        {


            Supplier<Exception> f = () -> {
                try {

                    List<SysUser> pageResult = easyProxyQuery.queryable(sysUserTable)
                            .where(o -> o
                                    .like(EasyStringUtil.isNotBlank(sysUserQueryRequest.getName()),sysUserTable.name(), sysUserQueryRequest.getName())
                                    .like(EasyStringUtil.isNotBlank(sysUserQueryRequest.getAccount()),sysUserTable.account(), sysUserQueryRequest.getAccount())
                                    .like(EasyStringUtil.isNotBlank(sysUserQueryRequest.getPhone()),sysUserTable.phone(), sysUserQueryRequest.getPhone())
                                    .like(EasyStringUtil.isNotBlank(sysUserQueryRequest.getDepartName()),sysUserTable.departName(), sysUserQueryRequest.getDepartName())
                                    .rangeClosed(sysUserTable.createTime(),sysUserQueryRequest.getCreateTimeBegin()!=null, sysUserQueryRequest.getCreateTimeBegin(),sysUserQueryRequest.getCreateTimeEnd()!=null, sysUserQueryRequest.getCreateTimeEnd())
                            )
                            .toList();
                } catch (Exception ex) {
                    return ex;
                }
                return null;
            };
            Exception exception = f.get();
            Assert.assertNotNull(exception);
            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
            Assert.assertEquals("SELECT `id`,`name`,`account`,`depart_name`,`phone`,`create_time` FROM `t_sys_user` WHERE `name` LIKE ? AND `phone` LIKE ? AND `create_time` >= ? AND `create_time` <= ?", easyQuerySQLStatementException.getSQL());

        }
    }
}
