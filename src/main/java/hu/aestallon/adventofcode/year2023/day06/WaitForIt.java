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

package hu.aestallon.adventofcode.year2023.day06;

import hu.aestallon.adventofcode.year2023.day06.internal.Race;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

final class WaitForIt {

  static WaitForIt create(List<String> lines) {
    final int[] times = parseLine(lines.get(0));
    final int[] topDistances = parseLine(lines.get(1));

    final long longTime = asSingleNumber(times);
    final long longDist = asSingleNumber(topDistances);

    return IntStream.range(0, Math.min(times.length, topDistances.length))
        .mapToObj(i -> new Race(times[i], topDistances[i]))
        .collect(collectingAndThen(toSet(), races1 -> new WaitForIt(
            races1,
            new Race(longTime, longDist))));
  }

  private static long asSingleNumber(int[] nums) {
    return Arrays.stream(nums)
        .mapToObj(String::valueOf)
        .collect(collectingAndThen(joining(), Long::parseLong));
  }

  private static int[] parseLine(String line) {
    final int prefixDelimiter = line.indexOf(':');
    return Arrays.stream(line.substring(prefixDelimiter + 1).trim().split("\\s"))
        .filter(it -> !it.isBlank())
        .map(String::trim)
        .mapToInt(Integer::parseInt)
        .toArray();
  }

  private final Set<Race> races;
  private final Race      longRace;

  private WaitForIt(Set<Race> races, Race longRace) {
    this.races = races;
    this.longRace = longRace;
  }

  long solvePart1() {
    return races.stream().mapToLong(Race::numberOfWaysToWin).reduce(1L, (a, b) -> a * b);
  }

  long solvePart2() {
    return longRace.numberOfWaysToWin();
  }

}
