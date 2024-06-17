package com.easy.query.api.lambda.crud.read;


import com.easy.query.api.lambda.crud.read.group.GroupExtData;
import com.easy.query.api.lambda.db.DbType;

import java.util.Map;

public class QueryData
{
//    // 条件
//    private final List<Criteria> criteria = new ArrayList<>();
//
//    public List<Criteria> getCriteria()
//    {
//        return criteria;
//    }
//
//    // 嵌套
//    private final List<QueryData> queryDataList;
//
//    public QueryData(List<QueryData> queryDataList, DbType dbType)
//    {
//        this.queryDataList = queryDataList;
//        this.dbType = dbType;
//    }
//    public QueryData()
//    {
//        queryDataList = Collections.emptyList();
//    }
//
//
//    public List<QueryData> getQueryDataList()
//    {
//        return queryDataList;
//    }

    // groupBy需要的额外信息
    private Map<String, GroupExtData> groupExtDataMap;

    public Map<String, GroupExtData> getGroupExtDataMap()
    {
        return groupExtDataMap;
    }

    public void setGroupExtDataMap(Map<String, GroupExtData> groupExtDataMap)
    {
        this.groupExtDataMap = groupExtDataMap;
    }

    private final DbType dbType;

    public QueryData(DbType dbType)
    {
        this.dbType = dbType;
    }

    public DbType getDbType()
    {
        return dbType;
    }
}
