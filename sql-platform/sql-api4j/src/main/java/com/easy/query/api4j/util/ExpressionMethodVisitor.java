package com.easy.query.api4j.util;

import com.easy.query.core.util.EasyStringUtil;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * create time 2023/11/6 11:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class ExpressionMethodVisitor extends MethodVisitor {
    private final StringBuilder _methodBody;

    public ExpressionMethodVisitor(StringBuilder methodBody) {
        super(Opcodes.ASM4);
        this._methodBody=methodBody;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
//        if (opcode == Opcodes.INVOKEVIRTUAL && owner.equals("java/lang/Object") && name.equals("getClass")) {
//            // Skip getClass() method, it's typically used in Lambda implementation classes
//        } else {
//            // Print method call
//            methodBody += name + ".";
//        }
//        System.out.println("-------------");
//        System.out.println(methodBody);
//        System.out.println("-------------");
        String attr = null;
        if (name.startsWith("get")) {
            attr = name.substring(3);
        } else {
            attr = name.substring(2);
        }
        _methodBody.append(".").append(EasyStringUtil.toLowerCaseFirstOne(attr));
    }

    @Override
    public void visitEnd() {
        _methodBody.deleteCharAt(0);
    }
}
