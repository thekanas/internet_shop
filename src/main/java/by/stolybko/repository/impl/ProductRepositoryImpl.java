package by.stolybko.repository.impl;

import by.stolybko.db.ConnectionManager;
import by.stolybko.model.Product;
import by.stolybko.repository.ProductRepository;
import by.stolybko.repository.mapper.BaseResultSetMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductRepositoryImpl implements ProductRepository {

    private final ConnectionManager connectionManager;
    private final BaseResultSetMapper<Product> resultSetMapper;

    public ProductRepositoryImpl(ConnectionManager connectionManager, BaseResultSetMapper<Product> resultSetMapper) {
        this.connectionManager = connectionManager;
        this.resultSetMapper = resultSetMapper;
    }

    private static final String SELECT_BY_ID = "SELECT productId, name, price FROM product WHERE productId = ?";

    @Override
    public Optional<Product> findById(UUID id) {

        Product product = null;

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = resultSetMapper.map(resultSet);
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(product);
    }

    private static final String SELECT_ALL = "SELECT productId, name, price FROM product";

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

            while (resultSet.next()) {
                products.add(resultSetMapper.map(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    private static final String INSERT = "INSERT INTO product (productId, name, price) VALUES(?, ?, ?)";

    @Override
    public Optional<Product> save(Product product) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {

            UUID uuid = UUID.randomUUID();

            preparedStatement.setObject(1, uuid);
            preparedStatement.setString(2, product.getName());
            preparedStatement.setBigDecimal(3, product.getPrice());
            preparedStatement.executeUpdate();

            product.setId(uuid);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(product);
    }

    private static final String UPDATE = "UPDATE product SET name = ?, price = ? WHERE productId = ?";

    @Override
    public Optional<Product> update(UUID id, Product product) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setObject(3, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(product);
    }

    private static final String DELETE_BY_ID = "DELETE FROM product WHERE productId = ?";

    @Override
    public boolean deleteById(UUID id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {

            preparedStatement.setObject(1, id);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }
}
