package com.easy.query.core.basic.api.select.executor;

import com.easy.query.core.basic.api.select.QueryAvailable;
import com.easy.query.core.common.Chunk;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * create time 2024/7/18 23:16
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ChunkAble<T> extends QueryAvailable<T> {

    /**
     * 分批处理 支持include
     *
     * @param size  每个批次大小
     * @param chunk 如何处理,返回true表示继续返回false表示中断
     */
    void toChunkIf(int size, Predicate<List<T>> chunk);

    /**
     * 分批处理 支持include
     *
     * @param size  每个批次大小
     * @param chunk 如何处理
     */
    default void toChunk(int size, Consumer<List<T>> chunk) {
        toChunkIf(size, list -> {
            chunk.accept(list);
            return list.size() <= size;
        });
    }

    void offsetChunk(int size, SQLFuncExpression1<Chunk<List<T>>, Chunk.Offset> chunk);

}
