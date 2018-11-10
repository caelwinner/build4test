package uk.co.caeldev.builder4test;

public class EntityBuilder<K> {

    private final Creator<K> creator;
    private final LookUp lookUp;

    EntityBuilder(Creator<K> creator) {
        this.creator = creator;
        this.lookUp = new LookUp();
    }

    static <T> EntityBuilder<T> entityBuilder(Creator<T> Creator) {
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
