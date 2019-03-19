package uk.co.caeldev.builder4test.resolvers;

import java.util.function.Supplier;

public class SupplierResolver<T> extends Resolver<T, Supplier> {

    public SupplierResolver(Supplier applier) {
        super(applier);
    }

    @Override
    public T resolve() {
        return (T)applier.get();
    }

}
