package uk.co.caeldev.builder4test.resolvers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SupplierResolverTest {

    @Test
    @DisplayName("Should resolve the supplier with non null value")
    void shouldResolveSupplierWithNonNullValues() {
        //Given
        String expectedValue = "test1";
        SupplierResolver<String> resolver = new SupplierResolver<>(() -> expectedValue);

        //When
        String result = resolver.resolve();

        //Then
        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Should resolve the supplier with null value")
    void shouldResolveSupplierWithNullValues() {
        //Given
        SupplierResolver<String> resolver = new SupplierResolver<>(() -> null);

        //When
        String result = resolver.resolve();

        //Then
        assertThat(result).isNull();
    }
}