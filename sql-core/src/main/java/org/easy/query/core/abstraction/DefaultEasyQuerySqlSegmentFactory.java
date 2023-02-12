package org.easy.query.core.abstraction;

/**
 * @FileName: DefaultEasyQuerySqlSegmentFactory.java
 * @Description: 文件说明
 * @Date: 2023/2/12 22:00
 * @Created by xuejiaming
 */
public class DefaultEasyQuerySqlSegmentFactory implements EasyQuerySqlSegmentFactory {
    @Override
    public SqlSegment createSqlSegment() {
        return new DefaultSqlSegment();
    }
}
