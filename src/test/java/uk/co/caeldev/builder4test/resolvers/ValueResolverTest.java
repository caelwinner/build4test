package uk.co.caeldev.builder4test.resolvers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ValueResolverTest {

    @Test
    @DisplayName("Should resolve a non null value and return it")
    void shouldResolveValue() {
        //Given
        String expectedValue = "test1";
        ValueResolver<String> valueResolver = new ValueResolver<>(expectedValue);

        //When
        String result = valueResolver.resolve();

        //Then
        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Should resolve a null value and return it")
    void shouldResolveNullValue() {
        //Given
        ValueResolver<String> valueResolver = new ValueResolver<>(null);

        //When
        String result = valueResolver.resolve();

        //Then
        assertThat(result).isNull();
    }

}