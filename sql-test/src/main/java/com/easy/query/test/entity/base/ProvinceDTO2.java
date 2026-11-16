package com.easy.query.test.entity.base;

import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * create time 2026/11/8 16:00
 * {@link Province}
 *
 * @author xuejiaming
 */
@Data
public class ProvinceDTO2 {

    private String name;

    private String code;

    @Navigate(value = RelationTypeEnum.OneToMany)
    private List<InternalCities> cities;


    /**
     * {@link com.easy.query.test.entity.base.City}
     **/
    @Data
    public static class InternalCities {
        private String code;
        private String provinceCode;
        private String name;
    }
}