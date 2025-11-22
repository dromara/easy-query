package com.easy.query.core.common;

/**
 * create time 2023/7/28 16:44
 * 文件说明
 *
 * @author xuejiaming
 */
public final class KeywordTool {
    public static final String ROW_NUM = "__rownum__";
    public static final String MAX_COLUMN = "__col__";

    private KeywordTool() {
    }

    public static boolean isIgnoreColumn(String columnName) {
        switch (columnName) {
            case ROW_NUM:
                return true;
        }
        return false;
    }

    public static String getMaxColumn(int index) {
        return "__col" + index + "__";
    }

    private static final String[] GROUP_KEY_ALIAS_ARRAY = new String[]{"__group_key1__", "__group_key2__", "__group_key3__", "__group_key4__", "__group_key5__", "__group_key6__", "__group_key7__", "__group_key8__", "__group_key9__", "__group_key10__"};

    public static String getGroupKeyAlias(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("index can not less than 0");
        }
        if (index < GROUP_KEY_ALIAS_ARRAY.length) {
            return GROUP_KEY_ALIAS_ARRAY[index];
        }
        return "__group_key" + index + "__";
    }
}
