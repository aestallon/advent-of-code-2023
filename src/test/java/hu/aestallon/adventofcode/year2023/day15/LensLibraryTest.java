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

import hu.aestallon.adventofcode.year2023.util.AocIO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class LensLibraryTest {

  private static final Logger log = LoggerFactory.getLogger(LensLibraryTest.class);

  @Test
  void exampleInputYieldsExampleResult() {
    final var input = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7";
    final var problem = new LensLibrary(input);
    assertThat(problem.solvePart1()).isEqualTo(1_320L);
    assertThat(problem.solvePart2()).isEqualTo(145L);
  }

  @Test
  void problemInputYieldsCorrectResult() {
    final var input = String.join("", AocIO.lines("15", "input15.txt"));
    final var problem = new LensLibrary(input);

    final long solution1 = problem.solvePart1();
    log.info("The sum of the instruction holiday HASHes is [ {} ]", solution1);
    assertThat(solution1).isEqualTo(513_172L);

    final long solution2 = problem.solvePart2();
    log.info("The focusing power of all boxes is [ {} ]", solution2);
    assertThat(solution2).isEqualTo(237_806L);
  }

}
