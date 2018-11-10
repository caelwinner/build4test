# Builder4test
[![Build Status](https://travis-ci.org/caelwinner/builder4test.svg?branch=master)](https://travis-ci.org/caelwinner/builder4test)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/97f9a4cec6274108af592a20ae31f82b)](https://www.codacy.com/app/adolfoecs/builder4test?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=caelwinner/builder4test&amp;utm_campaign=Badge_Grade)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/uk.co.caeldev/builder4test/badge.png?style=flat)](http://search.maven.org/#search|ga|1|g%3A%22uk.co.caeldev%22%20AND%20a%3A%22builder4test%22)

Library to build your POJO entities in a very easy and builder intuitive way.

# Motivation
Most of the time when I am writing my tests I have the need to write clean and readable tests. One way that I found is by having Test Builder but normally it takes time and are difficult to mantain in time. So after looking around I came up with this library to help you to create your pojo in a different and easy way.

# How to use it.

There are several ways to use it but the most flexible for me is creating a Class Builder that contains all the logic to create the objects.
The same example you can find in the lib tests.

```java
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
```
There many things going on there. but I will try to explain it in the best way that I can.
There are two concepts to keep in mind, Field, Creator and LookUp.

## Creator
The Creator implements method build that should contains how the object is going to be build.

## Field
The Field represent the value that you want to change by using the DSL in the construction of your objects.

## LookUp 
In charge of binding the Field instance and getting the corresponding value in the creator class.

In the example above the Creator is building a pojo using the constructor method and it is using the lookUp instance to get the corresponding value.

## Ways of using the the three things.

```java
public static Field<String> name = new Field<>();
public static Field<String> value = new Field<>();

new Pojo(lookUp.get(name, "defaultName"), 
            lookUp.get(value, "defaultValue")
```
the lookUp method is using a Field instance as first parameter and the second parameter to define what the default value will be in case that we don't set it by the DSL.
The other way to set the default value is by setting at the construction of the field it self so at the lookUp level you don't have to pass it.

```java
public static Field<String> name2 = new Field<>("defaultName");
public static Field<String> value2 = new Field<>("defaultValue");

new Pojo(lookUp.get(name2),
            lookUp.get(value2));
```

## How your tests would look like

At the end you would use this from tests like this:

```java
Pojo pojo = Builder.build()
                .entity(PojoBuilder.creator)
                .override(PojoBuilder.name, "nameoverrideed")
                .override(PojoBuilder.value, "valueoverrided")
                .get();
```

# Credits
The library is highly inspired by 

https://github.com/npryce/make-it-easy And AssertJ

Make It Ease lib provides a Hamcrest style DSL but I am more fun of using a builder kind of DSL like AssertJ that offers straight away the option that I can use.
I want to say thank you to all the collaborator of MakeItEasy project.