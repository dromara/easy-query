package com.easy.query.api.lambda.sqlext;


import com.easy.query.api.lambda.db.DbType;

import java.lang.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SqlTypes
{
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Exts
    {
        Ext[] value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(Exts.class)
    public @interface Ext
    {
        /**
         * 目标数据库
         */
        DbType dbType();

        /**
         * 类型
         */
        String type();
    }

    @Ext(dbType = DbType.H2, type = "DATE")
    @Ext(dbType = DbType.MySQL, type = "DATE")
    public static SqlType<LocalDate> date()
    {
        throw new SqlFunctionInvokeException();
    }

    @Ext(dbType = DbType.H2, type = "TIME")
    @Ext(dbType = DbType.MySQL, type = "TIME")
    public static SqlType<LocalTime> time()
    {
        throw new SqlFunctionInvokeException();
    }

    @Ext(dbType = DbType.H2, type = "DATETIME")
    @Ext(dbType = DbType.MySQL, type = "DATETIME")
    public static SqlType<LocalDateTime> dateTime()
    {
        throw new SqlFunctionInvokeException();
    }

    @Ext(dbType = DbType.H2, type = "VARCHAR")
    @Ext(dbType = DbType.MySQL, type = "VARCHAR")
    public static SqlType<String> varChar()
    {
        throw new SqlFunctionInvokeException();
    }

    @Ext(dbType = DbType.H2, type = "SIGNED")
    @Ext(dbType = DbType.MySQL, type = "SIGNED")
    public static SqlType<Integer> signed()
    {
        throw new SqlFunctionInvokeException();
    }

    @Ext(dbType = DbType.H2, type = "DECIMAL(36,16)")
    @Ext(dbType = DbType.MySQL, type = "DECIMAL(36,16)")
    public static SqlType<Double> doubled()
    {
        throw new SqlFunctionInvokeException();
    }

    @Ext(dbType = DbType.H2, type = "DECIMAL(36,18)")
    @Ext(dbType = DbType.MySQL, type = "DECIMAL(36,18)")
    public static SqlType<Double> decimal()
    {
        throw new SqlFunctionInvokeException();
    }

    public static class SqlType<T>
    {
        private SqlType()
        {

        }
    }
}
