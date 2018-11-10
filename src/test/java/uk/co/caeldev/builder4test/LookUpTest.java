package uk.co.caeldev.builder4test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LookUpTest {

    private LookUp lookUp;

    @BeforeEach
    public void setup() {
        this.lookUp = new LookUp();
    }

    @Test
    public void shouldPutAValue() {
        //Given
        Field<String> field = new Field<>();

        //When
        lookUp.put(field, "defaultValue");

        //Then
        Optional optional = lookUp.values.get(field);
        assertThat(optional).isPresent();
    }

    @Test
    public void shouldGetAValueWhenExists() {
        //Given
        Field<String> field = new Field<>();

        //And
        lookUp.put(field, "defaultValue");

        //When
        String value = lookUp.get(field, "defaultValue");

        //Then
        assertThat(value).isEqualTo("defaultValue");
    }

    @Test
    public void shouldGetDefaultValueWhenItDoesExists() {
        //Given
        Field<String> field = new Field<>();

        //When
        String value = lookUp.get(field, "defaultValue");

        //Then
        assertThat(value).isEqualTo("defaultValue");
    }

    @Test
    public void shouldGetNullWhenItHasBeenOverrideWithNull() {
        //Given
        Field<String> field = new Field<>();

        //And
        lookUp.put(field, null);

        //When
        String value = lookUp.get(field, "defaultValue");

        //Then
        assertThat(value).isNull();
    }

    @Test
    public void shouldGetNullWhenThereIsNoValueAndDefaultValueIsNull() {
        //Given
        Field<String> field = new Field<>();

        //When
        String value = lookUp.get(field, null);

        //Then
        assertThat(value).isNull();
    }

    @Test
    public void shouldGetDefaultWhenItUseFieldDefaultValue() {
        //Given
        Field<String> field = new Field<>("defaultValue");

        //When
        String value = lookUp.get(field);

        //Then
        assertThat(value).isEqualTo("defaultValue");
    }

    @Test
    public void shouldGetNullWhenThereIsNoDefaultValue() {
        //Given
        Field<String> field = new Field<>();

        //When
        String value = lookUp.get(field);

        //Then
        assertThat(value).isNull();
    }

    @Test
    public void shouldIgnoreDefaultValueBeenSetAtFieldInstantiation() {
        //Given
        Field<String> field = new Field<>("defaultValue");

        //When
        String value = lookUp.get(field, "defaultValueOverride");

        //Then
        assertThat(value).isEqualTo("defaultValueOverride");
    }
}