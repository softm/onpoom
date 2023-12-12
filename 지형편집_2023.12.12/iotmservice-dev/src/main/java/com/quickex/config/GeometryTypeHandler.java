package com.quickex.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgis.PGgeometry;

import java.sql.*;
import java.util.Objects;


//pgsql
public class GeometryTypeHandler extends BaseTypeHandler<Object> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null || parameter.toString().equals("") || Objects.isNull(parameter)) {
            ps.setNull(i, Types.OTHER);
        } else {
            PGgeometry geom = new PGgeometry(parameter.toString());
            ps.setObject(i, geom);
        }
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        PGgeometry geom = (PGgeometry) rs.getObject(columnName);
        return geom.getGeometry();
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        PGgeometry geom = (PGgeometry) rs.getObject(columnIndex);
        return geom.getGeometry();
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        PGgeometry geom = (PGgeometry) cs.getObject(columnIndex);
        return geom.getGeometry();
    }

}
