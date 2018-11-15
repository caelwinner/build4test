package uk.co.caeldev.builder4test;

import org.junit.jupiter.api.Test;
import uk.co.caeldev.builder4test.impl.Pojo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.caeldev.builder4test.impl.PojoBuilder.*;
import static uk.org.fyodor.generators.RDG.string;

class RandomListBuilderTest {
    
    @Test
    public void shouldReturnUniqueInstances() {
        //When
        RandomListBuilder<Pojo> builder1 = RandomListBuilder.randomListBuilder(1, creator);
        RandomListBuilder<Pojo> builder2 = RandomListBuilder.randomListBuilder(1, creator);

        //Then
        assertThat(builder1).isNotEqualTo(builder2);
    }

    @Test
    public void shouldBuildAListWithDefaultValuesNoRandoms() {
        //When
        RandomListBuilder<Pojo> builder = RandomListBuilder.randomListBuilder(2, creator);
        List<Pojo> pojos = builder.get();

        //Then
        assertThat(pojos).hasSize(2);
        assertThat(pojos.get(0).getName()).isEqualTo("defaultName");
        assertThat(pojos.get(0).getValue()).isEqualTo("defaultValue");
        assertThat(pojos.get(1).getName()).isEqualTo("defaultName");
        assertThat(pojos.get(1).getValue()).isEqualTo("defaultValue");
    }

    @Test
    public void shouldBuildAListWithOverrideConstantValues() {
        //When
        RandomListBuilder<Pojo> builder = RandomListBuilder.randomListBuilder(2, creator)
                .override(name, "testName").override(value, "testValue");
        List<Pojo> pojos = builder.get();

        //Then
        assertThat(pojos).hasSize(2);
        assertThat(pojos.get(0).getName()).isEqualTo("testName");
        assertThat(pojos.get(0).getValue()).isEqualTo("testValue");
        assertThat(pojos.get(1).getName()).isEqualTo("testName");
        assertThat(pojos.get(1).getValue()).isEqualTo("testValue");
    }

    @Test
    public void shouldBuildAListWithOverrideConstantAndRandomValues() {
        //When
        RandomListBuilder<Pojo> builder = RandomListBuilder.randomListBuilder(2, creator)
                .override(name, "testName").override(value, string());
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
    public void shouldBuildAListWithOverrideRandomValues() {
        //When
        RandomListBuilder<Pojo> builder = RandomListBuilder.randomListBuilder(2, creator)
                .override(name, string()).override(value, string());
        List<Pojo> pojos = builder.get();

        //Then
        assertThat(pojos).hasSize(2);
        assertThat(pojos.get(0).getName()).isNotEqualTo(pojos.get(1).getName());
        assertThat(pojos.get(0).getValue()).isNotEqualTo(pojos.get(1).getValue());
    }
}