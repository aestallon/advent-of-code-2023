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
