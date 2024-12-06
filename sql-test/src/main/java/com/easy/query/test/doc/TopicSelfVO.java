package com.easy.query.test.doc;

import com.easy.query.core.annotation.NavigateFlat;
import com.easy.query.core.annotation.NavigateJoin;
import com.easy.query.core.expression.parser.core.available.MappingPath;
import com.easy.query.test.doc.proxy.TestSelfProxy;
import lombok.Data;

/**
 * create time 2024/12/6 20:49
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class TopicSelfVO {
    private String id;

    private String name;


    private static final MappingPath UNAME1_PATH = TestSelfProxy.TABLE.myUser().name();
    @NavigateJoin(pathAlias = "UNAME1_PATH")
    private String uname1;
    private static final MappingPath UNAME2_PATH = TestSelfProxy.TABLE.parent().myUser().name();
    @NavigateJoin(pathAlias = "UNAME2_PATH")
    private String uname2;
}
