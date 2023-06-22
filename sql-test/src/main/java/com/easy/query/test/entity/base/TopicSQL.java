package com.easy.query.test.entity.base;

import com.easy.query.api.proxy.core.SQLQuery;
import com.easy.query.api.proxy.core.SQLSelectColumn;
import com.easy.query.api.proxy.core.SQLWhereColumn;
import com.easy.query.api.proxy.core.SQLWhereColumnImpl;
import com.easy.query.api.proxy.core.base.SQLGroup;
import com.easy.query.api.proxy.core.base.SQLOrder;
import com.easy.query.api.proxy.core.base.SQLSelect;
import com.easy.query.api.proxy.core.base.SQLSelectAs;
import com.easy.query.api.proxy.core.base.SQLSelectResult;
import com.easy.query.api.proxy.core.base.SQLWhere;
import com.easy.query.api.proxy.core.base.SQLWhereAggregate;
import com.easy.query.api.proxy.core.base.impl.SQLSelectColumnImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.test.entity.Topic;

import java.util.function.Function;

/**
 * create time 2023/6/21 17:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class TopicSQL implements SQLQuery<TopicSQL,
        TopicSQL.TopicWhere,
        TopicSQL.TopicWhereAggregate,
        TopicSQL.TopicSelect,
        TopicSQL.TopicSelectAs,
        TopicSQL.TopicSelectResult,
        TopicSQL.TopicOrder,
        TopicSQL.TopicGroup,
        Topic> {

    public static final TopicSQL DEFAULT = new TopicSQL();
    private static final Class<Topic> entityClass = Topic.class;

    private final TableAvailable table;

    private TopicSQL() {
        this.table = null;
    }

    public TopicSQL(TableAvailable table) {
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public TopicSQL create(Function<Class<Topic>, TopicSQL> creator) {
        return creator.apply(entityClass);
    }

    @Override
    public TopicWhere createWhere() {
        return null;
    }

    @Override
    public TopicWhereAggregate createWhereAggregate() {
        return null;
    }

    @Override
    public TopicSelect createSelect() {
        return null;
    }

    @Override
    public TopicSelectAs createSelectAs() {
        return null;
    }

    @Override
    public TopicSelectResult createSelectResult() {
        return null;
    }

    @Override
    public TopicOrder createOrder() {
        return null;
    }

    @Override
    public TopicGroup createGroup() {
        return null;
    }

    public static class TopicWhere implements SQLWhere {
        private final TableAvailable whereTable;
        public SQLWhereColumn<TopicWhere> id = new SQLWhereColumnImpl<>(this, "id");
        public SQLWhereColumn<TopicWhere> name = new SQLWhereColumnImpl<>(this, "name");

        public TopicWhere(TableAvailable whereTable) {
            this.whereTable = whereTable;
        }
        public TopicWhere or(){
            return this;
        }
        public TopicWhere or(SQLExpression1<TopicWhere> orExpression){
            return this;
        }
    }

    public static class TopicWhereAggregate implements SQLWhereAggregate {

    }

    public static class TopicSelect implements SQLSelect {
        public SQLSelectColumn<TopicSelect> id= new SQLSelectColumnImpl<>(this, "id");
        public SQLSelectColumn<TopicSelect> name = new SQLSelectColumnImpl<>(this, "name");
        @SafeVarargs
        public final TopicSelect columns(SQLSelectColumn<TopicSelect>... columns){
            return this;
        }

        public TopicSelect all() {
            return this;
        }

        public TopicSelect ignore(SQLSelectColumn<TopicSelect> column) {
            return this;

        }

        public <TSQLQuery2> TopicSelect as(SQLSelectColumn<TopicSelect> column1, SQLSelectColumn<TSQLQuery2> column) {
            return this;
        }
    }

    public static class TopicSelectAs implements SQLSelectAs {

    }

    public static class TopicSelectResult implements SQLSelectResult {

    }

    public static class TopicOrder implements SQLOrder {

    }

    public static class TopicGroup implements SQLGroup {

    }
}