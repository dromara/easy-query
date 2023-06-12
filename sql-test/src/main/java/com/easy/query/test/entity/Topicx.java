package com.easy.query.test.entity;

import com.easy.query.core.annotation.Table;

import java.time.LocalDateTime;

/**
 * create time 2023/6/12 17:20
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_topic")
public class Topicx {
    private String id;
    private Integer stars;
    private String title;
    private LocalDateTime createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
