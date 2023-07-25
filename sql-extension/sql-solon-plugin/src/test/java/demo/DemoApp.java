package demo;

import com.easy.query.core.annotation.EasyQueryTrack;
import com.easy.query.solon.integration.QueryTrackInterceptor;
import org.noear.solon.Solon;

/**
 * create time 2023/7/25 10:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class DemoApp {
    public static void main(String[] args) {
        Solon.start(DemoApp.class,args,(app)->{
            app.cfg().loadAdd("application.yml");
            app.context().beanAroundAdd(EasyQueryTrack.class,new QueryTrackInterceptor());
        });
    }
}
