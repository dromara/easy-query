package com.easy.query.core.expression.parser.core.base.tree;

import com.easy.query.core.enums.SQLUnionEnum;

/**
 * create time 2023/10/23 21:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class TreeCTEOption {
    private int limitDeep = -1;
    private boolean up = false;
    private boolean unionAll = true;
    private String cteTableName = "as_tree_cte";
    private String deepColumnName = "cte_deep";

    public int getLimitDeep() {
        return limitDeep;
    }

    public void setLimitDeep(int limitDeep) {
        this.limitDeep = limitDeep;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isUnionAll() {
        return unionAll;
    }

    public void setUnionAll(boolean unionAll) {
        this.unionAll = unionAll;
    }

    public String getCTETableName() {
        return cteTableName;
    }

    public void setCTETableName(String cteTableName) {
        this.cteTableName = cteTableName;
    }

    public String getDeepColumnName() {
        return deepColumnName;
    }

    public void setDeepColumnName(String deepColumnName) {
        this.deepColumnName = deepColumnName;
    }

    public SQLUnionEnum sqlUnion() {
        return unionAll ? SQLUnionEnum.UNION_ALL : SQLUnionEnum.UNION;
    }
}
