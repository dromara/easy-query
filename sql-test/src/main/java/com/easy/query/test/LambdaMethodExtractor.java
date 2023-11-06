package com.easy.query.test;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.test.entity.Company;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;
import org.springframework.asm.Opcodes;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * create time 2023/11/6 13:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class LambdaMethodExtractor {
    public static void main(String[] args) {
        Property<Company, String> lambda = e -> e.getAddress().getArea();
        String lambdaMethodBody = getLambdaMethodBody(lambda);
        System.out.println(lambdaMethodBody);
    }

    public static <T, R> String getLambdaMethodBody(Property<T, R> lambda) {
        try {
            Class<?> lambdaClass = lambda.getClass();
            String lambdaClassName = lambdaClass.getName();
            String lambdaMethodDescriptor = Type.getMethodDescriptor(lambdaClass.getDeclaredMethod("apply", Object.class));
            Method declaredMethod = lambda.getClass().getDeclaredMethod("writeReplace");
            declaredMethod.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) declaredMethod.invoke(lambda);

            String className = serializedLambda.getImplClass();
            ClassReader classReader = new ClassReader(className);
            LambdaMethodVisitor methodVisitor = new LambdaMethodVisitor(lambdaMethodDescriptor);
            classReader.accept(methodVisitor, 0);
            return methodVisitor.getMethodBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static class LambdaMethodVisitor extends ClassVisitor {

        private final String lambdaMethodDescriptor;
        private String methodBody = "";

        public LambdaMethodVisitor(String lambdaMethodDescriptor) {
            super(Opcodes.ASM4);
            this.lambdaMethodDescriptor = lambdaMethodDescriptor;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if (descriptor.equals(lambdaMethodDescriptor)) {
                return new MethodVisitor(Opcodes.ASM4) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                        if (opcode == Opcodes.INVOKEVIRTUAL && owner.equals("java/lang/Object") && name.equals("getClass")) {
                            // Skip getClass() method, it's typically used in Lambda implementation classes
                        } else {
                            // Print method call
                            methodBody += name + ".";
                        }
                    }

                    @Override
                    public void visitEnd() {
                        // Remove the trailing dot
                        methodBody = methodBody.substring(0, methodBody.length() - 1);
                    }
                };
            }
            return null;
        }

        public String getMethodBody() {
            return methodBody;
        }
    }
}