package com.easy.query.api4j.lambda;

import java.lang.invoke.SerializedLambda;
import java.util.Objects;

/**
 * create time 2023/11/6 14:34
 * 文件说明
 *
 * @author xuejiaming
 */
public final class SerializedDescriptor {
    public SerializedDescriptor(SerializedLambda lambda) {
        this.implClass = lambda.getImplClass();
        this.implMethodName = lambda.getImplMethodName();
        this.implMethodSignature = lambda.getImplMethodSignature();
        this.implMethodKind = lambda.getImplMethodKind();
        this.instantiatedMethodType = lambda.getInstantiatedMethodType();
    }
    private final String implClass;
    private final String implMethodName;
    private final String implMethodSignature;
    private final int implMethodKind;
    private final String instantiatedMethodType;

    public String getImplClass() {
        return implClass;
    }

    public String getImplMethodName() {
        return implMethodName;
    }

    public String getImplMethodSignature() {
        return implMethodSignature;
    }

    public int getImplMethodKind() {
        return implMethodKind;
    }

    public String getInstantiatedMethodType() {
        return instantiatedMethodType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SerializedDescriptor that = (SerializedDescriptor) o;
        return implMethodKind == that.implMethodKind && Objects.equals(implClass, that.implClass) && Objects.equals(implMethodName, that.implMethodName) && Objects.equals(implMethodSignature, that.implMethodSignature) && Objects.equals(instantiatedMethodType, that.instantiatedMethodType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(implClass, implMethodName, implMethodSignature, implMethodKind, instantiatedMethodType);
    }
}
