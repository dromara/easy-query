package com.easy.query.test.conversion;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.metadata.ColumnMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * create time 2025/11/13 20:46
 * 文件说明
 *
 * @author xuejiaming
 */
public class StarCodeValueConvert implements ValueConverter<String,String> {
    @Nullable
    @Override
    public String serialize(@Nullable String s,ColumnMetadata columnMetadata) {
        return "";
    }

    @Nullable
    @Override
    public String deserialize(@Nullable String s, ColumnMetadata columnMetadata) {
        return "**"+s+"**";
    }
}
