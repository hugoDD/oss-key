package org.maxkey.rsql.visitors;

import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.maxkey.rsql.enums.RainsWrapperKeyword;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MergeQueryWrapper<T> extends QueryWrapper<T> implements IMegeWrapper<T> {


    private Map<String, QueryWrapper> subList;

    public MergeQueryWrapper() {
        this.subList = new ConcurrentHashMap();

    }

    private MergeQueryWrapper(T entity, Class<T> entityClass, AtomicInteger paramNameSeq, Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString lastSql, SharedString sqlComment) {
        super.setEntity(entity);
        super.setEntityClass(entityClass);
        //  this.entityClass = entityClass;
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.lastSql = lastSql;
        this.sqlComment = sqlComment;
    }

    public QueryWrapper<T> getSubWrapper() {
        return this.instance();
    }

    public MergeQueryWrapper<T> createMergeWrapper() {
        MergeQueryWrapper<T> other = new MergeQueryWrapper(getEntity(), getEntityClass(), this.paramNameSeq, this.paramNameValuePairs, new MergeSegments(), SharedString.emptyString(), SharedString.emptyString());
        other.subList = this.subList;
        return other;
    }

    public QueryWrapper<T> re(boolean condition, String column, Object val) {
        return doIt(condition, () -> columnToString(column), () -> "REGEXP", () -> formatSql("{0}", val));

    }

    public QueryWrapper<T> mergeAnd(List<ISqlSegment> sqlSegments) {
        if (sqlSegments.size() == 1) {
            sqlSegments.add(0, RainsWrapperKeyword.LEFT_BRACKET);
            sqlSegments.add(RainsWrapperKeyword.RIGHT_BRACKET);
        }


        this.and(true);
        return (QueryWrapper) this.doIt(true, (ISqlSegment[]) sqlSegments.toArray(new ISqlSegment[sqlSegments.size()]));
    }

    public QueryWrapper<T> mergeOr(List<ISqlSegment> sqlSegments) {
//        sqlSegments.add(0, WrapperKeyword.LEFT_BRACKET);
//        sqlSegments.add(WrapperKeyword.RIGHT_BRACKET);
        if (sqlSegments.size() == 1) {
            sqlSegments.add(0, RainsWrapperKeyword.LEFT_BRACKET);
            sqlSegments.add(RainsWrapperKeyword.RIGHT_BRACKET);
        }

        this.or(true);
        return  this.doIt(true,  sqlSegments.toArray(new ISqlSegment[sqlSegments.size()]));
    }

    public QueryWrapper<T> mergeNested(Wrapper<T> queryWrapper) {
        List<ISqlSegment> sqlSegments = queryWrapper.getExpression().getNormal();
//        sqlSegments.add(0, RainsWrapperKeyword.LEFT_BRACKET);
//        sqlSegments.add(RainsWrapperKeyword.RIGHT_BRACKET);
        return this.doIt(true, sqlSegments.toArray(new ISqlSegment[sqlSegments.size()]));
    }

    public QueryWrapper<T> mergeSubQueryWrapper(String key, QueryWrapper subQueryWrapper) {
        if (this.subList == null) {
            this.subList = new ConcurrentHashMap();
        }

        this.subList.put(key, subQueryWrapper);
        return Wrappers.emptyWrapper();
    }

    public Map<String, QueryWrapper> getSubQueryList() {
        return this.subList;
    }
}
