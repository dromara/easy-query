package com.easy.query.core.sharding.manager;

import java.util.List;

/**
 * create time 2023/5/12 21:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryCountContext {
    private final SequenceCountLine sequenceCountLine = new DefaultSequenceCountLine();

    public void addCountResult(long total,boolean init) {
        sequenceCountLine.addCountResult(total,init);
    }

    public List<SequenceCountNode> getCountResult() {
        return sequenceCountLine.getCountNodes();
    }

    public SequenceCountLine getSequenceCountLine() {
        return sequenceCountLine;
    }
}
