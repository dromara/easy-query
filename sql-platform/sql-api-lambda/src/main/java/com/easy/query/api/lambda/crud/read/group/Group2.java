package com.easy.query.api.lambda.crud.read.group;


public class Group2<Key, T1, T2> extends SqlAggregation2<T1, T2> implements IGroup
{
    public Key key;
}
