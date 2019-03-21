package uk.co.caeldev.builder4test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.co.caeldev.builder4test.impl.Pojo;

import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.caeldev.builder4test.impl.PojoBuilder.*;
import static uk.org.fyodor.generators.RDG.string;

class RangeSizeListBuilderTest {
    
    @Test
    @DisplayName("Should be able to build distinct instances of RangeSizeListBuilder")
    public void shouldReturnUniqueInstances() {
        //When
        RangeSizeListBuilder<Pojo> builder1 = RangeSizeListBuilder.rangeSizeListBuilder(1, 1, creator);
        RangeSizeListBuilder<Pojo> builder2 = RangeSizeListBuilder.rangeSizeListBuilder(1, 1, creator);

        //Then
        assertThat(builder1).isNotEqualTo(builder2);
    }

    @Test
    @DisplayName("Should be able to build a list using defaults values")
    public void shouldBuildAListWithDefaultValuesNoRandoms() {
        //When
        RangeSizeListBuilder<Pojo> builder = RangeSizeListBuilder.rangeSizeListBuilder(1, 2, creator);
        List<Pojo> pojos = builder.get();

        //Then
        assertThat(pojos).hasSize(2);
        assertThat(pojos.get(0).getName()).isEqualTo("defaultName");
        assertThat(pojos.get(0).getValue()).isEqualTo("defaultValue");
        assertThat(pojos.get(1).getName()).isEqualTo("defaultName");
        assertThat(pojos.get(1).getValue()).isEqualTo("defaultValue");
    }

    @Test
    @DisplayName("Should be able to build a list overriding default values using Value types")
    public void shouldBuildAListWithOverrideConstantValues() {
        //When
        RangeSizeListBuilder<Pojo> builder = RangeSizeListBuilder.rangeSizeListBuilder(1, 2, creator)
                .applyValue(name,  "testName").applyValue(value, "testValue");
        List<Pojo> pojos = builder.get();

        //Then
        assertThat(pojos).hasSize(2);
        assertThat(pojos.get(0).getName()).isEqualTo("testName");
        assertThat(pojos.get(0).getValue()).isEqualTo("testValue");
        assertThat(pojos.get(1).getName()).isEqualTo("testName");
        assertThat(pojos.get(1).getValue()).isEqualTo("testValue");
    }

    @Test
    @DisplayName("Should be able to build a list overriding default values using Supplier")
    public void shouldBuildAListWithOverrideConstantValuesFromSupplier() {
        //When
        RangeSizeListBuilder<Pojo> builder = RangeSizeListBuilder.rangeSizeListBuilder(1, 2, creator)
                .applySupplier(name,  () -> "testName").applySupplier(value, () -> "testValue");
        List<Pojo> pojos = builder.get();

        //Then
        assertThat(pojos).hasSize(2);
        assertThat(pojos.get(0).getName()).isEqualTo("testName");
        assertThat(pojos.get(0).getValue()).isEqualTo("testValue");
        assertThat(pojos.get(1).getName()).isEqualTo("testName");
        assertThat(pojos.get(1).getValue()).isEqualTo("testValue");
    }

    @Test
    @DisplayName("Should be able to build a list overriding default values using Sequence Function")
    public void shouldBuildAListWithOverrideConstantValuesFromSequenceFunction() {
        //When
        RangeSizeListBuilder<Pojo> builder = RangeSizeListBuilder.rangeSizeListBuilder(1, 2, creator)
                .applySequence(name,  (index) -> index+"testName").applySupplier(value, () -> "testValue");
        List<Pojo> pojos = builder.get();

        //Then
        assertThat(pojos).hasSize(2);
        assertThat(pojos.get(0).getName()).isEqualTo("1testName");
        assertThat(pojos.get(0).getValue()).isEqualTo("testValue");
        assertThat(pojos.get(1).getName()).isEqualTo("2testName");
        assertThat(pojos.get(1).getValue()).isEqualTo("testValue");
    }

    @Test
    @DisplayName("Should be able to build a list overriding default with constant and random values")
    public void shouldBuildAListWithOverrideConstantAndRandomValues() {
        //When
        Supplier<String> stringSupplier = () -> string().next();
        RangeSizeListBuilder<Pojo> builder = RangeSizeListBuilder.rangeSizeListBuilder(1, 2, creator)
                .applySupplier(name, () -> "testName").applySupplier(value, stringSupplier);
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
        RangeSizeListBuilder<Pojo> builder = RangeSizeListBuilder.rangeSizeListBuilder(1, 2, creator)
                .applySupplier(name, stringSupplier).applySupplier(value, stringSupplier);
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
        RangeSizeListBuilder<Pojo> builder = RangeSizeListBuilder.rangeSizeListBuilder(1, 2, creator)
                .applyCreator(name, valueCreator).applyCreator(value, valueCreator);
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