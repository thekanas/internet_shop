package by.stolybko.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManagerImpl implements ConnectionManager {

    private final DataSource dataSource;

    public ConnectionManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
