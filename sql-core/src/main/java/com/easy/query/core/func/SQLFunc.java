package com.easy.query.core.func;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.SQLColumnOwner;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.concat.ColumnConcatSelector;
import com.easy.query.core.func.concat.DefaultColumnConcatSelector;
import com.easy.query.core.util.EasyArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/10/5 22:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFunc {
    default SQLFunction ifNull(String property, Object def) {
        return ifNull(null, property, def);
    }

    SQLFunction ifNull(SQLTableOwner tableOwner, String property, Object def);


    default SQLFunction dateTimeJavaFormat(String property, String javaFormat) {
        return dateTimeJavaFormat(null, property, javaFormat);
    }

    SQLFunction dateTimeJavaFormat(SQLTableOwner tableOwner, String property, String javaFormat);
    default SQLFunction dateTimeSQLFormat(String property, String format) {
        return dateTimeSQLFormat(null, property, format);
    }

    SQLFunction dateTimeSQLFormat(SQLTableOwner tableOwner, String property, String format);

    default SQLFunction concat(String property1,String property2,String... properties){
        return concat(s->{
            s.column(property1)
                    .column(property2);
            if(EasyArrayUtil.isNotEmpty(properties)){
                for (String property : properties) {
                    s.column(property);
                }
            }
        });
    }
   default SQLFunction concat(SQLExpression1<ColumnConcatSelector> sqlExpression){
       List<SQLColumnOwner> sqlColumns = new ArrayList<>();
       sqlExpression.apply(new DefaultColumnConcatSelector(sqlColumns));
        return concat(sqlColumns.toArray(new SQLColumnOwner[0]));
   }
    SQLFunction concat(SQLColumnOwner[] sqlColumns);
    default SQLFunction concatWs(String separator,String property1,String property2,String... properties){
        return concatWs(separator,s->{
            s.column(property1)
                    .column(property2);
            if(EasyArrayUtil.isNotEmpty(properties)){
                for (String property : properties) {
                    s.column(property);
                }
            }
        });
    }
   default SQLFunction concatWs(String separator,SQLExpression1<ColumnConcatSelector> sqlExpression){
       List<SQLColumnOwner> sqlColumns = new ArrayList<>();
       sqlExpression.apply(new DefaultColumnConcatSelector(sqlColumns));
        return concatWs(separator,sqlColumns.toArray(new SQLColumnOwner[0]));
   }
    SQLFunction concatWs(String separator,SQLColumnOwner[] sqlColumns);
}
