package com.easy.query.test.mysql8.vo;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;

/**
 * create time 2025/10/19 22:37
 * {@link com.easy.query.test.mysql8.entity.bank.SysUser}
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class UserDTO2 {

    private String id;
    private String name;
    private String thirdCardType;
    private String thirdCardCode;
    private String thirdCardBankName;
}
