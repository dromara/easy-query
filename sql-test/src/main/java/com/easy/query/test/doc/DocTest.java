package com.easy.query.test.doc;

import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.test.BaseTest;
import com.easy.query.test.doc.dto.SysUserQueryRequest;
import com.easy.query.test.doc.entity.SysUser;
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

        {

            Supplier<Exception> f = () -> {
                try {
                    EasyPageResult<SysUser> pageResult = entityQuery.queryable(SysUser.class)
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
                    List<SysUser> users = entityQuery.queryable(SysUser.class)
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
// .like(sysUserTable.name(), sysUserQueryRequest.getName())
//                .like(sysUserTable.account(), sysUserQueryRequest.getAccount())
//                .like(sysUserTable.phone(), sysUserQueryRequest.getPhone())
//                .like(sysUserTable.departName(), sysUserQueryRequest.getDepartName())
//                .rangeClosed(sysUserTable.createTime(), sysUserQueryRequest.getCreateTimeBegin(), sysUserQueryRequest.getCreateTimeEnd())


            Supplier<Exception> f = () -> {
                try {
                    List<SysUser> pageResult = entityQuery.queryable(SysUser.class)
                            .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                            .where(o -> {
                                o.name().like(sysUserQueryRequest.getName());
                                o.account().like(sysUserQueryRequest.getAccount());
                                o.phone().like(sysUserQueryRequest.getPhone());
                                o.departName().like(sysUserQueryRequest.getDepartName());
                                o.createTime().rangeClosed(sysUserQueryRequest.getCreateTimeBegin(), sysUserQueryRequest.getCreateTimeEnd());
                            })
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

                    List<SysUser> pageResult = entityQuery.queryable(SysUser.class)
                            .where(o -> {
                                o.name().like(EasyStringUtil.isNotBlank(sysUserQueryRequest.getName()),sysUserQueryRequest.getName());
                                o.account().like(EasyStringUtil.isNotBlank(sysUserQueryRequest.getAccount()),sysUserQueryRequest.getAccount());
                                o.phone().like(EasyStringUtil.isNotBlank(sysUserQueryRequest.getPhone()),sysUserQueryRequest.getPhone());
                                o.departName().like(EasyStringUtil.isNotBlank(sysUserQueryRequest.getDepartName()),sysUserQueryRequest.getDepartName());
                                o.createTime().rangeClosed(sysUserQueryRequest.getCreateTimeBegin() != null,sysUserQueryRequest.getCreateTimeBegin(),sysUserQueryRequest.getCreateTimeEnd() != null, sysUserQueryRequest.getCreateTimeEnd());
                            })
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

                    List<SysUser> pageResult = entityQuery.queryable(SysUser.class)
                            .where(EasyStringUtil.isNotBlank(sysUserQueryRequest.getName()),o->o.name().like(sysUserQueryRequest.getName()))
                            .where(EasyStringUtil.isNotBlank(sysUserQueryRequest.getAccount()),o->o.account().like(sysUserQueryRequest.getAccount()))
                            .where(EasyStringUtil.isNotBlank(sysUserQueryRequest.getPhone()),o->o.phone().like(sysUserQueryRequest.getPhone()))
                            .where(sysUserQueryRequest.getCreateTimeBegin() != null,o->o.createTime().ge(sysUserQueryRequest.getCreateTimeBegin()))
                            .where(sysUserQueryRequest.getCreateTimeEnd() != null,o->o.createTime().le(sysUserQueryRequest.getCreateTimeEnd()))
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
