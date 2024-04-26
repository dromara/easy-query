package com.easy.query.core.proxy.columns.types;

import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLNumberColumn;

import java.math.BigDecimal;

/**
 * create time 2024/4/26 13:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLBigDecimalTypeColumn<TProxy> extends SQLNumberColumn<TProxy, BigDecimal>,
        ProxyEntity<SQLBigDecimalTypeColumn<TProxy>, BigDecimal> {
}
