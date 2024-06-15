package com.easy.query.api.lambda.crud.read.group;

import com.easy.query.core.lambda.visitor.SqlValue;

import java.util.ArrayList;
import java.util.List;

public class GroupExtData
{
    public StringBuilder exprData = new StringBuilder();
    public List<SqlValue> sqlValues = new ArrayList<>();

    @Override
    public String toString()
    {
        return "GroupExtData{" +
                "exprData=" + exprData +
                ", sqlValues=" + sqlValues +
                '}';
    }
}
