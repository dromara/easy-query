package com.easy.query.test;

import com.easy.query.test.BaseTest;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.SysUserEncryption;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2023/3/25 10:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class EncryptionTest extends BaseTest {

    @Test
    public void phoneTest(){
        SysUser sysUser = easyEntityQuery.queryable(SysUser.class).whereById("1").firstOrNull();
        Assert.assertNotNull(sysUser);
        Assert.assertEquals("18888888"+sysUser.getId()+sysUser.getId(),sysUser.getPhone());
        Assert.assertEquals("333333333333333"+sysUser.getId()+sysUser.getId(),sysUser.getIdCard());
        Assert.assertEquals(sysUser.getPhone()+sysUser.getIdCard(),sysUser.getAddress());
    }
    @Test
    public void phoneTest1(){
        List<SysUser> sysUsers = easyEntityQuery.queryable(SysUser.class).toList();
        Assert.assertEquals(100,sysUsers.size());
        for (SysUser sysUser : sysUsers) {
            Assert.assertEquals("18888888"+sysUser.getId()+sysUser.getId(),sysUser.getPhone());
            Assert.assertEquals("333333333333333"+sysUser.getId()+sysUser.getId(),sysUser.getIdCard());
            Assert.assertEquals(sysUser.getPhone()+sysUser.getIdCard(),sysUser.getAddress());
        }
    }
    @Test
    public void phoneTest2(){
        List<SysUser> sysUsers = easyEntityQuery.queryable(SysUser.class).where(o->o.phone().likeMatchRight("8800")).toList();
        Assert.assertEquals(1,sysUsers.size());
        for (SysUser sysUser : sysUsers) {
            Assert.assertEquals("18888888"+sysUser.getId()+sysUser.getId(),sysUser.getPhone());
            Assert.assertEquals("333333333333333"+sysUser.getId()+sysUser.getId(),sysUser.getIdCard());
            Assert.assertEquals(sysUser.getPhone()+sysUser.getIdCard(),sysUser.getAddress());
        }
    }
    @Test
    public void phoneTest3(){
        List<SysUser> sysUsers = easyEntityQuery.queryable(SysUser.class).where(o->o.phone().likeMatchLeft("188888881")).toList();
        Assert.assertEquals(11,sysUsers.size());
        for (SysUser sysUser : sysUsers) {
            Assert.assertEquals("18888888"+sysUser.getId()+sysUser.getId(),sysUser.getPhone());
            Assert.assertEquals("333333333333333"+sysUser.getId()+sysUser.getId(),sysUser.getIdCard());
            Assert.assertEquals(sysUser.getPhone()+sysUser.getIdCard(),sysUser.getAddress());
        }
    }
    @Test
    public void phoneTest4(){
        List<SysUser> sysUsers = easyEntityQuery.queryable(SysUser.class).where(o->o.phone().like("1888888812")).toList();
        Assert.assertEquals(1,sysUsers.size());
        for (SysUser sysUser : sysUsers) {
            Assert.assertEquals("18888888"+sysUser.getId()+sysUser.getId(),sysUser.getPhone());
            Assert.assertEquals("333333333333333"+sysUser.getId()+sysUser.getId(),sysUser.getIdCard());
            Assert.assertEquals(sysUser.getPhone()+sysUser.getIdCard(),sysUser.getAddress());
        }
    }

    @Test
    public void test5(){
        long l = easyEntityQuery.deletable(SysUserEncryption.class)
                .whereById("1").executeRows();
        SysUserEncryption sysUserEncryption = new SysUserEncryption();
        sysUserEncryption.setId("1");
        sysUserEncryption.setName("name1");
        sysUserEncryption.setPhoneNotSupportLike("12345678901");
        sysUserEncryption.setAddressNotSupportLike("浙江省绍兴市越城区城市广场旁边2-102");
        long l1 = easyEntityQuery.insertable(sysUserEncryption).executeRows();
        Assert.assertEquals(1,l1);
        SysUserEncryption sysUserEncryption1 = easyEntityQuery.queryable(SysUserEncryption.class).whereById("1").firstOrNull();
        Assert.assertNotNull(sysUserEncryption1);
        Assert.assertEquals(sysUserEncryption.getId(),sysUserEncryption1.getId());
        Assert.assertEquals(sysUserEncryption.getPhoneNotSupportLike(),sysUserEncryption1.getPhoneNotSupportLike());
        Assert.assertEquals(sysUserEncryption.getAddressNotSupportLike(),sysUserEncryption1.getAddressNotSupportLike());
        SysUserEncryption sysUserEncryption2 = easyEntityQuery.queryable(SysUserEncryption.class).where(o -> o.phoneNotSupportLike().eq("12345678901")).firstOrNull();
        Assert.assertNotNull(sysUserEncryption2);
        Assert.assertEquals(sysUserEncryption1.getId(),sysUserEncryption2.getId());
        SysUserEncryption sysUserEncryption3 = easyEntityQuery.queryable(SysUserEncryption.class).where(o -> o.phoneNotSupportLike().like( "12345678901")).firstOrNull();
        Assert.assertNull(sysUserEncryption3);
    }
    @Test
    public void test6(){
        long l = easyEntityQuery.deletable(SysUserEncryption.class)
                .whereById("2").executeRows();
        SysUserEncryption sysUserEncryption = new SysUserEncryption();
        sysUserEncryption.setId("2");
        sysUserEncryption.setName("name2");
        sysUserEncryption.setPhoneSupportLike("12345678901");
        sysUserEncryption.setAddressSupportLike("浙江省绍兴市越城区城市广场旁边2-102");
        long l1 = easyEntityQuery.insertable(sysUserEncryption).executeRows();
        Assert.assertEquals(1,l1);
        SysUserEncryption sysUserEncryption1 = easyEntityQuery.queryable(SysUserEncryption.class).whereById("2").firstOrNull();
        Assert.assertNotNull(sysUserEncryption1);
        Assert.assertEquals(sysUserEncryption.getId(),sysUserEncryption1.getId());
        Assert.assertEquals(sysUserEncryption.getPhoneSupportLike(),sysUserEncryption1.getPhoneSupportLike());
        Assert.assertEquals(sysUserEncryption.getAddressSupportLike(),sysUserEncryption1.getAddressSupportLike());
        SysUserEncryption sysUserEncryption2 = easyEntityQuery.queryable(SysUserEncryption.class).where(o -> o.phoneSupportLike().eq( "12345678901")).firstOrNull();
        Assert.assertNotNull(sysUserEncryption2);
        Assert.assertEquals(sysUserEncryption1.getId(),sysUserEncryption2.getId());
        SysUserEncryption sysUserEncryption3 = easyEntityQuery.queryable(SysUserEncryption.class).where(o -> o.phoneSupportLike().like( "34567")).firstOrNull();
        Assert.assertNotNull(sysUserEncryption3);
        SysUserEncryption sysUserEncryption4 = easyEntityQuery.queryable(SysUserEncryption.class).where(o -> o.addressSupportLike().like( "2-102")).firstOrNull();
        Assert.assertNotNull(sysUserEncryption4);
        SysUserEncryption sysUserEncryption5 = easyEntityQuery.queryable(SysUserEncryption.class).where(o -> o.addressSupportLike().likeMatchRight("2-102")).firstOrNull();
        Assert.assertNotNull(sysUserEncryption5);
        SysUserEncryption sysUserEncryption6 = easyEntityQuery.queryable(SysUserEncryption.class).where(o -> o.addressSupportLike().likeMatchLeft("浙江省绍兴市")).firstOrNull();
        Assert.assertNotNull(sysUserEncryption6);
        SysUserEncryption sysUserEncryption7 = easyEntityQuery.queryable(SysUserEncryption.class).where(o -> o.addressSupportLike().like( "绍兴")).firstOrNull();
        Assert.assertNotNull(sysUserEncryption7);
        //中文单字符不支持查询所以是空
        SysUserEncryption sysUserEncryption8 = easyEntityQuery.queryable(SysUserEncryption.class).where(o -> o.addressSupportLike().like("绍")).firstOrNull();
        Assert.assertNull(sysUserEncryption8);
        sysUserEncryption7.setPhoneSupportLike("13232323321");
        long l2 = easyEntityQuery.updatable(sysUserEncryption7).executeRows();
        Assert.assertEquals(1,l2);
        SysUserEncryption sysUserEncryption9 = easyEntityQuery.queryable(SysUserEncryption.class).where(o -> o.phoneSupportLike().like( "23233")).firstOrNull();
        Assert.assertNotNull(sysUserEncryption9);
        long l3 = easyEntityQuery.updatable(SysUserEncryption.class).setColumns(s -> s.phoneSupportLike().set("19876543210"))
                .where(o -> o.id().eq("2")).executeRows();
        Assert.assertEquals(1,l3);
        SysUserEncryption sysUserEncryption10 = easyEntityQuery.queryable(SysUserEncryption.class).where(o -> o.phoneSupportLike().like("9876")).firstOrNull();
        Assert.assertNotNull(sysUserEncryption10);
        SysUserEncryption sysUserEncryption11 = easyEntityQuery.queryable(SysUserEncryption.class).where(o -> o.phoneSupportLike().like( "987")).firstOrNull();
        Assert.assertNull(sysUserEncryption11);
    }
}
