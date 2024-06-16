package com.easy.query.api.proxy.extension.ifequals;

import com.easy.query.api.proxy.extension.casewhen.CaseWhenEntityBuilder;
import com.easy.query.api.proxy.extension.casewhen.CaseWhenThenEntityBuilder;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2024/6/16 09:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class IfEqualsThenEntityBuilder {
    private final CaseWhenThenEntityBuilder caseWhenThenEntityBuilder;

    public IfEqualsThenEntityBuilder(CaseWhenThenEntityBuilder caseWhenThenEntityBuilder){

        this.caseWhenThenEntityBuilder = caseWhenThenEntityBuilder;
    }

    public IfEqualsEntityBuilder then(Object then){
        CaseWhenEntityBuilder thenBuilder = this.caseWhenThenEntityBuilder.then(then);
        return new IfEqualsEntityBuilder(thenBuilder);
    }
//    public IfEqualsEntityBuilder then(SQLColumn<?,?> thenSQLColumn){
//        CaseWhenEntityBuilder thenBuilder = this.caseWhenThenEntityBuilder.then(thenSQLColumn);
//        return new IfEqualsEntityBuilder(thenBuilder);
//    }
//    public IfEqualsEntityBuilder then(PropTypeColumn<?> thenSQLColumn){
//        CaseWhenEntityBuilder thenBuilder = this.caseWhenThenEntityBuilder.then(thenSQLColumn);
//        return new IfEqualsEntityBuilder(thenBuilder);
//    }
//    public <T extends SQLTableOwner & DSLSQLFunctionAvailable> IfEqualsEntityBuilder then(T then){
//        CaseWhenEntityBuilder thenBuilder = this.caseWhenThenEntityBuilder.then(then);
//        return new IfEqualsEntityBuilder(thenBuilder);
//    }
}
