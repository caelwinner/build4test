package uk.co.caeldev.builder4test;

import org.junit.jupiter.api.Test;
import uk.co.caeldev.builder4test.impl.Pojo;
import uk.co.caeldev.builder4test.impl.PojoBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ListBuilderTest {

    @Test
    public void shouldBuilderDifferentBuilders() {
        //When
        ListBuilder<Pojo> pojoListBuilder = ListBuilder.listBuilder(PojoBuilder.creator);
        ListBuilder<Pojo> pojoListBuilder2 = ListBuilder.listBuilder(PojoBuilder.creator);

        //Then
        assertThat(pojoListBuilder).isNotEqualTo(pojoListBuilder2);
    }

    @Test
    public void shouldBuildEmptyList() {
        //When
        List<Pojo> pojos = ListBuilder.listBuilder(PojoBuilder.creator).get();

        //Then
        assertThat(pojos).isEmpty();
    }

    @Test
    public void shouldBuildListWithOneElement() {
        //When
        List<Pojo> pojos = ListBuilder.listBuilder(PojoBuilder.creator)
                .element()
                    .override(PojoBuilder.name, "test")
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
    public void shouldBuildListWithTwoElements() {
        //When
        List<Pojo> pojos = ListBuilder.listBuilder(PojoBuilder.creator)
                .element()
                    .override(PojoBuilder.name, "test1")
                    .override(PojoBuilder.value, "testValue1")
                    .end()
                .element()
                    .override(PojoBuilder.name, "test2")
                    .override(PojoBuilder.value, "testValue2")
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