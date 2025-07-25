package com.easy.query.test.mysql8.vo;


import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.NavigateFlat;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.parser.core.available.MappingPath;
import com.easy.query.test.mysql8.entity.many.proxy.M8CityProxy;
import lombok.Data;

import java.util.List;

/**
 * this file automatically generated by easy-query struct dto mapping
 * 当前文件是easy-query自动生成的 结构化dto 映射
 * {@link com.easy.query.test.mysql8.entity.many.M8Province }
 *
 * @author xuejiaming
 * @easy-query-dto schema: normal
 */
@Data
public class M8ProvinceDTO2 {


    private String id;
    private String name;
    @Navigate(value = RelationTypeEnum.OneToMany)
    private List<InternalCities> cities;



    /**
     * {@link com.easy.query.test.mysql8.entity.many.M8AreaBuild }
     */
    @Data
    public static class InternalBuilds {

        private String id;
        private String aid;
        private String name;

        @NavigateFlat(pathAlias = "license.id")
        private String licenseId;


    }


    /**
     * {@link com.easy.query.test.mysql8.entity.many.M8City }
     */
    @Data
    public static class InternalCities {

        private String id;
        private String pid;
        private String name;


        private static final MappingPath BUILDS_PATH = M8CityProxy.TABLE.areas().flatElement().builds();
        @NavigateFlat(pathAlias = "BUILDS_PATH")
        private List<InternalBuilds> builds;
    }

}
