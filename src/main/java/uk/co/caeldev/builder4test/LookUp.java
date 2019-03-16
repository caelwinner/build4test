package uk.co.caeldev.builder4test;

public abstract class LookUp {

    protected abstract <V> void put(Field<V> field, V value);

    public abstract <V> V get(Field<V> field);

    public abstract <V> V get(Field<V> field, V defaultValue);

    public <V> V get(Field<V> field, Creator<V> defaultValue) {
        return get(field, defaultValue.build(this));
    }

}
