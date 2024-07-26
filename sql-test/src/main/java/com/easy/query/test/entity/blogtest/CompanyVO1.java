package com.easy.query.test.entity.blogtest;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.NavigateFlat;
import com.easy.query.core.expression.parser.core.available.MappingPath;
import com.easy.query.test.entity.blogtest.proxy.CompanyProxy;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2024/7/24 08:02
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class CompanyVO1 {
    private String id;
    private String name;
    private LocalDateTime createTime;

    private static final MappingPath USE_AGES_PATH = CompanyProxy.TABLE.users().flatElement().age();
    @NavigateFlat(pathAlias = "USE_AGES_PATH")
    private List<Integer> useAges;
}
