package com.easy.query.test;

import com.easy.query.BaseTest;
import com.easy.query.entity.SysUser;
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
}
