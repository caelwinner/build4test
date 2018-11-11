package uk.co.caeldev.builder4test;

import java.util.Map;
import java.util.Optional;

public class EntityBuilder<K> {

    private final Creator<K> creator;
    private final LookUp lookUp;

    private EntityBuilder(Creator<K> creator) {
        this.creator = creator;
        this.lookUp = new LookUp();
    }

    private EntityBuilder(Creator<K> creator, Map<Field, Optional> fields) {
        this.creator = creator;
        this.lookUp = new LookUp(fields);
    }

    protected static <T> EntityBuilder<T> entityBuilder(Creator<T> Creator, Map<Field, Optional> fields) {
        return new EntityBuilder<>(Creator, fields);
    }

    protected static <T> EntityBuilder<T> entityBuilder(Creator<T> Creator) {
        return new EntityBuilder<>(Creator);
    }

    public <V> EntityBuilder<K> override(Field<V> field, V value) {
        lookUp.put(field, value);
        return this;
    }

    public K get() {
        return creator.build(lookUp);
    }
}
