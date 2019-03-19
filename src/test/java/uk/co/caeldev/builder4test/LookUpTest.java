package uk.co.caeldev.builder4test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.caeldev.builder4test.resolvers.Resolver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LookUpTest {

    @Mock
    private Creator<String> creator;

    @Test
    @DisplayName("Should Create two different instance of ElementListBuilder")
    public void shouldUseCreatorAsValue() {
        //Given
        LookUp testLookUp = new TestLookUp();

        //When
        testLookUp.get(new Field<String>(), creator);

        //Then
        verify(creator).build(any(LookUp.class));
    }

    class TestLookUp extends LookUp {

        @Override
        protected <V, U> void put(Field<V> field, Resolver<V, U> value) {

        }

        @Override
        public <V> V get(Field<V> field) {
            return null;
        }

        @Override
        public <V> V get(Field<V> field, V defaultValue) {
            return null;
        }
    }

}