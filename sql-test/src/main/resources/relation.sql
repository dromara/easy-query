
create table school_class
(
    id varchar(32) not null comment '主键ID'primary key,
    name varchar(32) not null comment '班级名称'
)comment '班级表';


create table school_student
(
    id varchar(32) not null comment '主键ID'primary key,
    class_id varchar(32) not null comment '班级id',
    name varchar(32) not null comment '学生名称'
)comment '学生表';
create table school_student_address
(
    id varchar(32) not null comment '主键ID'primary key,
    student_id varchar(32) not null comment '学生id',
    address varchar(128) not null comment '学生地址'
)comment '学生地址表';
create table school_teacher
(
    id varchar(32) not null comment '主键ID'primary key,
    name varchar(32) not null comment '老师名称'
)comment '老师表';
create table school_class_teacher
(
    class_id varchar(32) not null comment '班级id',
    teacher_id varchar(32) not null comment '老师id',
    constraint class_teacher_id PRIMARY KEY(class_id, teacher_id)
)comment '班级老师';



create table t_province
(
    code varchar(32) not null comment '省份编号'primary key,
    name varchar(32) not null comment '省份名称'
)comment '省份';
create table t_city
(
    code varchar(32) not null comment '市区编号'primary key,
    province_code varchar(32) not null comment '省份编号',
    name varchar(32) not null comment '市区名称'
)comment '市区';
create table t_area
(
    code varchar(32) not null comment '区县编号'primary key,
    province_code varchar(32) not null comment '省份编号',
    city_code varchar(32) not null comment '市区编号',
    name varchar(32) not null comment '区县名称'
)comment '区县';


INSERT INTO t_province VALUES ('32','江苏省');
INSERT INTO t_province VALUES ('33','浙江省');

INSERT INTO t_city VALUES ('3201','32','南京市');
INSERT INTO t_city VALUES ('3202','32','无锡市');
INSERT INTO t_city VALUES ('3203','32','徐州市');
INSERT INTO t_city VALUES ('3204','32','常州市');
INSERT INTO t_city VALUES ('3205','32','苏州市');
INSERT INTO t_city VALUES ('3206','32','南通市');
INSERT INTO t_city VALUES ('3207','32','连云港市');
INSERT INTO t_city VALUES ('3208','32','淮安市');
INSERT INTO t_city VALUES ('3209','32','盐城市');
INSERT INTO t_city VALUES ('3210','32','扬州市');
INSERT INTO t_city VALUES ('3211','32','镇江市');
INSERT INTO t_city VALUES ('3212','32','泰州市');
INSERT INTO t_city VALUES ('3213','32','宿迁市');


INSERT INTO t_city VALUES ('3301','33', '杭州市');
INSERT INTO t_city VALUES ('3302','33', '宁波市');
INSERT INTO t_city VALUES ('3303','33', '温州市');
INSERT INTO t_city VALUES ('3304','33', '嘉兴市');
INSERT INTO t_city VALUES ('3305','33', '湖州市');
INSERT INTO t_city VALUES ('3306','33', '绍兴市');
INSERT INTO t_city VALUES ('3307','33', '金华市');
INSERT INTO t_city VALUES ('3308','33', '衢州市');
INSERT INTO t_city VALUES ('3309','33', '舟山市');
INSERT INTO t_city VALUES ('3310','33', '台州市');
INSERT INTO t_city VALUES ('3311','33', '丽水市');

INSERT INTO t_area VALUES ('320102', '32', '3201', '玄武区');
INSERT INTO t_area VALUES ('320104', '32', '3201', '秦淮区');
INSERT INTO t_area VALUES ('320105', '32', '3201', '建邺区');
INSERT INTO t_area VALUES ('320106', '32', '3201', '鼓楼区');
INSERT INTO t_area VALUES ('320111', '32', '3201', '浦口区');
INSERT INTO t_area VALUES ('320113', '32', '3201', '栖霞区');
INSERT INTO t_area VALUES ('320114', '32', '3201', '雨花台区');
INSERT INTO t_area VALUES ('320115', '32', '3201', '江宁区');
INSERT INTO t_area VALUES ('320116', '32', '3201', '六合区');
INSERT INTO t_area VALUES ('320117', '32', '3201', '溧水区');
INSERT INTO t_area VALUES ('320118', '32', '3201', '高淳区');
INSERT INTO t_area VALUES ('320205', '32', '3202', '锡山区');
INSERT INTO t_area VALUES ('320206', '32', '3202', '惠山区');
INSERT INTO t_area VALUES ('320211', '32', '3202', '滨湖区');
INSERT INTO t_area VALUES ('320213', '32', '3202', '梁溪区');
INSERT INTO t_area VALUES ('320214', '32', '3202', '新吴区');
INSERT INTO t_area VALUES ('320281', '32', '3202', '江阴市');
INSERT INTO t_area VALUES ('320282', '32', '3202', '宜兴市');
INSERT INTO t_area VALUES ('320302', '32', '3203', '鼓楼区');
INSERT INTO t_area VALUES ('320303', '32', '3203', '云龙区');
INSERT INTO t_area VALUES ('320305', '32', '3203', '贾汪区');
INSERT INTO t_area VALUES ('320311', '32', '3203', '泉山区');
INSERT INTO t_area VALUES ('320312', '32', '3203', '铜山区');
INSERT INTO t_area VALUES ('320321', '32', '3203', '丰县');
INSERT INTO t_area VALUES ('320322', '32', '3203', '沛县');
INSERT INTO t_area VALUES ('320324', '32', '3203', '睢宁县');
INSERT INTO t_area VALUES ('320371', '32', '3203', '徐州经济技术开发区');
INSERT INTO t_area VALUES ('320381', '32', '3203', '新沂市');
INSERT INTO t_area VALUES ('320382', '32', '3203', '邳州市');
INSERT INTO t_area VALUES ('320402', '32', '3204', '天宁区');
INSERT INTO t_area VALUES ('320404', '32', '3204', '钟楼区');
INSERT INTO t_area VALUES ('320411', '32', '3204', '新北区');
INSERT INTO t_area VALUES ('320412', '32', '3204', '武进区');
INSERT INTO t_area VALUES ('320413', '32', '3204', '金坛区');
INSERT INTO t_area VALUES ('320481', '32', '3204', '溧阳市');
INSERT INTO t_area VALUES ('320505', '32', '3205', '虎丘区');
INSERT INTO t_area VALUES ('320506', '32', '3205', '吴中区');
INSERT INTO t_area VALUES ('320507', '32', '3205', '相城区');
INSERT INTO t_area VALUES ('320508', '32', '3205', '姑苏区');
INSERT INTO t_area VALUES ('320509', '32', '3205', '吴江区');
INSERT INTO t_area VALUES ('320571', '32', '3205', '苏州工业园区');
INSERT INTO t_area VALUES ('320581', '32', '3205', '常熟市');
INSERT INTO t_area VALUES ('320582', '32', '3205', '张家港市');
INSERT INTO t_area VALUES ('320583', '32', '3205', '昆山市');
INSERT INTO t_area VALUES ('320585', '32', '3205', '太仓市');
INSERT INTO t_area VALUES ('320602', '32', '3206', '崇川区');
INSERT INTO t_area VALUES ('320611', '32', '3206', '港闸区');
INSERT INTO t_area VALUES ('320612', '32', '3206', '通州区');
INSERT INTO t_area VALUES ('320621', '32', '3206', '海安县');
INSERT INTO t_area VALUES ('320623', '32', '3206', '如东县');
INSERT INTO t_area VALUES ('320671', '32', '3206', '南通经济技术开发区');
INSERT INTO t_area VALUES ('320681', '32', '3206', '启东市');
INSERT INTO t_area VALUES ('320682', '32', '3206', '如皋市');
INSERT INTO t_area VALUES ('320684', '32', '3206', '海门市');
INSERT INTO t_area VALUES ('320703', '32', '3207', '连云区');
INSERT INTO t_area VALUES ('320706', '32', '3207', '海州区');
INSERT INTO t_area VALUES ('320707', '32', '3207', '赣榆区');
INSERT INTO t_area VALUES ('320722', '32', '3207', '东海县');
INSERT INTO t_area VALUES ('320723', '32', '3207', '灌云县');
INSERT INTO t_area VALUES ('320724', '32', '3207', '灌南县');
INSERT INTO t_area VALUES ('320771', '32', '3207', '连云港经济技术开发区');
INSERT INTO t_area VALUES ('320772', '32', '3207', '连云港高新技术产业开发区');
INSERT INTO t_area VALUES ('320803', '32', '3208', '淮安区');
INSERT INTO t_area VALUES ('320804', '32', '3208', '淮阴区');
INSERT INTO t_area VALUES ('320812', '32', '3208', '清江浦区');
INSERT INTO t_area VALUES ('320813', '32', '3208', '洪泽区');
INSERT INTO t_area VALUES ('320826', '32', '3208', '涟水县');
INSERT INTO t_area VALUES ('320830', '32', '3208', '盱眙县');
INSERT INTO t_area VALUES ('320831', '32', '3208', '金湖县');
INSERT INTO t_area VALUES ('320871', '32', '3208', '淮安经济技术开发区');
INSERT INTO t_area VALUES ('320902', '32', '3209', '亭湖区');
INSERT INTO t_area VALUES ('320903', '32', '3209', '盐都区');
INSERT INTO t_area VALUES ('320904', '32', '3209', '大丰区');
INSERT INTO t_area VALUES ('320921', '32', '3209', '响水县');
INSERT INTO t_area VALUES ('320922', '32', '3209', '滨海县');
INSERT INTO t_area VALUES ('320923', '32', '3209', '阜宁县');
INSERT INTO t_area VALUES ('320924', '32', '3209', '射阳县');
INSERT INTO t_area VALUES ('320925', '32', '3209', '建湖县');
INSERT INTO t_area VALUES ('320971', '32', '3209', '盐城经济技术开发区');
INSERT INTO t_area VALUES ('320981', '32', '3209', '东台市');
INSERT INTO t_area VALUES ('321002', '32', '3210', '广陵区');
INSERT INTO t_area VALUES ('321003', '32', '3210', '邗江区');
INSERT INTO t_area VALUES ('321012', '32', '3210', '江都区');
INSERT INTO t_area VALUES ('321023', '32', '3210', '宝应县');
INSERT INTO t_area VALUES ('321071', '32', '3210', '扬州经济技术开发区');
INSERT INTO t_area VALUES ('321081', '32', '3210', '仪征市');
INSERT INTO t_area VALUES ('321084', '32', '3210', '高邮市');
INSERT INTO t_area VALUES ('321102', '32', '3211', '京口区');
INSERT INTO t_area VALUES ('321111', '32', '3211', '润州区');
INSERT INTO t_area VALUES ('321112', '32', '3211', '丹徒区');
INSERT INTO t_area VALUES ('321171', '32', '3211', '镇江新区');
INSERT INTO t_area VALUES ('321181', '32', '3211', '丹阳市');
INSERT INTO t_area VALUES ('321182', '32', '3211', '扬中市');
INSERT INTO t_area VALUES ('321183', '32', '3211', '句容市');
INSERT INTO t_area VALUES ('321202', '32', '3212', '海陵区');
INSERT INTO t_area VALUES ('321203', '32', '3212', '高港区');
INSERT INTO t_area VALUES ('321204', '32', '3212', '姜堰区');
INSERT INTO t_area VALUES ('321271', '32', '3212', '泰州医药高新技术产业开发区');
INSERT INTO t_area VALUES ('321281', '32', '3212', '兴化市');
INSERT INTO t_area VALUES ('321282', '32', '3212', '靖江市');
INSERT INTO t_area VALUES ('321283', '32', '3212', '泰兴市');
INSERT INTO t_area VALUES ('321302', '32', '3213', '宿城区');
INSERT INTO t_area VALUES ('321311', '32', '3213', '宿豫区');
INSERT INTO t_area VALUES ('321322', '32', '3213', '沭阳县');
INSERT INTO t_area VALUES ('321323', '32', '3213', '泗阳县');
INSERT INTO t_area VALUES ('321324', '32', '3213', '泗洪县');
INSERT INTO t_area VALUES ('321371', '32', '3213', '宿迁经济技术开发区');
INSERT INTO t_area VALUES ('330102', '33', '3301', '上城区');
INSERT INTO t_area VALUES ('330103', '33', '3301', '下城区');
INSERT INTO t_area VALUES ('330104', '33', '3301', '江干区');
INSERT INTO t_area VALUES ('330105', '33', '3301', '拱墅区');
INSERT INTO t_area VALUES ('330106', '33', '3301', '西湖区');
INSERT INTO t_area VALUES ('330108', '33', '3301', '滨江区');
INSERT INTO t_area VALUES ('330109', '33', '3301', '萧山区');
INSERT INTO t_area VALUES ('330110', '33', '3301', '余杭区');
INSERT INTO t_area VALUES ('330111', '33', '3301', '富阳区');
INSERT INTO t_area VALUES ('330112', '33', '3301', '临安区');
INSERT INTO t_area VALUES ('330122', '33', '3301', '桐庐县');
INSERT INTO t_area VALUES ('330127', '33', '3301', '淳安县');
INSERT INTO t_area VALUES ('330182', '33', '3301', '建德市');
INSERT INTO t_area VALUES ('330203', '33', '3302', '海曙区');
INSERT INTO t_area VALUES ('330205', '33', '3302', '江北区');
INSERT INTO t_area VALUES ('330206', '33', '3302', '北仑区');
INSERT INTO t_area VALUES ('330211', '33', '3302', '镇海区');
INSERT INTO t_area VALUES ('330212', '33', '3302', '鄞州区');
INSERT INTO t_area VALUES ('330213', '33', '3302', '奉化区');
INSERT INTO t_area VALUES ('330225', '33', '3302', '象山县');
INSERT INTO t_area VALUES ('330226', '33', '3302', '宁海县');
INSERT INTO t_area VALUES ('330281', '33', '3302', '余姚市');
INSERT INTO t_area VALUES ('330282', '33', '3302', '慈溪市');
INSERT INTO t_area VALUES ('330302', '33', '3303', '鹿城区');
INSERT INTO t_area VALUES ('330303', '33', '3303', '龙湾区');
INSERT INTO t_area VALUES ('330304', '33', '3303', '瓯海区');
INSERT INTO t_area VALUES ('330305', '33', '3303', '洞头区');
INSERT INTO t_area VALUES ('330324', '33', '3303', '永嘉县');
INSERT INTO t_area VALUES ('330326', '33', '3303', '平阳县');
INSERT INTO t_area VALUES ('330327', '33', '3303', '苍南县');
INSERT INTO t_area VALUES ('330328', '33', '3303', '文成县');
INSERT INTO t_area VALUES ('330329', '33', '3303', '泰顺县');
INSERT INTO t_area VALUES ('330371', '33', '3303', '温州经济技术开发区');
INSERT INTO t_area VALUES ('330381', '33', '3303', '瑞安市');
INSERT INTO t_area VALUES ('330382', '33', '3303', '乐清市');
INSERT INTO t_area VALUES ('330402', '33', '3304', '南湖区');
INSERT INTO t_area VALUES ('330411', '33', '3304', '秀洲区');
INSERT INTO t_area VALUES ('330421', '33', '3304', '嘉善县');
INSERT INTO t_area VALUES ('330424', '33', '3304', '海盐县');
INSERT INTO t_area VALUES ('330481', '33', '3304', '海宁市');
INSERT INTO t_area VALUES ('330482', '33', '3304', '平湖市');
INSERT INTO t_area VALUES ('330483', '33', '3304', '桐乡市');
INSERT INTO t_area VALUES ('330502', '33', '3305', '吴兴区');
INSERT INTO t_area VALUES ('330503', '33', '3305', '南浔区');
INSERT INTO t_area VALUES ('330521', '33', '3305', '德清县');
INSERT INTO t_area VALUES ('330522', '33', '3305', '长兴县');
INSERT INTO t_area VALUES ('330523', '33', '3305', '安吉县');
INSERT INTO t_area VALUES ('330602', '33', '3306', '越城区');
INSERT INTO t_area VALUES ('330603', '33', '3306', '柯桥区');
INSERT INTO t_area VALUES ('330604', '33', '3306', '上虞区');
INSERT INTO t_area VALUES ('330624', '33', '3306', '新昌县');
INSERT INTO t_area VALUES ('330681', '33', '3306', '诸暨市');
INSERT INTO t_area VALUES ('330683', '33', '3306', '嵊州市');
INSERT INTO t_area VALUES ('330702', '33', '3307', '婺城区');
INSERT INTO t_area VALUES ('330703', '33', '3307', '金东区');
INSERT INTO t_area VALUES ('330723', '33', '3307', '武义县');
INSERT INTO t_area VALUES ('330726', '33', '3307', '浦江县');
INSERT INTO t_area VALUES ('330727', '33', '3307', '磐安县');
INSERT INTO t_area VALUES ('330781', '33', '3307', '兰溪市');
INSERT INTO t_area VALUES ('330782', '33', '3307', '义乌市');
INSERT INTO t_area VALUES ('330783', '33', '3307', '东阳市');
INSERT INTO t_area VALUES ('330784', '33', '3307', '永康市');
INSERT INTO t_area VALUES ('330802', '33', '3308', '柯城区');
INSERT INTO t_area VALUES ('330803', '33', '3308', '衢江区');
INSERT INTO t_area VALUES ('330822', '33', '3308', '常山县');
INSERT INTO t_area VALUES ('330824', '33', '3308', '开化县');
INSERT INTO t_area VALUES ('330825', '33', '3308', '龙游县');
INSERT INTO t_area VALUES ('330881', '33', '3308', '江山市');
INSERT INTO t_area VALUES ('330902', '33', '3309', '定海区');
INSERT INTO t_area VALUES ('330903', '33', '3309', '普陀区');
INSERT INTO t_area VALUES ('330921', '33', '3309', '岱山县');
INSERT INTO t_area VALUES ('330922', '33', '3309', '嵊泗县');
INSERT INTO t_area VALUES ('331002', '33', '3310', '椒江区');
INSERT INTO t_area VALUES ('331003', '33', '3310', '黄岩区');
INSERT INTO t_area VALUES ('331004', '33', '3310', '路桥区');
INSERT INTO t_area VALUES ('331022', '33', '3310', '三门县');
INSERT INTO t_area VALUES ('331023', '33', '3310', '天台县');
INSERT INTO t_area VALUES ('331024', '33', '3310', '仙居县');
INSERT INTO t_area VALUES ('331081', '33', '3310', '温岭市');
INSERT INTO t_area VALUES ('331082', '33', '3310', '临海市');
INSERT INTO t_area VALUES ('331083', '33', '3310', '玉环市');
INSERT INTO t_area VALUES ('331102', '33', '3311', '莲都区');
INSERT INTO t_area VALUES ('331121', '33', '3311', '青田县');
INSERT INTO t_area VALUES ('331122', '33', '3311', '缙云县');
INSERT INTO t_area VALUES ('331123', '33', '3311', '遂昌县');
INSERT INTO t_area VALUES ('331124', '33', '3311', '松阳县');
INSERT INTO t_area VALUES ('331125', '33', '3311', '云和县');
INSERT INTO t_area VALUES ('331126', '33', '3311', '庆元县');
INSERT INTO t_area VALUES ('331127', '33', '3311', '景宁畲族自治县');
INSERT INTO t_area VALUES ('331181', '33', '3311', '龙泉市');








create table my_school_class
(
    id varchar(32) not null comment '主键ID'primary key,
    name varchar(32) not null comment '班级名称'
)comment '班级表';


create table my_school_student
(
    id varchar(32) not null comment '主键ID'primary key,
    class_id varchar(32) not null comment '班级id',
    name varchar(32) not null comment '学生名称'
)comment '学生表';
create table my_school_student_address
(
    id varchar(32) not null comment '主键ID'primary key,
    student_id varchar(32) not null comment '学生id',
    address varchar(128) not null comment '学生地址'
)comment '学生地址表';
create table my_school_teacher
(
    id varchar(32) not null comment '主键ID'primary key,
    name varchar(32) not null comment '老师名称'
)comment '老师表';
create table my_school_class_teacher
(
    class_id varchar(32) not null comment '班级id',
    teacher_id varchar(32) not null comment '老师id',
    constraint class_teacher_id PRIMARY KEY(class_id, teacher_id)
)comment '班级老师';