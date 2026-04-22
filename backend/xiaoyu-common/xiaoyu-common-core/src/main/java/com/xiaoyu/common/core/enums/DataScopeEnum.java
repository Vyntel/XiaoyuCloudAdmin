package com.xiaoyu.common.core.enums;

import lombok.Getter;

/**
 * 数据权限枚举
 */
@Getter
public enum DataScopeEnum {

    ALL(1, "全部数据"),
    CUSTOM(2, "自定义数据"),
    DEPT(3, "本部门数据"),
    DEPT_AND_BELOW(4, "本部门及以下"),
    SELF(5, "仅本人数据");

    private final Integer code;
    private final String desc;

    DataScopeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     */
    public static DataScopeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (DataScopeEnum scope : values()) {
            if (scope.getCode().equals(code)) {
                return scope;
            }
        }
        return null;
    }
}
