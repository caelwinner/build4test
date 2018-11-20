package uk.co.caeldev.builder4test;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListBuilder<K> {

    private final Creator<K> creator;
    private final List<Map<Field, Optional>> elements;

    private ListBuilder(Creator<K> creator) {
        this.creator = creator;
        this.elements = new ArrayList<>();
    }

    protected static <K> ListBuilder<K> listBuilder(Creator<K> creator) {
        return new ListBuilder<>(creator);
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

    public RandomListBuilder<K> size(int size) {
        return RandomListBuilder.randomListBuilder(size, creator);
    }
}
