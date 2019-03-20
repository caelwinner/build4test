package uk.co.caeldev.builder4test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.co.caeldev.builder4test.impl.Pojo;
import uk.co.caeldev.builder4test.impl.PojoBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ElementListBuilderTest {

    @Test
    @DisplayName("Should build different instances of ElementListBuilder")
    public void shouldBuilderDifferentBuilders() {
        //When
        ElementListBuilder<Pojo> pojoListBuilder = ElementListBuilder.elementListBuilder(PojoBuilder.creator);
        ElementListBuilder<Pojo> pojoListBuilder2 = ElementListBuilder.elementListBuilder(PojoBuilder.creator);

        //Then
        assertThat(pojoListBuilder).isNotEqualTo(pojoListBuilder2);
    }

    @Test
    @DisplayName("Should build by default a list of one element where there is no size and elements definitions")
    public void shouldBuildAListOfOneElement() {
        //When
        List<Pojo> pojos = ElementListBuilder.elementListBuilder(PojoBuilder.creator).get();

        //Then
        assertThat(pojos).hasSize(1);
    }

    @Test
    @DisplayName("Should build a list of one element when there is one element definition")
    public void shouldBuildListWithOneElement() {
        //When
        List<Pojo> pojos = ElementListBuilder.elementListBuilder(PojoBuilder.creator)
                .element()
                    .applySupplier(PojoBuilder.name, () -> "test")
                    .end()
                .get();
        //Then
        assertThat(pojos).isNotEmpty();
        assertThat(pojos).hasSize(1);

        //And
        Pojo pojo = pojos.get(0);
        assertThat(pojo.getName()).isEqualTo("test");
        assertThat(pojo.getValue()).isEqualTo("defaultValue");
    }

    @Test
    @DisplayName("Should build a list of two elements when there is two element definitions")
    public void shouldBuildListWithTwoElements() {
        //When
        List<Pojo> pojos = ElementListBuilder.elementListBuilder(PojoBuilder.creator)
                .element()
                    .applySupplier(PojoBuilder.name, () -> "test1")
                    .applySupplier(PojoBuilder.value, () -> "testValue1")
                    .end()
                .element()
                    .applySupplier(PojoBuilder.name, () -> "test2")
                    .applySupplier(PojoBuilder.value, () -> "testValue2")
                    .end()
                .get();

        //Then
        assertThat(pojos).isNotEmpty();
        assertThat(pojos).hasSize(2);

        //And
        Pojo pojo = pojos.get(0);
        assertThat(pojo.getName()).isEqualTo("test1");
        assertThat(pojo.getValue()).isEqualTo("testValue1");

        //And
        Pojo pojo1 = pojos.get(1);
        assertThat(pojo1.getName()).isEqualTo("test2");
        assertThat(pojo1.getValue()).isEqualTo("testValue2");
    }

}