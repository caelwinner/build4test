package uk.co.caeldev.builder4test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.co.caeldev.builder4test.impl.Pojo;
import uk.co.caeldev.builder4test.impl.PojoBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.caeldev.builder4test.ElementListBuilder.elementListBuilder;

class ElementBuilderTest {

    @Test
    @DisplayName("Should create different instances of the builder")
    public void shouldCreateDifferentInstances() {
        //When
        ElementBuilder<Pojo> elementBuilder = ElementBuilder.elementBuilder(elementListBuilder(PojoBuilder.creator));
        ElementBuilder<Pojo> elementBuilder2 = ElementBuilder.elementBuilder(elementListBuilder(PojoBuilder.creator));

        //Then
        assertThat(elementBuilder).isNotEqualTo(elementBuilder2);
    }

    @Test
    @DisplayName("Should initialize to empty the fields")
    public void shouldInitFields() {
        //When
        ElementBuilder<Pojo> elementBuilder = ElementBuilder.elementBuilder(elementListBuilder(PojoBuilder.creator));

        //Then
        assertThat(elementBuilder.getFields()).isEmpty();
    }

    @Test
    @DisplayName("Should add a field override using values")
    public void shouldAddFieldOverrideUsingValue() {
        //Given
        Field<String> field = new Field<>("default");

        //When
        ElementBuilder<Pojo> elementBuilder = ElementBuilder.elementBuilder(elementListBuilder(PojoBuilder.creator));
        elementBuilder.overrideValue(field, "override");

        //Then
        assertThat(elementBuilder.getFields()).hasSize(1);
        assertThat(elementBuilder.getFields().get(field)).isNotNull();
        assertThat(elementBuilder.getFields().get(field).resolve()).asString().contains("override");
    }

    @Test
    @DisplayName("Should add a field override using supplier")
    public void shouldAddFieldOverrideUsingSupplier() {
        //Given
        Field<String> field = new Field<>("default");

        //When
        ElementBuilder<Pojo> elementBuilder = ElementBuilder.elementBuilder(elementListBuilder(PojoBuilder.creator));
        elementBuilder.override(field, () -> "override");

        //Then
        assertThat(elementBuilder.getFields()).hasSize(1);
        assertThat(elementBuilder.getFields().get(field)).isNotNull();
        assertThat(elementBuilder.getFields().get(field).resolve()).asString().contains("override");
    }

    @Test
    @DisplayName("Should return back the elements list builder")
    public void shouldGoBackToElementsListBuilder() {
        //Given
        Field<String> field = new Field<>("default");
        ElementListBuilder<Pojo> elementListBuilder = elementListBuilder(PojoBuilder.creator);

        //When
        ElementBuilder<Pojo> elementBuilder = ElementBuilder.elementBuilder(elementListBuilder);
        ElementListBuilder<Pojo> elementListBuilder1 = elementBuilder.override(field, () -> "override").end();

        //Then
        assertThat(elementBuilder.getFields()).hasSize(1);
        assertThat(elementBuilder.getFields().get(field)).isNotNull();
        assertThat(elementBuilder.getFields().get(field).resolve()).asString().contains("override");
        assertThat(elementListBuilder1).isEqualTo(elementListBuilder);
    }

    @Test
    @DisplayName("Should be able to nullify a field by not passing the null value")
    public void shouldBeAbleToNullifyAField() {
        //Given
        Field<String> field = new Field<>("default");
        ElementListBuilder<Pojo> elementListBuilder = elementListBuilder(PojoBuilder.creator);

        //When
        ElementBuilder<Pojo> elementBuilder = ElementBuilder.elementBuilder(elementListBuilder);
        ElementListBuilder<Pojo> elementListBuilder1 = elementBuilder.nullify(field).end();

        //Then
        assertThat(elementBuilder.getFields()).hasSize(1);
        assertThat(elementBuilder.getFields().get(field)).isNotNull();
        assertThat(elementListBuilder1).isEqualTo(elementListBuilder);
    }

    @Test
    @DisplayName("Should be able to use a creator as default value for a field")
    public void shouldBeAbleToUseCreatorAsDefaultValueForAField() {
        //Given
        Field<String> field = new Field<>("default");
        ElementListBuilder<Pojo> elementListBuilder = elementListBuilder(PojoBuilder.creator);


        //When
        ElementBuilder<Pojo> elementBuilder = ElementBuilder.elementBuilder(elementListBuilder);
        ElementListBuilder<Pojo> elementListBuilder1 = elementBuilder.override(field, PojoBuilder.valueCreator).end();

        //Then
        assertThat(elementBuilder.getFields()).hasSize(1);
        assertThat(elementBuilder.getFields().get(field)).isNotNull();
        assertThat(elementBuilder.getFields().get(field).resolve()).isEqualTo("test1");
        assertThat(elementListBuilder1).isEqualTo(elementListBuilder);
    }



}