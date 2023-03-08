package com.anastasiia.entity;

import com.anastasiia.exceptions.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityMapper<T> {
    T mapRow(ResultSet resultSet) throws DAOException, SQLException;
}
