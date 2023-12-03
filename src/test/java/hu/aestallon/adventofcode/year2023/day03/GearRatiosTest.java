package hu.aestallon.adventofcode.year2023.day03;

import hu.aestallon.adventofcode.year2023.util.AocIO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GearRatiosTest {

  @Test
  void exampleInputShouldReturnExampleAnswers() {
    final var input = """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...$.*....
        .664.598..""".lines().toList();
    final var problem = GearRatios.create(input);
    assertThat(problem.solvePart1()).isEqualTo(4_361L);
    assertThat(problem.solvePart2()).isEqualTo(467_835L);
  }

  @Test
  void problemInputShouldReturnProblemAnswers() {
    final var input = AocIO.lines("03", "input03.txt");
    final var problem = GearRatios.create(input);
    assertThat(problem.solvePart1()).isEqualTo(560_670L);
    assertThat(problem.solvePart2()).isEqualTo(91_622_824L);
  }

}