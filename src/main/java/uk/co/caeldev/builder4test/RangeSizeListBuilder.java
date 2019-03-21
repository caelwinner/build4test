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

public class RangeSizeListBuilder<K> implements ApplyField<RangeSizeListBuilder<K>> {

    private final Function<LookUp, K> creator;
    private final Map<Field, Resolver> values;
    private final int start;
    private final int end;

    private RangeSizeListBuilder(int start, int end, Function<LookUp, K> creator) {
        this.start = start;
        this.end = end;
        this.creator = creator;
        values = new HashMap<>();

    }

    protected static <U> RangeSizeListBuilder<U> rangeSizeListBuilder(int start, int end, Function<LookUp, U> creator) {
        return new RangeSizeListBuilder<>(start, end, creator);
    }

    @Override
    public <U> RangeSizeListBuilder<K> applySupplier(Field<U> field, Supplier<U> supplier) {
        values.put(field, new SupplierResolver(supplier));
        return this;
    }

    @Override
    public <U> RangeSizeListBuilder<K> applyValue(Field<U> field, U value) {
        values.put(field, new ValueResolver<>(value));
        return this;
    }

    @Override
    public <U> RangeSizeListBuilder<K> applyCreator(Field<U> field, Function<LookUp, U> creator) {
        applySupplier(field, () -> creator.apply(new DefaultLookUp(values)));
        return this;
    }

    public <U> RangeSizeListBuilder<K> applySequence(Field<U> field, Function<Integer, U> function) {
        values.put(field, new FunctionResolver<>(function));
        return this;
    }

    public List<K> get() {
        LookUp lookUp = new DefaultLookUp(values);
        return IntStream.rangeClosed(start, end)
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
