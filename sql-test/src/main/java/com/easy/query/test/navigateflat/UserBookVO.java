package com.easy.query.test.navigateflat;

import com.easy.query.core.annotation.NavigateFlat;
import com.easy.query.core.annotation.NavigateJoin;
import com.easy.query.core.expression.parser.core.available.MappingPath;
import com.easy.query.test.navigateflat.proxy.UserBookProxy;
import lombok.Data;

/**
 * create time 2025/1/2 14:04
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class UserBookVO {
    private String id;
    private String uid;
    private String name;

//    private static final MappingPath USER_ADDRESS_PATH = UserBookProxy.TABLE.myUser().userAddress().address();
//    @NavigateFlat(pathAlias = "USER_ADDRESS_PATH")
//    private String userAddress;


    private static final MappingPath USER_NAME_PATH = UserBookProxy.TABLE.myUser().name();
    @NavigateJoin(pathAlias = "USER_NAME_PATH")
    private String userName;
}
