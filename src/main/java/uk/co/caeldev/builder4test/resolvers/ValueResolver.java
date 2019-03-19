package uk.co.caeldev.builder4test.resolvers;

public class ValueResolver<T> extends Resolver<T, T> {

    public ValueResolver(T applier) {
        super(applier);
    }

    @Override
    public T resolve() {
        return applier;
    }
}
