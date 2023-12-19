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

package hu.aestallon.adventofcode.year2023.day18;

import hu.aestallon.adventofcode.year2023.util.AocIO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class LavaductLagoonTest {

  private static final Logger log = LoggerFactory.getLogger(LavaductLagoonTest.class);

  @Test
  void example() {
    final var input = """
        R 6 (#70c710)
        D 5 (#0dc571)
        L 2 (#5713f0)
        D 2 (#d2c081)
        R 2 (#59c680)
        D 2 (#411b91)
        L 5 (#8ceee2)
        U 2 (#caa173)
        L 1 (#1b58a2)
        U 2 (#caa171)
        R 2 (#7807d2)
        U 3 (#a77fa3)
        L 2 (#015232)
        U 2 (#7a21e3)""".lines().toList();
    final var problem = new LavaductLagoon(input);
    assertThat(problem.solvePart1()).isEqualTo(62L);
  }

  @Test
  void problem() {
    final var input = AocIO.lines("18", "input18.txt");
    final var problem = new LavaductLagoon(input);

    final long solution1 = problem.solvePart1();
    log.info("The volume of the excavated hole is [ {} ]", solution1);
  }

}
