package com.easy.query.core.exception;

/**
 * apt生成时候发生的错误
 * create time 2023/2/7 12:36
 * @author xuejiaming
 */
public class EasyQueryAptFileException extends EasyQueryException{
    public EasyQueryAptFileException(String msg){
        super(msg);
    }
    public EasyQueryAptFileException(Throwable e){
        super(e);
    }

    public EasyQueryAptFileException(String msg, Throwable e){
        super(msg,e);
    }

}
