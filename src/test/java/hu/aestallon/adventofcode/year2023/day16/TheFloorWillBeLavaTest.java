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

import hu.aestallon.adventofcode.year2023.util.AocIO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class TheFloorWillBeLavaTest {

  private static final Logger log = LoggerFactory.getLogger(TheFloorWillBeLavaTest.class);

  @Test
  void exampleInputYieldsExampleResult() {
    final var input = """
        .|...\\....
        |.-.\\.....
        .....|-...
        ........|.
        ..........
        .........\\
        ..../.\\\\..
        .-.-/..|..
        .|....-|.\\
        ..//.|....""".lines().toList();
    final var problem = new TheFloorWillBeLava(input);
    assertThat(problem.solvePart1()).isEqualTo(46L);
    assertThat(problem.solvePart2()).isEqualTo(51L);
  }

  @Test
  void problemInputYieldsCorrectResult() {
    final var input = AocIO.lines("16", "input16.txt");
    final var problem = new TheFloorWillBeLava(input);

    final long solution1 = problem.solvePart1();
    log.info("There are [ {} ] energized tiles.", solution1);
    assertThat(solution1).isEqualTo(8_901L);

    final long solution2 = problem.solvePart2();
    log.info("At most, [ {} ] tiles can be energized.", solution2);
    assertThat(solution2).isEqualTo(9_064L);
  }

}
