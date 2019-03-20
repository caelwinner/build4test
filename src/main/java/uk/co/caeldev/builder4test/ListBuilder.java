package uk.co.caeldev.builder4test;

import java.util.function.Function;

public class ListBuilder<K> {

    private final Function<LookUp, K> creator;

    private ListBuilder(Function<LookUp, K> creator) {
        this.creator = creator;
    }

    protected static <K> ListBuilder<K> listBuilder(Function<LookUp, K> creator) {
        return new ListBuilder<>(creator);
    }

    public ElementListBuilder<K> elements() {
        return ElementListBuilder.elementListBuilder(creator);
    }

    public FixedSizeListBuilder<K> size(int size) {
        return FixedSizeListBuilder.fixedSizeListBuilder(size, creator);
    }
}
