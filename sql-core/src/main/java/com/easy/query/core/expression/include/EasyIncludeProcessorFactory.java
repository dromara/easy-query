package com.easy.query.core.expression.include;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.sql.include.IncludeParserResult;
import com.easy.query.core.metadata.NavigateFlatMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/7/16 21:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyIncludeProcessorFactory implements IncludeProcessorFactory{
    @Override
    public IncludeProcessor createIncludeProcess(IncludeParserResult includeParserResult, QueryRuntimeContext runtimeContext) {
        //如果是flat那么就是包含自己用list集合做EasyFlatIncludeProcess
        List<NavigateFlatMetadata> navigateFlatMetadataList = includeParserResult.getNavigateFlatMetadataList();
        if(EasyCollectionUtil.isNotEmpty(navigateFlatMetadataList)){
            ArrayList<IncludeProcessor> includeProcessors = new ArrayList<>(navigateFlatMetadataList.size());
            for (NavigateFlatMetadata navigateFlatMetadata : navigateFlatMetadataList) {
                EasyFlatIncludeProcessor easyFlatIncludeProcessor = new EasyFlatIncludeProcessor(navigateFlatMetadata, includeParserResult, runtimeContext);
                includeProcessors.add(easyFlatIncludeProcessor);
            }
            return new EasyMultiIncludeProcessor(includeProcessors);
        }
        return new EasyIncludeProcess(includeParserResult,runtimeContext);
    }
}
