package com.kyh.model.enums;

import com.kyh.dao.handler.core.EnumCodeGetter;
import com.kyh.dao.handler.core.EnumDescriptionGetter;

/**
 * 关于枚举类型映射
 * 1.使用javaType: 默认使用EnumTypeHandler，将Enum字段映射为字符串。（备注：ADMIN(xx,xx)会被解析为"ADMIN"存入数据库）
 *   缺点：无法得到枚举值内部的code等字段值
 *
 * 2.使用handlerType:
 *   a.EnumOrdinalTypeHandler(系统提供，将Enum字段映射为int数值，此int值是enum对象的ordinal值[0~N])
 *   缺点：保存在数据库中的值是0~N
 *   b.自定义TypeHandler(最佳方案): 新建类型处理器，有两种做法：实现org.apache.ibatis.type.TypeHandler接口，或继承org.apache.ibatis.type.BaseTypeHandler类
 */
public enum UserType implements EnumCodeGetter, EnumDescriptionGetter{
    USER("1", "普通用户"), ADMIN("2", "系统管理员");

    private String code;
    private String description;

    private UserType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
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
