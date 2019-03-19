package uk.co.caeldev.builder4test.resolvers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FunctionResolverTest {

    @Test
    @DisplayName("Should resolve the function with non null argument")
    void shouldResolveFunction() {
        //Given
        FunctionResolver<String, String> resolver = new FunctionResolver<>((it) -> it + "1", "test");

        //When
        String result = resolver.resolve();

        //Then
        assertThat(result).isEqualTo("test1");
    }
}