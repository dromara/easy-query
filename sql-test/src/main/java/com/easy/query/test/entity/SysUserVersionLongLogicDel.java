package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.LogicDelete;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.Version;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategyEnum;
import com.easy.query.core.basic.extension.version.VersionLongStrategy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/3/25 10:55
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table(value = "t_sys_user_version_del")
public class SysUserVersionLongLogicDel {
    @Column(primaryKey = true)
    private String id;
    private String username;
    private String phone;
    private String idCard;
    private String address;
    /**
     * 创建时间;创建时间
     */
    private LocalDateTime createTime;
    @Version(strategy = VersionLongStrategy.class)
    private Long version;

    @LogicDelete(strategy = LogicDeleteStrategyEnum.BOOLEAN)
    private Boolean deleted;
}

//    id varchar(32) not null comment '主键ID'primary key,
//        username varchar(50) null comment '姓名',
//        phone varchar(250) null comment '手机号加密',
//        id_card varchar(500) null comment '身份证编号',
//        address text null comment '地址',
//        create_time datetime not null comment '创建时间'