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

package hu.aestallon.adventofcode.year2023.day11.internal;

import hu.aestallon.adventofcode.year2023.util.Pair;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class GalaxyChart {

  private static final char GALAXY_MARK = '#';

  public static GalaxyChart parse(char[][] chars, int expansionRate) {
    final Map<Position, Galaxy> galaxiesByPosition = new TreeMap<>();

    final int[] emptyRowIndices = emptyRowIndices(chars);
    final int[] emptyColIndices = emptyColIndices(chars);

    for (int row = 0; row < chars.length; row++) {
      final int expandedRowCount = countOfItemsLowerThan(emptyRowIndices, row);
      for (int col = 0; col < chars[row].length; col++) {
        if (chars[row][col] == GALAXY_MARK) {

          final int expandedColCount = countOfItemsLowerThan(emptyColIndices, col);
          final int x = (col - expandedColCount) + (expandedColCount * expansionRate);
          final int y = (row - expandedRowCount) + (expandedRowCount * expansionRate);
          final Position p = new Position(x, y);
          galaxiesByPosition.put(p, new Galaxy(p));

        }
      }
    }

    return new GalaxyChart(galaxiesByPosition);
  }

  private static int[] emptyRowIndices(char[][] chars) {
    return IntStream.range(0, chars.length)
        .filter(i -> {
          for (final char c : chars[i]) {
            if (c == GALAXY_MARK) {
              return false;
            }
          }
          return true;
        })
        .toArray();
  }

  private static int[] emptyColIndices(char[][] chars) {
    return IntStream.range(0, chars[0].length)
        .filter(i -> {
          for (final var arr : chars) {
            if (arr[i] == GALAXY_MARK) {
              return false;
            }
          }
          return true;
        })
        .toArray();
  }

  private static int countOfItemsLowerThan(int[] items, int value) {
    int res = 0;
    for (final int i : items) {
      if (i >= value) {
        break;
      }
      res++;
    }
    return res;
  }


  private final Map<Position, Galaxy> galaxiesByPosition;

  private GalaxyChart(Map<Position, Galaxy> galaxiesByPosition) {
    this.galaxiesByPosition = galaxiesByPosition;
  }

  public Stream<Pair<Galaxy, Galaxy>> galaxyPairs() {
    final Galaxy[] galaxies = galaxiesByPosition.values().toArray(Galaxy[]::new);
    return IntStream.range(0, galaxies.length - 1)
        .boxed()
        .flatMap(i -> IntStream.range(i, galaxies.length)
            .mapToObj(i2 -> Pair.of(galaxies[i], galaxies[i2])));
  }

}
