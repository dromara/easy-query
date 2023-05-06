package com.easy.query.sql.starter.config;


import com.easy.query.core.enums.SqlExecuteStrategyEnum;
import com.easy.query.sql.starter.option.DialectEnum;
import com.easy.query.sql.starter.option.NameConversionEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @FileName: EasyQueryProperties.java
 * @Description: 文件说明
 * @Date: 2023/3/11 14:25
 * @author xuejiaming
 */
@ConfigurationProperties(prefix = "easy-query")
public class EasyQueryProperties {

    private Boolean enable =false;
    private Boolean deleteThrow =true;
    private DialectEnum dialect;
    private NameConversionEnum nameConversion;
    private SqlExecuteStrategyEnum insertStrategy=SqlExecuteStrategyEnum.DEFAULT;
    private SqlExecuteStrategyEnum updateStrategy=SqlExecuteStrategyEnum.DEFAULT;

    private String logClass="com.easy.query.sql.starter.logging.Slf4jImpl";

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }


    public String getLogClass() {
        return logClass;
    }

    public void setLogClass(String logClass) {
        this.logClass = logClass;
    }


    public Boolean getDeleteThrow() {
        return deleteThrow;
    }

    public void setDeleteThrow(Boolean deleteThrow) {
        this.deleteThrow = deleteThrow;
    }


    public DialectEnum getDialect() {
        return dialect;
    }

    public void setDialect(DialectEnum dialect) {
        this.dialect = dialect;
    }

    public NameConversionEnum getNameConversion() {
        return nameConversion;
    }

    public void setNameConversion(NameConversionEnum nameConversion) {
        this.nameConversion = nameConversion;
    }

    public SqlExecuteStrategyEnum getInsertStrategy() {
        return insertStrategy;
    }

    public void setInsertStrategy(SqlExecuteStrategyEnum insertStrategy) {
        this.insertStrategy = insertStrategy;
    }

    public SqlExecuteStrategyEnum getUpdateStrategy() {
        return updateStrategy;
    }

    public void setUpdateStrategy(SqlExecuteStrategyEnum updateStrategy) {
        this.updateStrategy = updateStrategy;
    }

    public EasyQueryProperties() {
    }
}
