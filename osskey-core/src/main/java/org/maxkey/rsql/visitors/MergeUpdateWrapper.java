package org.maxkey.rsql.visitors;

import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.enums.WrapperKeyword;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MergeUpdateWrapper<T> extends UpdateWrapper<T> implements IMegeWrapper<T> {
    private Map<String, QueryWrapper> subList;

    public MergeUpdateWrapper() {
        this.subList = new ConcurrentHashMap();
    }

    private MergeUpdateWrapper(T entity, Class<T> entityClass, AtomicInteger paramNameSeq, Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString lastSql, SharedString sqlComment) {
        super.setEntity(entity);
        super.setEntityClass(entityClass);
       // this.entityClass = entityClass;
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.lastSql = lastSql;
        this.sqlComment = sqlComment;
    }

    protected UpdateWrapper<T> instance() {
        return super.instance();
    }

    public UpdateWrapper<T> getSubWrapper() {
        return this.instance();
    }

    public MergeUpdateWrapper<T> createMergeWrapper() {
        MergeUpdateWrapper<T> other = new MergeUpdateWrapper(getEntity(), getEntityClass(), this.paramNameSeq, this.paramNameValuePairs, new MergeSegments(), SharedString.emptyString(), SharedString.emptyString());
        other.subList = this.subList;
        return other;
    }

    public UpdateWrapper<T> mergeAnd(List<ISqlSegment> sqlSegments) {
//        sqlSegments.add(0, WrapperKeyword.LEFT_BRACKET);
//        sqlSegments.add(WrapperKeyword.RIGHT_BRACKET);
        this.and(true);
        return (UpdateWrapper)this.doIt(true, (ISqlSegment[])sqlSegments.toArray(new ISqlSegment[sqlSegments.size()]));
    }

    public UpdateWrapper<T> mergeOr(List<ISqlSegment> sqlSegments) {
//        sqlSegments.add(0, WrapperKeyword.LEFT_BRACKET);
//        sqlSegments.add(WrapperKeyword.RIGHT_BRACKET);
        this.or(true);
        return (UpdateWrapper)this.doIt(true, (ISqlSegment[])sqlSegments.toArray(new ISqlSegment[sqlSegments.size()]));
    }

    public UpdateWrapper<T> mergeNested(Wrapper<T> queryWrapper) {
        List<ISqlSegment> sqlSegments = queryWrapper.getExpression().getNormal();
//        sqlSegments.add(0, WrapperKeyword.LEFT_BRACKET);
//        sqlSegments.add(WrapperKeyword.RIGHT_BRACKET);
        return (UpdateWrapper)this.doIt(true, (ISqlSegment[])sqlSegments.toArray(new ISqlSegment[sqlSegments.size()]));
    }

    public UpdateWrapper<T> mergeSubQueryWrapper(String key, QueryWrapper subQueryWrapper) {
        if (this.subList == null) {
            this.subList = new ConcurrentHashMap();
        }

        this.subList.put(key, subQueryWrapper);
        return this;
    }

    public Map<String, QueryWrapper> getSubQueryList() {
        return this.subList;
    }
}
