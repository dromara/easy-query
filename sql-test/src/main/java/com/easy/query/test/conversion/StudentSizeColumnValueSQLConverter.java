package com.easy.query.test.conversion;

import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.SQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.SimpleEntitySQLTableOwner;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.test.entity.school.SchoolStudent;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2024/4/8 13:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class StudentSizeColumnValueSQLConverter implements ColumnValueSQLConverter {
    @Override
    public boolean isRealColumn() {
        return false;
    }

    @Override
    public void selectColumnConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext) {
//        SQLClientApiFactory sqlClientApiFactory = runtimeContext.getSQLClientApiFactory();
//        ClientQueryable<SchoolStudent> queryable = sqlClientApiFactory.createQueryable(SchoolStudent.class, runtimeContext);
//        ClientQueryable<Long> studentSizeQuery = queryable.where(t -> t.eq(new SimpleEntitySQLTableOwner<>(table), "classId", "id"))
//                .select(Long.class,s -> s.columnCount("id"));


        ClientQueryable<Long> studentSizeQuery = createSubQueryable(table, runtimeContext);
        sqlPropertyConverter.sqlNativeSegment("{0}",context->{
            context.expression(studentSizeQuery);
            context.setAlias(columnMetadata.getName());
        });

    }
    @Override
    public void propertyColumnConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext) {
//        SQLClientApiFactory sqlClientApiFactory = runtimeContext.getSQLClientApiFactory();
//        ClientQueryable<SchoolStudent> queryable = sqlClientApiFactory.createQueryable(SchoolStudent.class, runtimeContext);
//        ClientQueryable<Long> studentSizeQuery = queryable.where(t -> t.eq(new SimpleEntitySQLTableOwner<>(table), "classId", "id"))
//                .select(Long.class,s -> s.columnCount("id"));
        ClientQueryable<Long> studentSizeQuery = createSubQueryable(table, runtimeContext);
        sqlPropertyConverter.sqlNativeSegment("{0}",context->{
            context.expression(studentSizeQuery);
        });
    }
    private  ClientQueryable<Long> createSubQueryable(TableAvailable table, QueryRuntimeContext runtimeContext){
        SQLClientApiFactory sqlClientApiFactory = runtimeContext.getSQLClientApiFactory();
        ClientQueryable<SchoolStudent> queryable = sqlClientApiFactory.createQueryable(SchoolStudent.class, runtimeContext);
        return queryable.where(t -> t.eq(new SimpleEntitySQLTableOwner<>(table), "classId", "id"))
                .select(Long.class,s -> s.columnCount("id"));
    }

    @Override
    public void valueConvert(@NotNull TableAvailable table, @NotNull ColumnMetadata columnMetadata, @NotNull SQLParameter sqlParameter, @NotNull SQLPropertyConverter sqlPropertyConverter, @NotNull QueryRuntimeContext runtimeContext, boolean isCompareValue) {
        sqlPropertyConverter.sqlNativeSegment("{0}",context->{
            context.value(sqlParameter);
        });
    }
}
