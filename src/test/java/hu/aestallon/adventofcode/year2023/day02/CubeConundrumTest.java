package hu.aestallon.adventofcode.year2023.day02;

import hu.aestallon.adventofcode.year2023.day02.internal.Sample;
import hu.aestallon.adventofcode.year2023.util.AocIO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CubeConundrumTest {

  @Test
  void exampleInputShouldReturnExampleAnswers() {
    final var input1 = """
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""".lines().toList();

    final var problem1 = CubeConundrum.create(input1);
    assertThat(problem1.solvePart1(Sample.of(12, 13, 14))).isEqualTo(8);

    final var input2 = """
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""".lines().toList();
    final var problem2 = CubeConundrum.create(input2);
    assertThat(problem2.solvePart2()).isEqualTo(2286);
  }

  @Test
  void problemInputShouldReturnProblemAnswers() {
    final var input = AocIO.lines("02", "input02.txt");
    final var problem = CubeConundrum.create(input);
    assertThat(problem.solvePart1(Sample.of(12, 13, 14))).isEqualTo(2439);
    assertThat(problem.solvePart2()).isEqualTo(63711);
  }

}