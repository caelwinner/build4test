package uk.co.caeldev.builder4test;

import uk.co.caeldev.builder4test.resolvers.Resolver;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class DefaultLookUp extends LookUp {

    private Map<Field, Resolver> values;

    protected DefaultLookUp() {
        this.values = new HashMap<>();
    }

    protected DefaultLookUp(Map<Field, Resolver> values) {
        this.values = values;
    }

    @Override
    protected <V, U> void put(Field<V> field, Resolver<V, U> value) {
        values.put(field, value);
    }

    @Override
    public <V> V get(Field<V> field, V defaultValue) {
        Resolver<V, ?> resolver = values.get(field);

        if (isNull(resolver)) {
            return defaultValue;
        }

        return resolver.resolve();
    }

    @Override
    public <V> V get(Field<V> field) {
        return get(field, field.getDefaultValue());
    }
}
