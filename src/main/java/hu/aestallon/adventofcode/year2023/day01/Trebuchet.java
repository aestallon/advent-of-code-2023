package hu.aestallon.adventofcode.year2023.day01;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public final class Trebuchet {

  static Trebuchet create(List<String> lines) {
    return new Trebuchet(lines);
  }

  private static int digit(int codePoint) {
    return Character.digit(codePoint, 10);
  }

  private static OptionalInt findIntAt(String s, int idx) {
    if (idx >= s.length()) {
      return OptionalInt.empty();
    }
    return match(s.substring(idx));
  }

  private static OptionalInt match(String s) {
    if (s.startsWith("1") || s.startsWith("one")) {
      return OptionalInt.of(1);
    } else if (s.startsWith("2") || s.startsWith("two")) {
      return OptionalInt.of(2);
    } else if (s.startsWith("3") || s.startsWith("three")) {
      return OptionalInt.of(3);
    } else if (s.startsWith("4") || s.startsWith("four")) {
      return OptionalInt.of(4);
    } else if (s.startsWith("5") || s.startsWith("five")) {
      return OptionalInt.of(5);
    } else if (s.startsWith("6") || s.startsWith("six")) {
      return OptionalInt.of(6);
    } else if (s.startsWith("7") || s.startsWith("seven")) {
      return OptionalInt.of(7);
    } else if (s.startsWith("8") || s.startsWith("eight")) {
      return OptionalInt.of(8);
    } else if (s.startsWith("9") || s.startsWith("nine")) {
      return OptionalInt.of(9);
    } else {
      return OptionalInt.empty();
    }
  }

  private final List<String> lines;

  private Trebuchet(List<String> lines) {
    this.lines = lines;
  }

  int solvePart1() {
    return lines.stream()
        .map(s -> s.chars().filter(Character::isDigit).toArray())
        .filter(arr -> arr.length > 0)
        .mapToInt(arr -> digit(arr[0]) * 10 + digit(arr[arr.length - 1]))
        .sum();
  }

  int solvePart2() {
    return lines.stream()
        .map(s -> IntStream
            .range(0, s.length())
            .flatMap(i -> findIntAt(s, i).stream())
            .toArray())
        .filter(arr -> arr.length > 0)
        .mapToInt(arr -> arr[0] * 10 + arr[arr.length - 1])
        .sum();
  }

}
