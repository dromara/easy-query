package com.easy.query.test;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.test.entity.SysUserVersionLong;
import com.easy.query.test.entity.SysUserVersionLongLogicDel;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.function.Supplier;

/**
 * create time 2023/4/8 13:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class VersionTest extends BaseTest {
    @Test
    public void test1() {
        String id = "1";
        long l1 = easyQuery.deletable(SysUserVersionLong.class)
                .whereById(id).ignoreVersion().executeRows();
        SysUserVersionLong sysUserVersionLong = new SysUserVersionLong();
        sysUserVersionLong.setId(id);
        sysUserVersionLong.setCreateTime(LocalDateTime.now());
        sysUserVersionLong.setVersion(1L);
        sysUserVersionLong.setUsername("username" + id);
        sysUserVersionLong.setPhone("13232323232");
        sysUserVersionLong.setIdCard("0000000000");
        sysUserVersionLong.setAddress("浙江省绍兴市越城区城市广场");
        long l = easyQuery.insertable(sysUserVersionLong).executeRows();
        Assert.assertEquals(1, l);
        SysUserVersionLong sysUserVersionLong1 = easyQuery.queryable(SysUserVersionLong.class)
                .whereById(id).firstOrNull();
        Assert.assertNotNull(sysUserVersionLong1);
        long l2 = easyQuery.updatable(sysUserVersionLong1).executeRows();
        Assert.assertEquals(1, l2);
    }

    @Test
    public void test2() {
        String id = "2";
        long l1 = easyQuery.deletable(SysUserVersionLong.class)
                .whereById(id).ignoreVersion().executeRows();
        SysUserVersionLong sysUserVersionLong = new SysUserVersionLong();
        sysUserVersionLong.setId(id);
        sysUserVersionLong.setCreateTime(LocalDateTime.now());
        sysUserVersionLong.setVersion(1L);
        sysUserVersionLong.setUsername("username" + id);
        sysUserVersionLong.setPhone("13232323232");
        sysUserVersionLong.setIdCard("0000000000");
        sysUserVersionLong.setAddress("浙江省绍兴市越城区城市广场");
        long l = easyQuery.insertable(sysUserVersionLong).executeRows();
        Assert.assertEquals(1, l);

        {
            try {

                long l2 = easyQuery.updatable(SysUserVersionLong.class)
                        .set(SysUserVersionLong::getPhone, "123")
                        .whereById(id)
                        .executeRows();
                Assert.assertEquals(1, l2);
            } catch (Exception ex) {
                Assert.assertTrue(ex instanceof EasyQueryInvalidOperationException);
            }
        }
        {

            long l2 = easyQuery.updatable(SysUserVersionLong.class)
                    .set(SysUserVersionLong::getPhone, "123")
                    .whereById(id)
                    .ignoreVersion()
                    .executeRows();
            Assert.assertEquals(1, l2);
        }
        long l3 = easyQuery.updatable(SysUserVersionLong.class)
                .set(SysUserVersionLong::getPhone, "123")
                .where(o -> o.eq(SysUserVersionLong::getId, id))
                .withVersion(1L)
                .executeRows();
        Assert.assertEquals(1, l3);
        long l4 = easyQuery.updatable(SysUserVersionLong.class)
                .set(SysUserVersionLong::getPhone, "123")
                .withVersion(2L)
                .where(o -> o.eq(SysUserVersionLong::getId, id))
                .executeRows();
        Assert.assertEquals(1, l4);
    }

    @Test
    public void test3() {
        String id = "2";
        String s = easyQuery.updatable(SysUserVersionLong.class)
                .set(SysUserVersionLong::getPhone, "123")
                .whereById(id).ignoreVersion()
                .toSQL();
        Assert.assertEquals("UPDATE `t_sys_user_version` SET `phone` = ? WHERE `id` = ?", s);
        String s1 = easyQuery.updatable(SysUserVersionLong.class)
                .set(SysUserVersionLong::getPhone, "123").ignoreVersion()
                .where(o -> o.eq(SysUserVersionLong::getId, id)).toSQL();
        Assert.assertEquals("UPDATE `t_sys_user_version` SET `phone` = ? WHERE `id` = ?", s1);
        String s2 = easyQuery.updatable(SysUserVersionLong.class)
                .set(SysUserVersionLong::getPhone, "123")
                .withVersion(1L)
                .ignoreVersion(false)
                .where(o -> o.eq(SysUserVersionLong::getId, id))
                .toSQL();
        Assert.assertEquals("UPDATE `t_sys_user_version` SET `phone` = ?,`version` = ? WHERE `version` = ? AND `id` = ?", s2);
    }

    @Test
    public void test4() {
        String id = "4";
        long l1 = easyQuery.deletable(SysUserVersionLongLogicDel.class)
                .whereById(id).ignoreVersion().disableLogicDelete().executeRows();
        SysUserVersionLongLogicDel sysUserVersionLongLogicDel = new SysUserVersionLongLogicDel();
        sysUserVersionLongLogicDel.setId(id);
        sysUserVersionLongLogicDel.setCreateTime(LocalDateTime.now());
        sysUserVersionLongLogicDel.setVersion(1L);
        sysUserVersionLongLogicDel.setUsername("username" + id);
        sysUserVersionLongLogicDel.setPhone("13232323232");
        sysUserVersionLongLogicDel.setIdCard("0000000000");
        sysUserVersionLongLogicDel.setAddress("浙江省绍兴市越城区城市广场");
        sysUserVersionLongLogicDel.setDeleted(false);
        long l = easyQuery.insertable(sysUserVersionLongLogicDel).executeRows();
        Assert.assertEquals(1, l);
        long l2 = easyQuery.deletable(sysUserVersionLongLogicDel).executeRows();
        Assert.assertEquals(1, l2);
    }

    @Test
    public void test5() {
        String id = "5";
        long l1 = easyQuery.deletable(SysUserVersionLongLogicDel.class)
                .whereById(id).ignoreVersion().disableLogicDelete().executeRows();
        SysUserVersionLongLogicDel sysUserVersionLongLogicDel = new SysUserVersionLongLogicDel();
        sysUserVersionLongLogicDel.setId(id);
        sysUserVersionLongLogicDel.setCreateTime(LocalDateTime.now());
        sysUserVersionLongLogicDel.setVersion(1L);
        sysUserVersionLongLogicDel.setUsername("username" + id);
        sysUserVersionLongLogicDel.setPhone("13232323232");
        sysUserVersionLongLogicDel.setIdCard("0000000000");
        sysUserVersionLongLogicDel.setAddress("浙江省绍兴市越城区城市广场");
        sysUserVersionLongLogicDel.setDeleted(false);
        long l = easyQuery.insertable(sysUserVersionLongLogicDel).executeRows();
        Assert.assertEquals(1, l);
        long l2 = easyQuery.deletable(SysUserVersionLongLogicDel.class)
                .ignoreVersion(false)
                .withVersion(1L)
                .whereById(id).executeRows();
        Assert.assertEquals(1, l2);
    }

    @Test
    public void test6() {
        String id = "56";
        String sql = easyQuery.deletable(SysUserVersionLongLogicDel.class)
                .withVersion(1L)
                .whereById(id).toSQL();
        Assert.assertEquals("UPDATE `t_sys_user_version_del` SET `deleted` = ?,`version` = ? WHERE `deleted` = ? AND `version` = ? AND `id` = ?", sql);
    }
    @Test
    public void test7() {


        Supplier<Exception> f = () -> {
            try {
                SysUserVersionLongLogicDel sysUserVersionLongLogicDel = new SysUserVersionLongLogicDel();
                sysUserVersionLongLogicDel.setId("1x1");
                sysUserVersionLongLogicDel.setUsername("aaaa");
                easyQuery.updatable(sysUserVersionLongLogicDel)
                        .asTable("aaa")
                        .ignoreVersion()
                        .executeRows();
            }catch (Exception ex){
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
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("UPDATE `aaa` SET `username` = ?,`phone` = ?,`id_card` = ?,`address` = ?,`create_time` = ? WHERE `deleted` = ? AND `id` = ?",sql);

    }
    @Test
    public void test8() {


        Supplier<Exception> f = () -> {
            try {
                SysUserVersionLongLogicDel sysUserVersionLongLogicDel = new SysUserVersionLongLogicDel();
                sysUserVersionLongLogicDel.setId("1x1");
                sysUserVersionLongLogicDel.setUsername("aaaa");
                easyQuery.updatable(sysUserVersionLongLogicDel)
                        .executeRows();
            }catch (Exception ex){
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof NullPointerException);

    }
}
