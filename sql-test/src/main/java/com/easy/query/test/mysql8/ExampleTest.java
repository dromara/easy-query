package com.easy.query.test.mysql8;

import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.test.mysql8.entity.M8User;
import com.easy.query.test.mysql8.entity.bank.SysBank;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import org.junit.Test;

import java.util.List;

/**
 * create time 2025/6/7 10:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class ExampleTest extends BaseTest{

    @Test
    public void test2(){

        {
            List<SysBank> banks = easyEntityQuery.queryable(SysBank.class)
                    .where(bank -> {
                        bank.bankCards().orderBy(card->card.openTime().desc()).first().type().eq("123");
                    }).toList();

        }


        {
            List<SysBank> banks = easyEntityQuery.queryable(SysBank.class)
                    .where(bank -> {
                        bank.firstBankCard().type().eq("123");
                    }).toList();

        }
    }
    @Test
    public void sampleTest1(){
        {

            M8User xiaoMing = easyEntityQuery.queryable(M8User.class).where(m -> {
                m.name().eq("xiao ming");
            }).singleOrNull();
        }
        {

            List<M8User> users = easyEntityQuery.queryable(M8User.class).toList();
        }

        {

            List<M8User> users = easyEntityQuery.queryable(M8User.class)
                    .where(m -> {
                        if(true){
                            m.name().contains("zhang san");
                        }
                        m.name().contains(true,"zhang san");
                        m.age().gt(20);
                    }).toList();
        }

        {

            List<M8User> users = easyEntityQuery.queryable(M8User.class)
                    .where(m -> {
                        m.name().contains("zhang san");
                        m.age().gt(20);

                        m.expression().sql("{0} != {1}",c->{
                            c.expression(m.name());
                            c.value("小明");
                        });
                    }).orderBy(m -> {
                        m.age().asc();
                    }).toList();
        }

        {

            List<M8User> users = easyEntityQuery.queryable(M8User.class)
                    .where(m -> {
                        m.name().contains("zhang san");
                        m.age().gt(20);
                    }).orderBy(m -> {
                        m.age().asc();
                        if(true){
                            m.name().desc();
                        }
                        m.name().desc(true);
                    }).toList();
        }

        {


            String name="zhang san";
            Integer age=null;
            List<M8User> users = easyEntityQuery.queryable(M8User.class)
                    .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                    .where(m -> {
                        m.name().contains(name);
                        m.age().gt(age);
                    }).toList();
        }

    }
}
