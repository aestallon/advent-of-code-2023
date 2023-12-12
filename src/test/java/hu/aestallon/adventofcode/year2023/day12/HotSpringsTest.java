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

package hu.aestallon.adventofcode.year2023.day12;

import hu.aestallon.adventofcode.year2023.day12.internal.RowOfSprings;
import hu.aestallon.adventofcode.year2023.util.AocIO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class HotSpringsTest {

  private static final Logger log = LoggerFactory.getLogger(HotSpringsTest.class);

  @Test
  void exampleInputYieldsExampleResult() {
    final var input = """
        ???.### 1,1,3
        .??..??...?##. 1,1,3
        ?#?#?#?#?#?#?#? 1,3,1,6
        ????.#...#... 4,1,1
        ????.######..#####. 1,6,5
        ?###???????? 3,2,1""".lines().toList();
    final var problem = new HotSprings(input);
    assertThat(problem.solvePart1()).isEqualTo(21L);

    final var line = "???.### 1,1,3";
    final var row = RowOfSprings.parseUnfolded(line);
    assertThat(row.toString())
        .isEqualTo("???.###????.###????.###????.###????.### 1,1,3,1,1,3,1,1,3,1,1,3,1,1,3");

    assertThat(problem.solvePart2()).isEqualTo(525152L);
  }

  @Test
  void problemInputShouldYieldProblemResult() {
    final var input = AocIO.lines("12", "input12.txt");
    final var problem = new HotSprings(input);

    final long solution1 = problem.solvePart1();
    log.info("The sum of all ways to repair the springs is [ {} ]", solution1);
    assertThat(solution1).isEqualTo(7_260L);

    final long solution2 = problem.solvePart2();
    log.info("The unfolded sum is: [ {} ]", solution2);
    assertThat(solution2).isEqualTo(1_909_291_258_644L);
  }

}