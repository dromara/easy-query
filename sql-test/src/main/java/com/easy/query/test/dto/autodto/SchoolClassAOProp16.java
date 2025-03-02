package com.easy.query.test.dto.autodto;

import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.NavigateFlat;
import com.easy.query.core.annotation.NavigateJoin;
import com.easy.query.core.enums.RelationMappingTypeEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.test.entity.school.SchoolClass;
import lombok.Data;

import java.util.List;

/**
 * create time 2024/4/12 22:55
 * @see SchoolClass
 *
 * @author xuejiaming
 */
@Data
public class SchoolClassAOProp16 {

    //    @Column(primaryKey = true)//主键
//    private String id;
    private String name;
    //一对多 一个班级多个学生
    @Navigate(value = RelationTypeEnum.OneToMany)
    //完整配置,property忽略表示对应的主键
//    @Navigate(value = RelationTypeEnum.OneToMany,selfProperty = "id",targetProperty = "classId")
    private List<SchoolStudentAO> schoolStudents;

    /**
     * {@link  com.easy.query.test.entity.school.SchoolStudent}
     */
    @Data
    public static class  SchoolStudentAO{
        private String id;
        private String name;
        @NavigateJoin(pathAlias = "schoolStudentAddress.address")
        private String stuAddress;

//        @NavigateFlat(value = RelationMappingTypeEnum.ToMany,mappingPath = {
//                "schoolStudents","name"
//        })
//        private List<String> stuNames;


        @NavigateFlat(pathAlias = "schoolClass.name")
        private List<String> classNames;
    }
}
