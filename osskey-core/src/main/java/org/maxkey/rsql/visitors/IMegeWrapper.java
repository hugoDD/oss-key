package org.maxkey.rsql.visitors;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.util.List;

public interface IMegeWrapper<T> {
    Wrapper<T> mergeAnd(List<ISqlSegment> var1);

    Wrapper<T> mergeOr(List<ISqlSegment> var1);

    AbstractWrapper<T, String, ? extends Wrapper<T>> getSubWrapper();

    IMegeWrapper<T> createMergeWrapper();

    Wrapper<T> mergeNested(Wrapper<T> var1);
}
