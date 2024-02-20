package by.stolybko.repository.mapper;

import by.stolybko.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CustomerResultSetMapperImpl implements BaseResultSetMapper<Customer>{

    @Override
    public Customer map(ResultSet resultSet) throws SQLException {

        Customer customer = new Customer();
        customer.setId((UUID) resultSet.getObject("customerId"));
        customer.setFullName(resultSet.getString("fullName"));
        customer.setEmail(resultSet.getString("email"));

        return customer;
    }

}
