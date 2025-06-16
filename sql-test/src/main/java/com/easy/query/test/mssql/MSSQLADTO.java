package com.easy.query.test.mssql;

import com.easy.query.core.annotation.EntityProxy;
import lombok.Data;

/**
 * create time 2025/6/16 20:30
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class MSSQLADTO {
    private String code;
    private String name;
}
