package uk.co.caeldev.builder4test;

import java.util.Map;
import java.util.Optional;

public class EntityBuilder<K> {

    private final Creator<K> creator;
    private final LookUp lookUp;

    private EntityBuilder(Creator<K> creator) {
        this.creator = creator;
        this.lookUp = new DefaultLookUp();
    }

    private EntityBuilder(Creator<K> creator, Map<Field, Optional> fields) {
        this.creator = creator;
        this.lookUp = new DefaultLookUp(fields);
    }

    private EntityBuilder(Creator<K> creator, LookUp lookUp) {
        this.creator = creator;
        this.lookUp = lookUp;
    }

    protected static <T> EntityBuilder<T> entityBuilder(Creator<T> Creator, Map<Field, Optional> fields) {
        return new EntityBuilder<>(Creator, fields);
    }

    protected static <T> EntityBuilder<T> entityBuilder(Creator<T> Creator) {
        return new EntityBuilder<>(Creator);
    }

    protected static <T> EntityBuilder<T> entityBuilder(Creator<T> Creator, LookUp lookUp) {
        return new EntityBuilder<>(Creator, lookUp);
    }

    public <V> EntityBuilder<K> override(Field<V> field, V value) {
        lookUp.put(field, value);
        return this;
    }

    public <V> EntityBuilder<K> nullify(Field<V> field) {
        lookUp.put(field, null);
        return this;
    }

    public K get() {
        return creator.build(lookUp);
    }
}
