package by.stolybko.config;

import by.stolybko.db.ConnectionManager;
import by.stolybko.db.ConnectionManagerImpl;
import by.stolybko.repository.CustomerRepository;
import by.stolybko.repository.OrderRepository;
import by.stolybko.repository.ProductRepository;
import by.stolybko.repository.impl.CustomerRepositoryImpl;
import by.stolybko.repository.impl.OrderRepositoryImpl;
import by.stolybko.repository.impl.ProductRepositoryImpl;
import by.stolybko.repository.mapper.CustomerResultSetMapperImpl;
import by.stolybko.repository.mapper.OrderResultSetMapperImpl;
import by.stolybko.repository.mapper.ProductResultSetMapperImpl;
import by.stolybko.service.CustomerService;
import by.stolybko.service.OrderService;
import by.stolybko.service.ProductService;
import by.stolybko.service.impl.CustomerServiceImpl;
import by.stolybko.service.impl.OrderServiceImpl;
import by.stolybko.service.impl.ProductServiceImpl;
import by.stolybko.util.PropertiesManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Map;

public class AppConfig {

    private static final DataSource DATA_SOURCE   ;
    private static final ConnectionManager CONNECTION_MANAGER;
    private static final CustomerRepository CUSTOMER_REPOSITORY;
    private static final OrderRepository ORDER_REPOSITORY;
    private static final ProductRepository PRODUCT_REPOSITORY;
    private static final CustomerService CUSTOMER_SERVICE;
    private static final OrderService ORDER_SERVICE;
    private static final ProductService PRODUCT_SERVICE;


    static {

        HikariConfig config = new HikariConfig();
        Map<String, Object> databaseProperties = PropertiesManager.getProperty("db");
        config.setDriverClassName((String) databaseProperties.get("driver"));
        config.setJdbcUrl((String) databaseProperties.get("url"));
        config.setUsername((String) databaseProperties.get("user"));
        config.setPassword((String) databaseProperties.get("password"));
        config.setMaximumPoolSize((Integer) databaseProperties.get("pool.size"));

        DATA_SOURCE = new HikariDataSource(config);

        CONNECTION_MANAGER = new ConnectionManagerImpl(DATA_SOURCE);

        CUSTOMER_REPOSITORY = new CustomerRepositoryImpl(CONNECTION_MANAGER, new CustomerResultSetMapperImpl());
        ORDER_REPOSITORY = new OrderRepositoryImpl(CONNECTION_MANAGER, new OrderResultSetMapperImpl());
        PRODUCT_REPOSITORY = new ProductRepositoryImpl(CONNECTION_MANAGER, new ProductResultSetMapperImpl());

        CUSTOMER_SERVICE = new CustomerServiceImpl(CUSTOMER_REPOSITORY);
        ORDER_SERVICE = new OrderServiceImpl(ORDER_REPOSITORY);
        PRODUCT_SERVICE = new ProductServiceImpl(PRODUCT_REPOSITORY);

    }

    public static CustomerService getCustomerService() {
        return CUSTOMER_SERVICE;
    }

    public static OrderService getOrderService() {
        return ORDER_SERVICE;
    }

    public static ProductService getProductService() {
        return PRODUCT_SERVICE;
    }

    private AppConfig() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
