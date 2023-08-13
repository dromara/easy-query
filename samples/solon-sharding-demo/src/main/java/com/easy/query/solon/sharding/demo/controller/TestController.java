package com.easy.query.solon.sharding.demo.controller;

import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.solon.annotation.Db;
import com.easy.query.solon.sharding.demo.domain.SysUser;
import com.easy.query.solon.sharding.demo.domain.UserBook;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.MethodType;
import org.noear.solon.data.annotation.Tran;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * create time 2023/8/12 21:45
 * 文件说明
 *
 * @author xuejiaming
 */
@Controller
@Mapping("/test")
public class TestController {
    @Db
    private EasyQuery easyQuery;
    @Mapping(value = "/init",method = MethodType.GET)
    @Tran
    public String init(){
        {

            SysUser sysUser = new SysUser();
            sysUser.setId("1");
            sysUser.setName("用户1");
            sysUser.setPhone("12345678901");
            sysUser.setAddress("浙江省绍兴市越城区城市广场1234号");
            sysUser.setCreateTime(LocalDateTime.now());
            ArrayList<UserBook> userBooks = new ArrayList<>();
            UserBook userBook = new UserBook();
            userBook.setId("1");
            userBook.setUserId("1");
            userBook.setName("语文");
            userBooks.add(userBook);
            UserBook userBook1 = new UserBook();
            userBook1.setId("2");
            userBook1.setUserId("1");
            userBook1.setName("数学");
            userBooks.add(userBook1);
            easyQuery.insertable(sysUser).executeRows();
            easyQuery.insertable(userBooks).executeRows();
        }
        {

            SysUser sysUser = new SysUser();
            sysUser.setId("2");
            sysUser.setName("用户2");
            sysUser.setPhone("19012345678");
            sysUser.setAddress("浙江省杭州市上城区武林广场1234号");
            sysUser.setCreateTime(LocalDateTime.now());
            ArrayList<UserBook> userBooks = new ArrayList<>();
            UserBook userBook = new UserBook();
            userBook.setId("3");
            userBook.setUserId("2");
            userBook.setName("语文");
            userBooks.add(userBook);
            UserBook userBook1 = new UserBook();
            userBook1.setId("4");
            userBook1.setUserId("2");
            userBook1.setName("英语");
            userBooks.add(userBook1);
            easyQuery.insertable(sysUser).executeRows();
            easyQuery.insertable(userBooks).executeRows();
        }
        return "初始化完成";
    }
}
