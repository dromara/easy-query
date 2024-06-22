package com.easy.query.api.lambda.crud.read.group;


public class Group<Key, T> extends SqlAggregation<T> implements IGroup
{
    public Key key;
}
