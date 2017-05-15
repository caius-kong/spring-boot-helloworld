package com.kyh.dao.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@MappedTypes(List.class)
@MappedJdbcTypes({JdbcType.VARCHAR})
public class ListLongTypeHandler extends BaseTypeHandler<List<Long>> {
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<Long> longs, JdbcType jdbcType) throws SQLException {
        //1.List集合转字符串
        StringBuffer sb = new StringBuffer();
        for (Long l : longs) {
            sb.append(l).append(",");
        }
        //2.设置给ps
        preparedStatement.setString(i, sb.toString().substring(0, sb.toString().length() - 1));
    }

    public List<Long> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return convert(resultSet.getString(s));
    }

    public List<Long> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return convert(resultSet.getString(i));
    }

    public List<Long> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return convert(callableStatement.getString(i));
    }

    private List<Long> convert(String str){
        // 空值处理,防止空值调用异常
        if(str==null) return null;
        String[] split = str.split(",");
        return Arrays.asList(split)
                .stream()
                .map(s -> Long.parseLong(s.trim()))
                .collect(Collectors.toList());
    }
}