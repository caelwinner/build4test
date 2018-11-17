package uk.co.caeldev.builder4test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ElementBuilder<K> {
    private final ListBuilder<K> listBuilder;
    private final Map<Field, Optional> fields;

    private ElementBuilder(ListBuilder<K> listBuilder) {
        this.listBuilder = listBuilder;
        this.fields = new HashMap<>();
    }

    protected static <V> ElementBuilder<V> elementBuilder(ListBuilder<V> listBuilder) {
        return new ElementBuilder<>(listBuilder);
    }

    public <U> ElementBuilder<K> override(Field<U> field, U value) {
        this.fields.put(field, Optional.ofNullable(value));
        return this;
    }

    protected Map<Field, Optional> getFields() {
        return this.fields;
    }

    public ListBuilder<K> end() {
        return listBuilder;
    }
}
