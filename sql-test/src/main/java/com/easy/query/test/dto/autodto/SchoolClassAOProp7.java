package com.easy.query.test.dto.autodto;

import com.easy.query.core.annotation.NavigateFlat;
import com.easy.query.test.entity.school.SchoolClass;
import lombok.Data;

import java.util.List;

/**
 * create time 2024/4/12 22:55
 * {@link SchoolClass}
 *
 * @author xuejiaming
 */
@Data
public class SchoolClassAOProp7 {

    //    @Column(primaryKey = true)//主键
//    private String id;
    private String name;
    @NavigateFlat(pathAlias = "schoolTeachers.schoolClasses.name")
    private List<String> schoolTeachers;
    @NavigateFlat(pathAlias = "schoolTeachers.id")
    private List<String> schoolClasses;

//    @Data
//    public static class  SchoolStudentAO{
//        private String id;
//        private String name;
//    }
}
