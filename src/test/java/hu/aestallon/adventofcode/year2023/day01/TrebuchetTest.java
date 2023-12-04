package hu.aestallon.adventofcode.year2023.day01;

import hu.aestallon.adventofcode.year2023.util.AocIO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class TrebuchetTest {

  @Test
  void exampleInputShouldReturnExampleAnswers() {
    final var input1 = """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet""".lines().toList();
    final var trebuchet1 = new Trebuchet(input1);
    assertThat(trebuchet1.solvePart1()).isEqualTo(142);

    final var input2 = """
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen""".lines().toList();
    final var trebuchet2 = new Trebuchet(input2);
    assertThat(trebuchet2.solvePart2()).isEqualTo(281);
  }

  @Test
  void problemInputShouldReturnProblemAnswers() {
    final var lines = AocIO.lines("01", "input01.txt");
    final var trebuchet = new Trebuchet(lines);
    assertThat(trebuchet.solvePart1()).isEqualTo(54_304);
    assertThat(trebuchet.solvePart2()).isEqualTo(54_418);
  }

}
