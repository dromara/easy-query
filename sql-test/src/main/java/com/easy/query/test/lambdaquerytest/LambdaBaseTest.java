package com.easy.query.test.lambdaquerytest;

import com.easy.query.api.lambda.client.EasyLambdaQueryClient;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.h2.H2BaseTest;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LambdaBaseTest extends H2BaseTest
{
    public static EasyLambdaQueryClient elq;

    public LambdaBaseTest()
    {
        elq = new EasyLambdaQueryClient(easyQuery.getEasyQueryClient(), MethodHandles.lookup());
    }

    public void initSysUser()
    {
        boolean sysUserAny = easyQuery.queryable(SysUser.class).any();
        if (!sysUserAny) {
            List<SysUser> sysUsers = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                SysUser sysUser = new SysUser();
                sysUser.setId(String.valueOf(i));
                sysUser.setUsername("username" + i);
                sysUser.setCreateTime(LocalDateTime.now().plusDays(i));
                sysUser.setPhone("18888888" + i + i);
                sysUser.setIdCard("333333333333333" + i + i);
                sysUser.setAddress(sysUser.getPhone() + sysUser.getIdCard());
                sysUsers.add(sysUser);
            }
            long l = easyQuery.insertable(sysUsers).executeRows();
        }
    }
}
