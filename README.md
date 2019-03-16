# Builder4test
[![Build Status](https://travis-ci.org/caelwinner/builder4test.svg?branch=master)](https://travis-ci.org/caelwinner/builder4test)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/97f9a4cec6274108af592a20ae31f82b)](https://www.codacy.com/app/adolfoecs/builder4test?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=caelwinner/builder4test&amp;utm_campaign=Badge_Grade)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/uk.co.caeldev/builder4test/badge.svg)](https://maven-badges.herokuapp.com/maven-central/uk.co.caeldev/builder4test)

Library to build your POJO entities using a very intuitive DSL.

## Motivation
Most of the time when I am writing my tests I have the need to write clean and readable tests. One way to achieve is by having Test Builder, but normally it takes time and are difficult to mantain in time. So after looking around I came up with this library to help you to create your pojo in a different and easy way.

## How to use it

There are several ways to use it but the most flexible for me is creating a Class Builder that contains all the logic to create the objects.
The same example you can find in the lib tests.

you can inspect this [demo project](https://github.com/caelwinner/build4test-demo) to see how to use this library.

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
There many things going on there but I will try to explain it in the best way that I can.
There are s few concepts to keep in mind; Field, Creator and LookUp.

### Creator
The Creator implements a method build that should contains how the object is going to be build.

### Field
The Field represent the value that you want to change by using the DSL in the construction of your objects.

### LookUp 
In charge of binding the Field instance and getting the corresponding value in the creator class.
In the example above the Creator is building a pojo using the constructor method and it is using the lookUp instance to get the corresponding value.

## Build an entity

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
and below is the code to generate your pojo.

```java
Pojo pojo = Builder.build()
                .entity(PojoBuilder.creator)
                .override(PojoBuilder.name, "nameoverrideed")
                .override(PojoBuilder.value, "valueoverrided")
                .get();
```

You can nullify a field by just using nullify Method from DSL.

```java
Pojo pojo = Builder.build()
                .entity(PojoBuilder.creator)
                .override(PojoBuilder.name, "nameoverrideed")
                .nullify(PojoBuilder.value)
                .get();
```

## Reuse amd composition of Creators

if you have already creators and you want to reuse them on other creator, you can achieve by:

- Setting your existing creator as a default value when you define your new creator, note that you can even override the values of the creator that you are using as default. 

```java
    public static Field<String> name = new Field<>();
    public static Creator<String> nameCreator = lookUp -> lookUp.get(name, "test1");
    
    public static Field<String> secondName = new Field<>();
    public static Creator<String> creator = lookUp -> lookUp.get(secondName, secondCreator);
    
    Pojo pojo = Builder.build()
                    .entity(creator)
                    .override(name, "test2")
                    .get();
```

- The other way is by overriding a field using a creator as value:

```java

public static Field<String> secondName = new Field<>();
public static Creator<String> secondCreator = lookUp -> lookUp.get(secondName, "test1");

Pojo pojo = Builder.build()
                .entity(PojoBuilder.creator)
                .override(PojoBuilder.name, "nameoverrideed")
                .override(PojoBuilder.secondName, "secondName")
                .override(PojoBuilder.value, secondCreator)
                .get();
```

- You can also from there override a field of secondCreator but you have to override those before using the secondCreator.


## Build a list of entities

As easy as is creating an entity with Builder4Test, just use list method from the DSL and add as many elements to the collection as you want. For each element you can override all the fields.
In the example below we are creating a list of two elements overriding the fiend name.
 
```java
List<Pojo> testSiumple = Builder.build()
    .list(creator).elements()
        .element()
            .override(name, "testSiumple")
            .end()
        .element()
            .override(name, "testSiumple2")
            .end()
    .get();
```

Also if you want to generate a certain amount of elements using the defaults values or random now it is supported.

```java
List<Pojo> testSimple = Builder.build()
                .list(creator)
                .size(5)
                .override(name, RDG.string())
                .override(value, RDG.string())
                .get();
```
This code will generate a List of five elements and each element will contain a random value and field.
Using defaults generator provided by Fyodor is easy to generate your random values.

__Note:__ that you can use creators as default values in your collections. 

## Credits
The library is highly inspired by 

[Make it Easy](https://github.com/npryce/make-it-easy) And [AssertJ](https://github.com/joel-costigliola/assertj-core)

For the random generation we decided to use [Fyodor](https://github.com/fyodor-org-uk/fyodor)

Make It Ease lib provides a Hamcrest style DSL but I am more fun of using a builder kind of DSL like AssertJ that offers straight away the option that I can use.
I want to say thank you to all the collaborator of MakeItEasy project.