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

package hu.aestallon.adventofcode.year2023.day01;

import hu.aestallon.adventofcode.year2023.util.Digits;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.IntStream;

public final class Trebuchet {

  private static final String[] NUMBERS = {
      "one", "two", "three",
      "four", "five", "six",
      "seven", "eight", "nine"
  };

  static Trebuchet create(List<String> lines) {
    return new Trebuchet(lines);
  }

  private static OptionalInt findIntAt(String s, int idx) {
    if (idx >= s.length()) {
      return OptionalInt.empty();
    }
    return match(s.substring(idx));
  }

  private static OptionalInt match(String s) {
    return IntStream.rangeClosed(1, NUMBERS.length)
        .filter(i -> s.startsWith(String.valueOf(i)) || s.startsWith(NUMBERS[i - 1]))
        .findFirst();
  }

  private final List<String> lines;

  private Trebuchet(List<String> lines) {
    this.lines = lines;
  }

  int solvePart1() {
    return solve(s -> s.chars().filter(Character::isDigit).map(Digits::digit).toArray());
  }

  int solvePart2() {
    return solve(s -> IntStream.range(0, s.length())
        .flatMap(i -> findIntAt(s, i).stream())
        .toArray());
  }

  private int solve(Function<String, int[]> stringToDigits) {
    return lines.stream()
        .map(stringToDigits)
        .filter(arr -> arr.length > 0)
        .mapToInt(arr -> arr[0] * 10 + arr[arr.length - 1])
        .sum();
  }

}
