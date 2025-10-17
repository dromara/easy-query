package com.easy.query.test.duckdb;

import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.BaseTest;
import com.easy.query.test.mysql8.entity.save.M8ToMany1;
import com.easy.query.test.mysql8.entity.save.M8ToMany2;
import com.easy.query.test.mysql8.entity.save.M8ToMany3;
import org.junit.Test;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * create time 2025/9/8 09:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class M8Save4Test extends DuckDBBaseTest {
    @Test
    public void testToManyToMany1() {
        easyEntityQuery.deletable(M8ToMany1.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.a().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8ToMany2.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.d().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8ToMany3.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.g().isNotNull()).executeRows();

        invoke(context->{
            M8ToMany1 m8ToMany1 = new M8ToMany1();
            m8ToMany1.setA("a");
            m8ToMany1.setB("b");
            m8ToMany1.setC("c");
            m8ToMany1.setM8ToMany2List(new ArrayList<>());
            {
                M8ToMany2 m8ToMany2 = new M8ToMany2();
                m8ToMany2.setD("d1");
                m8ToMany2.setA("a");
                m8ToMany2.setE("e1");
                m8ToMany2.setF("f1");
                m8ToMany1.getM8ToMany2List().add(m8ToMany2);
                m8ToMany2.setM8ToMany3List(new ArrayList<>());
                {
                    M8ToMany3 m8ToMany3 = new M8ToMany3();
                    m8ToMany3.setG("g1");
                    m8ToMany3.setD("d1");
                    m8ToMany3.setH("h1");
                    m8ToMany3.setI("i1");
                    m8ToMany2.getM8ToMany3List().add(m8ToMany3);
                }
                {
                    M8ToMany3 m8ToMany3 = new M8ToMany3();
                    m8ToMany3.setG("g11");
                    m8ToMany3.setD("d1");
                    m8ToMany3.setH("h11");
                    m8ToMany3.setI("i11");
                    m8ToMany2.getM8ToMany3List().add(m8ToMany3);
                }
            }
            {
                M8ToMany2 m8ToMany2 = new M8ToMany2();
                m8ToMany2.setD("d2");
                m8ToMany2.setA("a");
                m8ToMany2.setE("e2");
                m8ToMany2.setF("f2");
                m8ToMany1.getM8ToMany2List().add(m8ToMany2);
                m8ToMany2.setM8ToMany3List(new ArrayList<>());
                {
                    M8ToMany3 m8ToMany3 = new M8ToMany3();
                    m8ToMany3.setG("g2");
                    m8ToMany3.setD("d2");
                    m8ToMany3.setH("h2");
                    m8ToMany3.setI("i2");
                    m8ToMany2.getM8ToMany3List().add(m8ToMany3);
                }
                {
                    M8ToMany3 m8ToMany3 = new M8ToMany3();
                    m8ToMany3.setG("g22");
                    m8ToMany3.setD("d2");
                    m8ToMany3.setH("h22");
                    m8ToMany3.setI("i22");
                    m8ToMany2.getM8ToMany3List().add(m8ToMany3);
                }
            }
            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8ToMany1).executeCommand();
                transaction.commit();
            }
        });

        invoke(context->{

            M8ToMany1 m8ToMany1 = easyEntityQuery.queryable(M8ToMany1.class)
                    .where(m -> {
                        m.a().eq("a");
                    })
                    .includes(m -> m.m8ToMany2List(), m2 -> {
                        m2.includes(x -> x.m8ToMany3List());
                    }).singleNotNull();

            m8ToMany1.setM8ToMany2List(new ArrayList<>());
            {
                M8ToMany2 m8ToMany2 = new M8ToMany2();
                m8ToMany2.setD("d3");
                m8ToMany2.setA("a");
                m8ToMany2.setE("e3");
                m8ToMany2.setF("f3");
                m8ToMany1.getM8ToMany2List().add(m8ToMany2);
                m8ToMany2.setM8ToMany3List(new ArrayList<>());
                {
                    M8ToMany3 m8ToMany3 = new M8ToMany3();
                    m8ToMany3.setG("g3");
                    m8ToMany3.setD("d3");
                    m8ToMany3.setH("h1");
                    m8ToMany3.setI("i1");
                    m8ToMany2.getM8ToMany3List().add(m8ToMany3);
                }
                {
                    M8ToMany3 m8ToMany3 = new M8ToMany3();
                    m8ToMany3.setG("g33");
                    m8ToMany3.setD("d3");
                    m8ToMany3.setH("h11");
                    m8ToMany3.setI("i11");
                    m8ToMany2.getM8ToMany3List().add(m8ToMany3);
                }
            }
            {
                M8ToMany2 m8ToMany2 = new M8ToMany2();
                m8ToMany2.setD("d2");
                m8ToMany2.setA("a");
                m8ToMany2.setE("e2");
                m8ToMany2.setF("f2");
                m8ToMany1.getM8ToMany2List().add(m8ToMany2);
                m8ToMany2.setM8ToMany3List(new ArrayList<>());
                {
                    M8ToMany3 m8ToMany3 = new M8ToMany3();
                    m8ToMany3.setG("g4");
                    m8ToMany3.setD("d2");
                    m8ToMany3.setH("h2");
                    m8ToMany3.setI("i2");
                    m8ToMany2.getM8ToMany3List().add(m8ToMany3);
                }
                {
                    M8ToMany3 m8ToMany3 = new M8ToMany3();
                    m8ToMany3.setG("g44");
                    m8ToMany3.setD("d2");
                    m8ToMany3.setH("h22");
                    m8ToMany3.setI("i22");
                    m8ToMany2.getM8ToMany3List().add(m8ToMany3);
                }
            }
            try (Transaction transaction = easyEntityQuery.beginTransaction()) {
                easyEntityQuery.savable(m8ToMany1).executeCommand();
                transaction.commit();
            }
        });
    }




    public void invoke(Consumer<ListenerContext> action) {

        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {
            trackManager.begin();


            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);

            action.accept(listenerContext);


        } finally {
            trackManager.release();
        }
    }
}
