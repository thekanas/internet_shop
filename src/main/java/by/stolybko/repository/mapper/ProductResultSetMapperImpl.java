package by.stolybko.repository.mapper;

import by.stolybko.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ProductResultSetMapperImpl implements BaseResultSetMapper<Product> {

    @Override
    public Product map(ResultSet resultSet) throws SQLException {

        Product product = new Product();
        product.setId((UUID) resultSet.getObject("productId"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getBigDecimal("price"));

        return product;
    }

}
