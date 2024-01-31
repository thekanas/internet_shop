package by.stolybko.repository;

import by.stolybko.model.Customer;

import java.util.UUID;

public interface CustomerRepository extends CRUDRepository<Customer, UUID> {
}
