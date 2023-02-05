import org.jdqc.sql.core.DefaultJQDCClient;
import org.jdqc.sql.core.Select;

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
    private DefaultJQDCClient client=null;
    public void Test1(){
        List<SysUser> sysUsers = client.query(SysUser.class)
                .leftJoin(SysUserExt.class, on -> on.eq(SysUser::getId, SysUserExt::getUid))
                .selectAll(SysUser.class)
                .eq(SysUser::getId, 100)
                .eq(SysUser::getId, SysUserExt::getUid)
                .toList();



    }
}
