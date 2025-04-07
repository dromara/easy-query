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
public class SchoolClassAOProp2 {

    //    @Column(primaryKey = true)//主键
//    private String id;
    private String name;
    //一对多 一个班级多个学生
//    @Navigate(value = RelationTypeEnum.OneToMany)
    //完整配置,property忽略表示对应的主键
//    @Navigate(value = RelationTypeEnum.OneToMany,selfProperty = "id",targetProperty = "classId")
    @NavigateFlat(pathAlias = "schoolStudents.id")
    private List<String> schoolStudentsIds;
    @NavigateFlat(pathAlias = "schoolTeachers.schoolClasses.id")
    private List<String> schoolTeachersClassIds;

//    @Data
//    public static class  SchoolStudentAO{
//        @Column(primaryKey = true)
//        private String id;
////        private String classId;
//        private String name;
//    }
}
