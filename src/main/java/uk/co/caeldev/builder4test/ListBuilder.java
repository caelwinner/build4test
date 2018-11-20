package uk.co.caeldev.builder4test;

public class ListBuilder<K> {

    private final Creator<K> creator;

    private ListBuilder(Creator<K> creator) {
        this.creator = creator;
    }

    protected static <K> ListBuilder<K> listBuilder(Creator<K> creator) {
        return new ListBuilder<>(creator);
    }

    public ElementListBuilder<K> elements() {
        return ElementListBuilder.elementListBuilder(creator);
    }

    public FixedSizeListBuilder<K> size(int size) {
        return FixedSizeListBuilder.fixedSizeListBuilder(size, creator);
    }
}
