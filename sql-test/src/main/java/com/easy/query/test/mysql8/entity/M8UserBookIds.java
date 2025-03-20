package com.easy.query.test.mysql8.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.mysql.config.ToManySubqueryMySQLColumnValuesSQLStrategy;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.proxy.M8UserBookIdsProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/3/19 12:35
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
@Table("m8_user_book_ids")
@FieldNameConstants
public class M8UserBookIds implements ProxyEntityAvailable<M8UserBookIds , M8UserBookIdsProxy> {
    @Column(primaryKey = true)
    private String userId;
    private String userName;
    private Integer userAge;
    private LocalDateTime createTime;

    private String bookIds;


    @Navigate(value = RelationTypeEnum.ManyToMany, selfProperty = {M8UserBookIds.Fields.bookIds}, targetProperty = {M8UserBook2.Fields.bookId},toManySubqueryClass = ToManySubqueryMySQLColumnValuesSQLStrategy.class)
    private List<M8UserBook2> books;
}
