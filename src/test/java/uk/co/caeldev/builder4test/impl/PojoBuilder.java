package uk.co.caeldev.builder4test.impl;

import uk.co.caeldev.builder4test.Creator;
import uk.co.caeldev.builder4test.Field;

public class PojoBuilder {

    public static Field<String> name = new Field<>();
    public static Field<String> value = new Field<>();

    public static Field<String> name2 = new Field<>("defaultName");
    public static Field<String> value2 = new Field<>("defaultValue");

    public static Creator<Pojo> creator = lookUp -> new Pojo(lookUp.get(name, "defaultName"),
            lookUp.get(value, "defaultValue"));

    public static Creator<Pojo> creatorWithPredefinedDefaults = lookUp -> new Pojo(lookUp.get(name2),
            lookUp.get(value2));

}
