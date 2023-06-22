package com.easy.query.test.entity.base;

import com.easy.query.api.proxy.core.SQLWhereColumnImpl;
import com.easy.query.api.proxy.core.SQLWhereColumn;
import com.easy.query.api.proxy.core.TablePP;

/**
 * create time 2023/6/21 23:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class TopicTable1 implements TablePP<TopicTable1, TopicTable1.TopicWhere, TopicTable1.TopicSelect, TopicTable1.TopicOrder, TopicTable1.TopicGroup> {





    public static class TopicWhere{
        private final SQLWhereColumnImpl<TopicWhere> predicate;
        public TopicWhere(){
            this.predicate=new SQLWhereColumnImpl<>(this);
        }
        public SQLWhereColumn<TopicWhere> id(){
            predicate.setProp("id");
            return predicate;
        }
        public SQLWhereColumn<TopicWhere> name(){
            predicate.setProp("name");
            return predicate;
        }
    }
    public static class TopicSelect{
        public TopicSelect id(){
            return this;
        }
        public TopicSelect name(){
            return this;
        }

    }
    public static class TopicOrder{

    }
    public static class TopicGroup{

    }
}
