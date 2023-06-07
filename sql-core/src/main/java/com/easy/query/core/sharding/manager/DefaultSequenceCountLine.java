package com.easy.query.core.sharding.manager;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/5/15 11:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSequenceCountLine implements SequenceCountLine{
    private final List<Long> results = new ArrayList<>();

    @Override
    public List<Long> getTotalLines() {
        return results;
    }

    @Override
    public void addCountResult(long total,boolean init) {
        if(init){
            results.add(total);
        }else {
            int size = results.size();
            boolean setOld = false;
            //获取旧值里面的total按顺序给第一个小于0就赋值
            for (int i = 0; i < size; i++) {
                Long value = results.get(i);
                if (value == null || value < 0) {
                    results.set(i, total);
                    setOld = true;
                    break;
                }
            }
            //如果旧的没有设置就添加
            if (!setOld) {
                results.add(total);
            }
        }
    }
}
