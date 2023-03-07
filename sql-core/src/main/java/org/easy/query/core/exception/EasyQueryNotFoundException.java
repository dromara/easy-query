package org.easy.query.core.exception;

/**
 * @FileName: EasyQueryNotFoundException.java
 * @Description: 文件说明
 * @Date: 2023/3/7 12:38
 * @Created by xuejiaming
 */
public class EasyQueryNotFoundException extends EasyQueryException{
    private static final long serialVersionUID = 4426844319659828849L;

    public EasyQueryNotFoundException(int code) {
        super(code);
    }
}
