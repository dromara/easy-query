package com.easy.query.test.mypage;

import com.easy.query.core.api.pagination.DefaultPageResult;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.pagination.DefaultEasyPageResultProvider;

import java.util.List;

/**
 * create time 2025/2/17 17:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyEasyPageResultProvider extends DefaultEasyPageResultProvider {
    @Override
    public <T> EasyPageResult<T> createPageResult(long pageIndex, long pageSize, long total, List<T> data) {
        return new MyPageResult<>(total,data);
    }
}
