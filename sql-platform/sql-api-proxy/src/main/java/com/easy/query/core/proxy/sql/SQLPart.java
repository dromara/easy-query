package com.easy.query.core.proxy.sql;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.part.proxy.Part10Proxy;
import com.easy.query.core.proxy.part.proxy.Part1Proxy;
import com.easy.query.core.proxy.part.proxy.Part2Proxy;
import com.easy.query.core.proxy.part.proxy.Part3Proxy;
import com.easy.query.core.proxy.part.proxy.Part4Proxy;
import com.easy.query.core.proxy.part.proxy.Part5Proxy;
import com.easy.query.core.proxy.part.proxy.Part6Proxy;
import com.easy.query.core.proxy.part.proxy.Part7Proxy;
import com.easy.query.core.proxy.part.proxy.Part8Proxy;
import com.easy.query.core.proxy.part.proxy.Part9Proxy;

/**
 * create time 2024/8/4 21:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLPart {
    public <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TSourceProxy extends ProxyEntity<TSourceProxy, TSource>, TSource> Part1Proxy<TKey1Proxy, TKey1, TSourceProxy, TSource> of(TSourceProxy tSourceProxy, TKey1Proxy column1) {
        Part1Proxy<TKey1Proxy, TKey1, TSourceProxy, TSource> part1Proxy = new Part1Proxy<>(tSourceProxy);
        part1Proxy.fetch(0, column1, part1Proxy.partColumn1());
        return part1Proxy;
    }

    public <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TKey2Proxy extends PropTypeColumn<TKey2>, TKey2, TSourceProxy extends ProxyEntity<TSourceProxy, TSource>, TSource> Part2Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TSourceProxy, TSource> of(TSourceProxy tSourceProxy, TKey1Proxy column1, TKey2Proxy column2) {
        Part2Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TSourceProxy, TSource> part2Proxy = new Part2Proxy<>(tSourceProxy);
        part2Proxy.fetch(0, column1, part2Proxy.partColumn1());
        part2Proxy.fetch(1, column2, part2Proxy.partColumn2());
        return part2Proxy;
    }

    public <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
            TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,
            TKey3Proxy extends PropTypeColumn<TKey3>, TKey3,
            TSourceProxy extends ProxyEntity<TSourceProxy, TSource>, TSource>
    Part3Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TSourceProxy, TSource>
    of(TSourceProxy tSourceProxy, TKey1Proxy column1, TKey2Proxy column2, TKey3Proxy column3) {
        Part3Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TSourceProxy, TSource> part3Proxy =
                new Part3Proxy<>(tSourceProxy);
        part3Proxy.fetch(0, column1, part3Proxy.partColumn1());
        part3Proxy.fetch(1, column2, part3Proxy.partColumn2());
        part3Proxy.fetch(2, column3, part3Proxy.partColumn3());
        return part3Proxy;
    }

    // column4
    public <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
            TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,
            TKey3Proxy extends PropTypeColumn<TKey3>, TKey3,
            TKey4Proxy extends PropTypeColumn<TKey4>, TKey4,
            TSourceProxy extends ProxyEntity<TSourceProxy, TSource>, TSource>
    Part4Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TSourceProxy, TSource>
    of(TSourceProxy tSourceProxy, TKey1Proxy column1, TKey2Proxy column2, TKey3Proxy column3, TKey4Proxy column4) {
        Part4Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TSourceProxy, TSource> part4Proxy =
                new Part4Proxy<>(tSourceProxy);
        part4Proxy.fetch(0, column1, part4Proxy.partColumn1());
        part4Proxy.fetch(1, column2, part4Proxy.partColumn2());
        part4Proxy.fetch(2, column3, part4Proxy.partColumn3());
        part4Proxy.fetch(3, column4, part4Proxy.partColumn4());
        return part4Proxy;
    }

    // column5
    public <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
            TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,
            TKey3Proxy extends PropTypeColumn<TKey3>, TKey3,
            TKey4Proxy extends PropTypeColumn<TKey4>, TKey4,
            TKey5Proxy extends PropTypeColumn<TKey5>, TKey5,
            TSourceProxy extends ProxyEntity<TSourceProxy, TSource>, TSource>
    Part5Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TSourceProxy, TSource>
    of(TSourceProxy tSourceProxy, TKey1Proxy column1, TKey2Proxy column2, TKey3Proxy column3, TKey4Proxy column4, TKey5Proxy column5) {
        Part5Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TSourceProxy, TSource> part5Proxy =
                new Part5Proxy<>(tSourceProxy);
        part5Proxy.fetch(0, column1, part5Proxy.partColumn1());
        part5Proxy.fetch(1, column2, part5Proxy.partColumn2());
        part5Proxy.fetch(2, column3, part5Proxy.partColumn3());
        part5Proxy.fetch(3, column4, part5Proxy.partColumn4());
        part5Proxy.fetch(4, column5, part5Proxy.partColumn5());
        return part5Proxy;
    }

    // 继续按相同模式补充 column6 到 column10 的方法...

    public <
            TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
            TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,
            TKey3Proxy extends PropTypeColumn<TKey3>, TKey3,
            TKey4Proxy extends PropTypeColumn<TKey4>, TKey4,
            TKey5Proxy extends PropTypeColumn<TKey5>, TKey5,
            TKey6Proxy extends PropTypeColumn<TKey6>, TKey6,
            TSourceProxy extends ProxyEntity<TSourceProxy, TSource>, TSource
            > Part6Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TSourceProxy, TSource>
    of(TSourceProxy tSourceProxy,
       TKey1Proxy column1,
       TKey2Proxy column2,
       TKey3Proxy column3,
       TKey4Proxy column4,
       TKey5Proxy column5,
       TKey6Proxy column6) {

        Part6Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TSourceProxy, TSource> part6Proxy =
                new Part6Proxy<>(tSourceProxy);
        part6Proxy.fetch(0, column1, part6Proxy.partColumn1());
        part6Proxy.fetch(1, column2, part6Proxy.partColumn2());
        part6Proxy.fetch(2, column3, part6Proxy.partColumn3());
        part6Proxy.fetch(3, column4, part6Proxy.partColumn4());
        part6Proxy.fetch(4, column5, part6Proxy.partColumn5());
        part6Proxy.fetch(5, column6, part6Proxy.partColumn6());
        return part6Proxy;
    }

    public <
            TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
            TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,
            TKey3Proxy extends PropTypeColumn<TKey3>, TKey3,
            TKey4Proxy extends PropTypeColumn<TKey4>, TKey4,
            TKey5Proxy extends PropTypeColumn<TKey5>, TKey5,
            TKey6Proxy extends PropTypeColumn<TKey6>, TKey6,
            TKey7Proxy extends PropTypeColumn<TKey7>, TKey7,
            TSourceProxy extends ProxyEntity<TSourceProxy, TSource>, TSource
            > Part7Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TSourceProxy, TSource>
    of(TSourceProxy tSourceProxy,
       TKey1Proxy column1,
       TKey2Proxy column2,
       TKey3Proxy column3,
       TKey4Proxy column4,
       TKey5Proxy column5,
       TKey6Proxy column6,
       TKey7Proxy column7) {

        Part7Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TSourceProxy, TSource> part7Proxy =
                new Part7Proxy<>(tSourceProxy);
        part7Proxy.fetch(0, column1, part7Proxy.partColumn1());
        part7Proxy.fetch(1, column2, part7Proxy.partColumn2());
        part7Proxy.fetch(2, column3, part7Proxy.partColumn3());
        part7Proxy.fetch(3, column4, part7Proxy.partColumn4());
        part7Proxy.fetch(4, column5, part7Proxy.partColumn5());
        part7Proxy.fetch(5, column6, part7Proxy.partColumn6());
        part7Proxy.fetch(6, column7, part7Proxy.partColumn7());
        return part7Proxy;
    }

    public <TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
            TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,
            TKey3Proxy extends PropTypeColumn<TKey3>, TKey3,
            TKey4Proxy extends PropTypeColumn<TKey4>, TKey4,
            TKey5Proxy extends PropTypeColumn<TKey5>, TKey5,
            TKey6Proxy extends PropTypeColumn<TKey6>, TKey6,
            TKey7Proxy extends PropTypeColumn<TKey7>, TKey7,
            TKey8Proxy extends PropTypeColumn<TKey8>, TKey8,
            TSourceProxy extends ProxyEntity<TSourceProxy, TSource>, TSource
            > Part8Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8, TSourceProxy, TSource>
    of(TSourceProxy tSourceProxy,
       TKey1Proxy column1,
       TKey2Proxy column2,
       TKey3Proxy column3,
       TKey4Proxy column4,
       TKey5Proxy column5,
       TKey6Proxy column6,
       TKey7Proxy column7,
       TKey8Proxy column8) {

        Part8Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8, TSourceProxy, TSource> part8Proxy =
                new Part8Proxy<>(tSourceProxy);
        part8Proxy.fetch(0, column1, part8Proxy.partColumn1());
        part8Proxy.fetch(1, column2, part8Proxy.partColumn2());
        part8Proxy.fetch(2, column3, part8Proxy.partColumn3());
        part8Proxy.fetch(3, column4, part8Proxy.partColumn4());
        part8Proxy.fetch(4, column5, part8Proxy.partColumn5());
        part8Proxy.fetch(5, column6, part8Proxy.partColumn6());
        part8Proxy.fetch(6, column7, part8Proxy.partColumn7());
        part8Proxy.fetch(7, column8, part8Proxy.partColumn8());
        return part8Proxy;
    }

    public <
            TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
            TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,
            TKey3Proxy extends PropTypeColumn<TKey3>, TKey3,
            TKey4Proxy extends PropTypeColumn<TKey4>, TKey4,
            TKey5Proxy extends PropTypeColumn<TKey5>, TKey5,
            TKey6Proxy extends PropTypeColumn<TKey6>, TKey6,
            TKey7Proxy extends PropTypeColumn<TKey7>, TKey7,
            TKey8Proxy extends PropTypeColumn<TKey8>, TKey8,
            TKey9Proxy extends PropTypeColumn<TKey9>, TKey9,
            TSourceProxy extends ProxyEntity<TSourceProxy, TSource>, TSource
            > Part9Proxy<TKey1Proxy,TKey1,TKey2Proxy,TKey2,TKey3Proxy,TKey3,TKey4Proxy,TKey4,TKey5Proxy,TKey5,TKey6Proxy,TKey6,TKey7Proxy,TKey7,TKey8Proxy,TKey8,TKey9Proxy,TKey9,TSourceProxy,TSource>
    of(TSourceProxy tSourceProxy,
       TKey1Proxy column1,
       TKey2Proxy column2,
       TKey3Proxy column3,
       TKey4Proxy column4,
       TKey5Proxy column5,
       TKey6Proxy column6,
       TKey7Proxy column7,
       TKey8Proxy column8,
       TKey9Proxy column9) {

        Part9Proxy<TKey1Proxy,TKey1,TKey2Proxy,TKey2,TKey3Proxy,TKey3,TKey4Proxy,TKey4,TKey5Proxy,TKey5,TKey6Proxy,TKey6,TKey7Proxy,TKey7,TKey8Proxy,TKey8,TKey9Proxy,TKey9,TSourceProxy,TSource> part9Proxy =
                new Part9Proxy<>(tSourceProxy);
        part9Proxy.fetch(0, column1, part9Proxy.partColumn1());
        part9Proxy.fetch(1, column2, part9Proxy.partColumn2());
        part9Proxy.fetch(2, column3, part9Proxy.partColumn3());
        part9Proxy.fetch(3, column4, part9Proxy.partColumn4());
        part9Proxy.fetch(4, column5, part9Proxy.partColumn5());
        part9Proxy.fetch(5, column6, part9Proxy.partColumn6());
        part9Proxy.fetch(6, column7, part9Proxy.partColumn7());
        part9Proxy.fetch(7, column8, part9Proxy.partColumn8());
        part9Proxy.fetch(8, column9, part9Proxy.partColumn9());
        return part9Proxy;
    }

    // Column10
    public <
            TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
            TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,
            TKey3Proxy extends PropTypeColumn<TKey3>, TKey3,
            TKey4Proxy extends PropTypeColumn<TKey4>, TKey4,
            TKey5Proxy extends PropTypeColumn<TKey5>, TKey5,
            TKey6Proxy extends PropTypeColumn<TKey6>, TKey6,
            TKey7Proxy extends PropTypeColumn<TKey7>, TKey7,
            TKey8Proxy extends PropTypeColumn<TKey8>, TKey8,
            TKey9Proxy extends PropTypeColumn<TKey9>, TKey9,
            TKey10Proxy extends PropTypeColumn<TKey10>, TKey10,
            TSourceProxy extends ProxyEntity<TSourceProxy, TSource>, TSource
            > Part10Proxy<TKey1Proxy,TKey1,TKey2Proxy,TKey2,TKey3Proxy,TKey3,TKey4Proxy,TKey4,TKey5Proxy,TKey5,TKey6Proxy,TKey6,TKey7Proxy,TKey7,TKey8Proxy,TKey8,TKey9Proxy,TKey9,TKey10Proxy,TKey10,TSourceProxy,TSource>
    of(TSourceProxy tSourceProxy,
       TKey1Proxy column1,
       TKey2Proxy column2,
       TKey3Proxy column3,
       TKey4Proxy column4,
       TKey5Proxy column5,
       TKey6Proxy column6,
       TKey7Proxy column7,
       TKey8Proxy column8,
       TKey9Proxy column9,
       TKey10Proxy column10) {

        Part10Proxy<TKey1Proxy,TKey1,TKey2Proxy,TKey2,TKey3Proxy,TKey3,TKey4Proxy,TKey4,TKey5Proxy,TKey5,TKey6Proxy,TKey6,TKey7Proxy,TKey7,TKey8Proxy,TKey8,TKey9Proxy,TKey9,TKey10Proxy,TKey10,TSourceProxy,TSource> part10Proxy =
                new Part10Proxy<>(tSourceProxy);
        part10Proxy.fetch(0, column1, part10Proxy.partColumn1());
        part10Proxy.fetch(1, column2, part10Proxy.partColumn2());
        part10Proxy.fetch(2, column3, part10Proxy.partColumn3());
        part10Proxy.fetch(3, column4, part10Proxy.partColumn4());
        part10Proxy.fetch(4, column5, part10Proxy.partColumn5());
        part10Proxy.fetch(5, column6, part10Proxy.partColumn6());
        part10Proxy.fetch(6, column7, part10Proxy.partColumn7());
        part10Proxy.fetch(7, column8, part10Proxy.partColumn8());
        part10Proxy.fetch(8, column9, part10Proxy.partColumn9());
        part10Proxy.fetch(9, column10, part10Proxy.partColumn10());
        return part10Proxy;
    }
}
