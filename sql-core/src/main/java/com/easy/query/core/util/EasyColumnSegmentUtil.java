package com.easy.query.core.util;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultSQLPropertyConverter;
import com.easy.query.core.basic.extension.generated.GeneratedKeySQLColumnGenerator;
import com.easy.query.core.basic.extension.version.VersionStrategy;
import com.easy.query.core.basic.jdbc.parameter.ConstLikeSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.PropertyTrackSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.VersionPropertySQLParameter;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2NativeSegmentImpl;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.Column2SegmentImpl;
import com.easy.query.core.expression.segment.ColumnValue2NativeSegmentImpl;
import com.easy.query.core.expression.segment.ColumnValue2Segment;
import com.easy.query.core.expression.segment.ColumnValue2SegmentImpl;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2024/12/5 09:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyColumnSegmentUtil {
    public static Column2Segment createSelectColumn2Segment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, String alias) {
        return createColumn2Segment(table, columnMetadata, expressionContext, true, alias != null);
    }

    public static Column2Segment createColumn2Segment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext) {
        return createColumn2Segment(table, columnMetadata, expressionContext, false, false);
    }

    public static Column2Segment createColumn2Segment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, boolean select, boolean ignoreAlias) {

        ColumnValueSQLConverter columnValueSQLConverter = columnMetadata.getColumnValueSQLConverter();
        if (columnValueSQLConverter == null) {
            return new Column2SegmentImpl(table, columnMetadata, expressionContext);
        } else {
            DefaultSQLPropertyConverter defaultSQLPropertyConverter = new DefaultSQLPropertyConverter(table, expressionContext, ignoreAlias);
            if (select) {
                columnValueSQLConverter.selectColumnConvert(table, columnMetadata, defaultSQLPropertyConverter, expressionContext.getRuntimeContext());
            } else {
                columnValueSQLConverter.propertyColumnConvert(table, columnMetadata, defaultSQLPropertyConverter, expressionContext.getRuntimeContext());
            }
            SQLNativeSegment sqlNativeSegment = defaultSQLPropertyConverter.getColumnSegment();
            return new Column2NativeSegmentImpl(table, columnMetadata, sqlNativeSegment, expressionContext);
        }
    }

    public static ColumnValue2Segment createColumnValue2Segment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, @Nullable VersionStrategy easyVersionStrategy) {
        GeneratedKeySQLColumnGenerator generatedSQLColumnGenerator = columnMetadata.getGeneratedSQLColumnGenerator();
        if (generatedSQLColumnGenerator != null) {
            DefaultSQLPropertyConverter sqlPropertyConverter = new DefaultSQLPropertyConverter(table, expressionContext);
            generatedSQLColumnGenerator.configure(table, columnMetadata, sqlPropertyConverter, expressionContext.getRuntimeContext());
            return new ColumnValue2NativeSegmentImpl(table, columnMetadata, expressionContext, null, sqlPropertyConverter.getColumnSegment());
        } else {
            SQLParameter sqlParameter = getPropertySQLParameter(table, columnMetadata, easyVersionStrategy);
            ColumnValueSQLConverter columnValueSQLConverter = columnMetadata.getColumnValueSQLConverter();
            if (columnValueSQLConverter == null) {
                return new ColumnValue2SegmentImpl(table, columnMetadata, expressionContext, sqlParameter);
            } else {
                DefaultSQLPropertyConverter sqlPropertyConverter = new DefaultSQLPropertyConverter(table, expressionContext);
                columnValueSQLConverter.valueConvert(table, columnMetadata, sqlParameter, sqlPropertyConverter, expressionContext.getRuntimeContext(), false);

                return new ColumnValue2NativeSegmentImpl(table, columnMetadata, expressionContext, sqlParameter, sqlPropertyConverter.getColumnSegment());
            }
        }
    }

    public static ColumnValue2Segment createColumnTrackValue2Segment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext) {
        PropertyTrackSQLParameter sqlParameter = new PropertyTrackSQLParameter(table, columnMetadata.getPropertyName(), expressionContext.getRuntimeContext());
        ColumnValueSQLConverter columnValueSQLConverter = columnMetadata.getColumnValueSQLConverter();
        if (columnValueSQLConverter == null) {
            return new ColumnValue2SegmentImpl(table, columnMetadata, expressionContext, sqlParameter);
        } else {
            DefaultSQLPropertyConverter sqlPropertyConverter = new DefaultSQLPropertyConverter(table, expressionContext);
            columnValueSQLConverter.valueConvert(table, columnMetadata, sqlParameter, sqlPropertyConverter, expressionContext.getRuntimeContext(), false);

            return new ColumnValue2NativeSegmentImpl(table, columnMetadata, expressionContext, sqlParameter, sqlPropertyConverter.getColumnSegment());
        }
    }

    private static SQLParameter getPropertySQLParameter(TableAvailable table, ColumnMetadata columnMetadata, VersionStrategy easyVersionStrategy) {
        PropertySQLParameter propertySQLParameter = new PropertySQLParameter(table, columnMetadata.getPropertyName());
        if (easyVersionStrategy != null) {
            return new VersionPropertySQLParameter(propertySQLParameter, easyVersionStrategy);
        }
        return propertySQLParameter;
    }

    public static ColumnValue2Segment createColumnCompareValue2Segment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, Object val) {

        return createColumnCompareValue2Segment(table, columnMetadata, expressionContext, val, false);
    }

    public static ColumnValue2Segment createColumnCompareValue2Segment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, Object val, boolean isLike) {

        SQLParameter sqlParameter =createSQLParameter(table,columnMetadata,val,isLike);
        ColumnValueSQLConverter columnValueSQLConverter = columnMetadata.getColumnValueSQLConverter();
        if (columnValueSQLConverter == null) {
            return new ColumnValue2SegmentImpl(table, columnMetadata, expressionContext, sqlParameter);
        } else {
            DefaultSQLPropertyConverter sqlPropertyConverter = new DefaultSQLPropertyConverter(table, expressionContext);
            columnValueSQLConverter.valueConvert(table, columnMetadata, sqlParameter, sqlPropertyConverter, expressionContext.getRuntimeContext(), true);
            return new ColumnValue2NativeSegmentImpl(table, columnMetadata, expressionContext, sqlParameter, sqlPropertyConverter.getColumnSegment());
        }
    }
    public static ColumnValue2Segment createColumnCompareValue2Segment(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, Object val, boolean isLike,boolean isCompareValue) {

        SQLParameter sqlParameter =createSQLParameter(table,columnMetadata,val,isLike);
        ColumnValueSQLConverter columnValueSQLConverter = columnMetadata.getColumnValueSQLConverter();
        if (columnValueSQLConverter == null) {
            return new ColumnValue2SegmentImpl(table, columnMetadata, expressionContext, sqlParameter);
        } else {
            DefaultSQLPropertyConverter sqlPropertyConverter = new DefaultSQLPropertyConverter(table, expressionContext);
            columnValueSQLConverter.valueConvert(table, columnMetadata, sqlParameter, sqlPropertyConverter, expressionContext.getRuntimeContext(), isCompareValue);
            return new ColumnValue2NativeSegmentImpl(table, columnMetadata, expressionContext, sqlParameter, sqlPropertyConverter.getColumnSegment());
        }
    }

    private static SQLParameter createSQLParameter(TableAvailable table, ColumnMetadata columnMetadata, Object val, boolean isLike) {

        EasyConstSQLParameter constSQLParameter = new EasyConstSQLParameter(table, columnMetadata.getPropertyName(), val);
        if (isLike) {
            return new ConstLikeSQLParameter(constSQLParameter);
        }
        return constSQLParameter;
    }
}
