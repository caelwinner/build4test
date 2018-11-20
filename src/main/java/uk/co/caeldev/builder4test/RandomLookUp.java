package uk.co.caeldev.builder4test;

import uk.org.fyodor.generators.Generator;

import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class RandomLookUp extends LookUp {

    private Map<Field, Optional> values;
    private Map<Field, Generator> generators;

    protected RandomLookUp(Map<Field, Optional> values, Map<Field, Generator> generators) {
        this.values = values;
        this.generators = generators;
    }

    @Override
    protected <V> void put(Field<V> field, V value) {
        values.put(field, Optional.ofNullable(value));
    }

    @Override
    public <V> V get(Field<V> field, V defaultValue) {
        Optional optValue = values.get(field);

        if (isNull(optValue)) {
            Generator generator = generators.get(field);

            if (nonNull(generator)) {
                return (V)generator.next();
            }

            return defaultValue;
        }

        if (optValue.isPresent()) {
            return (V)optValue.get();
        }

        return null;
    }

    @Override
    public <V> V get(Field<V> field) {
        return get(field, field.getDefaultValue());
    }
}
