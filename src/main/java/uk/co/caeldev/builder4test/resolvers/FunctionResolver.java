package uk.co.caeldev.builder4test.resolvers;

import java.util.function.Function;

public class FunctionResolver<T, U> extends Resolver<T, Function> {

    private final U argument;

    public FunctionResolver(Function applier, U argument) {
        super(applier);
        this.argument = argument;
    }

    @Override
    public T resolve() {
        return (T)applier.apply(argument);
    }
}
