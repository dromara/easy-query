import org.jdqc.sql.core.DefaultJQDCClient;
import org.jdqc.sql.core.JQDCClient;
import org.jdqc.sql.core.Select;
import org.jdqc.sql.core.Select2;

import java.util.List;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: MainTest.java
 * @Description: 文件说明
 * @Date: 2023/2/4 23:07
 * @Created by xuejiaming
 */
public class MainTest {
    private JQDCClient client=new DefaultJQDCClient();
    public void Test1(){
        SysUser sysUser = client.query(SysUser.class).eq(SysUser::getId, 1).firstOrNull();

        List<SysUser> sysUsers = client.query(SysUser.class)
                .leftJoin(SysUserExt.class, on -> on.eq(SysUser::getId, SysUserExt::getUid))
                .selectAll(SysUser.class)
                .selectAs(SysUserExt::getId,SysUser::getId)
                .eq(SysUser::getId, 100)
                .eq(SysUserExt::getId, 100)
                .eq(SysUser::getId, SysUserExt::getUid)
                .toList();

    }
}
