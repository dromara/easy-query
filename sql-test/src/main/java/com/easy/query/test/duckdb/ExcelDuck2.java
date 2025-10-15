package com.easy.query.test.duckdb;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.duckdb.proxy.ExcelDuck2Proxy;
import com.easy.query.test.duckdb.proxy.ExcelDuckProxy;
import lombok.Data;

/**
 * create time 2025/10/15 11:34
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table(value = "read_xlsx('./ducktest.xlsx',sheet='Sheet2')",keyword = false)
public class ExcelDuck2 implements ProxyEntityAvailable<ExcelDuck2, ExcelDuck2Proxy> {

    private String id;
    private String uid;
    private String name;
    private Integer age;
}
