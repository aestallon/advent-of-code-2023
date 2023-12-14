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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    assertThat(tiltedNorth.toString()).isEqualTo("""
        OOOO.#.O..
        OO..#....#
        OO..O##..O
        O..#.OO...
        ........#.
        ..#....#.#
        ..O..#.O.O
        ..O.......
        #....###..
        #....#....""");

    long northernLoad = tiltedNorth.currentLoad(Direction.NORTH);
    assertThat(northernLoad).isEqualTo(136);

    runPart2(input, 64L);
  }

  @Test
  void problemInputYieldsCorrectResult() {
    final var input = AocIO.lines("14", "input14.txt");
    var dish = new ParabolicReflectorDish(input);
    long northernLoad = dish.tilt(Direction.NORTH).currentLoad(Direction.NORTH);
    log.info("Northern load after tilting north is [ {} ]", northernLoad);
    assertThat(northernLoad).isEqualTo(105_249L);

    runPart2(input, 88_680L);
  }

  private void runPart2(final List<String> input, final long expected) {
    var dish = new ParabolicReflectorDish(input);
    // ParabolicReflectorDish is constructed so only the rocks contribute to the equivalence
    // examination: two dishes are considered equal, even if they differ in their cycle number.
    final Set<ParabolicReflectorDish> memo = new HashSet<>();
    final int limit = 1_000_000_000;
    for (int i = 0; i < limit; i++) {
      dish = dish.cycle();
      if (memo.contains(dish)) {
        final long startIdx = memo.stream().filter(dish::equals).findFirst().orElseThrow().cycleNr;
        log.debug("The CYCLE started with cycle [ {} ]", startIdx);

        final long cycleSize = dish.cycleNr - startIdx;
        log.debug("The CYCLE size was [ {} ]", cycleSize);

        final long idxOffset = (limit - startIdx) % cycleSize;
        log.debug("The experiment ends with CYCLE offset of [ {} ]", idxOffset);

        final long finalIdx = startIdx + idxOffset;
        log.debug("The experiment ends on the same state as CYCLE member [ {} ]", finalIdx);

        dish = memo.stream().filter(it -> finalIdx == it.cycleNr).findFirst().orElseThrow();
        break;

      } else {
        memo.add(dish);
      }
    }
    final long northernLoad = dish.currentLoad(Direction.NORTH);
    log.info("The northern load at the end of the cycle is [ {} ]", northernLoad);

    assertThat(northernLoad).isEqualTo(expected);
  }

}
