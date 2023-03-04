package org.easy.query.core.expression.segment;

/**
 * @FileName: SelectCountSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 12:52
 * @Created by xuejiaming
 */
public class SelectCountSegment extends SelectConstSegment{
    public SelectCountSegment() {
        super("COUNT(1)");
    }
}
