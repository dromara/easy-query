package com.easy.query.test.h2;

import com.easy.query.api4j.select.Queryable2;
import com.easy.query.api4j.select.Queryable3;
import com.easy.query.api4j.select.Queryable4;
import com.easy.query.test.h2.domain.DefTable;
import com.easy.query.test.h2.domain.DefTableLeft1;
import com.easy.query.test.h2.domain.DefTableLeft2;
import com.easy.query.test.h2.domain.DefTableLeft3;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2023/6/6 13:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class H2QueryTest extends H2BaseTest {
    @Test
    public void leftJoin1() {
        Queryable2<DefTable, DefTableLeft1> where = easyQuery.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.eq(t1, DefTable::getId, DefTableLeft1::getDefId))
                .where((t, t1) -> t.eq(DefTable::getId, "1").then(t1).eq(DefTableLeft1::getId, "1"));
        List<DefTable> list = where.cloneQueryable()
                .toList();
        Assert.assertEquals(1, list.size());
        String sql = where.cloneQueryable().toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`user_name`,t.`nickname`,t.`enable`,t.`score`,t.`mobile`,t.`avatar`,t.`number`,t.`status`,t.`created`,t.`options` FROM `t_def_table` t LEFT JOIN `t_def_table_left1` t1 ON t.`id` = t1.`def_id` WHERE t.`id` = ? AND t1.`id` = ?", sql);
    }

    @Test
    public void leftJoin1_1() {
        Queryable2<DefTable, DefTableLeft1> where = easyQuery.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.eq(t1, DefTable::getId, DefTableLeft1::getDefId))
                .where(t -> t.eq(DefTable::getId, "1"));
        List<DefTable> list = where.cloneQueryable()
                .toList();
        Assert.assertEquals(1, list.size());
        String sql = where.cloneQueryable().toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`user_name`,t.`nickname`,t.`enable`,t.`score`,t.`mobile`,t.`avatar`,t.`number`,t.`status`,t.`created`,t.`options` FROM `t_def_table` t LEFT JOIN `t_def_table_left1` t1 ON t.`id` = t1.`def_id` WHERE t.`id` = ?", sql);
    }

    @Test
    public void leftJoin2() {
        Queryable3<DefTable, DefTableLeft1, DefTableLeft2> where = easyQuery.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.eq(t1, DefTable::getId, DefTableLeft1::getDefId))
                .leftJoin(DefTableLeft2.class, (t, t1, t2) -> t.eq(t2, DefTable::getId, DefTableLeft2::getDef1Id))
                .where((t, t1, t2) -> t.eq(DefTable::getId, "1").then(t1).eq(DefTableLeft1::getId, "1")
                        .then(t2).eq(DefTableLeft2::getId, "1"));
        List<DefTable> list = where.cloneQueryable()
                .toList();
        Assert.assertEquals(1, list.size());
        String sql = where.cloneQueryable().toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`user_name`,t.`nickname`,t.`enable`,t.`score`,t.`mobile`,t.`avatar`,t.`number`,t.`status`,t.`created`,t.`options` FROM `t_def_table` t LEFT JOIN `t_def_table_left1` t1 ON t.`id` = t1.`def_id` LEFT JOIN `t_def_table_left2` t2 ON t.`id` = t2.`def1_id` WHERE t.`id` = ? AND t1.`id` = ? AND t2.`id` = ?", sql);
    }

    @Test
    public void leftJoin2_1() {
        Queryable3<DefTable, DefTableLeft1, DefTableLeft2> where = easyQuery.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.eq(t1, DefTable::getId, DefTableLeft1::getDefId))
                .leftJoin(DefTableLeft2.class, (t, t1, t2) -> t.eq(t2, DefTable::getId, DefTableLeft2::getDef1Id))
                .where(t -> t.eq(DefTable::getId, "1"));
        List<DefTable> list = where.cloneQueryable()
                .toList();
        Assert.assertEquals(1, list.size());
        String sql = where.cloneQueryable().toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`user_name`,t.`nickname`,t.`enable`,t.`score`,t.`mobile`,t.`avatar`,t.`number`,t.`status`,t.`created`,t.`options` FROM `t_def_table` t LEFT JOIN `t_def_table_left1` t1 ON t.`id` = t1.`def_id` LEFT JOIN `t_def_table_left2` t2 ON t.`id` = t2.`def1_id` WHERE t.`id` = ?", sql);
    }

    @Test
    public void leftJoin3() {
        Queryable3<DefTable, DefTableLeft1, DefTableLeft3> where = easyQuery.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.eq(t1, DefTable::getId, DefTableLeft1::getDefId))
                .leftJoin(DefTableLeft3.class, (t, t1, t3) -> t.eq(t3, DefTable::getId, DefTableLeft3::getDef2Id))
                .where((t, t1, t2) -> t.eq(DefTable::getId, "1").then(t1).eq(DefTableLeft1::getId, "1")
                        .then(t2).eq(DefTableLeft3::getId, "1"));
        List<DefTable> list = where.cloneQueryable()
                .toList();
        Assert.assertEquals(1, list.size());
        String sql = where.cloneQueryable().toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`user_name`,t.`nickname`,t.`enable`,t.`score`,t.`mobile`,t.`avatar`,t.`number`,t.`status`,t.`created`,t.`options` FROM `t_def_table` t LEFT JOIN `t_def_table_left1` t1 ON t.`id` = t1.`def_id` LEFT JOIN `t_def_table_left3` t2 ON t.`id` = t2.`def2_id` WHERE t.`id` = ? AND t1.`id` = ? AND t2.`id` = ?", sql);
    }

    @Test
    public void leftJoin3_1() {
        Queryable3<DefTable, DefTableLeft1, DefTableLeft3> where = easyQuery.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.eq(t1, DefTable::getId, DefTableLeft1::getDefId))
                .leftJoin(DefTableLeft3.class, (t, t1, t3) -> t.eq(t3, DefTable::getId, DefTableLeft3::getDef2Id))
                .where(t -> t.eq(DefTable::getId, "1"));
        List<DefTable> list = where.cloneQueryable()
                .toList();
        Assert.assertEquals(1, list.size());
        String sql = where.cloneQueryable().toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`user_name`,t.`nickname`,t.`enable`,t.`score`,t.`mobile`,t.`avatar`,t.`number`,t.`status`,t.`created`,t.`options` FROM `t_def_table` t LEFT JOIN `t_def_table_left1` t1 ON t.`id` = t1.`def_id` LEFT JOIN `t_def_table_left3` t2 ON t.`id` = t2.`def2_id` WHERE t.`id` = ?", sql);
    }

    @Test
    public void leftJoin4() {
        Queryable4<DefTable, DefTableLeft1, DefTableLeft2, DefTableLeft3> where = easyQuery.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.eq(t1, DefTable::getId, DefTableLeft1::getDefId))
                .leftJoin(DefTableLeft2.class, (t, t1, t2) -> t.eq(t2, DefTable::getId, DefTableLeft2::getDef1Id))
                .leftJoin(DefTableLeft3.class, (t, t1, t2, t3) -> t.eq(t3, DefTable::getId, DefTableLeft3::getDef2Id))
                .where((t, t1, t2, t3) -> t.eq(DefTable::getId, "1").then(t1).eq(DefTableLeft1::getId, "1")
                        .then(t2).eq(DefTableLeft2::getId, "1").then(t3).eq(DefTableLeft3::getId, "1"));
        List<DefTable> list = where.cloneQueryable()
                .toList();
        Assert.assertEquals(1, list.size());
        String sql = where.cloneQueryable().toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`user_name`,t.`nickname`,t.`enable`,t.`score`,t.`mobile`,t.`avatar`,t.`number`,t.`status`,t.`created`,t.`options` FROM `t_def_table` t LEFT JOIN `t_def_table_left1` t1 ON t.`id` = t1.`def_id` LEFT JOIN `t_def_table_left2` t2 ON t.`id` = t2.`def1_id` LEFT JOIN `t_def_table_left3` t3 ON t.`id` = t3.`def2_id` WHERE t.`id` = ? AND t1.`id` = ? AND t2.`id` = ? AND t3.`id` = ?", sql);
    }

    @Test
    public void leftJoin4_1() {
        Queryable4<DefTable, DefTableLeft1, DefTableLeft2, DefTableLeft3> where = easyQuery.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.eq(t1, DefTable::getId, DefTableLeft1::getDefId))
                .leftJoin(DefTableLeft2.class, (t, t1, t2) -> t.eq(t2, DefTable::getId, DefTableLeft2::getDef1Id))
                .leftJoin(DefTableLeft3.class, (t, t1, t2, t3) -> t.eq(t3, DefTable::getId, DefTableLeft3::getDef2Id))
                .where(t -> t.eq(DefTable::getId, "1"));
        List<DefTable> list = where.cloneQueryable()
                .toList();
        Assert.assertEquals(1, list.size());
        String sql = where.cloneQueryable().toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`user_name`,t.`nickname`,t.`enable`,t.`score`,t.`mobile`,t.`avatar`,t.`number`,t.`status`,t.`created`,t.`options` FROM `t_def_table` t LEFT JOIN `t_def_table_left1` t1 ON t.`id` = t1.`def_id` LEFT JOIN `t_def_table_left2` t2 ON t.`id` = t2.`def1_id` LEFT JOIN `t_def_table_left3` t3 ON t.`id` = t3.`def2_id` WHERE t.`id` = ?", sql);
    }
}
