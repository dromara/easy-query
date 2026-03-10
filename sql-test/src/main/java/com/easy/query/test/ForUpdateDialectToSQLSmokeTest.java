package com.easy.query.test;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.clickhouse.config.ClickHouseDatabaseConfiguration;
import com.easy.query.dameng.config.DamengDatabaseConfiguration;
import com.easy.query.db2.config.DB2DatabaseConfiguration;
import com.easy.query.duckdb.config.DuckDBSQLDatabaseConfiguration;
import com.easy.query.gauss.db.config.GaussDBDatabaseConfiguration;
import com.easy.query.h2.config.H2DatabaseConfiguration;
import com.easy.query.kingbase.es.config.KingbaseESDatabaseConfiguration;
import com.easy.query.mssql.config.MsSQLDatabaseConfiguration;
import com.easy.query.mssql.config.MsSQLRowNumberDatabaseConfiguration;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.oracle.config.OracleDatabaseConfiguration;
import com.easy.query.pgsql.config.PgSQLDatabaseConfiguration;
import com.easy.query.sqlite.config.SQLiteDatabaseConfiguration;
import com.easy.query.test.common.EmptyDataSource;
import com.easy.query.test.doc.entity.DocBankCard;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * create time 2026/3/6 00:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class ForUpdateDialectToSQLSmokeTest {
    @Before
    public void before() {
        LogFactory.useStdOutLogging();
    }

    private EasyEntityQuery create(DatabaseConfiguration databaseConfiguration) {
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(new EmptyDataSource())
                .optionConfigure(op -> op.setDeleteThrowError(false))
                .useDatabaseConfigure(databaseConfiguration)
                .build();
        return new DefaultEasyEntityQuery(easyQueryClient);
    }

    // ==================== Supported Dialects ====================

    @Test
    public void mysqlForUpdateToSQLSmoke() {
        EasyEntityQuery easyEntityQuery = create(new MySQLDatabaseConfiguration());
        try (Transaction transaction = easyEntityQuery.beginTransaction()) {
            String sql = easyEntityQuery.queryable(DocBankCard.class)
                    .where(card -> card.id().isNotNull())
                    .orderBy(card -> card.id().asc())
                    .limit(1, 2)
                    .forUpdate()
                    .toSQL();
            Assert.assertTrue(sql.endsWith(" FOR UPDATE"));
            Assert.assertEquals(1, countMatches(sql, "FOR UPDATE"));
            transaction.commit();
        }
    }

    @Test
    public void pgsqlForUpdateToSQLSmoke() {
        EasyEntityQuery easyEntityQuery = create(new PgSQLDatabaseConfiguration());
        try (Transaction transaction = easyEntityQuery.beginTransaction()) {
            String sql = easyEntityQuery.queryable(DocBankCard.class)
                    .where(card -> card.id().isNotNull())
                    .orderBy(card -> card.id().asc())
                    .limit(1, 2)
                    .forUpdate()
                    .toSQL();
            Assert.assertTrue(sql.endsWith(" FOR UPDATE"));
            Assert.assertEquals(1, countMatches(sql, "FOR UPDATE"));
            transaction.commit();
        }
    }

    @Test
    public void h2ForUpdateToSQLSmoke() {
        EasyEntityQuery easyEntityQuery = create(new H2DatabaseConfiguration());
        try (Transaction transaction = easyEntityQuery.beginTransaction()) {
            String sql = easyEntityQuery.queryable(DocBankCard.class)
                    .where(card -> card.id().isNotNull())
                    .orderBy(card -> card.id().asc())
                    .limit(1, 2)
                    .forUpdate()
                    .toSQL();
            Assert.assertTrue(sql.endsWith(" FOR UPDATE"));
            Assert.assertEquals(1, countMatches(sql, "FOR UPDATE"));
            transaction.commit();
        }
    }

    @Test
    public void sqliteForUpdateToSQLSmoke() {
        EasyEntityQuery easyEntityQuery = create(new SQLiteDatabaseConfiguration());
        try (Transaction transaction = easyEntityQuery.beginTransaction()) {
            String sql = easyEntityQuery.queryable(DocBankCard.class)
                    .where(card -> card.id().isNotNull())
                    .orderBy(card -> card.id().asc())
                    .limit(1, 2)
                    .forUpdate()
                    .toSQL();
            Assert.assertTrue(sql.endsWith(" FOR UPDATE"));
            Assert.assertEquals(1, countMatches(sql, "FOR UPDATE"));
            transaction.commit();
        }
    }

    @Test
    public void kingbaseESForUpdateToSQLSmoke() {
        EasyEntityQuery easyEntityQuery = create(new KingbaseESDatabaseConfiguration());
        try (Transaction transaction = easyEntityQuery.beginTransaction()) {
            String sql = easyEntityQuery.queryable(DocBankCard.class)
                    .where(card -> card.id().isNotNull())
                    .orderBy(card -> card.id().asc())
                    .limit(1, 2)
                    .forUpdate()
                    .toSQL();
            Assert.assertTrue(sql.endsWith(" FOR UPDATE"));
            Assert.assertEquals(1, countMatches(sql, "FOR UPDATE"));
            transaction.commit();
        }
    }

    @Test
    public void gaussDBForUpdateToSQLSmoke() {
        EasyEntityQuery easyEntityQuery = create(new GaussDBDatabaseConfiguration());
        try (Transaction transaction = easyEntityQuery.beginTransaction()) {
            String sql = easyEntityQuery.queryable(DocBankCard.class)
                    .where(card -> card.id().isNotNull())
                    .orderBy(card -> card.id().asc())
                    .limit(1, 2)
                    .forUpdate()
                    .toSQL();
            Assert.assertTrue(sql.endsWith(" FOR UPDATE"));
            Assert.assertEquals(1, countMatches(sql, "FOR UPDATE"));
            transaction.commit();
        }
    }

    @Test
    public void damengForUpdateToSQLSmoke() {
        EasyEntityQuery easyEntityQuery = create(new DamengDatabaseConfiguration());
        try (Transaction transaction = easyEntityQuery.beginTransaction()) {
            String sql = easyEntityQuery.queryable(DocBankCard.class)
                    .where(card -> card.id().isNotNull())
                    .orderBy(card -> card.id().asc())
                    .limit(1, 2)
                    .forUpdate()
                    .toSQL();
            Assert.assertTrue(sql.toUpperCase().contains("ROWNUM"));
            Assert.assertTrue(sql.endsWith(" FOR UPDATE"));
            Assert.assertEquals(1, countMatches(sql, "FOR UPDATE"));
            transaction.commit();
        }
    }

    @Test
    public void oracleForUpdateToSQLSmoke() {
        EasyEntityQuery easyEntityQuery = create(new OracleDatabaseConfiguration());
        try (Transaction transaction = easyEntityQuery.beginTransaction()) {
            String sql = easyEntityQuery.queryable(DocBankCard.class)
                    .where(card -> card.id().isNotNull())
                    .orderBy(card -> card.id().asc())
                    .limit(1, 2)
                    .forUpdate()
                    .toSQL();
            Assert.assertTrue(sql.toUpperCase().contains("ROWNUM"));
            Assert.assertTrue(sql.endsWith(" FOR UPDATE"));
            Assert.assertEquals(1, countMatches(sql, "FOR UPDATE"));
            transaction.commit();
        }
    }

    @Test
    public void db2ForUpdateToSQLSmoke() {
        EasyEntityQuery easyEntityQuery = create(new DB2DatabaseConfiguration());
        try (Transaction transaction = easyEntityQuery.beginTransaction()) {
            String sql = easyEntityQuery.queryable(DocBankCard.class)
                    .where(card -> card.id().isNotNull())
                    .orderBy(card -> card.id().asc())
                    .limit(1, 2)
                    .forUpdate()
                    .toSQL();
            Assert.assertTrue(sql.toUpperCase().contains("OFFSET 1 ROWS FETCH NEXT 2 ROWS ONLY FOR UPDATE"));
            Assert.assertEquals(1, countMatches(sql, "FOR UPDATE"));
            transaction.commit();
        }
    }

    // ==================== Unsupported Dialects (should throw UnsupportedOperationException) ====================

    @Test
    public void msSQLForUpdateThrowsUnsupported() {
        EasyEntityQuery easyEntityQuery = create(new MsSQLDatabaseConfiguration());
        try (Transaction transaction = easyEntityQuery.beginTransaction()) {
            try {
                easyEntityQuery.queryable(DocBankCard.class)
                        .where(card -> card.id().isNotNull())
                        .forUpdate()
                        .toSQL();
                Assert.fail("MsSQL should throw UnsupportedOperationException for FOR UPDATE");
            } catch (UnsupportedOperationException ex) {
                Assert.assertTrue(ex.getMessage().contains("SQL Server does not support FOR UPDATE"));
            }
            transaction.commit();
        }
    }

    @Test
    public void msSQLRowNumberForUpdateThrowsUnsupported() {
        EasyEntityQuery easyEntityQuery = create(new MsSQLRowNumberDatabaseConfiguration());
        try (Transaction transaction = easyEntityQuery.beginTransaction()) {
            try {
                easyEntityQuery.queryable(DocBankCard.class)
                        .where(card -> card.id().isNotNull())
                        .forUpdate()
                        .toSQL();
                Assert.fail("MsSQL RowNumber should throw UnsupportedOperationException for FOR UPDATE");
            } catch (UnsupportedOperationException ex) {
                Assert.assertTrue(ex.getMessage().contains("SQL Server does not support FOR UPDATE"));
            }
            transaction.commit();
        }
    }

    @Test
    public void clickHouseForUpdateThrowsUnsupported() {
        EasyEntityQuery easyEntityQuery = create(new ClickHouseDatabaseConfiguration());
        try (Transaction transaction = easyEntityQuery.beginTransaction()) {
            try {
                easyEntityQuery.queryable(DocBankCard.class)
                        .where(card -> card.id().isNotNull())
                        .forUpdate()
                        .toSQL();
                Assert.fail("ClickHouse should throw UnsupportedOperationException for FOR UPDATE");
            } catch (UnsupportedOperationException ex) {
                Assert.assertTrue(ex.getMessage().contains("ClickHouse does not support FOR UPDATE"));
            }
            transaction.commit();
        }
    }

    @Test
    public void duckDBForUpdateThrowsUnsupported() {
        EasyEntityQuery easyEntityQuery = create(new DuckDBSQLDatabaseConfiguration());
        try (Transaction transaction = easyEntityQuery.beginTransaction()) {
            try {
                easyEntityQuery.queryable(DocBankCard.class)
                        .where(card -> card.id().isNotNull())
                        .forUpdate()
                        .toSQL();
                Assert.fail("DuckDB should throw UnsupportedOperationException for FOR UPDATE");
            } catch (UnsupportedOperationException ex) {
                Assert.assertTrue(ex.getMessage().contains("DuckDB does not support FOR UPDATE"));
            }
            transaction.commit();
        }
    }

    private static int countMatches(String text, String part) {
        int count = 0;
        int index = 0;
        while (index >= 0) {
            index = text.indexOf(part, index);
            if (index >= 0) {
                count++;
                index += part.length();
            }
        }
        return count;
    }
}
