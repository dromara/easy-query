//package com.easy.query.core.api.pagination;
//
//import com.easy.query.core.basic.api.select.Query;
//import com.easy.query.core.basic.pagination.EasyPageResultProvider;
//import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
//
//import java.util.Collections;
//import java.util.List;
//
///**
// * create time 2023/9/30 17:12
// * 文件说明
// *
// * @author xuejiaming
// */
//public class DefaultPager<TEntity> implements Pager<TEntity,EasyPageResult<TEntity>>{
//    private final Class<TEntity> clazz;
//    private final long pageIndex;
//    private final long pageSize;
//    private final long pageTotal;
//
//    public DefaultPager(Class<TEntity> clazz,long pageIndex, long pageSize, long pageTotal){
//
//        this.clazz = clazz;
//        this.pageIndex = pageIndex;
//        this.pageSize = pageSize;
//        this.pageTotal = pageTotal;
//    }
//    @Override
//    public EasyPageResult<TEntity> toResult(Query<TEntity> query) {
//        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = query.getSQLEntityExpressionBuilder();
//        //设置每次获取多少条
//        long take = this.pageSize <= 0 ? 1 : this.pageSize;
//        //设置当前页码最小1
//        long index = this.pageIndex <= 0 ? 1 : this.pageIndex;
//        //需要跳过多少条
//        long offset = (index - 1) * take;
//        long total = this.pageTotal < 0 ? query.count() : this.pageTotal;
//        EasyPageResultProvider easyPageResultProvider = sqlEntityExpressionBuilder.getRuntimeContext().getEasyPageResultProvider();
//        if (total <= offset) {
//            return easyPageResultProvider.createPageResult(this.pageIndex, this.pageSize, total, Collections.emptyList());
//        }//获取剩余条数
//        long remainingCount = total - offset;
//        //当剩余条数小于take数就取remainingCount
//        long realTake = Math.min(remainingCount, take);
//        if (realTake <= 0) {
//            return easyPageResultProvider.createPageResult(this.pageIndex, this.pageSize, total, Collections.emptyList());
//        }
//        query.limit(offset, realTake);
//        List<TEntity> data = query.limit(offset, realTake).toList(this.clazz);
//        return easyPageResultProvider.createPageResult(this.pageIndex, this.pageSize, total, data);
//    }
//}
