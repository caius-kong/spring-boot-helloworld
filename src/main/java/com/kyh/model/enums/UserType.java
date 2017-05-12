package com.kyh.model.enums;

/**
 * Created by kongyunhui on 2017/5/12.
 */
public enum UserType {
    USER("USER", "普通用户"), ADMIN("ADMIN", "系统管理员");

    private String code;
    private String description;

    private UserType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
