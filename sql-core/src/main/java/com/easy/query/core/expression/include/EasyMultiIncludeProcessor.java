package com.easy.query.core.expression.include;

import java.util.List;

/**
 * create time 2024/5/15 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyMultiIncludeProcessor implements IncludeProcessor{
    private final List<IncludeProcessor> includeProcessors;

    public EasyMultiIncludeProcessor(List<IncludeProcessor> includeProcessors){

        this.includeProcessors = includeProcessors;
    }
    @Override
    public void process() {
        for (IncludeProcessor includeProcessor : includeProcessors) {
            includeProcessor.process();
        }
    }
}
