package com.easy.query.api4j.util;

import com.easy.query.api4j.lambda.SerializedDescriptor;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * create time 2023/11/6 12:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class ExpressionClassVisitor extends ClassVisitor {
    private final StringBuilder _methodBody;
    private final ClassLoader _loader;
    private final String _method;
    private final String _methodDesc;
    private Class<?> _type;

    public ExpressionClassVisitor(StringBuilder methodBody,ClassLoader loader, SerializedDescriptor serializedDescriptor) {
        super(Opcodes.ASM4);
        this._methodBody = methodBody;
        this._loader = loader;
        this._method = serializedDescriptor.getImplMethodName();
        this._methodDesc = serializedDescriptor.getImplMethodSignature();
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

        if (!_method.equals(name) || !_methodDesc.equals(desc))
            return null;

//        Type ret = Type.getReturnType(desc);
//
//        _type = getClass(ret);
//
//        Type[] args = Type.getArgumentTypes(desc);
//        Class<?>[] argTypes = new Class<?>[args.length];
//
//        for (int i = 0; i < args.length; i++)
//            argTypes[i] = getClass(args[i]);

        return new ExpressionMethodVisitor(_methodBody);
    }
//    Class<?> getClass(Type t) {
//        try {
//            switch (t.getSort()) {
//                case Type.BOOLEAN:
//                    return Boolean.TYPE;
//                case Type.CHAR:
//                    return Character.TYPE;
//                case Type.BYTE:
//                    return Byte.TYPE;
//                case Type.SHORT:
//                    return Short.TYPE;
//                case Type.INT:
//                    return Integer.TYPE;
//                case Type.FLOAT:
//                    return Float.TYPE;
//                case Type.LONG:
//                    return Long.TYPE;
//                case Type.DOUBLE:
//                    return Double.TYPE;
//                case Type.VOID:
//                    return Void.TYPE;
//            }
//            String cn = t.getInternalName();
//            cn = cn != null ? cn.replace('/', '.') : t.getClassName();
//
//            return Class.forName(cn, false, _loader);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//


    @Override
    public String toString() {
        return _methodBody.toString();
    }
}
