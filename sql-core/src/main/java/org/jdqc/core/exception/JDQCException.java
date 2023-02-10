package org.jdqc.core.exception;

/**
 * 框架执行过程中出现的错误
 * @FileName: JDQCException.java
 * @Description: 文件说明
 * @Date: 2023/2/7 12:36
 * @Created by xuejiaming
 */
public class JDQCException  extends RuntimeException{

    public static final int 	GENERIC_ERROR  = 0;
    public static final int 	INIT_META_DATA  = 1;
    public static final int 	CLASS_TABLE_MISS  = 2;
    public int code ;

    public JDQCException(int code){
        this.code = code;
    }

    public JDQCException(int code, Throwable e){
        super(e);
        this.code = code;
    }

    public JDQCException(int code, String msg, Throwable e){
        super(msg,e);
        this.code = code;
    }
    public JDQCException(String msg, Throwable e){
        super(msg,e);
        this.code = GENERIC_ERROR;
    }

    public JDQCException(int code, String msg){
        super(msg);
        this.code = code;
    }

    public JDQCException(String msg){
        super(msg);
        this.code = GENERIC_ERROR;
    }

    public int getCode() {
        return code;
    }
}
