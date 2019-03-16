package uk.co.caeldev.builder4test;

public interface OverrideField<L> {

    <U> L override(Field<U> field, Creator<U> creator);
    <U> L override(Field<U> field, U value);

}
