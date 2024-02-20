package by.stolybko.exception;


import java.util.UUID;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(UUID uuid, Class<?> clazz) {
        super("Entity " + clazz.getSimpleName() + " with id " + uuid + " is not found");
    }

}
