package com.easy.query.api.lambda.crud.read.group;

import com.easy.query.api.lambda.crud.read.IGroup;

public class Group4<Key, T1, T2, T3, T4> extends SqlAggregation4<T1, T2, T3, T4> implements IGroup
{
    public Key key;
}
