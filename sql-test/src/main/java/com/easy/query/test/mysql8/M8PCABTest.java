package com.easy.query.test.mysql8;

import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.test.dto.BlogQuery1Request;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.mysql8.entity.many.M8Province;
import com.easy.query.test.mysql8.vo.M8ProvinceDTO;
import org.junit.Test;

import java.util.List;

/**
 * create time 2025/7/17 22:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class M8PCABTest extends BaseTest{

    @Test
    public void testFlat(){
        List<M8ProvinceDTO> list = easyEntityQuery.queryable(M8Province.class)
                .selectAutoInclude(M8ProvinceDTO.class)
                .toList();
        for (M8ProvinceDTO m8ProvinceDTO : list) {

            for (M8ProvinceDTO.InternalCities city : m8ProvinceDTO.getCities()) {
                for (M8ProvinceDTO.InternalBuilds build : city.getBuilds()) {

                }
            }
        }
        System.out.println(list);
    }


//    @Test
//    public void test(){
//        BlogQuery1Request query = new BlogQuery1Request();
//        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
//                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
//                .where(t_blog -> {
//                    t_blog.content().like(query.getContent());
//                    t_blog.order().eq(query.getOrder());
//                    t_blog.publishTime().rangeClosed(query.getPublishTimeBegin(), query.getPublishTimeEnd());
//                    t_blog.status().in(query.getStatusList());
//                }).orderBy(t_blog -> {
//                    t_blog.createTime().asc(OrderByModeEnum.NULLS_LAST);
//                }).limit(100)
//                .toList();
//    }
}
