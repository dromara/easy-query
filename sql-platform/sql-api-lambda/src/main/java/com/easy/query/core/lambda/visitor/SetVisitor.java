package com.easy.query.core.lambda.visitor;

import com.easy.query.api.lambda.db.DbType;
import com.easy.query.api.lambda.sqlext.SqlFunctions;
import com.easy.query.core.lambda.exception.IllegalExpressionException;
import io.github.kiryu1223.expressionTree.expressions.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.easy.query.core.lambda.util.ExpressionUtil.*;
import static com.easy.query.core.lambda.util.SqlUtil.*;

public class SetVisitor extends BaseVisitor
{
    public SetVisitor(List<ParameterExpression> parameters, DbType dbType)
    {
        super(parameters, dbType);
    }

    private final List<Pair> pairs = new ArrayList<>();

    private Pair curPair;

    public List<Pair> getPairs()
    {
        return pairs;
    }

    private void checkHasPair(Expression expression)
    {
        if (curPair == null) throw new RuntimeException(expression.toString());
    }

    @Override
    public void visit(MethodCallExpression methodCall)
    {
        if (methodCall.getExpr().getKind() == Kind.Parameter)
        {
            ParameterExpression parameter = (ParameterExpression) methodCall.getExpr();
            if (parameters.contains(parameter))
            {
                curPair = new Pair();
                curPair.property = fieldName(methodCall.getMethod());
                for (Expression arg : methodCall.getArgs())
                {
                    visit(arg);
                }
                pairs.add(curPair);
                cleanIndexBlock();
            }
            else
            {
                throw new IllegalExpressionException(methodCall);
            }
        }
        else
        {
            checkHasPair(methodCall);
            StringBuilder sqlSegment = curPair.sqlSegment;
            Method callMethod = methodCall.getMethod();
            Class<?> declaringClass = callMethod.getDeclaringClass();

            if (SqlFunctions.class.isAssignableFrom(declaringClass))
            {
                SqlFunctions.Ext[] exts = callMethod.getAnnotationsByType(SqlFunctions.Ext.class);
                if (exts.length == 0)
                {
                    sqlSegment.append(callMethod.getName()).append("(");
                    List<Expression> args = methodCall.getArgs();
                    for (int i = 0; i < args.size(); i++)
                    {
                        Expression arg = args.get(i);
                        visit(arg);
                        if (i < args.size() - 1) sqlSegment.append(",");
                    }
                    sqlSegment.append(")");
                }
                else
                {
                    String function = getTargetSqlFuncExt(exts).function();
                    tryReplace(methodCall, function, sqlSegment);
                }
            }
            else
            {
                if (hasParameter(methodCall))
                {
                    throw new RuntimeException(methodCall.toString());
                }
                sqlSegment.append(indexBlock());
                curPair.sqlValue.add(new SqlValue(methodCall.getValue()));
            }
        }
    }

    @Override
    public void visit(ConstantExpression constant)
    {
        checkHasPair(constant);
        curPair.sqlSegment.append(indexBlock());
        curPair.sqlValue.add(new SqlValue(constant.getValue()));
    }

    @Override
    public void visit(ReferenceExpression reference)
    {
        checkHasPair(reference);
        curPair.sqlSegment.append(indexBlock());
        curPair.sqlValue.add(new SqlValue(reference.getValue()));
    }
}
