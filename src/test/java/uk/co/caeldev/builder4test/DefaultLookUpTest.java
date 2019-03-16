package uk.co.caeldev.builder4test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultLookUpTest {

    private DefaultLookUp defaultLookUp;

    @BeforeEach
    public void setUp() {
        this.defaultLookUp = new DefaultLookUp();
    }

    @Test
    @DisplayName("Should put and retrieve a value for a field")
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
    @DisplayName("Should retrieve a value for a field")
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
    @DisplayName("Should retrieve default values when there is no value for a field")
    public void shouldGetDefaultValueWhenItDoesExists() {
        //Given
        Field<String> field = new Field<>();

        //When
        String value = defaultLookUp.get(field, "defaultValue");

        //Then
        assertThat(value).isEqualTo("defaultValue");
    }

    @Test
    @DisplayName("Should retrieve null when there is null value for a field")
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
    @DisplayName("Should retrieve default value declared by using field constructor")
    public void shouldGetDefaultWhenItUseFieldDefaultValue() {
        //Given
        Field<String> field = new Field<>("defaultValue");

        //When
        String value = defaultLookUp.get(field);

        //Then
        assertThat(value).isEqualTo("defaultValue");
    }

    @Test
    @DisplayName("Should retrieve null when there is no default value and no value for a field")
    public void shouldGetNullWhenThereIsNoDefaultValue() {
        //Given
        Field<String> field = new Field<>();

        //When
        String value = defaultLookUp.get(field);

        //Then
        assertThat(value).isNull();
    }

    @Test
    @DisplayName("Should override the default value set by field constructor by default value pass as argument ")
    public void shouldIgnoreDefaultValueBeenSetAtFieldInstantiation() {
        //Given
        Field<String> field = new Field<>("defaultValue");

        //When
        String value = defaultLookUp.get(field, "defaultValueOverride");

        //Then
        assertThat(value).isEqualTo("defaultValueOverride");
    }
}