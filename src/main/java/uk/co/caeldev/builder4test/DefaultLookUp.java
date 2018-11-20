package uk.co.caeldev.builder4test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;

public class DefaultLookUp extends LookUp {

    private Map<Field, Optional> values;

    protected DefaultLookUp() {
        this.values = new HashMap<>();
    }

    protected DefaultLookUp(Map<Field, Optional> values) {
        this.values = values;
    }

    @Override
    protected <V> void put(Field<V> field, V value) {
        values.put(field, Optional.ofNullable(value));
    }

    @Override
    public <V> V get(Field<V> field, V defaultValue) {
        Optional optValue = values.get(field);

        if (isNull(optValue)) {
            return defaultValue;
        }

        if (optValue.isPresent()) {
            return (V) optValue.get();
        }

        return null;
    }

    @Override
    public <V> V get(Field<V> field) {
        return get(field, field.getDefaultValue());
    }
}
