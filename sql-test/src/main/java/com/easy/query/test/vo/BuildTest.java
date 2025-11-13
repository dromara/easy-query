package com.easy.query.test.vo;

import com.easy.query.core.annotation.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create time 2025/11/13 20:17
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuildTest {
    @Column
    private String name = "123";
}
