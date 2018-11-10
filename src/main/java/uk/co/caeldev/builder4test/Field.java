package uk.co.caeldev.builder4test;

public class Field<T> {

    T defaultValue;

    public Field() {
    }

    public Field(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

}
