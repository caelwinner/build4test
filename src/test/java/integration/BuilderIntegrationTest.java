package integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import uk.co.caeldev.builder4test.Builder;
import uk.co.caeldev.builder4test.impl.Pojo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.caeldev.builder4test.impl.PojoBuilder.*;
import static uk.org.fyodor.generators.RDG.string;

public class BuilderIntegrationTest {

    @Test
    @DisplayName("should build a pojo successfully using defaults values")
    public void shouldBuildPojo() {
        //When
        Pojo pojo = Builder.build().entity(creator).get();

        //Then
        assertThat(pojo.getName()).isEqualTo("defaultName");
        assertThat(pojo.getValue()).isEqualTo("defaultValue");
    }

    @Test
    @DisplayName("should build a pojo successfully when override default values")
    public void shouldBuildPojoOverridingValues() {
        //When
        Pojo pojo = Builder.build()
                .entity(creator)
                .applyValue(name, "nameoverrideed")
                .applyValue(value, "valueoverrided")
                .get();

        //Then
        assertThat(pojo.getName()).isEqualTo("nameoverrideed");
        assertThat(pojo.getValue()).isEqualTo("valueoverrided");
    }

    @Test
    @DisplayName("should build a pojo successfully when override default values using value and supplier")
    public void shouldBuildPojoOverridingValuesWithValueAndSupplier() {
        //When
        Pojo pojo = Builder.build()
                .entity(creator)
                .applyValue(name, "nameoverrideed")
                .applySupplier(value, () -> "valueoverrided")
                .get();

        //Then
        assertThat(pojo.getName()).isEqualTo("nameoverrideed");
        assertThat(pojo.getValue()).isEqualTo("valueoverrided");
    }

    @Test
    @DisplayName("should build a pojo successfully when override default values using suppliers")
    public void shouldBuildPojoOverridingValuesWithSuppliers() {
        //When
        Pojo pojo = Builder.build()
                .entity(creator)
                .applySupplier(name, () -> "nameoverrideed")
                .applySupplier(value, () -> "valueoverrided")
                .get();

        //Then
        assertThat(pojo.getName()).isEqualTo("nameoverrideed");
        assertThat(pojo.getValue()).isEqualTo("valueoverrided");
    }

    @Test
    @DisplayName("should build different pojo successfully when overrides the default values for both objects")
    public void shouldBuilTwoDifferentInstancesWithDifferentValues() {
        //When
        Pojo pojo1 = Builder.build()
                .entity(creator)
                .applyValue(name, "nameoverrideed1")
                .applyValue(value, "valueoverrided1")
                .get();

        //And
        Pojo pojo2 = Builder.build()
                .entity(creator)
                .applyValue(name, "nameoverrideed2")
                .applyValue(value, "valueoverrided2")
                .get();

        //Then
        assertThat(pojo1).isNotEqualTo(pojo2);
        assertThat(pojo1.getName()).isEqualTo("nameoverrideed1");
        assertThat(pojo1.getValue()).isEqualTo("valueoverrided1");
        assertThat(pojo2.getName()).isEqualTo("nameoverrideed2");
        assertThat(pojo2.getValue()).isEqualTo("valueoverrided2");
    }

    @Test
    @DisplayName("should build pojo using defaults values sets by field constructor")
    public void shouldUseDefaultValuesFromFieldInstantiation() {
        //When
        Pojo pojo1 = Builder.build()
                .entity(creatorWithPredefinedDefaults)
                .get();

        //Then
        assertThat(pojo1.getName()).isEqualTo("defaultName");
        assertThat(pojo1.getValue()).isEqualTo("defaultValue");
    }

    @Test
    @DisplayName("should build a pojo successfully overriding defaults values sets by constructor")
    public void shouldOverrideDefaultValuesFromFieldInstantiation() {
        //When
        Pojo pojo1 = Builder.build()
                .entity(creatorWithPredefinedDefaults)
                .applyValue(name2, "overridedName")
                .applyValue(value2, "overridedValue")
                .get();

        //Then
        assertThat(pojo1.getName()).isEqualTo("overridedName");
        assertThat(pojo1.getValue()).isEqualTo("overridedValue");
    }


    @Test
    @DisplayName("should build a pojo successfully using another creators available")
    public void shouldOverrideDefaultValuesFromFieldInstantiationUsingAnotherCreator() {
        //When
        Pojo pojo1 = Builder.build()
                .entity(creatorWithPredefinedDefaults)
                .applyCreator(name2, valueCreator)
                .applyValue(value2, "overridedValue")
                .get();

        //Then
        assertThat(pojo1.getName()).isEqualTo("test1");
        assertThat(pojo1.getValue()).isEqualTo("overridedValue");
    }

    @Test
    @DisplayName("should build a pojo by overriding using a creator and overriding a value of it")
    public void shouldOverrideDefaultValuesFromFieldInstantiationUsingAnotherCreator2() {
        //When
        Pojo pojo1 = Builder.build()
                .entity(creatorWithPredefinedCreatorDefaults)
                .applyValue(testValue, "overridedValue1")
                .applyCreator(name2, valueTestCreator)
                .get();

        //Then
        assertThat(pojo1.getName()).isEqualTo("overridedValue1");
        assertThat(pojo1.getValue()).isEqualTo("defaultValue");
    }

    @Test
    @DisplayName("should build a pojo successfully setting nulls as values")
    public void shouldOverrideWithNulls() {
        //When
        Pojo pojo = Builder.build()
                .entity(creator)
                .nullify(name)
                .nullify(value)
                .get();

        //Then
        assertThat(pojo.getName()).isNull();
        assertThat(pojo.getValue()).isNull();
    }

    @Nested
    class ListGenerationTest {

        @Test
        @DisplayName("should build a list of two elements overriding defaults values")
        public void shouldBuildAListOfTwoEntities() {

            //When
            List<Pojo> testSimple = Builder.build()
                    .list(creator)
                    .elements()
                        .element()
                            .applyValue(name, "testSimple")
                            .end()
                        .element()
                            .applyValue(name, "testSimple2")
                            .end()
                    .get();

            //Then
            assertThat(testSimple).isNotEmpty();
            assertThat(testSimple).hasSize(2);

            //And
            Pojo pojo = testSimple.get(0);
            assertThat(pojo.getName()).isEqualTo("testSimple");
            assertThat(pojo.getValue()).isEqualTo("defaultValue");

            //And
            Pojo pojo1 = testSimple.get(1);
            assertThat(pojo1.getName()).isEqualTo("testSimple2");
            assertThat(pojo1.getValue()).isEqualTo("defaultValue");
        }

        @Test
        @DisplayName("should build a list of two elements overriding defaults values using Suppliers")
        public void shouldBuildAListOfTwoEntitiesUsingSuppliers() {

            //When
            List<Pojo> testSiumple = Builder.build()
                    .list(creator)
                    .elements()
                    .element()
                    .applySupplier(name, () -> "testSimple")
                    .end()
                    .element()
                    .applySupplier(name, () -> "testSimple2")
                    .end()
                    .get();

            //Then
            assertThat(testSiumple).isNotEmpty();
            assertThat(testSiumple).hasSize(2);

            //And
            Pojo pojo = testSiumple.get(0);
            assertThat(pojo.getName()).isEqualTo("testSimple");
            assertThat(pojo.getValue()).isEqualTo("defaultValue");

            //And
            Pojo pojo1 = testSiumple.get(1);
            assertThat(pojo1.getName()).isEqualTo("testSimple2");
            assertThat(pojo1.getValue()).isEqualTo("defaultValue");
        }

        @Test
        @DisplayName("should build a list of two elements overriding defaults values using third party classes")
        public void shouldBuildAListOfTwoUsingThirdPartyClasses() {
            //Given
            int size = 2;

            //When
            List<Pojo> testSimple = Builder.build()
                    .list(creator)
                    .size(size)
                    .applySupplier(name, () -> string().next())
                    .applySupplier(value, () -> string().next())
                    .get();

            //Then
            assertThat(testSimple).isNotEmpty();
            assertThat(testSimple).hasSize(size);

            assertThat(testSimple.get(0).getName()).isNotEqualTo(testSimple.get(1).getName());
            assertThat(testSimple.get(0).getValue()).isNotEqualTo(testSimple.get(1).getValue());
        }

        @Test
        @DisplayName("should build a list of two elements overriding defaults values using iteration index as arguments to the function")
        public void shouldBuildAListOfTwoUsingThirdPartyClassesAndFunction() {
            //Given
            int size = 2;

            //When
            List<Pojo> testSimple = Builder.build()
                    .list(creator)
                    .size(size)
                    .applySequence(name, (index) -> index + "test")
                    .get();

            //Then
            assertThat(testSimple).isNotEmpty();
            assertThat(testSimple).hasSize(size);

            assertThat(testSimple.get(0).getName()).isEqualTo("1test");
            assertThat(testSimple.get(1).getName()).isEqualTo("2test");
        }

        @Test
        @DisplayName("should build a list of two elements overriding defaults values with creators using size")
        public void shouldBuildAListOfTwoUsingCreatorAndSize() {
            //Given
            int size = 2;

            //When
            List<Pojo> testSimple = Builder.build()
                    .list(creator)
                    .size(size)
                    .applyCreator(name, valueCreator)
                    .applyCreator(value, valueCreator)
                    .get();

            //Then
            assertThat(testSimple).isNotEmpty();
            assertThat(testSimple).hasSize(size);

            assertThat(testSimple.get(0).getName()).isEqualTo(testSimple.get(1).getName());
            assertThat(testSimple.get(0).getValue()).isEqualTo(testSimple.get(1).getValue());
            assertThat(testSimple.get(0).getName()).isEqualTo("test1");
            assertThat(testSimple.get(0).getValue()).isEqualTo("test1");
        }

        @Test
        @DisplayName("should build a list of two elements overriding defaults values with creator and supplier using size")
        public void shouldBuildAListOfTwoUsingCreatorAndSupplierAndSize() {
            //Given
            int size = 2;

            //When
            List<Pojo> testSimple = Builder.build()
                    .list(creator)
                    .size(size)
                    .applyCreator(name, valueCreator)
                    .applySupplier(value, () -> "test2")
                    .get();

            //Then
            assertThat(testSimple).isNotEmpty();
            assertThat(testSimple).hasSize(size);

            assertThat(testSimple.get(0).getName()).isEqualTo(testSimple.get(1).getName());
            assertThat(testSimple.get(0).getValue()).isEqualTo(testSimple.get(1).getValue());
            assertThat(testSimple.get(0).getName()).isEqualTo("test1");
            assertThat(testSimple.get(0).getValue()).isEqualTo("test2");
        }

        @Test
        @DisplayName("should build a list of one element overriding defaults values with creators")
        public void shouldBuildAListOfTwoUsingCreators() {
            //When
            List<Pojo> testSimple = Builder.build()
                    .list(creator)
                    .elements()
                        .element()
                            .applyCreator(name, valueCreator).end()
                        .element()
                            .applyCreator(value, valueCreator).end()
                    .get();

            //Then
            assertThat(testSimple).isNotEmpty();
            assertThat(testSimple).hasSize(2);

            assertThat(testSimple.get(0).getName()).isEqualTo("test1");
            assertThat(testSimple.get(1).getValue()).isEqualTo("test1");
        }

        @Test
        @DisplayName("should build a list of one elements using defaults values when there is no size or elements definitions")
        public void shouldBuildAListSizeOneWithNoSizeAndNoElementsDefinitions() {
            //When
            List<Pojo> testSimple = Builder.build()
                    .list(creator).elements().get();

            //Then
            assertThat(testSimple).isNotEmpty();
            assertThat(testSimple).hasSize(1);

            assertThat(testSimple.get(0).getName()).isEqualTo("defaultName");
            assertThat(testSimple.get(0).getValue()).isEqualTo("defaultValue");
        }

        @Test
        @DisplayName("should build a list based on a range")
        public void shouldBuildAListBasedOnARange() {
            //When
            List<Pojo> testSimple = Builder.build()
                    .list(creator).range(2, 3).get();

            //Then
            assertThat(testSimple).isNotEmpty();
            assertThat(testSimple).hasSize(2);

            assertThat(testSimple.get(0).getName()).isEqualTo("defaultName");
            assertThat(testSimple.get(0).getValue()).isEqualTo("defaultValue");

            assertThat(testSimple.get(1).getName()).isEqualTo("defaultName");
            assertThat(testSimple.get(1).getValue()).isEqualTo("defaultValue");
        }
    }
}
