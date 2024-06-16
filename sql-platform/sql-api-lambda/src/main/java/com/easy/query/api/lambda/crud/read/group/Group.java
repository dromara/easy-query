package com.easy.query.api.lambda.crud.read.group;


import com.easy.query.api.lambda.crud.read.IGroup;

public class Group<Key,T> extends SqlAggregation<T> implements IGroup
{
    public Key key;
}
