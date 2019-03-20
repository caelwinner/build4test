package uk.co.caeldev.builder4test.resolvers;

import java.util.function.Function;

public class FunctionResolver<T, U> extends Resolver<T, Function> {

    private U argument;

    public FunctionResolver(Function applier) {
        super(applier);
    }

    @Override
    public T resolve() {
        return (T)applier.apply(argument);
    }

    public void setArgument(U argument) {
        this.argument = argument;
    }
}
