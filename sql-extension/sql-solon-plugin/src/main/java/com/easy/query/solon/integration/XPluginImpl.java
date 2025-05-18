package com.easy.query.solon.integration;

import com.easy.query.core.annotation.EasyQueryTrack;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.solon.integration.holder.EasyQueryHolder;
import com.easy.query.solon.integration.logging.Slf4jImpl;
import org.noear.solon.Utils;
import org.noear.solon.core.AppContext;
import org.noear.solon.core.BeanWrap;
import org.noear.solon.core.Plugin;
import org.noear.solon.core.VarHolder;

import javax.sql.DataSource;

/**
 * create time 2023/7/19 21:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class XPluginImpl implements Plugin {
    @Override
    public void start(AppContext context) {
        String logClass = context.cfg().getProperty(CommonConstant.TAG + ".log-class");
        initLogging(logClass);
        // 事件监听，用于时实初始化
        context.subWrapsOfType(DataSource.class, bw -> {
            DbManager.global().reg(bw);
        });

//        context.beanBuilderAdd(com.easy.query.solon.annotation.Db.class, (clz, wrap, anno) -> {
//            builderAddDo(clz, wrap, anno.value());
//        });
        context.beanInjectorAdd(com.easy.query.solon.annotation.Db.class, (varH, anno) -> {
            varH.required(true);
            injectorAddDo(varH, anno.value());
        });
        context.beanInterceptorAdd(EasyQueryTrack.class,new QueryTrackInterceptor());
    }



    private void initLogging(String logClass){

        if (EasyStringUtil.isBlank(logClass)) {
            LogFactory.useCustomLogging(Slf4jImpl.class);
        } else {
            try {
                Class<?> aClass = Class.forName(logClass);
                if (Log.class.isAssignableFrom(aClass)) {
                    Class<? extends Log> logClass0 = EasyObjectUtil.typeCastNullable(aClass);
                    LogFactory.useCustomLogging(logClass0);
                } else {
                    LogFactory.useStdOutLogging();
                    System.out.println("cant found log:[" + logClass + "]!!!!!!");
                }
            } catch (ClassNotFoundException e) {
                System.err.println("cant found log:[" + logClass + "]!!!!!!");
                e.printStackTrace();
            }
        }
    }
    private void injectorAddDo(VarHolder varH, String annoValue) {
        if (Utils.isEmpty(annoValue)) {
            varH.context().getWrapAsync(DataSource.class, (dsBw) -> {
                inject0(varH, dsBw);
            });
        } else {
            varH.context().getWrapAsync(annoValue, (dsBw) -> {
                if (dsBw.raw() instanceof DataSource) {
                    inject0(varH, dsBw);
                }
            });
        }
    }
    private void inject0(VarHolder varH, BeanWrap dsBw) {
        EasyQueryHolder holder = DbManager.get(dsBw);
        if (holder != null) {
            holder.injectTo(varH);
        }
    }
}
