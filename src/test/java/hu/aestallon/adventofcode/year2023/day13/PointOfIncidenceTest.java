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

package hu.aestallon.adventofcode.year2023.day13;

import hu.aestallon.adventofcode.year2023.util.AocIO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class PointOfIncidenceTest {

  private static final Logger log = LoggerFactory.getLogger(PointOfIncidenceTest.class);

  @Test
  void exampleInputYieldsExampleResult() {
    final var input = """
        #.##..##.
        ..#.##.#.
        ##......#
        ##......#
        ..#.##.#.
        ..##..##.
        #.#.##.#.
                
        #...##..#
        #....#..#
        ..##..###
        #####.##.
        #####.##.
        ..##..###
        #....#..#""".lines().toList();
    final var problem = new PointOfIncidence(input);

    assertThat(problem.solvePart1()).isEqualTo(405L);
    assertThat(problem.solvePart2()).isEqualTo(400L);
  }

  @Test
  void problemInputYieldsCorrectResult() {
    final var input = AocIO.lines("13", "input13.txt");
    final var problem = new PointOfIncidence(input);

    final long solution1 = problem.solvePart1();
    log.info("The weighted sum of all lines of reflection is [ {} ]", solution1);
    assertThat(solution1).isEqualTo(32_371L);

    final long solution2 = problem.solvePart2();
    log.info("The weighted sum of the smudgeless reflections is [ {} ]", solution2);
    assertThat(solution2).isEqualTo(37_416L);
  }

}