package uk.co.caeldev.builder4test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.co.caeldev.builder4test.impl.Pojo;
import uk.co.caeldev.builder4test.impl.PojoBuilder;

import static org.assertj.core.api.Assertions.assertThat;

class ListBuilderTest {

    @Test
    @DisplayName("Should Create two different instance of ElementListBuilder")
    public void shouldCreateDistinctElementsBuilder() {
        //When
        ElementListBuilder<Pojo> elements = ListBuilder.listBuilder(PojoBuilder.creator).elements();
        ElementListBuilder<Pojo> elements2 = ListBuilder.listBuilder(PojoBuilder.creator).elements();

        //Then
        assertThat(elements).isNotEqualTo(elements2);
    }

    @Test
    @DisplayName("Should Create two different instance of RangeSizeListBuilder")
    public void shouldCreateDistinctFixedSizeListBuilder() {
        //When
        RangeSizeListBuilder<Pojo> rangeSizeListBuilder = ListBuilder.listBuilder(PojoBuilder.creator).size(1);
        RangeSizeListBuilder<Pojo> rangeSizeListBuilder2 = ListBuilder.listBuilder(PojoBuilder.creator).size(1);

        //Then
        assertThat(rangeSizeListBuilder).isNotEqualTo(rangeSizeListBuilder2);
    }

    @Test
    @DisplayName("Should Create two different instance of RangeSizeListBuilder")
    public void shouldCreateDistinctRangeSizeListBuilder() {
        //When
        RangeSizeListBuilder<Pojo> rangeSizeListBuilder = ListBuilder.listBuilder(PojoBuilder.creator).range(1, 1);
        RangeSizeListBuilder<Pojo> rangeSizeListBuilder2 = ListBuilder.listBuilder(PojoBuilder.creator).range(1, 1);

        //Then
        assertThat(rangeSizeListBuilder).isNotEqualTo(rangeSizeListBuilder2);
    }

}