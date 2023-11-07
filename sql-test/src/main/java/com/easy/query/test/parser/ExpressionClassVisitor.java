package com.easy.query.test.parser;

import com.easy.query.api4j.lambda.SerializedDescriptor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.springframework.asm.Opcodes;

/**
 * create time 2023/11/6 12:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class ExpressionClassVisitor extends ClassVisitor {
    private final StringBuilder _methodBody;
    private final String _method;
    private final String _methodDesc;

    public ExpressionClassVisitor(StringBuilder methodBody,SerializedDescriptor serializedDescriptor) {
        super(Opcodes.ASM9);
        this._methodBody = methodBody;
        this._method = serializedDescriptor.getImplMethodName();
        this._methodDesc = serializedDescriptor.getImplMethodSignature();
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

        if (!_method.equals(name) || !_methodDesc.equals(desc))
            return null;

        return new ExpressionMethodVisitor(_methodBody);
    }


    @Override
    public String toString() {
        return _methodBody.toString();
    }
}
