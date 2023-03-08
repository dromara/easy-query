package org.easy.query.core.exception;

/**
 * @FileName: EasyQueryNotFoundException.java
 * @Description: 文件说明
 * @Date: 2023/3/7 12:38
 * @Created by xuejiaming
 */
public class EasyQueryNotFoundException extends EasyQueryException {
    private static final long serialVersionUID = 4426844319659828849L;

    private final String code;

    public EasyQueryNotFoundException(String msg) {
        this(msg, null, null);
    }

    public EasyQueryNotFoundException(String msg, String code) {
        this(msg, code, null);
    }

    public EasyQueryNotFoundException(Throwable e) {
        this(null, null, e);
    }

    public EasyQueryNotFoundException(String msg, String code, Throwable e) {
        super(msg, e);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
