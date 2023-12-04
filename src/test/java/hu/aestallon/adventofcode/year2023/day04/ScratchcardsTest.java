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

package hu.aestallon.adventofcode.year2023.day04;

import hu.aestallon.adventofcode.year2023.util.AocIO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class ScratchcardsTest {

  private static final Logger log = LoggerFactory.getLogger(ScratchcardsTest.class);

  @Test
  void exampleInputShouldYieldExampleResult() {
    final var input = """
        Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
        Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
        Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
        Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
        Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11""".lines().toList();
    final var problem = Scratchcards.create(input);
    assertThat(problem.solvePart1()).isEqualTo(13L);
    assertThat(problem.solvePart2()).isEqualTo(30L);
  }

  @Test
  void problemInputShouldYieldCorrectResult() {
    final var input = AocIO.lines("04", "input04.txt");
    final var problem = Scratchcards.create(input);

    log.info("The sum of the value of all scratchcards is [ {} ]", problem.solvePart1());
    log.info("The correct total value is [ {} ]", problem.solvePart2());

    assertThat(problem.solvePart1()).isEqualTo(18_519L);
    assertThat(problem.solvePart2()).isEqualTo(11_787_590L);
  }

}