package com.kyh.dao.handler.core;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnumCodeTypeHandler<E extends Enum<E> & EnumCodeGetter> extends BaseTypeHandler<E> {

    private final E[] enums;

    public EnumCodeTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.enums = type.getEnumConstants();
        if (this.enums == null) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String name) throws SQLException {
        return convert(rs.getString(name));
    }

    @Override
    public E getNullableResult(ResultSet rs, int i) throws SQLException {
        return convert(rs.getString(i));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int i) throws SQLException {
        return convert(cs.getString(i));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E enumObj, JdbcType type) throws SQLException {
        ps.setString(i, enumObj.getCode());
    }

    private E convert(String status) {
        for (E em : enums) {
            if (em.getCode().equals(status)) {
                return em;
            }
        }
        return null;
    }
}
