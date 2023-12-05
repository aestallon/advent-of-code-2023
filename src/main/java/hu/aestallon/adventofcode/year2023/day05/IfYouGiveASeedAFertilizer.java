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

package hu.aestallon.adventofcode.year2023.day05;

import hu.aestallon.adventofcode.year2023.day05.internal.Almanac;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

final class IfYouGiveASeedAFertilizer {

  private final Almanac almanac;

  IfYouGiveASeedAFertilizer(List<String> lines) {
    this.almanac = Almanac.parse(lines);
  }
  long solvePart1() {
    return almanac.seedsByLocation().values().stream()
        .mapToLong(Long::longValue)
        .min()
        .orElse(-1L);
  }

  long solvePart2() {
    return almanac.lowestLocation();
  }
}
