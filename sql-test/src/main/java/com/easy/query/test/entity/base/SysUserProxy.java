package com.easy.query.test.entity.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.impl.SQLColumnImpl;
import com.easy.query.test.entity.SysUser;

/**
 * create time 2023/6/21 17:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class SysUserProxy implements ProxyEntity<SysUserProxy, SysUser> {

    public static final SysUserProxy SYS_USER_PROXY = new SysUserProxy();
    private static final Class<SysUser> entityClass = SysUser.class;

    public final SQLColumn<SysUserProxy,String> username;
    public final SQLColumn<SysUserProxy,String> phone;
    public final SQLColumn<SysUserProxy,String> idCard;
    private final TableAvailable table;

    private SysUserProxy() {
        this.table = null;
        this.username = null;
        this.phone = null;
        this.idCard = null;
    }

    public SysUserProxy(TableAvailable table) {
        this.table = table;
        this.username = new SQLColumnImpl<>(table, "username");
        this.phone = new SQLColumnImpl<>(table, "phone");
        this.idCard = new SQLColumnImpl<>(table, "idCard");
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<SysUser> getEntityClass() {
        return entityClass;
    }

    @Override
    public SysUserProxy create(TableAvailable table) {
        return new SysUserProxy(table);
    }
}