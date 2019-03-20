package uk.co.caeldev.builder4test;

import java.util.function.Function;
import java.util.function.Supplier;

public interface ApplyField<L> {

    <U> L applyCreator(Field<U> field, Function<LookUp, U> creator);
    <U> L applySupplier(Field<U> field, Supplier<U> supplier);
    <U> L applyValue(Field<U> field, U value);

}
