package uk.co.caeldev.builder4test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.co.caeldev.builder4test.impl.PojoBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.caeldev.builder4test.Builder.build;

class BuilderTest {

    @Test
    @DisplayName("Should be abe to create to different instances of Builder")
    public void shouldReturnANewInstanceOfBuilder() {
        Builder instance1 = build();
        Builder instance2 = build();

        assertThat(instance1).isNotEqualTo(instance2);
    }

    @Test
    @DisplayName("Should be able to build two different instances of the EntityBuilder")
    public void shouldBeAbleBuildDifferentInstancesOfEntityBuilder() {
        EntityBuilder entityBuilder = build().entity(PojoBuilder.creator);
        EntityBuilder entityBuilder2 = build().entity(PojoBuilder.creator);

        assertThat(entityBuilder).isNotEqualTo(entityBuilder2);
    }

    @Test
    @DisplayName("Should be able to build two different instances of the ListBuilder")
    public void shouldBeAbleBuildDifferentInstancesOfListBuilder() {
        ListBuilder listBuilder = build().list(PojoBuilder.creator);
        ListBuilder listBuilder2 = build().list(PojoBuilder.creator);

        assertThat(listBuilder).isNotEqualTo(listBuilder2);
    }

}