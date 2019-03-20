package uk.co.caeldev.builder4test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.co.caeldev.builder4test.impl.Pojo;
import uk.co.caeldev.builder4test.impl.PojoBuilder;

import static org.assertj.core.api.Assertions.assertThat;

class EntityBuilderTest {

    @Test
    @DisplayName("Should build different instances of EntityBuilder")
    public void shouldBuild() {
        EntityBuilder entityBuilder = EntityBuilder.entityBuilder(PojoBuilder.creator);
        EntityBuilder entityBuilder2 = EntityBuilder.entityBuilder(PojoBuilder.creator);

        assertThat(entityBuilder).isNotEqualTo(entityBuilder2);
    }

    @Test
    @DisplayName("Should build entity using the default values")
    public void shouldGetEntityUsingDefaultValues() {
        Pojo pojo = EntityBuilder.entityBuilder(PojoBuilder.creator).get();

        assertThat(pojo.getName()).isEqualTo("defaultName");
        assertThat(pojo.getValue()).isEqualTo("defaultValue");
    }

    @Test
    @DisplayName("Should be able to override the default values")
    public void shouldBindValueAndNotUseDefault() {
        Pojo pojo = EntityBuilder.entityBuilder(PojoBuilder.creator)
                .applyValue(PojoBuilder.name, "newNAme")
                .applyValue(PojoBuilder.value, "newValue")
                .get();

        assertThat(pojo.getName()).isEqualTo("newNAme");
        assertThat(pojo.getValue()).isEqualTo("newValue");
    }

    @Test
    @DisplayName("Should be able to override the default values using suppliers")
    public void shouldBindValueAndNotUseDefaultUsingSuppliers() {
        Pojo pojo = EntityBuilder.entityBuilder(PojoBuilder.creator)
                .applySupplier(PojoBuilder.name, () -> "newNAme")
                .applySupplier(PojoBuilder.value, () -> "newValue")
                .get();

        assertThat(pojo.getName()).isEqualTo("newNAme");
        assertThat(pojo.getValue()).isEqualTo("newValue");
    }

    @Test
    @DisplayName("Should be able to set null to a field")
    public void shouldBindNullValues() {
        Pojo pojo = EntityBuilder.entityBuilder(PojoBuilder.creator)
                .nullify(PojoBuilder.name)
                .applyValue(PojoBuilder.value, null)
                .get();

        assertThat(pojo.getName()).isNull();
        assertThat(pojo.getValue()).isNull();
    }

    @Test
    @DisplayName("Should be able to set null to a field using nullify method")
    public void shouldBindNullValuesUsingNullifyMethod() {
        Pojo pojo = EntityBuilder.entityBuilder(PojoBuilder.creator)
                .nullify(PojoBuilder.name)
                .applyValue(PojoBuilder.value, "defaultValue")
                .get();

        assertThat(pojo.getName()).isNull();
        assertThat(pojo.getValue()).isNotNull();
    }

    @Test
    @DisplayName("Should be able to a creator as a value of the field")
    public void shouldBeAbleToSetACreatorAsValue() {
        Pojo pojo = EntityBuilder.entityBuilder(PojoBuilder.creator)
                .nullify(PojoBuilder.name)
                .applyCreator(PojoBuilder.value, PojoBuilder.valueCreator)
                .get();

        assertThat(pojo.getName()).isNull();
        assertThat(pojo.getValue()).isNotNull();
        assertThat(pojo.getValue()).isEqualTo("test1");
    }
}