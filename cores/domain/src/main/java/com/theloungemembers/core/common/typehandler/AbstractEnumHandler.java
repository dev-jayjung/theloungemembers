package com.theloungemembers.core.common.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.theloungemembers.core.type.BaseCodeEnum;
import com.theloungemembers.core.util.AssertUtil;

public abstract class AbstractEnumHandler<E extends Enum<E> & BaseCodeEnum> extends BaseTypeHandler<E> {

    private final Class<E> type;

    public AbstractEnumHandler(Class<E> type) {
        AssertUtil.notNull("Type argument cannot be null");

        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getEnum(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getEnum(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getEnum(cs.getString(columnIndex));
    }

    private E getEnum(String code) {
        if (code == null) {
            return null;
        }

        return EnumSet.allOf(type)
                .stream()
                .filter(e -> e.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}