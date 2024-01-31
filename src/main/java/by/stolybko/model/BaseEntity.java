package by.stolybko.model;


public abstract class BaseEntity<U> {

    private U id;

    public U getUuid() {
        return id;
    }

    public void setId(U id) {
        this.id = id;
    }

}
