package com.easy.query.core.proxy;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/12/4 23:45
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractSelector<TProxy extends AbstractProxyEntity<TProxy,TEntity>,TEntity,TChain extends AbstractSelector<TProxy,TEntity,TChain>> implements SQLSelectAsExpression {
    protected final TProxy proxy;
    protected final List<SQLSelectAsExpression> selects = new ArrayList<>();

    public AbstractSelector(TProxy proxy) {

        this.proxy = proxy;
    }
    protected TProxy getProxy(){
        return proxy;
    }
    protected TChain add(SQLColumn<TProxy,?> column){
        selects.add(column);
        return EasyObjectUtil.typeCastNullable(this);
    }
    protected TChain add(PropColumn column){
        selects.add(SQLSelectAsExpression.createDefault(getProxy().getTable(),column.getValue()));
        return EasyObjectUtil.typeCastNullable(this);
    }
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> TChain as(SQLColumn<TRProxy,?> column) {
        SQLSelectAsExpression sqlSelectAs = EasyCollectionUtil.getLastOrNull(selects);
        if(sqlSelectAs!=null){
            if(SQLColumn.class.isAssignableFrom(sqlSelectAs.getClass())){
                SQLSelectAsExpression sqlSelectNewAs = sqlSelectAs.as(column);
                EasyCollectionUtil.replaceLast(selects,sqlSelectNewAs);
            }else {
                throw new EasyQueryInvalidOperationException("[as] preview is not SQLColumn");
            }
        }
        return EasyObjectUtil.typeCastNullable(this);
    }

    public TChain allFields(){
        selects.add(proxy.allFields());
        return EasyObjectUtil.typeCastNullable(this);
    }
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final TChain allFieldsExclude(SQLColumn<TProxy,?>... ignoreColumns){
        selects.add(proxy.allFieldsExclude(ignoreColumns));
        return EasyObjectUtil.typeCastNullable(this);
    }
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final TChain columns(SQLColumn<TProxy,?>... columns){
        for (PropColumn column : columns) {
            add(column);
        }
        return EasyObjectUtil.typeCastNullable(this);
    }
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final TChain columnExclude(SQLColumn<TProxy,?> column, SQLColumn<TProxy,?>... ignoreColumns){
        SQLSelectAsExpression columnWithout = SQLSelectAsExpression.createColumnExclude(column, ignoreColumns);
        return add(columnWithout);
    }
//
//    public SQLSelectAs fetch(){
//        return Select.of(selects.toArray(new SQLSelectAs[0]));
//    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TableAvailable getTable() {
        return getProxy().getTable();
    }

    @Override
    public void accept(GroupSelector s) {
        for (SQLSelectAsExpression select : selects) {
            select.accept(s);
        }
    }

    @Override
    public void accept(Selector s) {
        for (SQLSelectAsExpression select : selects) {
            select.accept(s);
        }
    }

    @Override
    public void accept(AsSelector f) {
        for (SQLSelectAsExpression select : selects) {
            select.accept(f);
        }
    }
}
