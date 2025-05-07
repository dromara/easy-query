package com.easy.query.test.kingbase;

import com.easy.query.core.annotation.EasyAssertMessage;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.kingbase.proxy.HelpCategoryAndSubjectEntityProxy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.Length;


/**
 * 安全教育类目与在线学习专题中间表 实体类。
 *
 * @author yaq
 * @since 1.0
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "help_category_and_subject")
@EntityProxy
@EasyAssertMessage("未找到对应的在线学习关联信息")
@FieldNameConstants
public class HelpCategoryAndSubjectEntity extends BaseEntity implements ProxyEntityAvailable<HelpCategoryAndSubjectEntity , HelpCategoryAndSubjectEntityProxy> {

    /**
     * 安全教育类目id
     */
    private String contextId;

    /**
     * 在线学习专题id
     */
    private String subjectId;
}
