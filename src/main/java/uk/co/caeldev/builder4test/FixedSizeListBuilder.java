package uk.co.caeldev.builder4test;

import uk.co.caeldev.builder4test.resolvers.FunctionResolver;
import uk.co.caeldev.builder4test.resolvers.Resolver;
import uk.co.caeldev.builder4test.resolvers.SupplierResolver;
import uk.co.caeldev.builder4test.resolvers.ValueResolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FixedSizeListBuilder<K> implements ApplyField<FixedSizeListBuilder<K>> {

    private final int size;
    private final Function<LookUp, K> creator;
    private final Map<Field, Resolver> values;

    private FixedSizeListBuilder(int size, Function<LookUp, K> creator) {
        this.size = size;
        this.creator = creator;
        values = new HashMap<>();

    }

    protected static <U> FixedSizeListBuilder<U> fixedSizeListBuilder(int size, Function<LookUp, U> creator) {
        return new FixedSizeListBuilder<>(size, creator);
    }

    @Override
    public <U> FixedSizeListBuilder<K> applySupplier(Field<U> field, Supplier<U> supplier) {
        values.put(field, new SupplierResolver(supplier));
        return this;
    }

    @Override
    public <U> FixedSizeListBuilder<K> applyValue(Field<U> field, U value) {
        values.put(field, new ValueResolver<>(value));
        return this;
    }

    @Override
    public <U> FixedSizeListBuilder<K> applyCreator(Field<U> field, Function<LookUp, U> creator) {
        applySupplier(field, () -> creator.apply(new DefaultLookUp(values)));
        return this;
    }

    public <U> FixedSizeListBuilder<K> applySequence(Field<U> field, Function<Integer, U> function) {
        values.put(field, new FunctionResolver<>(function));
        return this;
    }

    public List<K> get() {
        LookUp lookUp = new DefaultLookUp(values);
        return IntStream.rangeClosed(1, size)
                .mapToObj(it -> {
                    passArgumentToSequenceFunctions(it);
                    return EntityBuilder.entityBuilder(creator, lookUp).get();
                })
                .collect(Collectors.toList());
    }

    private void passArgumentToSequenceFunctions(int it) {
        values.entrySet().stream()
                .filter(entry -> entry.getValue() instanceof FunctionResolver)
                .forEach(entry -> ((FunctionResolver) entry.getValue()).setArgument(it));
    }
}
