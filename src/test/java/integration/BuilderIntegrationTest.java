package integration;

import org.junit.jupiter.api.Test;
import uk.co.caeldev.builder4test.Builder;
import uk.co.caeldev.builder4test.impl.Pojo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.caeldev.builder4test.impl.PojoBuilder.*;

public class BuilderIntegrationTest {

    @Test
    public void shouldBuildPojo() {
        //When
        Pojo pojo = Builder.build().entity(creator).get();

        //Then
        assertThat(pojo.getName()).isEqualTo("defaultName");
        assertThat(pojo.getValue()).isEqualTo("defaultValue");
    }

    @Test
    public void shouldBuildPojoOverridingValues() {
        //When
        Pojo pojo = Builder.build()
                .entity(creator)
                .override(name, "nameoverrideed")
                .override(value, "valueoverrided")
                .get();

        //Then
        assertThat(pojo.getName()).isEqualTo("nameoverrideed");
        assertThat(pojo.getValue()).isEqualTo("valueoverrided");
    }

    @Test
    public void shouldBuilTwoDifferentInstancesWithDifferentValues() {
        //When
        Pojo pojo1 = Builder.build()
                .entity(creator)
                .override(name, "nameoverrideed1")
                .override(value, "valueoverrided1")
                .get();

        //And
        Pojo pojo2 = Builder.build()
                .entity(creator)
                .override(name, "nameoverrideed2")
                .override(value, "valueoverrided2")
                .get();

        //Then
        assertThat(pojo1).isNotEqualTo(pojo2);
        assertThat(pojo1.getName()).isEqualTo("nameoverrideed1");
        assertThat(pojo1.getValue()).isEqualTo("valueoverrided1");
        assertThat(pojo2.getName()).isEqualTo("nameoverrideed2");
        assertThat(pojo2.getValue()).isEqualTo("valueoverrided2");
    }

    @Test
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
    public void shouldOverrideDefaultValuesFromFieldInstantiation() {
        //When
        Pojo pojo1 = Builder.build()
                .entity(creatorWithPredefinedDefaults)
                .override(name2, "overridedName")
                .override(value2, "overridedValue")
                .get();

        //Then
        assertThat(pojo1.getName()).isEqualTo("overridedName");
        assertThat(pojo1.getValue()).isEqualTo("overridedValue");
    }

    @Test
    public void shouldOverrideWithNulls() {
        //When
        Pojo pojo = Builder.build()
                .entity(creator)
                .override(name, null)
                .override(value, null)
                .get();

        //Then
        assertThat(pojo.getName()).isNull();
        assertThat(pojo.getValue()).isNull();
    }

    @Test
    public void shouldBuildAListOfTwoEntities() {

        //When
        List<Pojo> testSiumple = Builder.build()
                .list(creator)
                    .element()
                        .field(name, "testSiumple")
                        .end()
                    .element()
                        .field(name, "testSiumple2")
                    .end()
                .get();

        //Then
        assertThat(testSiumple).isNotEmpty();
        assertThat(testSiumple).hasSize(2);

        //And
        Pojo pojo = testSiumple.get(0);
        assertThat(pojo.getName()).isEqualTo("testSiumple");
        assertThat(pojo.getValue()).isEqualTo("defaultValue");

        //And
        Pojo pojo1 = testSiumple.get(1);
        assertThat(pojo1.getName()).isEqualTo("testSiumple2");
        assertThat(pojo1.getValue()).isEqualTo("defaultValue");
    }
}
