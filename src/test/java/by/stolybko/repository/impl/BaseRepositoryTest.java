/*
package by.stolybko.repository.impl;

import by.stolybko.db.ConnectionManager;
import by.stolybko.db.ConnectionManagerImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;


public abstract class BaseRepositoryTest {

    private static final String CLEAR_INIT_DATA = "db/clear-init-data.sql";
    private static final PostgreSQLContainer<?> CONTAINER = new PostgreSQLContainer<>("postgres:14.1")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("db/db-migration-test.SQL");

    protected ConnectionManager connectionManager;

    public BaseRepositoryTest() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(CONTAINER.getJdbcUrl());
        dataSource.setUser(CONTAINER.getUsername());
        dataSource.setPassword(CONTAINER.getPassword());
        dataSource.setDatabaseName(CONTAINER.getDatabaseName());
        this.connectionManager = new ConnectionManagerImpl(dataSource);
    }

    @BeforeAll
    static void beforeAll() {
        CONTAINER.start();
    }

    @AfterAll
    static void afterAll() {
        CONTAINER.stop();
    }

    @BeforeEach
    void beforeEach() {
        clearInit();
    }

    private void clearInit() {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement();
             InputStream inputStream = BaseRepositoryTest.class.getClassLoader().getResourceAsStream(CLEAR_INIT_DATA)) {

            String clearInitData = new String(Objects.requireNonNull(inputStream).readAllBytes(), StandardCharsets.UTF_8);

            statement.executeUpdate(clearInitData);

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


}
*/
