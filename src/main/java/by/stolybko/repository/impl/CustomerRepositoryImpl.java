package by.stolybko.repository.impl;

import by.stolybko.db.ConnectionManager;
import by.stolybko.model.Customer;
import by.stolybko.repository.CustomerRepository;
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

public class CustomerRepositoryImpl implements CustomerRepository {

    private final ConnectionManager connectionManager;
    private final BaseResultSetMapper<Customer> resultSetMapper;

    public CustomerRepositoryImpl(ConnectionManager connectionManager, BaseResultSetMapper<Customer> resultSetMapper) {
        this.connectionManager = connectionManager;
        this.resultSetMapper = resultSetMapper;
    }

    private static final String SELECT_BY_ID = "SELECT customerId, fullName, email FROM customer WHERE customerId = ?";

    @Override
    public Optional<Customer> findById(UUID id) {

        Customer customer = null;

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customer = resultSetMapper.map(resultSet);
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(customer);
    }

    private static final String SELECT_ALL = "SELECT customerId, fullName, email FROM customer";

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

            while (resultSet.next()) {
                customers.add(resultSetMapper.map(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customers;
    }

    private static final String INSERT = "INSERT INTO customer (customerId, fullName, email) VALUES(?, ?, ?)";

    @Override
    public Optional<Customer> save(Customer customer) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {

            UUID uuid = UUID.randomUUID();

            preparedStatement.setObject(1, uuid);
            preparedStatement.setString(2, customer.getFullName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.executeUpdate();

            customer.setId(uuid);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(customer);
    }

    private static final String UPDATE = "UPDATE customer SET fullName = ?, email = ? WHERE customerId = ?";

    @Override
    public Optional<Customer> update(UUID id, Customer customer) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, customer.getFullName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setObject(3, id);
            preparedStatement.executeUpdate();

            customer.setId(id);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(customer);
    }

    private static final String DELETE_BY_ID = "DELETE FROM customer WHERE customerId = ?";

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
