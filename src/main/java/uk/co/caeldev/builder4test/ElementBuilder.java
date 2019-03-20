package uk.co.caeldev.builder4test;

import uk.co.caeldev.builder4test.resolvers.Resolver;
import uk.co.caeldev.builder4test.resolvers.SupplierResolver;
import uk.co.caeldev.builder4test.resolvers.ValueResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ElementBuilder<K> implements ApplyField<ElementBuilder<K>> {

    private final ElementListBuilder<K> elementListBuilder;
    private final Map<Field, Resolver> fields;

    private ElementBuilder(ElementListBuilder<K> elementListBuilder) {
        this.elementListBuilder = elementListBuilder;
        this.fields = new HashMap<>();
    }

    public static <U> ElementBuilder<U> elementBuilder(ElementListBuilder<U> elementListBuilder) {
        return new ElementBuilder<>(elementListBuilder);
    }

    protected Map<Field, Resolver> getFields() {
        return this.fields;
    }

    @Override
    public <U> ElementBuilder<K> applySupplier(Field<U> field, Supplier<U> value) {
        this.fields.put(field, new SupplierResolver(value));
        return this;
    }

    @Override
    public <U> ElementBuilder<K> applyValue(Field<U> field, U value) {
        this.fields.put(field, new ValueResolver<>(value));
        return this;
    }

    @Override
    public <U> ElementBuilder<K> applyCreator(Field<U> field, Creator<U> creator) {
        return applySupplier(field, () -> creator.build(new DefaultLookUp(fields)));
    }

    public <U> ElementBuilder<K> nullify(Field<U> field) {
        this.fields.put(field, new SupplierResolver(() -> null));
        return this;
    }

    public ElementListBuilder<K> end() {
        return elementListBuilder;
    }
}
