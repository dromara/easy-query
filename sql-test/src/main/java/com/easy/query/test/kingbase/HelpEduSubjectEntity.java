package com.easy.query.test.kingbase;

import com.easy.query.core.annotation.EasyAssertMessage;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.OrderByProperty;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.OrderByPropertyModeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.kingbase.proxy.HelpEduSubjectEntityProxy;
import com.easy.query.test.kingbase.proxy.HelpEduVideoEntityProxy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 在线学习专题 实体类。
 *
 * @author yaq
 * @since 1.0
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "help_edu_subject")
@EntityProxy
@EasyAssertMessage("未找到对应的在线学习配置信息")
public class HelpEduSubjectEntity extends BaseEntity implements ProxyEntityAvailable<HelpEduSubjectEntity, HelpEduSubjectEntityProxy> {
    @Length(max = 128, message = "专题名称过长")
    private String name;
    private Integer orderBy;



    @Navigate(value = RelationTypeEnum.ManyToMany, mappingClass = HelpCategoryAndSubjectEntity.class, selfMappingProperty = "subjectId", targetMappingProperty = "contextId",
    orderByProps = {
            @OrderByProperty(property = "orderBy")
    })
    private List<HelpEduVideoEntity> helpEduVideoList;
}
