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

package hu.aestallon.adventofcode.year2023.day14;

import hu.aestallon.adventofcode.year2023.day14.internal.Direction;
import hu.aestallon.adventofcode.year2023.util.AocIO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class ParabolicReflectorDishTest {

  private static final Logger log = LoggerFactory.getLogger(ParabolicReflectorDishTest.class);

  @Test
  void exampleInputYieldsExampleResult() {
    final var input = """
        O....#....
        O.OO#....#
        .....##...
        OO.#O....O
        .O.....O#.
        O.#..O.#.#
        ..O..#O..O
        .......O..
        #....###..
        #OO..#....""".lines().toList();
    var dish = new ParabolicReflectorDish(input);
    ParabolicReflectorDish tiltedNorth = dish.tilt(Direction.NORTH);
    long northernLoad = tiltedNorth.currentLoad(Direction.NORTH);
    assertThat(northernLoad).isEqualTo(136);

    dish = new ParabolicReflectorDish(input);
    for (int i = 0; i < 1_000; i++) {
      dish = dish.cycle();
    }
    assertThat(dish.currentLoad(Direction.NORTH)).isEqualTo(64);
  }

  @Test
  void problemInputYieldsCorrectResult() {
    final var input = AocIO.lines("14", "input14.txt");
    var dish = new ParabolicReflectorDish(input);
    long northernLoad = dish.tilt(Direction.NORTH).currentLoad(Direction.NORTH);
    log.info("Northern load after tilting north is [ {} ]", northernLoad);
    assertThat(northernLoad).isEqualTo(105_249L);

    dish = new ParabolicReflectorDish(input);
    // this is practically cheating: the round rocks will eventually reach an equilibrium, thus
    // there is no need to actually run a billion cycles:
    for (int i = 0; i < 1_000; i++) {
      dish = dish.cycle();
    }
    assertThat(dish.currentLoad(Direction.NORTH)).isEqualTo(88_680L);
  }

}
