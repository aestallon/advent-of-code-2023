package hu.aestallon.adventofcode.year2023.day03;

import hu.aestallon.adventofcode.year2023.day03.internal.Schematic;
import hu.aestallon.adventofcode.year2023.util.AocIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GearRatios {

  private static final Logger log = LoggerFactory.getLogger(GearRatios.class);

  public static void main(String[] args) {
    final char[][] rawSchematic = AocIO.lines("03", "input03.txt").stream()
        .map(String::toCharArray)
        .toArray(char[][]::new);

    final var schematic = new Schematic(rawSchematic);
    final long sum = schematic.numbersAdjacentToSymbols()
        .mapToLong(Schematic.Number::longVal)
        .sum();
    log.info("The sum of all numbers adjacent to symbols is [ {} ]", sum);

    final long gearRatioSum = schematic.gears()
        .mapToLong(it -> it.parts().stream()
            .mapToLong(Schematic.Number::longVal)
            .reduce(1L, (a, b) -> a * b))
        .sum();
    log.info("The sum of all gear ratios in the schematic is [ {} ]", gearRatioSum);
  }
}
