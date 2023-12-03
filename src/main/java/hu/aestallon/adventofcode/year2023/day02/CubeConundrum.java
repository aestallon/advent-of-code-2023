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

package hu.aestallon.adventofcode.year2023.day02;

import hu.aestallon.adventofcode.year2023.day02.internal.Game;
import hu.aestallon.adventofcode.year2023.day02.internal.Sample;

import java.util.List;

public final class CubeConundrum {

  public static CubeConundrum create(List<String> lines) {
    return new CubeConundrum(lines.stream()
        .map(Game::parse)
        .toList());
  }

  private final List<Game> games;

  private CubeConundrum(List<Game> games) {
    this.games = games;
  }

  int solvePart1(final Sample bound) {
    return games.stream()
        .filter(it -> it.isPossible(bound))
        .mapToInt(Game::id)
        .sum();
  }

  long solvePart2() {
    return games.stream()
        .map(Game::lowerBound)
        .mapToLong(Sample::power)
        .sum();
  }

}
