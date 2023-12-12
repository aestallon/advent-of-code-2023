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

package hu.aestallon.adventofcode.year2023.day12.internal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

public record RowOfSprings(List<SpringState> springs, List<Integer> damagedRuns) {

  private static final Map<RowOfSprings, Long> MEMO = new HashMap<>();

  public static RowOfSprings parse(String s) {
    final var parts = s.split("\\s");
    final var states = parts[0].chars().mapToObj(SpringState::ofCodePoint).toList();
    final var damagedRuns = Arrays.stream(parts[1].split(","))
        .map(Integer::parseInt)
        .toList();
    return new RowOfSprings(states, damagedRuns);
  }

  public static RowOfSprings parseUnfolded(String s) {
    final var parts = s.split("\\s");
    final var states = IntStream.range(0, 5)
        .mapToObj(__ -> parts[0])
        .collect(joining("?")).chars()
        .mapToObj(SpringState::ofCodePoint)
        .toList();
    final var runs = Arrays.stream(parts[1].split(","))
        .map(Integer::parseInt)
        .toList();
    final var damagedRuns = IntStream.range(0, 5)
        .mapToObj(__ -> runs)
        .flatMap(List::stream)
        .toList();
    return new RowOfSprings(states, damagedRuns);
  }

  public long solve() {
    if (springs.isEmpty()) {
      return damagedRuns.isEmpty() ? 1 : 0;
    }

    return switch (springs.getFirst()) {
      case OPERATIONAL -> shrinkBy(1, 0).solve();
      case DAMAGED -> solveInternalAndMemoize();
      case UNKNOWN -> shrinkBy(1, 0).solve() + solveInternalAndMemoize();
    };
  }

  private long solveInternalAndMemoize() {
    final long x = solveInternal();
    MEMO.put(this, x);
    return x;
  }

  private long solveInternal() {
    final Long solution = MEMO.get(this);
    if (solution != null) {
      return solution;
    }

    if (damagedRuns.isEmpty()) {
      return 0;
    }

    final int firstRun = damagedRuns.getFirst();
    if (springs.size() < firstRun) {
      return 0;
    }

    if (springs.stream().limit(firstRun).anyMatch(it -> SpringState.OPERATIONAL == it)) {
      return 0;
    }

    if (springs.size() == firstRun) {
      return (damagedRuns.size() == 1) ? 1 : 0;
    }

    if (springs.get(firstRun) == SpringState.DAMAGED) {
      return 0;
    }

    return shrinkBy(firstRun + 1, 1).solve();
  }

  /**
   * A copy of row with some amounts of spring-marks and run inscriptions dropped.
   *
   * @param spring the amount of {@link SpringState} spring marks to drop, >= 0
   * @param run    the amount of {@link #damagedRuns} markings to drop, >= 0
   *
   * @return a new {@link RowOfSprings} with their leftmost {@code spring} markings and {@code run}
   * runs dropped
   */
  private RowOfSprings shrinkBy(int spring, int run) {
    return new RowOfSprings(
        springs.subList(spring, springs.size()),
        damagedRuns.subList(run, damagedRuns.size()));
  }

  @Override
  public String toString() {
    return springs.stream().map(it -> String.valueOf((char) it.codePoint())).collect(joining())
           + " "
           + damagedRuns.stream().map(String::valueOf).collect(joining(","));
  }

}
