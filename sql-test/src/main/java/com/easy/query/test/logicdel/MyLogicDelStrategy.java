package com.easy.query.test.logicdel;

import com.easy.query.core.basic.extension.logicdel.LogicDeleteBuilder;
import com.easy.query.core.basic.extension.logicdel.abstraction.AbstractLogicDeleteStrategy;
import com.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.Predicate;
import com.easy.query.core.expression.segment.condition.predicate.ValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ValuesPredicate;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.impl.DeleteExpressionBuilder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * create time 2023/4/1 22:51
 * 文件说明
 *
 * @author xuejiaming
 */
//@Component //如果是spring
public class MyLogicDelStrategy extends AbstractLogicDeleteStrategy {
    /**
     * 允许datetime类型的属性
     */
    private final Set<Class<?>> allowTypes = new HashSet<>(Arrays.asList(LocalDateTime.class));


    @Override
    protected SQLActionExpression1<WherePredicate<Object>> getPredicateFilterExpression(LogicDeleteBuilder builder, String propertyName) {
        return o -> o.isNull(propertyName);
    }

    @Override
    protected SQLActionExpression1<ColumnSetter<Object>> getDeletedSQLExpression(LogicDeleteBuilder builder, String propertyName) {
//        LocalDateTime now = LocalDateTime.now();
//        return o->o.set(lambdaProperty,now);
        //上面的是错误用法,将now值获取后那么这个now就是个固定值而不是动态值
        return o -> {
            //通过表达式解析出where的id但是只处理eq的情况in的情况自行处理
//            EntityExpressionBuilder entityExpressionBuilder = o.getSetter().getEntityExpressionBuilder();
//            DeleteExpressionBuilder deleteExpressionBuilder = (DeleteExpressionBuilder) entityExpressionBuilder;
//            PredicateSegment where = deleteExpressionBuilder.getWhere();
//            TableAvailable fromTable = entityExpressionBuilder.getFromTable().getEntityTable();
//            Object id = getIdValueFromWhere(fromTable, where);
//            System.out.println("删除的id是:"+id);
//            o.set(propertyName, LocalDateTime.now())
//                    .set("deletedUser", CurrentUserHelper.getUserId());


            //如果是主键设置为主键
//            EntityExpressionBuilder entityExpressionBuilder = o.getSetter().getEntityExpressionBuilder();
//            TableAvailable fromTable = entityExpressionBuilder.getFromTable().getEntityTable();
//            String singleKeyProperty = fromTable.getEntityMetadata().getSingleKeyProperty();
//
//            Collection<String> keyProperties = fromTable.getEntityMetadata().getKeyProperties();
//
//            o.setWithColumn(propertyName, singleKeyProperty);


            o.set(propertyName, LocalDateTime.now())
                    .set("deletedUser", CurrentUserHelper.getUserId());

        };
    }
//
//    private Object getIdValueFromWhere(TableAvailable fromTable, PredicateSegment where) {
//        List<Predicate> flatAndPredicates = where.getFlatAndPredicates();
//        for (Predicate predicate : flatAndPredicates) {
//
//            if (predicate.getTable() == fromTable &&
//                    (predicate.getOperator() == SQLPredicateCompareEnum.EQ || predicate.getOperator() == SQLPredicateCompareEnum.IN)) {
//                if (Objects.equals("id", predicate.getPropertyName())) {
//                    if (predicate.getOperator() == SQLPredicateCompareEnum.EQ) {
//                        if (predicate instanceof ValuePredicate) {
//                            ValuePredicate valuePredicate = (ValuePredicate) predicate;
//                            SQLParameter parameter = valuePredicate.getParameter();
//                            if (parameter instanceof ConstSQLParameter) {
//                                Object value = parameter.getValue();
//                                return value;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return null;
//    }

    @Override
    public String getStrategy() {
        return "MyLogicDelStrategy";
    }

    @Override
    public Set<Class<?>> allowedPropertyTypes() {
        return allowTypes;
    }
}
