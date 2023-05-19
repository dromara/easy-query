package com.easy.query.core.util;

import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLLikeEnum;

import java.util.List;

/**
 * @FileName: SQLUtil.java
 * @Description: 文件说明
 * @Date: 2023/3/9 22:40
 * @author xuejiaming
 */
public class EasySQLUtil {
    private EasySQLUtil() {
    }

    public static String getLikeParameter(Object val, SQLLikeEnum sqlLike) {
       switch (sqlLike){
           case LIKE_START:return val+"%";
           case LIKE_END:return "%"+val;
           default:return "%"+val+"%";
       }
    }

    public static String sqlParameterToString(List<SQLParameter> sqlParameters) {
        if (sqlParameters == null) {
            return EasyStringUtil.EMPTY;
        }
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (SQLParameter sqlParameter : sqlParameters) {
            if (sqlParameter instanceof EasyConstSQLParameter) {
                Object param = sqlParameter.getValue();
                if (i++ != 0) {
                    builder.append(",");
                }
                builder.append(param);
                builder.append("(");
                builder.append(param == null ? "null" : EasyClassUtil.getInstanceSimpleName(param));
                builder.append(")");
            } else if (sqlParameter instanceof PropertySQLParameter) {
                String alias = sqlParameter.getTable().getAlias();
                String propertyName = sqlParameter.getPropertyName();
                String param = alias == null ? propertyName : alias + "." + propertyName;
                if (i++ != 0) {
                    builder.append(",");
                }
                builder.append(param);
            }
        }
        return builder.toString();
    }



    public static void addParameter(ToSQLContext sqlParameterCollector, SQLParameter sqlParameter){
        if(sqlParameterCollector!=null){
            sqlParameterCollector.addParameter(sqlParameter);
        }
    }
}
