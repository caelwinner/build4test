package uk.co.caeldev.builder4test.impl;

import uk.co.caeldev.builder4test.Field;
import uk.co.caeldev.builder4test.LookUp;

import java.util.function.Function;

public class PojoBuilder {

    public static Field<String> name = new Field<>();
    public static Field<String> value = new Field<>();
    public static Function<LookUp, Pojo> creator = lookUp -> new Pojo(lookUp.get(name, "defaultName"),
            lookUp.get(value, "defaultValue"));


    public static Field<String> name2 = new Field<>("defaultName");
    public static Field<String> value2 = new Field<>("defaultValue");
    public static Function<LookUp, Pojo> creatorWithPredefinedDefaults = lookUp -> new Pojo(lookUp.get(name2),
            lookUp.get(value2));

    public static Function<LookUp, String> valueCreator = lookUp -> "test1";

    public static Field<String> testValue = new Field<>();
    public static Function<LookUp, String> valueTestCreator = lookUp -> lookUp.get(testValue, "test1");

    public static Function<LookUp, Pojo> creatorWithPredefinedCreatorDefaults = lookUp -> new Pojo(lookUp.get(name2, valueTestCreator),
            lookUp.get(value2));


}
