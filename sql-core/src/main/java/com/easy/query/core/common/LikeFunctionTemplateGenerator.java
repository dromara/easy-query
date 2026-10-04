package com.easy.query.core.common;

import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncValueExpression;
import com.easy.query.core.func.column.ColumnPropertyExpression;
import com.easy.query.core.func.column.impl.ColumnFuncValueExpressionImpl;
import com.easy.query.core.metadata.ColumnMetadata;

import java.util.List;
import java.util.function.Function;

/**
 * create time 2026/10/4 14:47
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class LikeFunctionTemplateGenerator {
    private final List<ColumnExpression> columnExpressions;
    private final SQLLikeEnum sqlLikeEnum;
    private final Function<ColumnExpression, ColumnFuncValueExpression> columnFuncValueExpressionFunction;
    private final String escapeStartsWith;
    private final String escapeEndsWith;
    private final String escapeContainsWith;
    private final String startsWith;
    private final String endsWith;
    private final String containsWith;

    public LikeFunctionTemplateGenerator(List<ColumnExpression> columnExpressions, SQLLikeEnum sqlLikeEnum
                                         , Function<ColumnExpression,ColumnFuncValueExpression>  columnFuncValueExpressionFunction
            , String escapeStartsWith, String escapeEndsWith, String escapeContainsWith
            , String startsWith, String endsWith, String containsWith){
        this.columnExpressions = columnExpressions;
        this.sqlLikeEnum = sqlLikeEnum;
        this.columnFuncValueExpressionFunction = columnFuncValueExpressionFunction;

        this.escapeStartsWith = escapeStartsWith;
        this.escapeEndsWith = escapeEndsWith;
        this.escapeContainsWith = escapeContainsWith;
        this.startsWith = startsWith;
        this.endsWith = endsWith;
        this.containsWith = containsWith;
    }
    public String sqlSegment(TableAvailable defaultTable) {
        if (columnExpressions.size() != 2) {
            throw new IllegalArgumentException("like arguments != 2");
        }
        ColumnExpression leftColumnExpression = columnExpressions.get(0);
        ColumnExpression columnExpression = columnExpressions.get(1);
        ColumnFuncValueExpression columnFuncValueExpression = columnFuncValueExpressionFunction.apply(columnExpression);
        if (columnFuncValueExpression != null) {
            Object value = columnFuncValueExpression.getValue();
            if (value instanceof String) {
                LikeFunctionValue likeFunctionValue = getRightColumnExpressionValue(leftColumnExpression, (String) value);
                if(!likeFunctionValue.encrypt){
                    String valueString = likeFunctionValue.value;
                    if (valueString.contains("%") || valueString.contains("_")) {

                        String escapeValue = escape(valueString);//转义
                        ColumnFuncValueExpressionImpl columnFuncEscapeValueExpression = new ColumnFuncValueExpressionImpl(escapeValue);
                        columnExpressions.set(1, columnFuncEscapeValueExpression);
                        if (sqlLikeEnum == SQLLikeEnum.LIKE_PERCENT_RIGHT) {
                            return escapeStartsWith;
                        }
                        if (sqlLikeEnum == SQLLikeEnum.LIKE_PERCENT_LEFT) {
                            return escapeEndsWith;
                        }
                        return escapeContainsWith;
                    }
                }else{
                    ColumnFuncValueExpressionImpl columnFuncEscapeValueExpression = new ColumnFuncValueExpressionImpl(likeFunctionValue.value);
                    columnExpressions.set(1, columnFuncEscapeValueExpression);
                }
            }
        }
        if (sqlLikeEnum == SQLLikeEnum.LIKE_PERCENT_RIGHT) {
            return startsWith;
        }
        if (sqlLikeEnum == SQLLikeEnum.LIKE_PERCENT_LEFT) {
            return endsWith;
        }
        return containsWith;
    }

    protected abstract String escape(String value);

    private LikeFunctionValue getRightColumnExpressionValue(ColumnExpression leftColumnExpression, String value) {
        if (leftColumnExpression instanceof ColumnPropertyExpression) {
            ColumnPropertyExpression leftColumnPropertyExpression = (ColumnPropertyExpression) leftColumnExpression;
            TableAvailable leftTable = leftColumnPropertyExpression.getTableOrNull();
            if (leftTable != null) {
                String property = leftColumnPropertyExpression.getProperty();
                if (property != null) {
                    ColumnMetadata leftColumnMetadata = leftTable.getEntityMetadata().getColumnOrNull(property);
                    if (leftColumnMetadata != null && leftColumnMetadata.isSupportQueryLike()) {
                        EncryptionStrategy encryptionStrategy = leftColumnMetadata.getEncryptionStrategy();
                        Object encrypt = encryptionStrategy.encrypt(leftTable.getEntityClass(), property, value);
                        if (encrypt != null) {
                            return new LikeFunctionValue(true, encrypt.toString());
                        }
                    }
                }
            }

        }
        return new LikeFunctionValue(false, value);
    }
}
