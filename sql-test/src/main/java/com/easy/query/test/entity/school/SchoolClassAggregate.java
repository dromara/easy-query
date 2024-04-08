package com.easy.query.test.entity.school;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.InsertIgnore;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.UpdateIgnore;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.conversion.StudentSizeColumnValueSQLConverter;
import com.easy.query.test.entity.school.proxy.SchoolClassAggregateProxy;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * create time 2024/4/8 13:22
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("school_class")
@Data
@ToString
@EntityFileProxy
public class SchoolClassAggregate implements ProxyEntityAvailable<SchoolClassAggregate , SchoolClassAggregateProxy> {
    @Column(primaryKey = true)//主键
    private String id;
    private String name;
    //一对多 一个班级多个学生
    @Navigate(value = RelationTypeEnum.OneToMany, targetProperty = "classId")
    //完整配置,property忽略表示对应的主键
//    @Navigate(value = RelationTypeEnum.OneToMany,selfProperty = "id",targetProperty = "classId")
    private List<SchoolStudent> schoolStudents;

    @Column(sqlConversion = StudentSizeColumnValueSQLConverter.class,realColumn = false)
    @InsertIgnore
    @UpdateIgnore
    private Long studentSize;

    @Override
    public Class<SchoolClassAggregateProxy> proxyTableClass() {
        return SchoolClassAggregateProxy.class;
    }
}
