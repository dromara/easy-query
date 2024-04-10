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
    public boolean isMergeSubQuery() {
        return true;
    }

    @Override
    public void selectColumnConvert(TableAvailable table, ColumnMetadata columnMetadata, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext) {
        SQLClientApiFactory sqlClientApiFactory = runtimeContext.getSQLClientApiFactory();
        ClientQueryable<SchoolStudent> queryable = sqlClientApiFactory.createQueryable(SchoolStudent.class, runtimeContext);
        ClientQueryable<Long> studentSizeQuery = queryable.where(t -> t.eq(new SimpleEntitySQLTableOwner<>(table), "classId", "id"))
                .select(Long.class,s -> s.columnCount("id"));

        sqlPropertyConverter.sqlNativeSegment("{0}",context->{
            context.expression(studentSizeQuery);
            context.setAlias(columnMetadata.getName());
        });
    }
    @Override
    public void propertyColumnConvert(TableAvailable table, ColumnMetadata columnMetadata, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext) {
        SQLClientApiFactory sqlClientApiFactory = runtimeContext.getSQLClientApiFactory();
        ClientQueryable<SchoolStudent> queryable = sqlClientApiFactory.createQueryable(SchoolStudent.class, runtimeContext);
        ClientQueryable<Long> studentSizeQuery = queryable.where(t -> t.eq(new SimpleEntitySQLTableOwner<>(table), "classId", "id"))
                .select(Long.class,s -> s.columnCount("id"));

        sqlPropertyConverter.sqlNativeSegment("{0}",context->{
            context.expression(studentSizeQuery);
        });
    }

    @Override
    public void valueConvert(TableAvailable table, ColumnMetadata columnMetadata, SQLParameter sqlParameter, SQLPropertyConverter sqlPropertyConverter, QueryRuntimeContext runtimeContext, boolean isCompareValue) {
        sqlPropertyConverter.sqlNativeSegment("{0}",context->{
            context.value(sqlParameter);
        });
    }
}
