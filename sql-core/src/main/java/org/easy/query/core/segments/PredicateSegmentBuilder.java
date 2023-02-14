package org.easy.query.core.segments;

import org.easy.query.core.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: PredicateSegmentBuilder.java
 * @Description: 文件说明
 * @Date: 2023/2/14 12:19
 * @Created by xuejiaming
 */
public class PredicateSegmentBuilder {
    private List<PredicateSegmentBuilder> children =new ArrayList<>();
    private PredicateSegment00 predicate;

    public PredicateSegment00 getPredicate() {
        return predicate;
    }

    public void setPredicate(PredicateSegment00 predicate) {
        this.predicate = predicate;
    }

    public List<PredicateSegmentBuilder> getChildren() {
        return children;
    }
    private boolean isFinal(){
        return predicate!=null;
    }
    public String getSql(){
        if(isFinal()){
            return predicate.getColumn()+" "+predicate.getOperate()+" "+ predicate.getValue();
        }else{
            StringBuilder sql=new StringBuilder();
            for (PredicateSegmentBuilder child : children) {
                if(child instanceof AndPredicateSegmentBuilder){
                    if(sql.length()==0){
                        sql.append(child.getSql());
                    }else{
                        sql.append(" AND ").append(child.getSql());
                    }
                }else if(child instanceof OrPredicateSegmentBuilder){
                    if(sql.length()==0){
                        sql.append(child.getSql());
                    }else{
                        sql.append(" OR ").append(child.getSql());
                    }
                }
            }
            if(sql.length()!=0){
                return "("+sql+")";
            }
            return StringUtil.EMPTY;
        }
    }
}
