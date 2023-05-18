package com.easy.query.core.util;

/**
 * create time 2023/5/13 22:24
 * 文件说明
 *
 * @author xuejiaming
 */
public final class EasyBitwiseUtil {
    private EasyBitwiseUtil(){}

    public static boolean hasBit(final int source,final int bit){
        return (source & bit)== bit;
    }
    public static int addBit(final int source,final int bit){
        if(!hasBit(source,bit)){
            return source|bit;
        }
        return source;
    }
    public static int removeBit(final int source,final int bit){
        if(hasBit(source,bit)){
            return  source &~bit;
        }
        return source;
    }
}
