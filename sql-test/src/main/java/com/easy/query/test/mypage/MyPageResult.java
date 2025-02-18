package com.easy.query.test.mypage;

import com.easy.query.core.api.pagination.DefaultPageResult;

import java.util.List;

/**
 * create time 2025/2/17 17:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyPageResult<T> extends DefaultPageResult<T> {
    public MyPageResult(long total, List<T> data) {
        super(total, data);
    }

    public List<T> getRecords() {
        return super.getData();
    }
}
