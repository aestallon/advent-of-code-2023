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

package hu.aestallon.adventofcode.year2023.day08;

import hu.aestallon.adventofcode.year2023.day08.internal.WastelandMap;

import java.util.List;

public class HauntedWasteland {

  private final String       instructions;
  private final WastelandMap map;

  HauntedWasteland(List<String> lines) {
    this.instructions = lines.getFirst();
    this.map = WastelandMap.parse(lines.subList(2, lines.size()));
  }

  int solvePart1() {
    return map.stepsToTraverse("AAA", "ZZZ", instructions);
  }

  long solvePart2() {
    return map.ghostStepsToTraverse(instructions);
  }

}
