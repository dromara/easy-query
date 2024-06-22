package com.easy.query.core.lambda.util;

import com.easy.query.api.lambda.crud.read.group.IGroup;
import io.github.kiryu1223.expressionTree.expressions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionUtil
{
    public static boolean isVoid(Class<?> c)
    {
        return c == void.class || c == Void.class;
    }

    public static boolean isTableProperty(List<ParameterExpression> parameters, Expression expression)
    {
        if (expression.getKind() == Kind.MethodCall)
        {
            MethodCallExpression methodCall = (MethodCallExpression) expression;
            if (!methodCall.inParameters(parameters)
                    || !methodCall.getArgs().isEmpty()
                    || isVoid(methodCall.getMethod().getReturnType()))
            {
                return false;
            }
            String methodName = methodCall.getMethod().getName();
            return !methodName.equals("hashCode")
                    && !methodName.equals("clone")
                    && !methodName.equals("toString")
                    && !methodName.equals("finalize");
        }
        else if (expression.getKind() == Kind.FieldSelect)
        {
            FieldSelectExpression fieldSelect = (FieldSelectExpression) expression;
            return fieldSelect.inParameters(parameters);
        }
        else
        {
            return false;
        }
    }

    public static boolean isGroupKey(List<ParameterExpression> parameters, Expression expression)
    {
        if (expression instanceof FieldSelectExpression)
        {
            FieldSelectExpression fieldSelect = (FieldSelectExpression) expression;
            Field field = fieldSelect.getField();
            return fieldSelect.inParameters(parameters)
                    && (IGroup.class.isAssignableFrom(field.getDeclaringClass()))
                    && field.getName().equals("key");
        }
        return false;
    }

    public static boolean isEquals(Method method)
    {
        return method.getName().equals("equals")
                && method.getParameterCount() == 1
                && method.getParameters()[0].getType() == Object.class;
    }

    public static boolean isStaticMethod(Method method)
    {
        return Modifier.isStatic(method.getModifiers());
    }

    public static boolean isStaticComparatorMethod(Method method)
    {
        return isStaticMethod(method)
                && method.getParameterCount() == 2
                && (method.getReturnType() == boolean.class
                || method.getReturnType() == Boolean.class);
    }

    public static boolean isStaticNullCheckMethod(Method method)
    {
        return isStaticMethod(method)
                && method.getParameterCount() == 1
                && (method.getReturnType() == boolean.class
                || method.getReturnType() == Boolean.class);
    }

    public static boolean isStaticOperationMethod(Method method)
    {
        return isStaticMethod(method)
                && method.getParameterCount() == 2
                && method.getReturnType() != void.class
                && method.getReturnType() != Void.class;
    }

    public static boolean hasParameter(Expression expression)
    {
        AtomicBoolean flag = new AtomicBoolean(false);
        expression.accept(new DeepFindVisitor()
        {
            @Override
            public void visit(ParameterExpression parameterExpression)
            {
                flag.set(true);
            }
        });
        return flag.get();
    }

    public static ParamMatcher match(String input)
    {
        ParamMatcher paramMatcher = new ParamMatcher();

        List<String> bracesContent = paramMatcher.bracesContent;
        List<String> remainder = paramMatcher.remainder;
        // 正则表达式匹配"{}"内的内容
        Pattern pattern = Pattern.compile("\\{([^}]+)}");
        Matcher matcher = pattern.matcher(input);

        int lastIndex = 0; // 上一个匹配项结束的位置
        while (matcher.find())
        {
            // 添加上一个匹配项到剩余字符串（如果有的话）
            if (lastIndex < matcher.start())
            {
                remainder.add(input.substring(lastIndex, matcher.start()));
            }

            // 提取并添加"{}"内的内容
            bracesContent.add(matcher.group(1));

            // 更新上一个匹配项结束的位置
            lastIndex = matcher.end();
        }

        // 添加最后一个匹配项之后的剩余字符串（如果有的话）
        if (lastIndex < input.length())
        {
            remainder.add(input.substring(lastIndex));
        }

        return paramMatcher;
    }
}
