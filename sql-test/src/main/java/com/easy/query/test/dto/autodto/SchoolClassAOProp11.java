package com.easy.query.test.dto.autodto;

import com.easy.query.core.annotation.NavigateFlat;
import com.easy.query.core.enums.RelationMappingTypeEnum;
import com.easy.query.core.expression.parser.core.available.MappingPath;
import com.easy.query.test.entity.school.SchoolClass;
import com.easy.query.test.entity.school.proxy.SchoolClassProxy;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * create time 2024/4/12 22:55
 * @see SchoolClass
 *
 * @author xuejiaming
 */
@Data
@FieldNameConstants(onlyExplicitlyIncluded = true)
public class SchoolClassAOProp11 {

    //    @Column(primaryKey = true)//主键
//    private String id;
    private String name;
    //一对多 一个班级多个学生
//    @Navigate(value = RelationTypeEnum.OneToMany)
    //完整配置,property忽略表示对应的主键
//    @Navigate(value = RelationTypeEnum.OneToMany,selfProperty = "id",targetProperty = "classId")


//    @NavigateFlat(value = RelationMappingTypeEnum.ToMany, mapping = "schoolStudentsIdsFlat")
//    @NavigateFlat(value = RelationMappingTypeEnum.ToMany,mapping = "schoolStudentsIdsPath")

    @FieldNameConstants.Include
    private static final MappingPath SCHOOL_STUDENT_ID_PATH= SchoolClassProxy.TABLE.schoolStudents().flatElement().id();

    @NavigateFlat(value = RelationMappingTypeEnum.ToMany, pathAlias = Fields.SCHOOL_STUDENT_ID_PATH)
    private List<String> schoolStudentsIds;
    @NavigateFlat(value = RelationMappingTypeEnum.ToMany,mappingPath = {"schoolTeachers","schoolClasses","id"})
    private List<String> schoolTeachersClassIds;
    @NavigateFlat(value = RelationMappingTypeEnum.ToMany,mappingPath = {"schoolTeachers","schoolClasses","name"})
    private List<String> schoolTeachersClassId1s;

//    @Data
//    public static class  SchoolStudentAO{
//        @Column(primaryKey = true)
//        private String id;
////        private String classId;
//        private String name;
//    }
}
