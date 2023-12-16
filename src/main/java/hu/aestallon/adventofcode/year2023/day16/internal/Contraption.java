/*
 * Copyright 2023 Szabolcs Bazil Papp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.aestallon.adventofcode.year2023.day16.internal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Contraption {

  public static Contraption parse(List<String> lines) {
    final var elements = lines.stream()
        .map(it -> it.chars().mapToObj(Element::parse).toArray(Element[]::new))
        .toArray(Element[][]::new);
    return new Contraption(elements);
  }

  private final Element[][] elements;

  private Contraption(Element[][] elements) {
    this.elements = elements;
  }

  public Set<Position> run(Direction d, Position p) {
    return run(new Light(d, p));
  }

  private Set<Position> run(Light light) {
    final Set<Light> knownLights = new HashSet<>();

    Set<Light> runningLights = new HashSet<>();
    runningLights.add(light);
    do {
      knownLights.addAll(runningLights);
      runningLights = runningLights.stream()
          .flatMap(it -> getElement(it.p()).handle(it).stream())
          .filter(it -> !isOutOfBounds(it.p()))
          .filter(it -> !knownLights.contains(it))
          .collect(Collectors.toSet());
    } while (!runningLights.isEmpty());

    return knownLights.stream().map(Light::p).collect(Collectors.toSet());
  }

  public Stream<Set<Position>> runAll() {
    return prepare().map(this::run);
  }

  public Stream<Light> prepare() {
    return Arrays.stream(Direction.values())
        .flatMap(it -> switch (it) {
          case E -> IntStream.range(0, elements.length)
              .mapToObj(i -> new Light(it, new Position(i, 0)));
          case W -> IntStream.range(0, elements.length)
              .mapToObj(i -> new Light(it, new Position(i, elements[i].length - 1)));
          case N -> IntStream.range(0, elements[elements.length - 1].length)
              .mapToObj(i -> new Light(it, new Position(elements.length - 1, i)));
          case S -> IntStream.range(0, elements[0].length)
              .mapToObj(i -> new Light(it, new Position(0, i)));
        });
  }

  private Element getElement(Position p) {
    return elements[p.r()][p.c()];
  }
  private boolean isOutOfBounds(Position p) {
    return p.r() < 0 || p.c() < 0 || p.r() >= elements.length || p.c() >= elements[0].length;
  }


  // -----------------------------------------------------------------------------------------------
  // for visualisation:

  public ContraptionRunner newRunner(Light light) {
    return new ContraptionRunner(light);
  }

  public final class ContraptionRunner {

    private final Set<Light> knownLights;
    private Set<Light> runningLights;

    public ContraptionRunner(Light startingLight) {
      this.knownLights = new HashSet<>();
      this.runningLights = new HashSet<>();
      runningLights.add(startingLight);
    }

    public Set<Light> knownLights() {
      return knownLights;
    }

    public int height() {
      return elements.length;
    }

    public int width() {
      return elements[0].length;
    }

    public boolean isComplete() {
      return runningLights.isEmpty();
    }

    public void step() {
      knownLights.addAll(runningLights);
      runningLights = runningLights.stream()
          .flatMap(it -> getElement(it.p()).handle(it).stream())
          .filter(it -> !isOutOfBounds(it.p()))
          .filter(it -> !knownLights.contains(it))
          .collect(Collectors.toSet());
    }

  }

}
