package by.stolybko.service;


import java.util.List;
import java.util.UUID;

public interface CRUDService<RESPONSE, REQUEST> {

    RESPONSE getById(UUID id);

    boolean deleteById(UUID id);

    List<RESPONSE> getAll();

    RESPONSE save(REQUEST r);

    RESPONSE update(UUID id, REQUEST r);

}
