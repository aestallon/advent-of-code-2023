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

package hu.aestallon.adventofcode.year2023.day11;

import hu.aestallon.adventofcode.year2023.util.AocIO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;


class CosmicExpansionTest {

  private static final Logger log = LoggerFactory.getLogger(CosmicExpansionTest.class);

  @Test
  void exampleInputShouldYieldExampleResult() {
    final int expansionRate = 2;
    final var input = """
        ...#......
        .......#..
        #.........
        ..........
        ......#...
        .#........
        .........#
        ..........
        .......#..
        #...#.....""".lines().toList();
    final var problem = new CosmicExpansion(input, expansionRate);
    assertThat(problem.solve()).isEqualTo(374L);
  }

  @Test
  void problemInputShouldYieldProblemResult() {
    final var input = AocIO.lines("11", "input11.txt");

    final int expansionRate1 = 2;
    final var problem1 = new CosmicExpansion(input, expansionRate1);
    final long solution1 = problem1.solve();
    log.info("The sum of the shortest path between galaxy pairs is [ {} ]", solution1);
    assertThat(solution1).isEqualTo(9_647_174L);

    final int expansionRate2 = 1_000_000;
    final var problem2 = new CosmicExpansion(input, expansionRate2);
    final long solution2 = problem2.solve();
    log.info("The sum of the shortest path between galaxy pairs is [ {} ]", solution2);
    assertThat(solution2).isEqualTo(377_318_892_554L);
  }
}