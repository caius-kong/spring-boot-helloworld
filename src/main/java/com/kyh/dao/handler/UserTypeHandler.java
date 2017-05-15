package com.kyh.dao.handler;

import com.kyh.dao.handler.core.EnumCodeTypeHandler;
import com.kyh.model.enums.UserType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @MappedTypes 定义的是JavaType的数据类型, 描述了哪些Java类型可被拦截
 * @MappedJdbcTypes 定义的是JdbcType类型
 * 注册: mybatis.type-handlers-package=com.kyh.dao.handler
 *
 * 这种纯注解的方式 ==> 只能解决读取时的数据转换问题 （例如select，会自动根据配置文件启用typeHandler，然后@MappedTypes拦截了userType字段）
 *
 * 在插入时，我们依然需要配置typeHandler
 *
 * 最佳解决方案：涉及userType字段时，都是用handlerType处理
 */
public class UserTypeHandler extends EnumCodeTypeHandler<UserType> {

    public UserTypeHandler() {
        super(UserType.class);
    }

}
