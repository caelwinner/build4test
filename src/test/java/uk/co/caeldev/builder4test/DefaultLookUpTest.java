package uk.co.caeldev.builder4test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultLookUpTest {

    private DefaultLookUp defaultLookUp;

    @BeforeEach
    public void setUp() {
        this.defaultLookUp = new DefaultLookUp();
    }

    @Test
    public void shouldPutAValue() {
        //Given
        Field<String> field = new Field<>();

        //When
        defaultLookUp.put(field, "defaultValue");

        //Then
        String value = defaultLookUp.get(field);
        assertThat(value).isEqualTo("defaultValue");
    }

    @Test
    public void shouldGetAValueWhenExists() {
        //Given
        Field<String> field = new Field<>();

        //And
        defaultLookUp.put(field, "defaultValue");

        //When
        String value = defaultLookUp.get(field, "defaultValue");

        //Then
        assertThat(value).isEqualTo("defaultValue");
    }

    @Test
    public void shouldGetDefaultValueWhenItDoesExists() {
        //Given
        Field<String> field = new Field<>();

        //When
        String value = defaultLookUp.get(field, "defaultValue");

        //Then
        assertThat(value).isEqualTo("defaultValue");
    }

    @Test
    public void shouldGetNullWhenItHasBeenOverrideWithNull() {
        //Given
        Field<String> field = new Field<>();

        //And
        defaultLookUp.put(field, null);

        //When
        String value = defaultLookUp.get(field, "defaultValue");

        //Then
        assertThat(value).isNull();
    }

    @Test
    public void shouldGetNullWhenThereIsNoValueAndDefaultValueIsNull() {
        //Given
        Field<String> field = new Field<>();

        //When
        String value = defaultLookUp.get(field, null);

        //Then
        assertThat(value).isNull();
    }

    @Test
    public void shouldGetDefaultWhenItUseFieldDefaultValue() {
        //Given
        Field<String> field = new Field<>("defaultValue");

        //When
        String value = defaultLookUp.get(field);

        //Then
        assertThat(value).isEqualTo("defaultValue");
    }

    @Test
    public void shouldGetNullWhenThereIsNoDefaultValue() {
        //Given
        Field<String> field = new Field<>();

        //When
        String value = defaultLookUp.get(field);

        //Then
        assertThat(value).isNull();
    }

    @Test
    public void shouldIgnoreDefaultValueBeenSetAtFieldInstantiation() {
        //Given
        Field<String> field = new Field<>("defaultValue");

        //When
        String value = defaultLookUp.get(field, "defaultValueOverride");

        //Then
        assertThat(value).isEqualTo("defaultValueOverride");
    }
}