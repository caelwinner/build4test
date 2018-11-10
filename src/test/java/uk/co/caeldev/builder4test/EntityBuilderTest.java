package uk.co.caeldev.builder4test;

import org.junit.jupiter.api.Test;
import uk.co.caeldev.builder4test.impl.Pojo;
import uk.co.caeldev.builder4test.impl.PojoCreator;

import static org.assertj.core.api.Assertions.assertThat;

class EntityBuilderTest {

    @Test
    public void shouldBuild() {
        EntityBuilder entityBuilder = EntityBuilder.entityBuilder(PojoCreator.creator);
        EntityBuilder entityBuilder2 = EntityBuilder.entityBuilder(PojoCreator.creator);

        assertThat(entityBuilder).isNotEqualTo(entityBuilder2);
    }

    @Test
    public void shouldGetEntityUsingDefaultValues() {
        Pojo pojo = EntityBuilder.entityBuilder(PojoCreator.creator).get();

        assertThat(pojo.getName()).isEqualTo("defaultName");
        assertThat(pojo.getValue()).isEqualTo("defaultValue");
    }

    @Test
    public void shouldBindValueAndNotUseDefault() {
        Pojo pojo = EntityBuilder.entityBuilder(PojoCreator.creator)
                .override(PojoCreator.name, "newNAme")
                .override(PojoCreator.value, "newValue")
                .get();

        assertThat(pojo.getName()).isEqualTo("newNAme");
        assertThat(pojo.getValue()).isEqualTo("newValue");
    }
}