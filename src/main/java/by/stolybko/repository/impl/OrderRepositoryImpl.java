package by.stolybko.repository.impl;

import by.stolybko.db.ConnectionManager;
import by.stolybko.model.Order;
import by.stolybko.model.Product;
import by.stolybko.repository.OrderRepository;
import by.stolybko.repository.mapper.BaseResultSetMapper;
import by.stolybko.repository.mapper.OrderResultSetMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderRepositoryImpl implements OrderRepository {
    private final ConnectionManager connectionManager;
    private final OrderResultSetMapper resultSetMapper;

    public OrderRepositoryImpl(ConnectionManager connectionManager, OrderResultSetMapper resultSetMapper) {
        this.connectionManager = connectionManager;
        this.resultSetMapper = resultSetMapper;
    }

    private static final String SELECT_BY_ID = "SELECT orderId, createDate, customerId FROM orders WHERE orderId = ?";

    @Override
    public Optional<Order> findById(UUID id) {

        Order order = null;

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order = resultSetMapper.map(resultSet);
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(order);
    }

    private static final String SELECT_BY_ID_WITH_PRODUCT = """
            SELECT o.orderId, o.customerId, o.createDate, p.productId, p.name, p.price
            FROM orders o
                JOIN order_product op on o.orderId = op.orderId
                JOIN product p on p.productId = op.productId
            WHERE o.orderId = ?;
            """;

    @Override
    public Optional<Order> findByIdWithProduct(UUID id) {

        Order order = null;

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_WITH_PRODUCT)) {

            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order = resultSetMapper.mapWithProducts(resultSet);
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(order);
    }

    private static final String SELECT_BY_CUSTOMER_ID = """
            SELECT DISTINCT ON (o.orderId) o.orderId, o.createDate, o.customerId
            FROM orders o
                JOIN customer c on c.customerId = o.customerId
            WHERE o.customerId = ?
                        
            """;

    @Override
    public List<Order> findAllByCustomerId(UUID customerId) {

        List<Order> orders = new ArrayList<>();

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_CUSTOMER_ID)) {

            preparedStatement.setObject(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                orders.add(resultSetMapper.map(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }

    private static final String SELECT_ALL = "SELECT orderId, createDate, customerId FROM orders";

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

            while (resultSet.next()) {
                orders.add(resultSetMapper.map(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }

    private static final String INSERT = "INSERT INTO orders (orderId, customerId, createDate) VALUES(?, ?, ?)";
    private static final String INSERT_IN_ORDER_PRODUCT = "INSERT INTO order_product (orderId, productId) VALUES(?, ?)";

    @Override
    public Optional<Order> save(Order order) {
        Connection connection = null;
        PreparedStatement preparedStatementOrder = null;
        PreparedStatement preparedStatementProduct = null;
        try {
            connection = connectionManager.getConnection();
            connection.setAutoCommit(false);

            preparedStatementOrder = connection.prepareStatement(INSERT);

            UUID uuid = UUID.randomUUID();
            order.setId(uuid);
            order.setCreateDate(LocalDateTime.now());

            preparedStatementOrder.setObject(1, order.getId());
            preparedStatementOrder.setObject(2, order.getCustomerId());
            preparedStatementOrder.setTimestamp(3, Timestamp.valueOf(order.getCreateDate()));
            preparedStatementOrder.executeUpdate();


            preparedStatementProduct = connection.prepareStatement(INSERT_IN_ORDER_PRODUCT);

            for (Product product : order.getProducts()) {

                preparedStatementProduct.setObject(1, order.getId());
                preparedStatementProduct.setObject(2, product.getId());
                preparedStatementProduct.addBatch();

            }
            preparedStatementProduct.executeBatch();

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();

            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                e.printStackTrace();
            }

            return Optional.empty();

        } finally {

            try {
                if (preparedStatementOrder != null) {
                    preparedStatementOrder.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (preparedStatementProduct != null) {
                    preparedStatementProduct.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return Optional.of(order);
    }

    private static final String UPDATE = "UPDATE orders SET customerId = ? WHERE orderId = ?";
    private static final String CLEAR_PRODUCTS = "DELETE FROM order_product WHERE orderId = ?";

    @Override
    public Optional<Order> update(UUID uuid, Order order) {
        Connection connection = null;
        PreparedStatement preparedStatementOrder = null;
        PreparedStatement preparedStatementClear = null;
        PreparedStatement preparedStatementProduct = null;
        try {
            connection = connectionManager.getConnection();
            connection.setAutoCommit(false);

            preparedStatementOrder = connection.prepareStatement(UPDATE);


            preparedStatementOrder.setObject(1, order.getCustomerId());
            preparedStatementOrder.setObject(2, uuid);
            preparedStatementOrder.executeUpdate();

            preparedStatementClear = connection.prepareStatement(CLEAR_PRODUCTS);
            preparedStatementClear.setObject(1, uuid);
            preparedStatementClear.executeUpdate();

            preparedStatementProduct = connection.prepareStatement(INSERT_IN_ORDER_PRODUCT);

            for (Product product : order.getProducts()) {

                preparedStatementProduct.setObject(1, uuid);
                preparedStatementProduct.setObject(2, product.getId());
                preparedStatementProduct.addBatch();

            }
            preparedStatementProduct.executeBatch();

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();

            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                e.printStackTrace();
            }

            return Optional.empty();

        } finally {

            try {
                if (preparedStatementOrder != null) {
                    preparedStatementOrder.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (preparedStatementClear != null) {
                    preparedStatementClear.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (preparedStatementProduct != null) {
                    preparedStatementProduct.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return findByIdWithProduct(uuid);
    }



    /*@Override
    public Optional<Order> update(UUID id, Order order) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setObject(1, order.getCustomerId());
            preparedStatement.setObject(2, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(order);

    }*/

    private static final String DELETE_BY_ID = "DELETE FROM orders WHERE orderId = ?";

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
