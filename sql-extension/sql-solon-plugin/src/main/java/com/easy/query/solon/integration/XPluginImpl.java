package com.easy.query.solon.integration;

import com.easy.query.core.annotation.EasyQueryTrack;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.solon.integration.logging.Slf4jImpl;
import org.noear.solon.Utils;
import org.noear.solon.core.AppContext;
import org.noear.solon.core.BeanWrap;
import org.noear.solon.core.Plugin;
import org.noear.solon.core.VarHolder;
import org.noear.solon.core.event.AppBeanLoadEndEvent;
import org.noear.solon.core.exception.InjectionException;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create time 2023/7/19 21:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class XPluginImpl implements Plugin {
    private final Map<Object, List<VarHolder>> checkMap = new HashMap<>();
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
            injectorAddDo(varH, anno.value());
        });
        context.beanInterceptorAdd(EasyQueryTrack.class,new QueryTrackInterceptor());
        context.onEvent(AppBeanLoadEndEvent.class,event -> {
           if(!checkMap.isEmpty()){
               for(Object key : checkMap.keySet()){
                   List<VarHolder> list = checkMap.get(key);
                   for(VarHolder holder : list){
                       System.out.println("@Db inject failed:"+holder.getFullName());
                   }
               }
               throw new InjectionException("@Db inject failed");
           }
        });
    }



    private void initLogging(String logClass){

        if (EasyStringUtil.isBlank(logClass)) {
            LogFactory.useCustomLogging(Slf4jImpl.class);
        } else {
            try {
                Class<?> aClass = Class.forName(logClass);
                if (Log.class.isAssignableFrom(aClass)) {
                    LogFactory.useCustomLogging((Class<? extends Log>) aClass);
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
//    private void builderAddDo(Class<?> clz, BeanWrap wrap, String annoValue) {
//        if (clz.isInterface() == false) {
//            return;
//        }
//
//        if (Utils.isEmpty(annoValue)) {
//            wrap.context().getWrapAsync(DataSource.class, (dsBw) -> {
//                create0(clz, dsBw);
//            });
//        } else {
//            wrap.context().getWrapAsync(annoValue, (dsBw) -> {
//                if (dsBw.raw() instanceof DataSource) {
//                    create0(clz, dsBw);
//                }
//            });
//        }
//    }
    private void injectorAddDo(VarHolder varH, String annoValue) {
        Object nameOrType = Utils.isEmpty(annoValue) ? DataSource.class : annoValue;
        //记录需要注入的字段，注入完成后删除，剩下的就是注入失败的
        List<VarHolder> list = checkMap.computeIfAbsent(nameOrType, k -> new ArrayList<>());
        list.add(varH);
        varH.context().getWrapAsync(nameOrType, (dsBw) -> {
            if (dsBw.raw() instanceof DataSource) {
                inject0(varH, dsBw);
                checkMap.remove(nameOrType);
            }
        });
        /*if (Utils.isEmpty(annoValue)) {
            varH.context().getWrapAsync(DataSource.class, (dsBw) -> {
                inject0(varH, dsBw);
            });
        } else {
            varH.context().getWrapAsync(annoValue, (dsBw) -> {
                if (dsBw.raw() instanceof DataSource) {
                    inject0(varH, dsBw);
                }
            });
        }*/
    }

//    private void create0(Class<?> clz, BeanWrap dsBw) {
//        Object raw = MybatisAdapterManager.get(dsBw).getMapper(clz);
//        dsBw.context().wrapAndPut(clz, raw);
//    }
    private void inject0(VarHolder varH, BeanWrap dsBw) {
        EasyQueryHolder holder = DbManager.get(dsBw);
        if (holder != null) {
            holder.injectTo(varH);
        }
    }
}
