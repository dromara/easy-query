package com.easy.query.search.op;

import com.easy.query.search.exception.EasySearchStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运算符构造器
 *
 * @author bkbits
 */
public class EasySearchOpBuilder {
    private final List<Op> opList = new ArrayList<>();

    public EasySearchOpBuilder() {
        //添加默认符号
        this.add(Equals.class)
            .add(GreaterEquals.class)
            .add(GreaterThan.class)
            .add(In.class)
            .add(IsNotNull.class)
            .add(LessEquals.class)
            .add(LessThan.class)
            .add(Like.class)
            .add(LikeMatchLeft.class)
            .add(LikeMatchRight.class)
            .add(NotEquals.class)
            .add(NotIn.class)
            .add(NotLike.class)
            .add(NotLikeMatchLeft.class)
            .add(NotLikeMatchRight.class)
            .add(NotRangeClosed.class)
            .add(NotRangeClosedOpen.class)
            .add(NotRangeOpen.class)
            .add(NotRangeOpenClosed.class)
            .add(RangeClosed.class)
            .add(RangeClosedOpen.class)
            .add(RangeOpen.class)
            .add(RangeOpenClosed.class);
    }

    public Map<String, Op> build() {
        Map<String, Op> opMap = new HashMap<>();
        for (Op op : opList) {
            String opName = op.getName().trim();
            if (opMap.containsKey(opName)) {
                throw new EasySearchStatusException("重复的操作符名：" + opName);
            }
            opMap.put(opName, op);
        }
        return opMap;
    }

    public EasySearchOpBuilder add(Op op) {
        opList.add(op);
        return this;
    }

    public EasySearchOpBuilder add(Class<? extends Op> opClass) {
        try {
            return add(opClass.getConstructor().newInstance());
        } catch (Exception e) {
            throw new EasySearchStatusException("符号类缺少默认构造函数：" + opClass.getName());
        }
    }
}
