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

package hu.aestallon.adventofcode.year2023.day15;

import hu.aestallon.adventofcode.year2023.day15.internal.HolidayHash;
import hu.aestallon.adventofcode.year2023.day15.internal.HolidayMap;
import hu.aestallon.adventofcode.year2023.day15.internal.Instruction;

import java.util.Arrays;
import java.util.List;

final class LensLibrary {

  private final List<Instruction> instructions;

  LensLibrary(String input) {
    this.instructions = Arrays.stream(input.split(",")).map(Instruction::parse).toList();
  }

  long solvePart1() {
    return instructions.stream()
        .map(Instruction::literal)
        .mapToLong(HolidayHash::hash)
        .sum();
  }

  long solvePart2() {
    final var map = HolidayMap.newInstance();
    instructions.forEach(map::put);
    return map.focusingPower();
  }

}
