package com.easy.query.test;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * create time 2025/8/4 14:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class BigDecimalUtils {

    /**
     * 比较两个 BigDecimal 并返回较大值，处理 null 值（null 视为最小值）
     * 如果两个值均为 null 则返回 null
     *
     * @param bd1 第一个值
     * @param bd2 第二个值
     * @return 较大的 BigDecimal 值（若一个为 null 返回另一个；两个都 null 返回 null）
     */
    public static BigDecimal max(BigDecimal bd1, BigDecimal bd2) {
        if (bd1 == null && bd2 == null) {
            return null;
        }
        if (bd1 == null) {
            return bd2;
        }
        if (bd2 == null) {
            return bd1;
        }
        return bd1.compareTo(bd2) >= 0 ? bd1 : bd2;
    }

    /**
     * 使用 Comparator 的实现版本（适合集合排序）
     */
    public static final Comparator<BigDecimal> NULL_SAFE_COMPARATOR =
            Comparator.nullsLast(Comparator.naturalOrder());

    /**
     * 从多个 BigDecimal 中找出最大值（处理 null 值）
     *
     * @param values 要比较的值
     * @return 最大值（全部为 null 时返回 null）
     */
    public static BigDecimal max(BigDecimal... values) {
        if (values == null || values.length == 0) {
            return null;
        }

        BigDecimal result = null;
        for (BigDecimal value : values) {
            if (value != null) {
                if (result == null || value.compareTo(result) > 0) {
                    result = value;
                }
            }
        }
        return result;
    }
}