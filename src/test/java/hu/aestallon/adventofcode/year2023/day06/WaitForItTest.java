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

package hu.aestallon.adventofcode.year2023.day06;

import hu.aestallon.adventofcode.year2023.util.AocIO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class WaitForItTest {

  private static final Logger log = LoggerFactory.getLogger(WaitForItTest.class);

  @Test
  void exampleSampleYieldsExampleResult() {
    final var input = """
        Time:      7  15   30
        Distance:  9  40  200""".lines().toList();
    final var problem = WaitForIt.create(input);
    assertThat(problem.solvePart1()).isEqualTo(288L);
    assertThat(problem.solvePart2()).isEqualTo(71_503L);
  }

  @Test
  void problemInputYieldsCorrectResult() {
    final var input = AocIO.lines("06", "input06.txt");
    final var problem = WaitForIt.create(input);

    final long res1 = problem.solvePart1();
    log.info("The product of winning strategies is [ {} ]", res1);
    assertThat(res1).isEqualTo(1_159_152L);

    final long res2 = problem.solvePart2();
    log.info("The number of ways to win the loooong race is [ {} ]", res2);
    assertThat(res2).isEqualTo(41_513_103L);
  }

}