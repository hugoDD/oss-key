package org.maxkey.rsql.enums;

import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.toolkit.StringPool;

public enum RainsWrapperKeyword implements ISqlSegment {
    /**
     * 只用作于辨识,不用于其他
     */
    LEFT_BRACKET(StringPool.LEFT_BRACKET),
    RIGHT_BRACKET(StringPool.RIGHT_BRACKET),
    REGEXP("REGEXP");

    private final String keyword;

    RainsWrapperKeyword(final String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String getSqlSegment() {
        return keyword;
    }
}
