package hu.aestallon.adventofcode.year2023.day01;

import hu.aestallon.adventofcode.year2023.util.AocIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.OptionalInt;
import java.util.stream.IntStream;

public final class Trebuchet {

  private static final Logger log = LoggerFactory.getLogger(Trebuchet.class);

  public static void main(String[] args) {
    final var lines = AocIO.lines("01", "input01.txt");

    // part 1:
    final int sum = lines.stream()
        .map(s -> s.chars().filter(Character::isDigit).toArray())
        .filter(arr -> arr.length >= 1)
        .mapToInt(arr -> {
          final char c1 = (char) arr[0];
          final char c2 = (char) arr[arr.length - 1];
          return Integer.parseInt(new String(new char[]{c1, c2}));
        })
        .sum();
    log.info("The sum of all calibration values is [ {} ]", sum);

    // part 2:
    final int correctedSum = lines.stream()
        .mapToInt(s -> {
          int[] nums = IntStream
              .range(0, s.length())
              .flatMap(i -> findIntAt(s, i).stream())
              .toArray();
          return nums[0] * 10 + nums[nums.length - 1];
        })
        .sum();
    log.info("The sum of all corrected calibration values is [ {} ]", correctedSum);
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

}
