package com.easy.query.test.parser;

import com.easy.query.core.util.EasyStringUtil;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * create time 2023/11/6 11:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class ExpressionMethodVisitor extends MethodVisitor {
    private final StringBuilder _methodBody;

    public ExpressionMethodVisitor(StringBuilder methodBody) {
        super(Opcodes.ASM9);
        this._methodBody=methodBody;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        if(opcode==Opcodes.INVOKEVIRTUAL){
            String attr = null;
            if (name.startsWith("get")) {
                attr = name.substring(3);
            } else {
                attr = name.substring(2);
            }
            _methodBody.append(".").append(EasyStringUtil.toLowerCaseFirstOne(attr));
        }
    }

    @Override
    public void visitEnd() {
        _methodBody.deleteCharAt(0);
    }
}
