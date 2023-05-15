package com.easy.query.core.sharding.manager;

import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/5/15 11:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSequenceCountLine implements SequenceCountLine{
    private final List<SequenceCountNode> results = new ArrayList<>();
    @Override
    public List<SequenceCountNode> getCountNodes() {
        return results;
    }

    @Override
    public void addCountResult(long total,boolean init) {
        if(init){
            results.add(new DefaultSequenceCountNode(total));
        }else{
            SequenceCountNode firstFillResult = EasyCollectionUtil.firstOrDefault(results, o -> o.getTotal() < 0, null);
            if (firstFillResult != null) {
                firstFillResult.setTotal(total);
            } else {
                results.add(new DefaultSequenceCountNode(total));
            }
        }
    }
}
