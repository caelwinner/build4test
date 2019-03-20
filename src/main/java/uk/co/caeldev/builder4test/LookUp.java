package uk.co.caeldev.builder4test;

import uk.co.caeldev.builder4test.resolvers.Resolver;

import java.util.function.Function;

public abstract class LookUp {

    protected abstract <V, U> void put(Field<V> field, Resolver<V, U> value);

    public abstract <V> V get(Field<V> field);

    public abstract <V> V get(Field<V> field, V defaultValue);

    public <V> V get(Field<V> field, Function<LookUp, V> defaultValue) {
        return get(field, defaultValue.apply(this));
    }

}
