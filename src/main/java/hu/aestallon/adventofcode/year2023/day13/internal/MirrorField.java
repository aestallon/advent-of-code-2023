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

package hu.aestallon.adventofcode.year2023.day13.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public final class MirrorField {

  private static final Logger log = LoggerFactory.getLogger(MirrorField.class);

  public static MirrorField parse(List<String> lines) {
    final Material[][] materials = lines.stream()
        .map(it -> it.chars()
            .mapToObj(Material::ofCodePoint)
            .toArray(Material[]::new))
        .toArray(Material[][]::new);
    return new MirrorField(materials);
  }

  private final Material[][]    materials;
  private       Set<Reflection> reflections;

  private MirrorField(Material[][] materials) {
    this.materials = materials;
  }

  private MirrorField(MirrorField original) {
    final Material[][] ms = new Material[original.materials.length][];
    for (int i = 0; i < ms.length; i++) {
      ms[i] = Arrays.copyOf(original.materials[i], original.materials[i].length);
    }
    this.materials = ms;
  }

  public Set<Reflection> reflections() {
    if (reflections == null) {
      reflections = Stream
          .concat(
              verticalReflections().stream().map(Reflection::vertical),
              horizontalReflections().stream().map(Reflection::horizontal))
          .collect(Collectors.toSet());
    }
    return reflections;
  }

  private Set<Integer> verticalReflections() {
    var candidates = verticalReflectionCandidates(materials[0]);
    for (int i = 1; i < materials.length; i++) {
      candidates.retainAll(verticalReflectionCandidates(materials[i]));
    }
    return candidates;
  }


  private Set<Integer> verticalReflectionCandidates(Material[] mats) {
    return IntStream.range(1, mats.length)
        .filter(i -> {
          final int span = Math.min(i, mats.length - i);
          return IntStream.rangeClosed(1, span).allMatch(x -> mats[i - x] == mats[i + x - 1]);
        })
        .boxed()
        .collect(Collectors.toSet());
  }

  private Set<Integer> horizontalReflections() {
    return IntStream.range(1, materials.length)
        .filter(i -> {
          final int span = Math.min(i, materials.length - i);
          return IntStream.rangeClosed(1, span)
              .allMatch(x -> Arrays.equals(materials[i - x], materials[i + x - 1]));
        })
        .boxed()
        .collect(Collectors.toSet());
  }

  public Reflection fixSmudge() {
    // first attempt creating vertical lines of reflections:
    for (int i = 0; i < materials.length; i++) {
      for (int j = 0; j < materials[0].length; j++) {
        final var copy = new MirrorField(this);
        copy.flip(i, j);

        final var newReflection = newReflectionIn(copy);
        if (newReflection != null) {
          return newReflection;
        }
      }
    }

    // if that did not succeed, find a closely matching pairs of lines:
    for (int i = 0; i < materials.length - 1; i++) {
      final var row1 = materials[i];
      final var row2 = materials[i + 1];
      final var differentIndices = differentIndices(row1, row2);
      if (differentIndices.length == 1) {
        final var copy = new MirrorField(this);
        copy.flip(i, differentIndices[0]);

        final var newReflection = newReflectionIn(copy);
        if (newReflection != null) {
          return newReflection;
        }
      }
    }

    // if we failed, there is no smudge:
    log.warn("fixSmudge - Found no candidates resulting in a new reflection for [ {} ]", this);
    return reflections().stream().findAny().orElseThrow();
  }

  private Reflection newReflectionIn(MirrorField copy) {
    final var copyReflections = copy.reflections();
    return copyReflections.stream()
        .filter(it -> !reflections().contains(it))
        .findFirst()
        .orElse(null);
  }

  private void flip(int row, int col) {
    final var mat = materials[row][col];
    materials[row][col] = (mat == Material.ASH) ? Material.ROCK : Material.ASH;
  }


  private static <T extends Enum<T>> int[] differentIndices(T[] arr1, T[] arr2) {
    return IntStream.range(0, Math.min(arr1.length, arr2.length))
        .filter(i -> arr1[i] != arr2[i])
        .toArray();
  }

  @Override
  public String toString() {
    return Arrays.stream(materials)
        .map(row -> Arrays.stream(row)
            .map(it -> Material.ROCK == it ? "#" : ".")
            .collect(joining()))
        .collect(joining(System.lineSeparator()));
  }
}
