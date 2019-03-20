package uk.co.caeldev.builder4test;

import com.google.common.collect.Lists;
import uk.co.caeldev.builder4test.resolvers.Resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ElementListBuilder<K> {

    private final Function<LookUp, K> creator;
    private final List<Map<Field, Resolver>> elements;

    private ElementListBuilder(Function<LookUp, K> creator) {
        this.creator = creator;
        this.elements = new ArrayList<>();
    }

    public static <K> ElementListBuilder<K> elementListBuilder(Function<LookUp, K> creator) {
        return new ElementListBuilder<>(creator);
    }

    public ElementBuilder<K> element() {
        ElementBuilder<K> elementBuilder = ElementBuilder.elementBuilder(this);
        this.elements.add(elementBuilder.getFields());
        return elementBuilder;
    }

    public List<K> get() {

        if (elements.isEmpty()) {
            return Lists.newArrayList(EntityBuilder.entityBuilder(creator).get());
        }

        return elements.stream()
                .filter(it -> it.size() != 0)
                .map(it -> EntityBuilder.entityBuilder(creator, it).get())
                .collect(Collectors.toList());
    }
}
