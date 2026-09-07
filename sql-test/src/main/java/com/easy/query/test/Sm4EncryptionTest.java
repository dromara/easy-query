package com.easy.query.test;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.test.entity.SysUserSm4Encryption;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2026/9/6 22:15
 * SM4加密(hex存储)单元测试
 *
 * @author xuejiaming
 */
public class Sm4EncryptionTest extends BaseTest {

    @Test
    public void sm4Test() {
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(SysUserSm4Encryption.class));
        codeFirstCommand.executeWithTransaction(a -> a.commit());

        long l = easyEntityQuery.deletable(SysUserSm4Encryption.class)
                .allowDeleteStatement(true)
                .whereById("1").executeRows();
        SysUserSm4Encryption sysUserSm4Encryption = new SysUserSm4Encryption();
        sysUserSm4Encryption.setId("1");
        sysUserSm4Encryption.setName("name1");
        sysUserSm4Encryption.setPhone("12345678901");
        sysUserSm4Encryption.setAddress("浙江省绍兴市越城区城市广场旁边2-102");
        long l1 = easyEntityQuery.insertable(sysUserSm4Encryption).executeRows();
        Assert.assertEquals(1, l1);

        //数据库实际存储为16进制字符串
        String dbPhone = queryRawColumn("phone");
        Assert.assertNotNull(dbPhone);
        Assert.assertTrue(dbPhone.matches("^[0-9a-f]+$"));
        Assert.assertEquals(0, dbPhone.length() % 32);
        String dbAddress = queryRawColumn("address");
        Assert.assertNotNull(dbAddress);
        Assert.assertTrue(dbAddress.matches("^[0-9a-f]+$"));

        SysUserSm4Encryption sysUserSm4Encryption1 = easyEntityQuery.queryable(SysUserSm4Encryption.class).whereById("1").firstOrNull();
        Assert.assertNotNull(sysUserSm4Encryption1);
        Assert.assertEquals(sysUserSm4Encryption.getId(), sysUserSm4Encryption1.getId());
        Assert.assertEquals(sysUserSm4Encryption.getPhone(), sysUserSm4Encryption1.getPhone());
        Assert.assertEquals(sysUserSm4Encryption.getAddress(), sysUserSm4Encryption1.getAddress());

        SysUserSm4Encryption sysUserSm4Encryption2 = easyEntityQuery.queryable(SysUserSm4Encryption.class).where(o -> o.phone().eq("12345678901")).firstOrNull();
        Assert.assertNotNull(sysUserSm4Encryption2);
        Assert.assertEquals(sysUserSm4Encryption1.getId(), sysUserSm4Encryption2.getId());

        SysUserSm4Encryption sysUserSm4Encryption3 = easyEntityQuery.queryable(SysUserSm4Encryption.class).where(o -> o.phone().like("34567")).firstOrNull();
        Assert.assertNotNull(sysUserSm4Encryption3);
        SysUserSm4Encryption sysUserSm4Encryption4 = easyEntityQuery.queryable(SysUserSm4Encryption.class).where(o -> o.address().like("2-102")).firstOrNull();
        Assert.assertNotNull(sysUserSm4Encryption4);
        SysUserSm4Encryption sysUserSm4Encryption5 = easyEntityQuery.queryable(SysUserSm4Encryption.class).where(o -> o.address().likeMatchRight("2-102")).firstOrNull();
        Assert.assertNotNull(sysUserSm4Encryption5);
        SysUserSm4Encryption sysUserSm4Encryption6 = easyEntityQuery.queryable(SysUserSm4Encryption.class).where(o -> o.address().likeMatchLeft("浙江省绍兴市")).firstOrNull();
        Assert.assertNotNull(sysUserSm4Encryption6);
        SysUserSm4Encryption sysUserSm4Encryption7 = easyEntityQuery.queryable(SysUserSm4Encryption.class).where(o -> o.address().like("绍兴")).firstOrNull();
        Assert.assertNotNull(sysUserSm4Encryption7);
        //中文单字符不支持查询所以是空
        SysUserSm4Encryption sysUserSm4Encryption8 = easyEntityQuery.queryable(SysUserSm4Encryption.class).where(o -> o.address().like("绍")).firstOrNull();
        Assert.assertNull(sysUserSm4Encryption8);

        sysUserSm4Encryption7.setPhone("13232323321");
        long l2 = easyEntityQuery.updatable(sysUserSm4Encryption7).executeRows();
        Assert.assertEquals(1, l2);
        SysUserSm4Encryption sysUserSm4Encryption9 = easyEntityQuery.queryable(SysUserSm4Encryption.class).where(o -> o.phone().like("23233")).firstOrNull();
        Assert.assertNotNull(sysUserSm4Encryption9);
        long l3 = easyEntityQuery.updatable(SysUserSm4Encryption.class).setColumns(s -> s.phone().set("19876543210"))
                .where(o -> o.id().eq("1")).executeRows();
        Assert.assertEquals(1, l3);
        SysUserSm4Encryption sysUserSm4Encryption10 = easyEntityQuery.queryable(SysUserSm4Encryption.class).where(o -> o.phone().like("9876")).firstOrNull();
        Assert.assertNotNull(sysUserSm4Encryption10);
        SysUserSm4Encryption sysUserSm4Encryption11 = easyEntityQuery.queryable(SysUserSm4Encryption.class).where(o -> o.phone().like("987")).firstOrNull();
        Assert.assertNull(sysUserSm4Encryption11);

        String phone = easyEntityQuery.queryable(SysUserSm4Encryption.class).whereById("1").selectColumn(s -> s.phone()).singleNotNull();
        Assert.assertEquals("19876543210", phone);

        List<SysUserSm4Encryption> list = easyEntityQuery.queryable(SysUserSm4Encryption.class).where(o -> o.phone().likeMatchLeft("19876")).toList();
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("19876543210", list.get(0).getPhone());
    }

    private String queryRawColumn(String column) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select " + column + " from t_sys_user_sm4_encryption where id='1'");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
