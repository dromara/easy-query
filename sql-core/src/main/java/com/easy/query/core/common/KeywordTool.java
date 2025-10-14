package com.easy.query.core.common;

/**
 * create time 2023/7/28 16:44
 * 文件说明
 *
 * @author xuejiaming
 */
public final class KeywordTool {
    public static final String ROW_NUM="__rownum__";
    public static final String MAX_COLUMN="__col__";
    private KeywordTool(){}

    public static boolean isIgnoreColumn(String columnName){
        switch (columnName){
            case ROW_NUM:return true;
        }
        return false;
    }
    public static String getMaxColumn(int index){
        return "__col"+index+"__";
    }
}
