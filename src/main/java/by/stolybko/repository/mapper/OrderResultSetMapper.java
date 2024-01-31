package by.stolybko.repository.mapper;

import by.stolybko.model.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface OrderResultSetMapper extends BaseResultSetMapper<Order> {

    Order mapWithProducts(ResultSet resultSet) throws SQLException;

}
