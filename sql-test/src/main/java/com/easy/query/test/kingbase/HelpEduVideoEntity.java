package com.easy.query.test.kingbase;

import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EasyAssertMessage;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.kingbase.proxy.HelpEduVideoEntityProxy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 安全教育视频 实体类。
 *
 * @author xjm
 * @since 1.0
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "help_edu_video")
@EntityProxy
@EasyAssertMessage("未找到对应的安全教育视频信息")
@EasyAlias("edu_video")
@FieldNameConstants
public class HelpEduVideoEntity extends BaseEntity implements ProxyEntityAvailable<HelpEduVideoEntity , HelpEduVideoEntityProxy> {

    /**
     * 排序
     */
    private Integer orderBy;

    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {BaseEntity.Fields.id}, targetProperty = {HelpCategoryAndSubjectEntity.Fields.contextId})
    private List<HelpCategoryAndSubjectEntity> categoryAndSubjectList;

}
