package com.easy.query.core.lambda.visitor;

import java.util.ArrayList;
import java.util.List;

public class Pair
{
    public String property;
    public StringBuilder sqlSegment = new StringBuilder();
    public List<SqlValue> sqlValue = new ArrayList<>(1);
}
