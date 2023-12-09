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

package hu.aestallon.adventofcode.year2023.day09;

import hu.aestallon.adventofcode.year2023.util.AocIO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class MirageMaintenanceTest {

  private static final Logger log = LoggerFactory.getLogger(MirageMaintenanceTest.class);

  @Test
  void exampleInputYieldsExampleResult() {
    final var input = """
        0 3 6 9 12 15
        1 3 6 10 15 21
        10 13 16 21 30 45""".lines().toList();
    final var problem = new MirageMaintenance(input);
    assertThat(problem.solvePart1()).isEqualTo(114L);
    assertThat(problem.solvePart2()).isEqualTo(2L);
  }

  @Test
  void problemInputYieldsCorrectResult() {
    final var input = AocIO.lines("09", "input09.txt");
    final var problem = new MirageMaintenance(input);

    final long solution1 = problem.solvePart1();
    log.info("The sum of the extrapolated last values is [ {} ]", solution1);
    assertThat(solution1).isEqualTo(2_175_229_206L);

    final long solution2 = problem.solvePart2();
    log.info("The sum of the extrapolated FIRST values is [ {} ]", solution2);
    assertThat(solution2).isEqualTo(942L);
  }

}