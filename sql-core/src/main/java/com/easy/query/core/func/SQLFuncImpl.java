package com.easy.query.core.func;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.func.column.ColumnFuncSelectorImpl;
import com.easy.query.core.func.def.AnySQLFunction;
import com.easy.query.core.func.def.BooleanSQLFunction;
import com.easy.query.core.func.def.DistinctDefaultSQLFunction;
import com.easy.query.core.func.def.PartitionBySQLFunction;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.func.def.enums.DateTimeUnitEnum;
import com.easy.query.core.func.def.enums.MathMethodEnum;
import com.easy.query.core.func.def.enums.NumberCalcEnum;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.func.def.enums.TimeUnitEnum;
import com.easy.query.core.func.def.impl.AbsSQLFunction;
import com.easy.query.core.func.def.impl.AvgOverSQLFunction;
import com.easy.query.core.func.def.impl.AvgSQLFunction;
import com.easy.query.core.func.def.impl.BankSQLFunction;
import com.easy.query.core.func.def.impl.BooleanConstantSQLFunction;
import com.easy.query.core.func.def.impl.CastSQLFunction;
import com.easy.query.core.func.def.impl.ConcatSQLFunction;
import com.easy.query.core.func.def.impl.ConstSQLFunction;
import com.easy.query.core.func.def.impl.CountOverSQLFunction;
import com.easy.query.core.func.def.impl.CountSQLFunction;
import com.easy.query.core.func.def.impl.DateTimeDuration2SQLFunction;
import com.easy.query.core.func.def.impl.DenseRankOverSQLFunction;
import com.easy.query.core.func.def.impl.DateTime2PlusSQLFunction;
import com.easy.query.core.func.def.impl.DateTimeDurationSQLFunction;
import com.easy.query.core.func.def.impl.DateTimeFormatSQLFunction;
import com.easy.query.core.func.def.impl.DateTimePlusMonthSQLFunction;
import com.easy.query.core.func.def.impl.DateTimePlusSQLFunction;
import com.easy.query.core.func.def.impl.DateTimePlusYearSQLFunction;
import com.easy.query.core.func.def.impl.DateTimePropertySQLFunction;
import com.easy.query.core.func.def.impl.DateTimeSQLFormatSQLFunction;
import com.easy.query.core.func.def.impl.EmptySQLFunction;
import com.easy.query.core.func.def.impl.EqualsWithSQLFunction;
import com.easy.query.core.func.def.impl.IndexOfSQLFunction;
import com.easy.query.core.func.def.impl.JoiningSQLFunction;
import com.easy.query.core.func.def.impl.JsonFieldSQLFunction;
import com.easy.query.core.func.def.impl.LeftPadSQLFunction;
import com.easy.query.core.func.def.impl.LengthSQLFunction;
import com.easy.query.core.func.def.impl.LikeSQLFunction;
import com.easy.query.core.func.def.impl.MathSQLFunction;
import com.easy.query.core.func.def.impl.MaxOverSQLFunction;
import com.easy.query.core.func.def.impl.MaxSQLFunction;
import com.easy.query.core.func.def.impl.MinOverSQLFunction;
import com.easy.query.core.func.def.impl.MinSQLFunction;
import com.easy.query.core.func.def.impl.NotBankSQLFunction;
import com.easy.query.core.func.def.impl.NotEmptySQLFunction;
import com.easy.query.core.func.def.impl.NotSQLFunction;
import com.easy.query.core.func.def.impl.NowSQLFunction;
import com.easy.query.core.func.def.impl.NullDefaultSQLFunction;
import com.easy.query.core.func.def.impl.NumberCalcSQLFunction;
import com.easy.query.core.func.def.impl.OrderByNullsModeSQLFunction;
import com.easy.query.core.func.def.impl.RandomSQLFunction;
import com.easy.query.core.func.def.impl.RankOverSQLFunction;
import com.easy.query.core.func.def.impl.ReplaceSQLFunction;
import com.easy.query.core.func.def.impl.RightPadSQLFunction;
import com.easy.query.core.func.def.impl.RoundSQLFunction;
import com.easy.query.core.func.def.impl.RowNumberOverSQLFunction;
import com.easy.query.core.func.def.impl.StringCompareToSQLFunction;
import com.easy.query.core.func.def.impl.SubQueryExistsSQLFunction;
import com.easy.query.core.func.def.impl.SubQuerySQLFunction;
import com.easy.query.core.func.def.impl.SubStringSQLFunction;
import com.easy.query.core.func.def.impl.SumOverSQLFunction;
import com.easy.query.core.func.def.impl.SumSQLFunction;
import com.easy.query.core.func.def.impl.ToLowerSQLFunction;
import com.easy.query.core.func.def.impl.ToUpperSQLFunction;
import com.easy.query.core.func.def.impl.TrimEndSQLFunction;
import com.easy.query.core.func.def.impl.TrimSQLFunction;
import com.easy.query.core.func.def.impl.TrimStartSQLFunction;
import com.easy.query.core.func.def.impl.UtcNowSQLFunction;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/10/5 22:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLFuncImpl implements SQLFunc {
    protected TableAvailable getTable(SQLTableOwner tableOwner) {
        return EasyObjectUtil.getValueOrNull(tableOwner, SQLTableOwner::getTable);
    }

    protected List<ColumnExpression> getColumnExpressions(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        List<ColumnExpression> columnExpressions = new ArrayList<>();
        sqlExpression.apply(new ColumnFuncSelectorImpl(columnExpressions));
        return columnExpressions;
    }

    @Override
    public DistinctDefaultSQLFunction sum(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new SumSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public DistinctDefaultSQLFunction count(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new CountSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction max(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new MaxSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction min(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new MinSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction toLower(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new ToLowerSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction toUpper(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new ToUpperSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction subString(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new SubStringSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction indexOf(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new IndexOfSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public DistinctDefaultSQLFunction avg(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new AvgSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction nullOrDefault(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        List<ColumnExpression> columnExpressions = new ArrayList<>();
        sqlExpression.apply(new ColumnFuncSelectorImpl(columnExpressions));
        return new NullDefaultSQLFunction(columnExpressions);
    }

    @Override
    public SQLFunction abs(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new AbsSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction round(SQLTableOwner tableOwner, String property, int scale) {
        return new RoundSQLFunction(getTable(tableOwner), property, scale);
    }

    @Override
    public SQLFunction dateTimeFormat(SQLActionExpression1<ColumnFuncSelector> sqlExpression, String javaFormat) {
        return new DateTimeFormatSQLFunction(getColumnExpressions(sqlExpression), javaFormat);
    }

    @Override
    public SQLFunction dateTimeSQLFormat(SQLTableOwner tableOwner, String property, String format) {
        return new DateTimeSQLFormatSQLFunction(getTable(tableOwner), property, format);
    }

    @Override
    public SQLFunction concat(List<ColumnExpression> concatExpressions) {
        return new ConcatSQLFunction(concatExpressions);
    }

    @Override
    public SQLFunction bank(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new BankSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction notBank(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new NotBankSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction empty(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new EmptySQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction notEmpty(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new NotEmptySQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction constValue(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new ConstSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction subQueryValue(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new SubQuerySQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction exists(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new SubQueryExistsSQLFunction(getColumnExpressions(sqlExpression));
    }

    //    @Override
//    public SQLFunction join(String separator, List<ColumnExpression> concatExpressions) {
//        return new StringJoinSQLFunction(separator, concatExpressions);
//    }

    @Override
    public SQLFunction now() {
        return NowSQLFunction.INSTANCE;
    }

    @Override
    public SQLFunction utcNow() {
        return UtcNowSQLFunction.INSTANCE;
    }

    @Override
    public SQLFunction random() {
        return new RandomSQLFunction();
    }

    @Override
    public SQLFunction trim(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new TrimSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction trimStart(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new TrimStartSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction trimEnd(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new TrimEndSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction replace(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new ReplaceSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction stringCompareTo(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new StringCompareToSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction leftPad(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new LeftPadSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction rightPad(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new RightPadSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction joining(SQLActionExpression1<ColumnFuncSelector> sqlExpression, boolean distinct) {
        return new JoiningSQLFunction(getColumnExpressions(sqlExpression), distinct);
    }

    @Override
    public SQLFunction length(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new LengthSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction cast(SQLActionExpression1<ColumnFuncSelector> sqlExpression, Class<?> targetClazz) {
        return new CastSQLFunction(getColumnExpressions(sqlExpression), targetClazz);
    }

    @Override
    public SQLFunction plusDateTime(SQLActionExpression1<ColumnFuncSelector> sqlExpression, long duration, TimeUnit timeUnit) {
        return new DateTimePlusSQLFunction(getColumnExpressions(sqlExpression), duration, timeUnit);
    }

    @Override
    public SQLFunction plusDateTime2(SQLActionExpression1<ColumnFuncSelector> sqlExpression, TimeUnitEnum timeUnit) {
        return new DateTime2PlusSQLFunction(getColumnExpressions(sqlExpression), timeUnit);
    }

    @Override
    public SQLFunction plusDateTimeMonths(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new DateTimePlusMonthSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction plusDateTimeYears(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new DateTimePlusYearSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction dateTimeProperty(SQLActionExpression1<ColumnFuncSelector> sqlExpression, DateTimeUnitEnum dateTimeUnitEnum) {
        return new DateTimePropertySQLFunction(getColumnExpressions(sqlExpression), dateTimeUnitEnum);
    }

    @Override
    public SQLFunction duration(SQLActionExpression1<ColumnFuncSelector> sqlExpression, DateTimeDurationEnum durationEnum) {
        return new DateTimeDurationSQLFunction(getColumnExpressions(sqlExpression), durationEnum);
    }

    @Override
    public SQLFunction duration2(SQLActionExpression1<ColumnFuncSelector> sqlExpression, DateTimeDurationEnum durationEnum) {
        return new DateTimeDuration2SQLFunction(getColumnExpressions(sqlExpression), durationEnum);
    }

    @Override
    public SQLFunction math(SQLActionExpression1<ColumnFuncSelector> sqlExpression, MathMethodEnum mathMethodEnum) {
        return new MathSQLFunction(getColumnExpressions(sqlExpression), mathMethodEnum);
    }

    @Override
    public SQLFunction not(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new NotSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction equalsWith(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new EqualsWithSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction orderByNullsMode(SQLActionExpression1<ColumnFuncSelector> sqlExpression, boolean asc, @NotNull OrderByModeEnum orderByModeEnum) {
        return new OrderByNullsModeSQLFunction(getColumnExpressions(sqlExpression), asc, orderByModeEnum);
    }

    @Override
    public SQLFunction numberCalc(SQLActionExpression1<ColumnFuncSelector> sqlExpression, NumberCalcEnum numberCalcEnum) {
        return new NumberCalcSQLFunction(getColumnExpressions(sqlExpression), numberCalcEnum);
    }

    @Override
    public SQLFunction like(SQLActionExpression1<ColumnFuncSelector> sqlExpression, boolean like, SQLLikeEnum sqlLike) {
        LikeSQLFunction likeSQLFunction = new LikeSQLFunction(getColumnExpressions(sqlExpression), sqlLike);
        if (!like) {
            return not(x -> x.sqlFunc(likeSQLFunction));
        }
        return likeSQLFunction;
    }

    @Override
    public SQLFunction anySQLFunction(String sqlSegment, SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new AnySQLFunction(sqlSegment, getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction booleanSQLFunction(String sqlSegment, SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new BooleanSQLFunction(anySQLFunction(sqlSegment, sqlExpression));
    }

    @Override
    public PartitionBySQLFunction rowNumberOver(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new RowNumberOverSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public PartitionBySQLFunction rankNumberOver(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new RankOverSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public PartitionBySQLFunction denseRankNumberOver(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new DenseRankOverSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public PartitionBySQLFunction countOver(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new CountOverSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public PartitionBySQLFunction sumOver(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new SumOverSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public PartitionBySQLFunction avgOver(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new AvgOverSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public PartitionBySQLFunction maxOver(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new MaxOverSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public PartitionBySQLFunction minOver(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new MinOverSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction jsonField(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new JsonFieldSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction containsField(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLFunction booleanConstantSQLFunction(boolean trueOrFalse) {
        return new BooleanConstantSQLFunction(trueOrFalse);
    }
}
