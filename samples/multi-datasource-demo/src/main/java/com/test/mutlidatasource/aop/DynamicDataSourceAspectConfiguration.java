package com.test.mutlidatasource.aop;

import com.easy.query.core.util.EasyStringUtil;
import com.test.mutlidatasource.core.EasyMultiEntityQuery;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * create time 2024/3/13 16:02
 * 文件说明
 *
 * @author xuejiaming
 */
@Slf4j
@Aspect
@Configuration
public class DynamicDataSourceAspectConfiguration {
    @Autowired
    private EasyMultiEntityQuery easyMultiEntityQuery;
    @Around("execution(public * *(..)) && @annotation(com.test.mutlidatasource.aop.DynamicDataSource)")
    public Object interceptorTenantScope(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        DynamicDataSource dynamicDataSource = method.getAnnotation(DynamicDataSource.class); //通过反射拿到注解对象
        Object[] arguments = pjp.getArgs();
        String[] paramNames = getParamterNames(method);
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < arguments.length; i++) {
            context.setVariable(paramNames[i], arguments[i]);
        }

        String setDataSource = null;
        if(EasyStringUtil.isNotBlank(dynamicDataSource.value())){
            Expression expression = parser.parseExpression(dynamicDataSource.value());
            String value = expression.getValue(context, String.class);
            if(EasyStringUtil.isNotBlank(value)){
                setDataSource=value;
            }
        }
        try {
            easyMultiEntityQuery.setCurrent(setDataSource);
            return pjp.proceed();
        }finally {
            easyMultiEntityQuery.clear();
        }
    }
    private String[] getParamterNames(Method method) {
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        return u.getParameterNames(method);
    }
}