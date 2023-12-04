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

package hu.aestallon.adventofcode.year2023.day04;

import hu.aestallon.adventofcode.year2023.day04.internal.Scratchcard;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

public class Scratchcards {

  static Scratchcards create(List<String> lines) {
    return new Scratchcards(lines.stream().map(Scratchcard::parse).toList());
  }

  private final List<Scratchcard> scratchcards;

  private Scratchcards(List<Scratchcard> scratchcards) {this.scratchcards = scratchcards;}

  long solvePart1() {
    return scratchcards.stream()
        .mapToLong(Scratchcard::value)
        .sum();
  }

  long solvePart2() {
    final var copyCounts = new int[scratchcards.size()];
    Arrays.fill(copyCounts, 1);

    for (var card : scratchcards) {
      final int id = card.id();
      final int count = copyCounts[id - 1];
      final int matches = card.matches();
      for (int i = id + 1; i <= id + matches; i++) {
        copyCounts[i - 1] += count;
      }
    }
    return IntStream.of(copyCounts).sum();
  }
}
