package by.stolybko.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BaseResultSetMapper<T> {

    T map(ResultSet resultSet) throws SQLException;

}
