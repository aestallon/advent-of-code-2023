# Advent of Code 2023

## Prerequisites

Building this project requires **JDK 21**.

## How to Build

Standing in the project root, execute the following:

```shell
./gradlew test --info
```

This will compile the project classes and execute the tests associated with each
exercise.

## How to Build Visualisations

For some solutions, visualisations are included, using the original solution. To build such
visualisations, first build the respective JAR, e.g.:

```shell
./gradlew demo16
```

Then, run the generated JAR:

```shell
java -jar ./build/libs/demo-16.jar
```

Such visualisations are currently available for the following days:

- [x] 16




