package com.easy.query.core.basic.extension.navigate;

import com.easy.query.core.metadata.NavigateOption;

/**
 * create time 2024/3/1 15:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class NavigateBuilder {
    private final NavigateOption navigateOption;

    public NavigateBuilder(NavigateOption navigateOption){

        this.navigateOption = navigateOption;
    }

    public NavigateOption getNavigateOption() {
        return navigateOption;
    }
}
