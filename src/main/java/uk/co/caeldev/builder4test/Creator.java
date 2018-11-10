package uk.co.caeldev.builder4test;

public interface Creator<T> {

    T build(LookUp lookUp);

}
