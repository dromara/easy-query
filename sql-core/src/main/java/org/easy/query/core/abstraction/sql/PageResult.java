package org.easy.query.core.abstraction.sql;

import java.util.List;

/**
 * @FileName: IPageResult.java
 * @Description: 文件说明
 * @Date: 2023/2/22 10:51
 * @Created by xuejiaming
 */
public interface PageResult<T> {
    long getTotal();
    List<T> getData();
}
