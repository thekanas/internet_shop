package by.stolybko.repository.mapper;

import by.stolybko.model.Order;
import by.stolybko.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderResultSetMapperImpl implements OrderResultSetMapper {

    private final ProductResultSetMapperImpl productResultSetMapper = new ProductResultSetMapperImpl();

    @Override
    public Order map(ResultSet resultSet) throws SQLException {

        Order order = new Order();
        order.setId((UUID) resultSet.getObject("orderId"));
        order.setCustomerId((UUID) resultSet.getObject("customerId"));
        order.setCreateDate(resultSet.getTimestamp("createDate").toLocalDateTime());

        return order;
    }

    public Order mapWithProducts(ResultSet resultSet) throws SQLException {

        Order order = new Order();
        order.setId((UUID) resultSet.getObject("orderId"));
        order.setCustomerId((UUID) resultSet.getObject("customerId"));
        order.setCreateDate(resultSet.getTimestamp("createDate").toLocalDateTime());

        List<Product> products = new ArrayList<>();
        do {
            products.add(productResultSetMapper.map(resultSet));
        } while (resultSet.next());

        order.setProducts(products);

        return order;
    }

}
