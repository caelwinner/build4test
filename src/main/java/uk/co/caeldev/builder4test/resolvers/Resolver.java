package uk.co.caeldev.builder4test.resolvers;

public abstract class Resolver<T, V> {

    final V applier;

    public Resolver(V applier) {
        this.applier = applier;
    }

    public abstract T resolve();
}
