package com.easy.query.test.listener;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/11/11 23:15
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
public class ListenerContext {
    private final boolean multi;

    public ListenerContext(){
        this(false);
    }
    public ListenerContext(boolean multi){

        this.multi = multi;
    }
    private JdbcExecuteAfterArg jdbcExecuteAfterArg;
    private List<JdbcExecuteAfterArg> jdbcExecuteAfterArgs=new ArrayList<>();

    public void record(JdbcExecuteAfterArg jdbcExecuteAfterArg){
        if(multi){
            jdbcExecuteAfterArgs.add(jdbcExecuteAfterArg);
        }else{
            this.jdbcExecuteAfterArg=jdbcExecuteAfterArg;
        }
    }
}
