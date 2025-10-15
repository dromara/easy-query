package com.easy.query.test.duckdb;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.duckdb.proxy.ExcelDuck2Proxy;
import com.easy.query.test.duckdb.proxy.ExcelDuckProxy;
import lombok.Data;

import java.util.List;

/**
 * create time 2025/10/15 11:34
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table(value = "read_xlsx('./ducktest.xlsx',sheet='Sheet1')", keyword = false)
public class ExcelDuck implements ProxyEntityAvailable<ExcelDuck, ExcelDuckProxy> {

    private String id;
    private String name;
    private Integer age;

    /**
     *
     **/
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {ExcelDuckProxy.Fields.id}, targetProperty = {ExcelDuck2Proxy.Fields.uid})
    private List<ExcelDuck2> excelDuck2List;
}
