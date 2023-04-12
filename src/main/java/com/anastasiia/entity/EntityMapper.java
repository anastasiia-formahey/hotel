package com.anastasiia.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityMapper<T> {
    T mapRow(ResultSet resultSet) throws SQLException;
}
