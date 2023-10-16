package com.easy.query.test.kt

import com.easy.query.core.annotation.Column
import com.easy.query.core.annotation.LogicDelete
import com.easy.query.core.annotation.Table
import com.easy.query.core.annotation.UpdateIgnore
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategyEnum
import java.math.BigDecimal
import java.time.LocalDateTime

@Table("t_blog")
internal class BlogKtEntity {
    @Column(primaryKey = true)
    var id: String? = null
    var createTime: LocalDateTime? = null;
    var updateTime: LocalDateTime? = null;
    var createBy: String? = null;
    var updateBy: String? = null;

    @LogicDelete(strategy = LogicDeleteStrategyEnum.BOOLEAN)
    @UpdateIgnore
    var deleted: Boolean? = null;


    /**
     * 标题
     */
    var title: String? = null

    /**
     * 内容
     */
    @Column(large = true)
    var content: String? = null

    /**
     * 博客链接
     */
    var url: String? = null

    /**
     * 点赞数
     */
    var star: Int? = null

    /**
     * 发布时间
     */
    var publishTime: LocalDateTime? = null

    /**
     * 评分
     */
    var score: BigDecimal? = null

    /**
     * 状态
     */
    var status: Int? = null

    /**
     * 排序
     */
    var order: BigDecimal? = null
//
//    /**
//     * 是否置顶
//     */
//    var isTop: Boolean? = null

    /**
     * 是否置顶
     */
    var top: Boolean? = null
}


//    @Column(primaryKey = true)
//    private String id;
//    /**
//     * 创建时间;创建时间
//     */
//    private LocalDateTime createTime;
//    /**
//     * 修改时间;修改时间
//     */
//    private LocalDateTime updateTime;
//    /**
//     * 创建人;创建人
//     */
//    private String createBy;
//    /**
//     * 修改人;修改人
//     */
//    private String updateBy;
//    /**
//     * 是否删除;是否删除
//     */
//    @LogicDelete(strategy = LogicDeleteStrategyEnum.BOOLEAN)
//    @UpdateIgnore
//    private Boolean deleted;