package org.maxkey.rsql.visitors;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.rutledgepaulv.qbuilders.nodes.AndNode;
import com.github.rutledgepaulv.qbuilders.nodes.ComparisonNode;
import com.github.rutledgepaulv.qbuilders.nodes.OrNode;
import com.github.rutledgepaulv.qbuilders.operators.ComparisonOperator;
import com.github.rutledgepaulv.qbuilders.visitors.ContextualNodeVisitor;
import org.maxkey.rsql.operators.MybatisComparisonOperator;
import org.maxkey.util.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MybatisPlusQueryVisitor<T> extends ContextualNodeVisitor<Wrapper<T>, MybatisPlusQueryVisitor.Context> {
    protected final Function<Object, Object> normalizer = Function.identity();
    private Type type;
    private Class<T> classType;
    private boolean toLine =true;

    public MybatisPlusQueryVisitor(boolean toLine) {
        this.toLine = toLine;
        Type superClass = this.getClass().getGenericSuperclass();
        this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        if (this.type instanceof ParameterizedType) {
            this.classType = (Class) ((ParameterizedType) this.type).getRawType();
        } else {
            this.classType = (Class) this.type;
        }

    }

    protected Wrapper<T> visit(AndNode node, MybatisPlusQueryVisitor.Context context) {
        IMegeWrapper<T> andWrapper = context.wrapper.createMergeWrapper();
        Predicate<Wrapper<T>> predicate = (p) -> {
            return p != Wrappers.emptyWrapper() && p != context.wrapper;
        };
        node.getChildren().stream().map(child -> this.visitAny(child, context)).filter(predicate).forEach((c) -> {
            andWrapper.mergeAnd(c.getExpression().getNormal());
        });
        return (Wrapper) andWrapper;
    }

    protected Wrapper<T> visit(OrNode node, MybatisPlusQueryVisitor.Context context) {
        IMegeWrapper<T> orWrapper = context.wrapper.createMergeWrapper();
        Predicate<Wrapper<T>> predicate = (p) -> p != Wrappers.emptyWrapper() && p != context.wrapper;

        node.getChildren().stream().map((child) -> this.visitAny(child, context)).filter(predicate).forEach((c) -> {
            orWrapper.mergeOr(c.getExpression().getNormal());
        });
        return (Wrapper) orWrapper;
    }

    protected Wrapper<T> visit(ComparisonNode node, MybatisPlusQueryVisitor.Context context) {
        ComparisonOperator operator = node.getOperator();
        AbstractWrapper queryWrapper = context.wrapper.getSubWrapper();
        Collection<?> values = (Collection) node.getValues().stream().map(this.normalizer).collect(Collectors.toList());

        String key =toLine? StringUtils.toLine(node.getField().asKey()):node.getField().asKey();
        if (ComparisonOperator.EQ.equals(operator)) {
            if ("asc".equals(this.single(node.getValues())) && !context.isUpdate) {
                ((AbstractWrapper) context.wrapper).orderByAsc(key);
                return Wrappers.emptyWrapper();
            } else if ("desc".equals(this.single(node.getValues())) && !context.isUpdate) {
                ((AbstractWrapper) context.wrapper).orderByDesc(key);
                return Wrappers.emptyWrapper();
            } else {
                queryWrapper.eq(key, this.single(values));
                return queryWrapper;
            }
        } else if (ComparisonOperator.NE.equals(operator)) {
            queryWrapper.ne(key, this.single(values));
            return queryWrapper;
        } else if (ComparisonOperator.EX.equals(operator)) {
            queryWrapper.exists(key);
            return queryWrapper;
        } else if (ComparisonOperator.GT.equals(operator)) {
            queryWrapper.gt(key, this.single(values));
            return queryWrapper;
        } else if (ComparisonOperator.LT.equals(operator)) {
            queryWrapper.lt(key, this.single(values));
            return queryWrapper;
        } else if (ComparisonOperator.GTE.equals(operator)) {
            queryWrapper.ge(key, this.single(values));
            return queryWrapper;
        } else if (ComparisonOperator.LTE.equals(operator)) {
            queryWrapper.le(key, this.single(values));
            return queryWrapper;
        } else if (ComparisonOperator.IN.equals(operator)) {
            queryWrapper.in(key, values);
            return queryWrapper;
        } else if (ComparisonOperator.NIN.equals(operator)) {
            queryWrapper.notIn(key, values);
            return queryWrapper;
        } else if (ComparisonOperator.RE.equals(operator)) {
            queryWrapper.apply(key + " REGEXP {0}", new Object[]{this.single(values)});
            return queryWrapper;
        } else if (MybatisComparisonOperator.ORDERBY.equals(operator)) {
            if (!context.isUpdate) {
                ((AbstractWrapper) context.wrapper).orderByAsc(key);
            }

            return Wrappers.emptyWrapper();
        }
//        else if (ComparisonOperator.SUB_CONDITION_ANY.equals(operator)) {
//            Wrapper<T> conditionWrapper = (Wrapper)this.condition(node, context);
//            if (conditionWrapper instanceof QueryWrapper) {
//                ((MergeQueryWrapper)context.wrapper).mergeSubQueryWrapper(key, (QueryWrapper)conditionWrapper);
//            }
//
//            return Wrappers.emptyWrapper();
//        }
        else {
            throw new UnsupportedOperationException("This visitor does not support the operator " + operator + ".");
        }
    }

    public static class Context<T> {
        private IMegeWrapper<T> wrapper;
        private boolean isUpdate = false;

        public Context() {
            this.wrapper = new MergeQueryWrapper();
        }

        public Context(boolean isUpdate) {
            this.isUpdate = isUpdate;
            if (isUpdate) {
                this.wrapper = new MergeUpdateWrapper();
            } else {
                this.wrapper = new MergeQueryWrapper();
            }

        }

        public IMegeWrapper<T> getWrapper() {
            return this.wrapper;
        }
    }
}
