package by.stolybko.repository;

import by.stolybko.model.Product;

import java.util.UUID;

public interface ProductRepository extends CRUDRepository<Product, UUID> {
}
