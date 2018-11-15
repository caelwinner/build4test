package uk.co.caeldev.builder4test;

public interface LookUp {

    <V> void put(Field<V> field, V value);

    <V> V get(Field<V> field);

    <V> V get(Field<V> field, V defaultValue);

}
