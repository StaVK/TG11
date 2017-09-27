package ru.javawebinar.topjava.model;

public class BaseEntity {
    public static final int START_SEQ = 100000;

    protected Integer id;

    protected BaseEntity() {
    }
    protected BaseEntity(Integer id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getId() {
        return id;
    }


    public boolean isNew() {
        return (getId() == null);
    }
}
