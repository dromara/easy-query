package com.easy.query.test;

import com.easy.query.BaseTest;
import com.easy.query.entity.SysUser;
import com.easy.query.entity.SysUserEncryption;
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
        SysUser sysUser = easyQuery.queryable(SysUser.class).whereById("1").firstOrNull();
        Assert.assertNotNull(sysUser);
        Assert.assertEquals("18888888"+sysUser.getId()+sysUser.getId(),sysUser.getPhone());
        Assert.assertEquals("333333333333333"+sysUser.getId()+sysUser.getId(),sysUser.getIdCard());
        Assert.assertEquals(sysUser.getPhone()+sysUser.getIdCard(),sysUser.getAddress());
    }
    @Test
    public void phoneTest1(){
        List<SysUser> sysUsers = easyQuery.queryable(SysUser.class).toList();
        Assert.assertEquals(100,sysUsers.size());
        for (SysUser sysUser : sysUsers) {
            Assert.assertEquals("18888888"+sysUser.getId()+sysUser.getId(),sysUser.getPhone());
            Assert.assertEquals("333333333333333"+sysUser.getId()+sysUser.getId(),sysUser.getIdCard());
            Assert.assertEquals(sysUser.getPhone()+sysUser.getIdCard(),sysUser.getAddress());
        }
    }
    @Test
    public void phoneTest2(){
        List<SysUser> sysUsers = easyQuery.queryable(SysUser.class).where(o->o.likeMatchRight(SysUser::getPhone,"8800")).toList();
        Assert.assertEquals(1,sysUsers.size());
        for (SysUser sysUser : sysUsers) {
            Assert.assertEquals("18888888"+sysUser.getId()+sysUser.getId(),sysUser.getPhone());
            Assert.assertEquals("333333333333333"+sysUser.getId()+sysUser.getId(),sysUser.getIdCard());
            Assert.assertEquals(sysUser.getPhone()+sysUser.getIdCard(),sysUser.getAddress());
        }
    }
    @Test
    public void phoneTest3(){
        List<SysUser> sysUsers = easyQuery.queryable(SysUser.class).where(o->o.likeMatchLeft(SysUser::getPhone,"188888881")).toList();
        Assert.assertEquals(11,sysUsers.size());
        for (SysUser sysUser : sysUsers) {
            Assert.assertEquals("18888888"+sysUser.getId()+sysUser.getId(),sysUser.getPhone());
            Assert.assertEquals("333333333333333"+sysUser.getId()+sysUser.getId(),sysUser.getIdCard());
            Assert.assertEquals(sysUser.getPhone()+sysUser.getIdCard(),sysUser.getAddress());
        }
    }
    @Test
    public void phoneTest4(){
        List<SysUser> sysUsers = easyQuery.queryable(SysUser.class).where(o->o.like(SysUser::getPhone,"1888888812")).toList();
        Assert.assertEquals(1,sysUsers.size());
        for (SysUser sysUser : sysUsers) {
            Assert.assertEquals("18888888"+sysUser.getId()+sysUser.getId(),sysUser.getPhone());
            Assert.assertEquals("333333333333333"+sysUser.getId()+sysUser.getId(),sysUser.getIdCard());
            Assert.assertEquals(sysUser.getPhone()+sysUser.getIdCard(),sysUser.getAddress());
        }
    }

    @Test
    public void test5(){
        long l = easyQuery.deletable(SysUserEncryption.class)
                .whereById("1").executeRows();
        SysUserEncryption sysUserEncryption = new SysUserEncryption();
        sysUserEncryption.setId("1");
        sysUserEncryption.setName("name1");
        sysUserEncryption.setPhoneNotSupportLike("12345678901");
        sysUserEncryption.setAddressNotSupportLike("浙江省绍兴市越城区城市广场旁边2-102");
        long l1 = easyQuery.insertable(sysUserEncryption).executeRows();
        Assert.assertEquals(1,l1);
        SysUserEncryption sysUserEncryption1 = easyQuery.queryable(SysUserEncryption.class).whereById("1").firstOrNull();
        Assert.assertNotNull(sysUserEncryption1);
        Assert.assertEquals(sysUserEncryption.getId(),sysUserEncryption1.getId());
        Assert.assertEquals(sysUserEncryption.getPhoneNotSupportLike(),sysUserEncryption1.getPhoneNotSupportLike());
        Assert.assertEquals(sysUserEncryption.getAddressNotSupportLike(),sysUserEncryption1.getAddressNotSupportLike());
        SysUserEncryption sysUserEncryption2 = easyQuery.queryable(SysUserEncryption.class).where(o -> o.eq(SysUserEncryption::getPhoneNotSupportLike, "12345678901")).firstOrNull();
        Assert.assertNotNull(sysUserEncryption2);
        Assert.assertEquals(sysUserEncryption1.getId(),sysUserEncryption2.getId());
        SysUserEncryption sysUserEncryption3 = easyQuery.queryable(SysUserEncryption.class).where(o -> o.like(SysUserEncryption::getPhoneNotSupportLike, "12345678901")).firstOrNull();
        Assert.assertNull(sysUserEncryption3);
    }
    @Test
    public void test6(){
        long l = easyQuery.deletable(SysUserEncryption.class)
                .whereById("2").executeRows();
        SysUserEncryption sysUserEncryption = new SysUserEncryption();
        sysUserEncryption.setId("2");
        sysUserEncryption.setName("name2");
        sysUserEncryption.setPhoneSupportLike("12345678901");
        sysUserEncryption.setAddressSupportLike("浙江省绍兴市越城区城市广场旁边2-102");
        long l1 = easyQuery.insertable(sysUserEncryption).executeRows();
        Assert.assertEquals(1,l1);
        SysUserEncryption sysUserEncryption1 = easyQuery.queryable(SysUserEncryption.class).whereById("2").firstOrNull();
        Assert.assertNotNull(sysUserEncryption1);
        Assert.assertEquals(sysUserEncryption.getId(),sysUserEncryption1.getId());
        Assert.assertEquals(sysUserEncryption.getPhoneSupportLike(),sysUserEncryption1.getPhoneSupportLike());
        Assert.assertEquals(sysUserEncryption.getAddressSupportLike(),sysUserEncryption1.getAddressSupportLike());
        SysUserEncryption sysUserEncryption2 = easyQuery.queryable(SysUserEncryption.class).where(o -> o.eq(SysUserEncryption::getPhoneSupportLike, "12345678901")).firstOrNull();
        Assert.assertNotNull(sysUserEncryption2);
        Assert.assertEquals(sysUserEncryption1.getId(),sysUserEncryption2.getId());
        SysUserEncryption sysUserEncryption3 = easyQuery.queryable(SysUserEncryption.class).where(o -> o.like(SysUserEncryption::getPhoneSupportLike, "34567")).firstOrNull();
        Assert.assertNotNull(sysUserEncryption3);
        SysUserEncryption sysUserEncryption4 = easyQuery.queryable(SysUserEncryption.class).where(o -> o.like(SysUserEncryption::getAddressSupportLike, "2-102")).firstOrNull();
        Assert.assertNotNull(sysUserEncryption4);
        SysUserEncryption sysUserEncryption5 = easyQuery.queryable(SysUserEncryption.class).where(o -> o.likeMatchRight(SysUserEncryption::getAddressSupportLike, "2-102")).firstOrNull();
        Assert.assertNotNull(sysUserEncryption5);
        SysUserEncryption sysUserEncryption6 = easyQuery.queryable(SysUserEncryption.class).where(o -> o.likeMatchLeft(SysUserEncryption::getAddressSupportLike, "浙江省绍兴市")).firstOrNull();
        Assert.assertNotNull(sysUserEncryption6);
        SysUserEncryption sysUserEncryption7 = easyQuery.queryable(SysUserEncryption.class).where(o -> o.like(SysUserEncryption::getAddressSupportLike, "绍兴")).firstOrNull();
        Assert.assertNotNull(sysUserEncryption7);
        //中文单字符不支持查询所以是空
        SysUserEncryption sysUserEncryption8 = easyQuery.queryable(SysUserEncryption.class).where(o -> o.like(SysUserEncryption::getAddressSupportLike, "绍")).firstOrNull();
        Assert.assertNull(sysUserEncryption8);
    }
}
