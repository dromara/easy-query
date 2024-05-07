package com.easy.query.api4j.func;

import com.easy.query.api4j.func.column.SQLColumnFuncSelector;
import com.easy.query.api4j.func.column.SQLColumnFuncSelectorImpl;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.SQLFuncAvailable;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncSelectorImpl;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2024/5/6 16:28
 * 文件说明
 *
 * @author xuejiaming
 */
public interface LambdaSQLStringFunc<T1> extends SQLFuncAvailable {


    /**
     * 连接函数将多个列合并在一起
     * @param property1 属性列1
     * @param property2 属性列2
     * @return 链接函数
     */
    default SQLFunction concat(Property<T1, ?> property1, Property<T1, ?> property2) {
        return concat(s -> {
            SQLColumnFuncSelector<T1> s1 = EasyObjectUtil.typeCastNullable(s);
            s1.column(property1)
                    .column(property2);
        });
    }

    /**
     * 连接函数将多个列合并在一起
     *
     * @param property1 属性列1
     * @param property2 属性列2
     * @param property3 属性列3
     * @return 链接函数
     */
    default SQLFunction concat(Property<T1, ?> property1, Property<T1, ?> property2, Property<T1, ?> property3) {
        return this.<T1>concat(s -> {
            s.column(property1)
                    .column(property2)
                    .column(property3);
        });
    }

    /**
     * 链接函数表达式 将多个列合并在一起
     *
     * @param sqlExpression 指定多个属性列
     * @return 链接函数
     */
    default SQLFunction concat(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression) {
        List<ColumnExpression> columnExpressions = new ArrayList<>();
        sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(new ColumnFuncSelectorImpl(columnExpressions)));
        return concat(columnExpressions);
    }

    /**
     * 链接函数 将多个列合并在一起
     *
     * @param concatExpressions 链接列或者常量等表达式
     * @return 链接函数
     */
    default SQLFunction concat(List<ColumnExpression> concatExpressions) {
        return getSQLFunc().concat(concatExpressions);
    }


    /**
     * 列组合
     * @param property 属性列
     * @param delimiter 分隔符
     * @return 列组合函数
     */
    default SQLFunction join(Property<T1, ?> property, String delimiter){
        return join(s->{
            s.value(delimiter);
            s.column(property);
        });
    }
    /**
     * 列组合
     * @param sqlFunction sql函数
     * @param delimiter 分隔符
     * @return 列组合函数
     */
    default SQLFunction join(SQLFunction sqlFunction,String delimiter){
        return join(s->{
            s.value(delimiter);
            s.sqlFunc(sqlFunction);
        });
    }

    /**
     * 列组合
     * @param sqlExpression 属性选择函数
     * @return 列组合函数
     */
    default SQLFunction join(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
        return getSQLFunc().join(x->{
            sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
        });
    }



    default SQLFunction bank(Property<T1, ?> property){
        return bank(o->{
            o.column(property);
        });
    }
    default SQLFunction bank(SQLFunction sqlFunction){
        return bank(o->{
            o.sqlFunc(sqlFunction);
        });
    }
    default SQLFunction bank(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
        return getSQLFunc().bank(x->{
            sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
        });
    }


    default SQLFunction notBank(Property<T1, ?> property){
        return notBank(o->{
            o.column(property);
        });
    }
    default SQLFunction notBank(SQLFunction sqlFunction){
        return notBank(o->{
            o.sqlFunc(sqlFunction);
        });
    }
    default SQLFunction notBank(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
        return getSQLFunc().notBank(x->{
            sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
        });
    }



    default SQLFunction empty(Property<T1, ?> property){
        return empty(o->{
            o.column(property);
        });
    }
    default SQLFunction empty(SQLFunction sqlFunction){
        return empty(o->{
            o.sqlFunc(sqlFunction);
        });
    }
   default SQLFunction empty(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
       return getSQLFunc().empty(x->{
           sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
       });
   }
    default SQLFunction notEmpty(Property<T1, ?> property){
        return notEmpty(o->{
            o.column(property);
        });
    }
    default SQLFunction notEmpty(SQLFunction sqlFunction){
        return notEmpty(o->{
            o.sqlFunc(sqlFunction);
        });
    }
   default SQLFunction notEmpty(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
       return getSQLFunc().notEmpty(x->{
           sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
       });
   }




    /**
     * 小写
     * @param property 属性列
     * @return 小写函数
     */
    default SQLFunction toLower(Property<T1, ?> property){
        return toLower(s->{
            s.column(property);
        });
    }
    /**
     * 小写
     * @param sqlFunction sql函数
     * @return 小写函数
     */
    default SQLFunction toLower(SQLFunction sqlFunction){
        return toLower(s->{
            s.sqlFunc(sqlFunction);
        });
    }

    /**
     * 小写
     * @param sqlExpression 属性选择函数
     * @return 小写函数
     */
    default SQLFunction toLower(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
        return getSQLFunc().toLower(x->{
            sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
        });
    }

    /**
     * 大写
     * @param property 属性列
     * @return 大写函数
     */
    default SQLFunction toUpper(Property<T1, ?> property){
        return toUpper(s->{
            s.column(property);
        });
    }
    /**
     * 大写
     * @param sqlFunction sql函数
     * @return 大写函数
     */
    default SQLFunction toUpper(SQLFunction sqlFunction){
        return toUpper(s->{
            s.sqlFunc(sqlFunction);
        });
    }

    /**
     * 大写
     * @param sqlExpression 属性选择函数
     * @return 大写函数
     */
   default SQLFunction toUpper(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
       return getSQLFunc().toUpper(x->{
           sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
       });
   }


    /**
     * 截取字符串
     * @param property 属性列
     * @return 截取字符串函数
     */
    default SQLFunction subString(Property<T1, ?> property,int begin,int length){
        return subString(s->{
            s.column(property);
            s.format(begin+1);
            s.format(length);
        });
    }
    /**
     * 截取字符串
     * @param sqlFunction sql函数
     * @return 截取字符串函数
     */
    default SQLFunction subString(SQLFunction sqlFunction,int begin,int length){
        return subString(s->{
            s.sqlFunc(sqlFunction);
            s.format(begin+1);
            s.format(length);
        });
    }

    /**
     * 截取字符串
     * @param sqlExpression 属性选择函数
     * @return 截取字符串函数
     */
   default SQLFunction subString(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
       return getSQLFunc().subString(x->{
           sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
       });
   }
    /**
     * 截取字符串
     * @param property 属性列
     * @return 截取字符串函数
     */
    default SQLFunction trim(Property<T1, ?> property){
        return trim(s->{
            s.column(property);
        });
    }
    /**
     * 截取字符串
     * @param sqlFunction sql函数
     * @return 截取字符串函数
     */
    default SQLFunction trim(SQLFunction sqlFunction){
        return trim(s->{
            s.sqlFunc(sqlFunction);
        });
    }

    /**
     * 截取字符串
     * @param sqlExpression 属性选择函数
     * @return 截取字符串函数
     */
   default SQLFunction trim(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
       return getSQLFunc().trim(x->{
           sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
       });
   }


    /**
     * 截取字符串
     * @param property 属性列
     * @return 截取字符串函数
     */
    default SQLFunction trimStart(Property<T1, ?> property){
        return trimStart(s->{
            s.column(property);
        });
    }
    /**
     * 截取字符串
     * @param sqlFunction sql函数
     * @return 截取字符串函数
     */
    default SQLFunction trimStart(SQLFunction sqlFunction){
        return trimStart(s->{
            s.sqlFunc(sqlFunction);
        });
    }

    /**
     * 截取字符串
     * @param sqlExpression 属性选择函数
     * @return 截取字符串函数
     */
   default SQLFunction trimStart(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
       return getSQLFunc().trimStart(x->{
           sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
       });
   }

    /**
     * 截取字符串
     * @param property 属性列
     * @return 截取字符串函数
     */
    default SQLFunction trimEnd(Property<T1, ?> property){
        return trimEnd(s->{
            s.column(property);
        });
    }
    /**
     * 截取字符串
     * @param sqlFunction sql函数
     * @return 截取字符串函数
     */
    default SQLFunction trimEnd(SQLFunction sqlFunction){
        return trimEnd(s->{
            s.sqlFunc(sqlFunction);
        });
    }

    /**
     * 截取字符串
     * @param sqlExpression 属性选择函数
     * @return 截取字符串函数
     */
   default SQLFunction trimEnd(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
       return getSQLFunc().trimEnd(x->{
           sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
       });
   }

    /**
     * 替换字符串
     * @param property 属性列
     * @param oldValue 要被替换的字符
     * @param newValue 替换为的字符
     * @return 替换字符串函数
     */
    default SQLFunction replace(Property<T1, ?> property,String oldValue,String newValue){
        return replace(s->{
            s.column(property);
            s.value(oldValue);
            s.value(newValue);
        });
    }
    /**
     * 替换字符串
     * @param sqlFunction sql函数
     * @param oldValue 要被替换的字符
     * @param newValue 替换为的字符
     * @return 替换字符串函数
     */
    default SQLFunction replace(SQLFunction sqlFunction,String oldValue,String newValue){
        return replace(s->{
            s.sqlFunc(sqlFunction);
            s.value(oldValue);
            s.value(newValue);
        });
    }

    /**
     * 替换字符串
     * @param sqlExpression 属性选择函数
     * @return 替换字符串函数
     */
   default SQLFunction replace(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
       return getSQLFunc().replace(x->{
           sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
       });
   }

    /**
     * 替换字符串
     * @param property 属性列
     * @param comparedValue 被比较的值
     * @return 替换字符串函数
     */
    default SQLFunction stringCompareTo(Property<T1, ?> property, String comparedValue){
        return stringCompareTo(s->{
            s.column(property);
            s.value(comparedValue);
        });
    }
    default <T2> SQLFunction stringCompareTo(Property<T1, ?> property, SQLTableOwner tableOwner, Property<T2, ?> otherProperty){
        return stringCompareTo(s->{
            s.column(property);
            s.column(tableOwner,otherProperty);
        });
    }
    default SQLFunction stringCompareTo(Property<T1, ?> property, SQLFunction sqlFunction){
        return stringCompareTo(s->{
            s.column(property);
            s.sqlFunc(sqlFunction);
        });
    }
    /**
     * 比较字符串函数
     * @param sqlFunction sql函数
     * @param comparedValue 被比较的值
     * @return 比较字符串函数
     */
    default SQLFunction stringCompareTo(SQLFunction sqlFunction, String comparedValue){
        return stringCompareTo(s->{
            s.sqlFunc(sqlFunction);
            s.value(comparedValue);
        });
    }
    default <T2> SQLFunction stringCompareTo(SQLFunction sqlFunction, SQLTableOwner tableOwner, Property<T2, ?> otherProperty){
        return stringCompareTo(s->{
            s.sqlFunc(sqlFunction);
            s.column(tableOwner,otherProperty);
        });
    }
    default SQLFunction stringCompareTo(SQLFunction sqlFunction, SQLFunction comparedSQLFunction){
        return stringCompareTo(s->{
            s.sqlFunc(sqlFunction);
            s.sqlFunc(comparedSQLFunction);
        });
    }

    /**
     * 替换字符串
     * @param sqlExpression 属性选择函数
     * @return 替换字符串函数
     */
    default SQLFunction stringCompareTo(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
        return getSQLFunc().stringCompareTo(x->{
            sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
        });
    }


    /**
     * 字符串补全 左侧补齐totalWidth位数用空格补齐
     * @param property 属性列
     * @param totalWidth 补多少位
     * @return 字符串补全函数
     */
    default SQLFunction leftPad(Property<T1, ?> property, int totalWidth){
        return leftPad(s->{
            s.column(property);
            s.format(totalWidth);
        });
    }

    /**
     * 字符串补全 左侧补齐totalWidth位数用paddingChar补齐
     * @param property 属性列
     * @param totalWidth 补多少位
     * @param paddingChar 用哪个字符补
     * @return 字符串补全函数
     */
    default SQLFunction leftPad(Property<T1, ?> property, int totalWidth, char paddingChar){
        return leftPad(s->{
            s.column(property);
            s.format(totalWidth);
            s.value(String.valueOf(paddingChar));
        });
    }
    /**
     * 字符串补全 左侧补齐totalWidth位数用空格补齐
     * @param sqlFunction 要补齐的值
     * @param totalWidth 补多少位
     * @return 字符串补全函数
     */
    default SQLFunction leftPad(SQLFunction sqlFunction, int totalWidth){
        return leftPad(s->{
            s.sqlFunc(sqlFunction);
            s.format(totalWidth);
        });
    }

    /**
     * 字符串补全 左侧补齐totalWidth位数用paddingChar补齐
     * @param sqlFunction 要补齐的值
     * @param totalWidth 补多少位
     * @param paddingChar 用哪个字符补
     * @return 字符串补全函数
     */
    default SQLFunction leftPad(SQLFunction sqlFunction, int totalWidth, char paddingChar){
        return leftPad(s->{
            s.sqlFunc(sqlFunction);
            s.format(totalWidth);
            s.value(String.valueOf(paddingChar));
        });
    }

    /**
     * 字符串补齐
     * @param sqlExpression 属性选择函数
     * @return 字符串补齐函数
     */
   default SQLFunction leftPad(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
       return getSQLFunc().leftPad(x->{
           sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
       });
   }




    /**
     * 字符串补全 右侧补齐totalWidth位数用空格补齐
     * @param property 属性列
     * @param totalWidth 补多少位
     * @return 字符串补全函数
     */
    default SQLFunction rightPad(Property<T1, ?> property, int totalWidth){
        return rightPad(s->{
            s.column(property);
            s.format(totalWidth);
        });
    }

    /**
     * 字符串补全 右侧补齐totalWidth位数用paddingChar补齐
     * @param property 属性列
     * @param totalWidth 补多少位
     * @param paddingChar 用哪个字符补
     * @return 字符串补全函数
     */
    default SQLFunction rightPad(Property<T1, ?> property, int totalWidth, char paddingChar){
        return rightPad(s->{
            s.column(property);
            s.format(totalWidth);
            s.value(String.valueOf(paddingChar));
        });
    }
    /**
     * 字符串补全 右侧补齐totalWidth位数用空格补齐
     * @param sqlFunction 要补齐的值
     * @param totalWidth 补多少位
     * @return 字符串补全函数
     */
    default SQLFunction rightPad(SQLFunction sqlFunction, int totalWidth){
        return rightPad(s->{
            s.sqlFunc(sqlFunction);
            s.format(totalWidth);
        });
    }

    /**
     * 字符串补全 右侧补齐totalWidth位数用paddingChar补齐
     * @param sqlFunction 要补齐的值
     * @param totalWidth 补多少位
     * @param paddingChar 用哪个字符补
     * @return 字符串补全函数
     */
    default SQLFunction rightPad(SQLFunction sqlFunction, int totalWidth, char paddingChar){
        return rightPad(s->{
            s.sqlFunc(sqlFunction);
            s.format(totalWidth);
            s.value(String.valueOf(paddingChar));
        });
    }

    /**
     * 字符串补齐
     * @param sqlExpression 属性选择函数
     * @return 字符串补齐函数
     */
   default SQLFunction rightPad(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
       return getSQLFunc().rightPad(x->{
           sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
       });
   }

    /**
     * 长度
     * @param property 属性列
     * @return 长度函数
     */
    default SQLFunction length(Property<T1, ?> property){
        return length(s->{
            s.column(property);
        });
    }
    /**
     * 长度
     * @param sqlFunction sql函数
     * @return 长度函数
     */
    default SQLFunction length(SQLFunction sqlFunction){
        return length(s->{
            s.sqlFunc(sqlFunction);
        });
    }

    /**
     * 长度
     * @param sqlExpression 属性选择函数
     * @return 列组合函数
     */
    default SQLFunction length(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
        return getSQLFunc().length(x->{
            sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
        });
    }
}
