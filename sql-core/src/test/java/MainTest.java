
/**
 *
 * @FileName: MainTest.java
 * @Description: 文件说明
 * create time 2023/2/4 23:07
 * @author xuejiaming
 */
public class MainTest {
//    private JQDCClient client=new MySQLJQDCClient(new JDQCConfiguration());
//    private Select1<SysUser> x;
    public void Test1(){
//        SysUser sysUser = client.select(SysUser.class).eq(SysUser::getId, 1).firstOrNull();
//
//        List<SysUser> sysUsers = client.select(SysUser.class)
//                .leftJoin(SysUserExt.class, on -> on.eq(SysUser::getId, SysUserExt::getUid))
//                .selectAll(SysUser.class)
//                .selectAs(SysUserExt::getId,SysUser::getId)
//                .eq(SysUser::getId, 100)
//                .eq(SysUserExt::getId, 100)
//                .eq(SysUser::getId, SysUserExt::getUid)
//                .toList();

//        SysUserExt sysUserExt = client.select(SysUser.class)
//                .leftJoin(SysUserExt.class, (a, b) -> a.eq(b, SysUser::getName, SysUserExt::getId))
//                .where((a, b) -> a.eq(SysUser::getId, "123"))
////                .select((a,b)->a.columnAll().columnAs(SysUser::getId,SysUserExt::getId).use(b).columnAvg(SysUserExt::getId,SysUserExt::getId))
//                .skip(1).take(2)
//                .groupBy(a->a.column(SysUser::getId).column(SysUser::getName))
//                .firstOrNull();

    }
}
