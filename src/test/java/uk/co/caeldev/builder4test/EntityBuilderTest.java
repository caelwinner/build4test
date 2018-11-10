package uk.co.caeldev.builder4test;

import org.junit.jupiter.api.Test;
import uk.co.caeldev.builder4test.impl.Pojo;
import uk.co.caeldev.builder4test.impl.PojoBuilder;

import static org.assertj.core.api.Assertions.assertThat;

class EntityBuilderTest {

    @Test
    public void shouldBuild() {
        EntityBuilder entityBuilder = EntityBuilder.entityBuilder(PojoBuilder.creator);
        EntityBuilder entityBuilder2 = EntityBuilder.entityBuilder(PojoBuilder.creator);

        assertThat(entityBuilder).isNotEqualTo(entityBuilder2);
    }

    @Test
    public void shouldGetEntityUsingDefaultValues() {
        Pojo pojo = EntityBuilder.entityBuilder(PojoBuilder.creator).get();

        assertThat(pojo.getName()).isEqualTo("defaultName");
        assertThat(pojo.getValue()).isEqualTo("defaultValue");
    }

    @Test
    public void shouldBindValueAndNotUseDefault() {
        Pojo pojo = EntityBuilder.entityBuilder(PojoBuilder.creator)
                .override(PojoBuilder.name, "newNAme")
                .override(PojoBuilder.value, "newValue")
                .get();

        assertThat(pojo.getName()).isEqualTo("newNAme");
        assertThat(pojo.getValue()).isEqualTo("newValue");
    }
}