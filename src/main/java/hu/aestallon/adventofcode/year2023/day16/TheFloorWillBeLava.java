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

package hu.aestallon.adventofcode.year2023.day16;

import hu.aestallon.adventofcode.year2023.day16.internal.Contraption;
import hu.aestallon.adventofcode.year2023.day16.internal.Direction;
import hu.aestallon.adventofcode.year2023.day16.internal.Position;

import java.util.List;
import java.util.Set;

final class TheFloorWillBeLava {

  private final Contraption contraption;

  TheFloorWillBeLava(List<String> lines) {
    this.contraption = Contraption.parse(lines);
  }


  long solvePart1() {
    return contraption.run(Direction.E, new Position(0, 0)).size();
  }

  long solvePart2() {
    return contraption.runAll().mapToInt(Set::size).max().orElse(0);
  }

}
