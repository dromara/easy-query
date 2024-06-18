package com.easy.query.core.configuration.dialect;

import com.easy.query.core.util.EasyStringUtil;

/**
 * @FileName: DefaultEasyQueryDialect.java
 * @Description: 文件说明
 * @Date: 2023/2/12 09:22
 * @author xuejiaming
 */
public abstract class AbstractSQLKeyword implements SQLKeyword {
    protected abstract String getQuoteStart();

    protected abstract String getQuoteEnd();

    @Override
    public String getQuoteName(String keyword) {
        String quoteStart = getQuoteStart();
        String quoteEnd = getQuoteEnd();
        StringBuilder keyBuilder = new StringBuilder();
        if(quoteStart!=null&&!EasyStringUtil.startsWith(keyword,quoteStart)){
            keyBuilder.append(quoteStart);
        }
        keyBuilder.append(keyword);
        if(quoteEnd!=null&&!EasyStringUtil.endsWith(keyword,quoteEnd)){
            keyBuilder.append(quoteEnd);
        }
        return keyBuilder.toString();
    }
}
