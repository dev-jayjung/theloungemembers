package com.theloungemembers.core.common.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(OffsetDateTime.class)
public class KstOffsetDateTimeTypeHandler extends BaseTypeHandler<OffsetDateTime> {

    // KST (+09:00) 오프셋
    private static final ZoneOffset KST_OFFSET = ZoneId.of("Asia/Seoul").getRules().getOffset(Instant.now());

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OffsetDateTime parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter);
    }

    @Override
    public OffsetDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convertToKst(rs.getObject(columnName, OffsetDateTime.class));
    }

    @Override
    public OffsetDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convertToKst(rs.getObject(columnIndex, OffsetDateTime.class));
    }

    @Override
    public OffsetDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convertToKst(cs.getObject(columnIndex, OffsetDateTime.class));
    }

    private OffsetDateTime convertToKst(OffsetDateTime odt) {
        if (odt == null) {
            return null;
        }

        // DB에서 불러온 UTC 시간을 KST 오프셋으로 시차 변환
        return odt.withOffsetSameInstant(KST_OFFSET);
    }
}