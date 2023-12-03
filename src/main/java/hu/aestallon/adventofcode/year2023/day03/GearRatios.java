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

package hu.aestallon.adventofcode.year2023.day03;

import hu.aestallon.adventofcode.year2023.day03.internal.Schematic;

import java.util.List;

public final class GearRatios {

  public static GearRatios create(List<String> lines) {
    final char[][] rawSchematic = lines.stream()
        .map(String::toCharArray)
        .toArray(char[][]::new);
    return new GearRatios(new Schematic(rawSchematic));
  }

  private final Schematic schematic;

  private GearRatios(Schematic schematic) {
    this.schematic = schematic;
  }

  long solvePart1() {
    return schematic.numbersAdjacentToSymbols()
        .mapToLong(Schematic.Number::longVal)
        .sum();
  }

  long solvePart2() {
    return schematic.gears()
        .mapToLong(it -> it.parts().stream()
            .mapToLong(Schematic.Number::longVal)
            .reduce(1L, (a, b) -> a * b))
        .sum();
  }

}
