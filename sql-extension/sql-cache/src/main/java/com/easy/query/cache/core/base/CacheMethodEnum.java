package com.easy.query.cache.core.base;

/**
 * create time 2024/1/25 20:47
 * 文件说明
 *
 * @author xuejiaming
 */
public enum CacheMethodEnum {
    UNKNOWN(1),
    INSERT(1<<1),
    UPDATE(1<<2),
    DELETE(1<<3);
    private final int code;

    CacheMethodEnum(int code){

        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CacheMethodEnum enumOfOrNull(Integer val){
        return  enumOfOrDefault(val,null);
    }
    public static CacheMethodEnum enumOfOrDefault(Integer val,CacheMethodEnum def){
        if(val!=null){
            switch (val){
                case 1:return UNKNOWN;
                case 1<<1:return INSERT;
                case 1<<2:return UPDATE;
                case 1<<3:return DELETE;
            }
        }
        return  def;
    }
}