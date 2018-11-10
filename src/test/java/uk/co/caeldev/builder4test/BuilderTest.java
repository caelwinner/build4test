package uk.co.caeldev.builder4test;

import org.junit.jupiter.api.Test;
import uk.co.caeldev.builder4test.impl.PojoCreator;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.caeldev.builder4test.Builder.build;

class BuilderTest {

    @Test
    public void shouldReturnANewInstanceOfBuilder() {
        Builder instance1 = build();
        Builder instance2 = build();

        assertThat(instance1).isNotEqualTo(instance2);
    }

    @Test
    public void shouldUseDefaultEntity() {
        EntityBuilder entityBuilder = build().entity(PojoCreator.creator);
        EntityBuilder entityBuilder2 = build().entity(PojoCreator.creator);

        assertThat(entityBuilder).isNotEqualTo(entityBuilder2);
    }

}