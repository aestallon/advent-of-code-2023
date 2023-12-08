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

import hu.aestallon.adventofcode.year2023.util.AocIO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class HauntedWastelandTest {

  private static final Logger log = LoggerFactory.getLogger(HauntedWastelandTest.class);

  @Test
  void example1() {
    final var input = """
        RL
                
        AAA = (BBB, CCC)
        BBB = (DDD, EEE)
        CCC = (ZZZ, GGG)
        DDD = (DDD, DDD)
        EEE = (EEE, EEE)
        GGG = (GGG, GGG)
        ZZZ = (ZZZ, ZZZ)""".lines().toList();
    final var problem = new HauntedWasteland(input);
    assertThat(problem.solvePart1()).isEqualTo(2);
  }

  @Test
  void example2() {
    final var input = """
        LLR
                
        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)""".lines().toList();
    final var problem = new HauntedWasteland(input);
    assertThat(problem.solvePart1()).isEqualTo(6);
  }

  @Test
  void exampleForPart2() {
    final var input = """
        LR
                
        11A = (11B, XXX)
        11B = (XXX, 11Z)
        11Z = (11B, XXX)
        22A = (22B, XXX)
        22B = (22C, 22C)
        22C = (22Z, 22Z)
        22Z = (22B, 22B)
        XXX = (XXX, XXX)""".lines().toList();
    final var problem = new HauntedWasteland(input);
    assertThat(problem.solvePart2()).isEqualTo(6);
  }

  @Test
  void problemInputShouldYieldCorrectResult() {
    final var input = AocIO.lines("08", "input08.txt");
    final var problem = new HauntedWasteland(input);

    final int result1 = problem.solvePart1();
    log.info("It takes [ {} ] steps to traverse from AAA to ZZZ!", result1);
    assertThat(result1).isEqualTo(13_771);

    final long result2 = problem.solvePart2();
    log.info("It takes [ {} ] steps for ghosts to traverse to Z-terminating nodes!", result2);
    assertThat(result2).isEqualTo(13_129_439_557_681L);
  }

}