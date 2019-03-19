package uk.co.caeldev.builder4test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.co.caeldev.builder4test.impl.Pojo;

import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.caeldev.builder4test.impl.PojoBuilder.creator;
import static uk.co.caeldev.builder4test.impl.PojoBuilder.name;
import static uk.co.caeldev.builder4test.impl.PojoBuilder.value;
import static uk.co.caeldev.builder4test.impl.PojoBuilder.valueCreator;
import static uk.org.fyodor.generators.RDG.string;

class FixedSizeListBuilderTest {
    
    @Test
    @DisplayName("Should be able to build distinct instances of FixedSizeListBuilder")
    public void shouldReturnUniqueInstances() {
        //When
        FixedSizeListBuilder<Pojo> builder1 = FixedSizeListBuilder.fixedSizeListBuilder(1, creator);
        FixedSizeListBuilder<Pojo> builder2 = FixedSizeListBuilder.fixedSizeListBuilder(1, creator);

        //Then
        assertThat(builder1).isNotEqualTo(builder2);
    }

    @Test
    @DisplayName("Should be able to build a list using defaults values")
    public void shouldBuildAListWithDefaultValuesNoRandoms() {
        //When
        FixedSizeListBuilder<Pojo> builder = FixedSizeListBuilder.fixedSizeListBuilder(2, creator);
        List<Pojo> pojos = builder.get();

        //Then
        assertThat(pojos).hasSize(2);
        assertThat(pojos.get(0).getName()).isEqualTo("defaultName");
        assertThat(pojos.get(0).getValue()).isEqualTo("defaultValue");
        assertThat(pojos.get(1).getName()).isEqualTo("defaultName");
        assertThat(pojos.get(1).getValue()).isEqualTo("defaultValue");
    }

    @Test
    @DisplayName("Should be able to build a list overriding default values")
    public void shouldBuildAListWithOverrideConstantValues() {
        //When
        FixedSizeListBuilder<Pojo> builder = FixedSizeListBuilder.fixedSizeListBuilder(2, creator)
                .overrideValue(name,  "testName").overrideValue(value, "testValue");
        List<Pojo> pojos = builder.get();

        //Then
        assertThat(pojos).hasSize(2);
        assertThat(pojos.get(0).getName()).isEqualTo("testName");
        assertThat(pojos.get(0).getValue()).isEqualTo("testValue");
        assertThat(pojos.get(1).getName()).isEqualTo("testName");
        assertThat(pojos.get(1).getValue()).isEqualTo("testValue");
    }

    @Test
    @DisplayName("Should be able to build a list overriding default values")
    public void shouldBuildAListWithOverrideConstantValuesFromSupplier() {
        //When
        FixedSizeListBuilder<Pojo> builder = FixedSizeListBuilder.fixedSizeListBuilder(2, creator)
                .override(name,  () -> "testName").override(value, () -> "testValue");
        List<Pojo> pojos = builder.get();

        //Then
        assertThat(pojos).hasSize(2);
        assertThat(pojos.get(0).getName()).isEqualTo("testName");
        assertThat(pojos.get(0).getValue()).isEqualTo("testValue");
        assertThat(pojos.get(1).getName()).isEqualTo("testName");
        assertThat(pojos.get(1).getValue()).isEqualTo("testValue");
    }

    @Test
    @DisplayName("Should be able to build a list overriding default with constant and random values")
    public void shouldBuildAListWithOverrideConstantAndRandomValues() {
        //When
        Supplier<String> stringSupplier = () -> string().next();
        FixedSizeListBuilder<Pojo> builder = FixedSizeListBuilder.fixedSizeListBuilder(2, creator)
                .override(name, () -> "testName").override(value, stringSupplier);
        List<Pojo> pojos = builder.get();

        //Then
        assertThat(pojos).hasSize(2);
        assertThat(pojos.get(0).getName()).isEqualTo("testName");
        assertThat(pojos.get(1).getName()).isEqualTo("testName");
        assertThat(pojos.get(0).getValue()).isNotEqualTo("defaultValue");
        assertThat(pojos.get(1).getValue()).isNotEqualTo("defaultValue");
        assertThat(pojos.get(0).getValue()).isNotEqualTo(pojos.get(1).getValue());
    }

    @Test
    @DisplayName("Should be able to build a list overriding default with only random values")
    public void shouldBuildAListWithOverrideRandomValues() {
        //When
        Supplier<String> stringSupplier = () -> string().next();
        FixedSizeListBuilder<Pojo> builder = FixedSizeListBuilder.fixedSizeListBuilder(2, creator)
                .override(name, stringSupplier).override(value, stringSupplier);
        List<Pojo> pojos = builder.get();

        //Then
        assertThat(pojos).hasSize(2);
        assertThat(pojos.get(0).getName()).isNotEqualTo(pojos.get(1).getName());
        assertThat(pojos.get(0).getValue()).isNotEqualTo(pojos.get(1).getValue());
    }

    @Test
    @DisplayName("Should be able to build a list overriding default with creators")
    public void shouldBuildAListWithOverrideCreatorValues() {
        //When
        FixedSizeListBuilder<Pojo> builder = FixedSizeListBuilder.fixedSizeListBuilder(2, creator)
                .override(name, valueCreator).override(value, valueCreator);
        List<Pojo> pojos = builder.get();

        //Then
        assertThat(pojos).hasSize(2);
        assertThat(pojos.get(0).getName()).isEqualTo(pojos.get(1).getName());
        assertThat(pojos.get(0).getValue()).isEqualTo(pojos.get(1).getValue());

        //And
        assertThat(pojos.get(0).getName()).isEqualTo("test1");
        assertThat(pojos.get(0).getValue()).isEqualTo("test1");
    }
}