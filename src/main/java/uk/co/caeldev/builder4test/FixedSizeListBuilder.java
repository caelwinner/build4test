package uk.co.caeldev.builder4test;

import uk.co.caeldev.builder4test.resolvers.Resolver;
import uk.co.caeldev.builder4test.resolvers.SupplierResolver;
import uk.co.caeldev.builder4test.resolvers.ValueResolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FixedSizeListBuilder<K> implements OverrideField<FixedSizeListBuilder<K>>{

    private final int size;
    private final Creator<K> creator;
    private final Map<Field, Resolver> values;

    private FixedSizeListBuilder(int size, Creator<K> creator) {
        this.size = size;
        this.creator = creator;
        values = new HashMap<>();

    }

    protected static <U> FixedSizeListBuilder<U> fixedSizeListBuilder(int size, Creator<U> creator) {
        return new FixedSizeListBuilder<>(size, creator);
    }

    @Override
    public <U> FixedSizeListBuilder<K> override(Field<U> field, Supplier<U> supplier) {
        values.put(field, new SupplierResolver(supplier));
        return this;
    }

    @Override
    public <U> FixedSizeListBuilder<K> overrideValue(Field<U> field, U value) {
        values.put(field, new ValueResolver<>(value));
        return this;
    }

    @Override
    public <U> FixedSizeListBuilder<K> override(Field<U> field, Creator<U> creator) {
        override(field, () -> creator.build(new DefaultLookUp(values)));
        return this;
    }

    public List<K> get() {
        LookUp lookUp = new DefaultLookUp(values);
        return IntStream.rangeClosed(1, size)
                .mapToObj(it -> EntityBuilder.entityBuilder(creator, lookUp).get())
                .collect(Collectors.toList());
    }
}
