package uk.co.caeldev.builder4test;

import java.util.function.Function;

public class Builder {

    private Builder() {
    }

    public static Builder build() {
        return new Builder();
    }

    public <K> EntityBuilder<K> entity(Function<LookUp, K> creator) {
        return EntityBuilder.entityBuilder(creator);
    }

    public <K> ListBuilder<K> list(Function<LookUp, K> creator) {
        return ListBuilder.listBuilder(creator);
    }


}
