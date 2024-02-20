package by.stolybko.repository;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<T, K> {

    Optional<T> findById(K id);

    boolean deleteById(K id);

    List<T> findAll();

    Optional<T> save(T t);

    Optional<T> update(K id, T t);

}
