package com.easy.query.core.api.pagination;

import java.util.List;

/**
 * @FileName: IPageResult.java
 * @Description: 文件说明
 * create time 2023/2/22 10:51
 * @author xuejiaming
 */
public interface EasyPageResult<T> {
     long getTotal();

     List<T> getData();
}
