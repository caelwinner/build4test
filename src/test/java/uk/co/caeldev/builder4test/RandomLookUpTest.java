package uk.co.caeldev.builder4test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.org.fyodor.generators.RDG.string;

class RandomLookUpTest {


    @Test
    public void shouldLookupARandomValue() {
        //Given
        Field<String> field = new Field<>();
        RandomLookUp lookUp = new RandomLookUp(Maps.newHashMap(), ImmutableMap.of(field, string()));

        //When
        String result1 = lookUp.get(field);
        String result2 = lookUp.get(field);


        //Then
        assertThat(result1).isNotBlank();
        assertThat(result2).isNotBlank();
        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    public void shouldLookupARandomValueAndConstantValue() {
        //Given
        Field<String> field1 = new Field<>();
        Field<String> field2 = new Field<>();
        RandomLookUp lookUp = new RandomLookUp(ImmutableMap.of(field1, Optional.of("test")),
                ImmutableMap.of(field2, string()));

        //When
        String result1 = lookUp.get(field1);
        String result2 = lookUp.get(field2);


        //Then
        assertThat(result1).isNotBlank();
        assertThat(result2).isNotBlank();
        assertThat(result1).isEqualTo("test");
        assertThat(result2).isNotEqualTo(result1);
    }

    @Test
    public void shouldLookupConstantValueOverridesRandomValueForDoubleDefinition() {
        //Given
        Field<String> field1 = new Field<>();
        RandomLookUp lookUp = new RandomLookUp(ImmutableMap.of(field1, Optional.of("test")),
                ImmutableMap.of(field1, string()));

        //When
        String result1 = lookUp.get(field1);

        //Then
        assertThat(result1).isNotBlank();
        assertThat(result1).isEqualTo("test");
    }

    @Test
    public void shouldLookupForDefaultValueWhenThereIsNoValue() {
        //Given
        Field<String> field1 = new Field<>();
        RandomLookUp lookUp = new RandomLookUp(Maps.newHashMap(), Maps.newHashMap());

        //When
        String result1 = lookUp.get(field1, "test");

        //Then
        assertThat(result1).isEqualTo("test");
    }

    @Test
    public void shouldLookupForNullWhenThereIsNullValueSet() {
        //Given
        Field<String> field1 = new Field<>();
        RandomLookUp lookUp = new RandomLookUp(ImmutableMap.of(field1, Optional.empty()), Maps.newHashMap());

        //When
        String result1 = lookUp.get(field1, "test");

        //Then
        assertThat(result1).isNull();
    }

    @Test
    public void shouldLookupForEmptyWhenThereIsEmptyValueSet() {
        //Given
        Field<String> field1 = new Field<>();
        RandomLookUp lookUp = new RandomLookUp(ImmutableMap.of(field1, Optional.of("")), Maps.newHashMap());

        //When
        String result1 = lookUp.get(field1, "test");

        //Then
        assertThat(result1).isEmpty();
    }
}