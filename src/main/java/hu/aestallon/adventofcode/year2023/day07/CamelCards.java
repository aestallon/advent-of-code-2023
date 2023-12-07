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

package hu.aestallon.adventofcode.year2023.day07;

import hu.aestallon.adventofcode.year2023.day07.internal.Hand;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.Comparator.reverseOrder;

final class CamelCards {

  private final List<String> lines;

  CamelCards(List<String> lines) {
    this.lines = lines;
  }

  long solvePart1() {
    return solve(Hand::parse);
  }

  long solvePart2() {
    return solve(Hand::parseWithJokers);
  }

  private long solve(Function<String, Hand> handParser) {
    final int[] bids = lines.stream()
        .map(handParser)
        .sorted(reverseOrder())
        .mapToInt(Hand::bid)
        .toArray();
    return IntStream.range(0, bids.length)
        .mapToLong(i -> (long) (i + 1) * bids[i])
        .sum();
  }

}
