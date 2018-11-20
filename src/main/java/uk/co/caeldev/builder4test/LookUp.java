package uk.co.caeldev.builder4test;

public abstract class LookUp {

    protected abstract <V> void put(Field<V> field, V value);

    public abstract <V> V get(Field<V> field);

    public abstract <V> V get(Field<V> field, V defaultValue);

}
