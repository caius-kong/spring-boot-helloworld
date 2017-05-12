package com.kyh.model.enums;

/**
 * Created by kongyunhui on 2017/5/12.
 *
 * 关于枚举类型映射：
 * 1、对应的数据库字段类型是"字符串类型"，则直接用javaType处理就行，因为默认使用了EnumTypeHandler
 * 2、对应的数据库字段类型是int/char(1)，则需要使用typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler
 *
 * 备注：如果是复杂类型映射：需要创建BaseTypeHandler<T>子类，typeHandler=该子类
 *                        或者该子类加上@MappedTypes(clz)注解，properties中指定路径
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
