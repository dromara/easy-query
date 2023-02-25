package org.easy.query.core.config;

/**
 * @FileName: JDQCConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/2/7 09:06
 * @Created by xuejiaming
 */
public class EasyQueryConfiguration {


    private NameConversion nameConversion = new DefaultNameConversion();
    private EasyQueryDialect dialect = new DefaultEasyQueryDialect();

    public EasyQueryConfiguration() {
    }

    public NameConversion getNameConversion() {
        return nameConversion;
    }

    public void setNameConversion(NameConversion nameConversion) {
        this.nameConversion = nameConversion;
    }

    public EasyQueryDialect getDialect() {
        return dialect;
    }

    public void setDialect(EasyQueryDialect dialect) {
        this.dialect = dialect;
    }
}
