package demo;

import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.core.annotation.EasyQueryTrack;
import com.easy.query.solon.annotation.Db;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.noear.solon.test.SolonJUnit4ClassRunner;
import org.noear.solon.test.SolonTest;

import java.util.List;
import java.util.Map;

/**
 * create time 2023/7/25 10:17
 * 文件说明
 *
 * @author xuejiaming
 */
@RunWith(SolonJUnit4ClassRunner.class)
@SolonTest(DemoApp.class)
public class DemoTest {
    @Db("db1")
    EasyQuery easyQuery;

    @EasyQueryTrack
    @Test
    public void test(){
        List<Map> maps = easyQuery.sqlQuery("select * from t_topic", Map.class);
        System.out.println(maps);
    }
}
