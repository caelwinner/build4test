package uk.co.caeldev.builder4test;

public class Builder {

    private Builder() {
    }

    public static Builder build() {
        return new Builder();
    }

    public <K> EntityBuilder<K> entity(Creator<K> creator) {
        return EntityBuilder.entityBuilder(creator);
    }

    public <K> ListBuilder<K> list(Creator<K> creator) {
        return ListBuilder.listBuilder(creator);
    }


}
