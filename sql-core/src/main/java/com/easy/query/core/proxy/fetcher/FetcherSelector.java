package com.easy.query.core.proxy.fetcher;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.PropColumn;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/12/6 23:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class FetcherSelector {
    private final TableAvailable table;
    private final List<PropColumn> propColumns;

    public FetcherSelector(TableAvailable table){

        this.table = table;
        this.propColumns=new ArrayList<>();
    }

    public TableAvailable getTable() {
        return table;
    }
    public void fetchProp(PropColumn propColumn){
        propColumns.add(propColumn);
    }

    public List<PropColumn> getPropColumns() {
        return propColumns;
    }
}
