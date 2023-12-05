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

package hu.aestallon.adventofcode.year2023.day05;


import hu.aestallon.adventofcode.year2023.day04.Scratchcards;
import hu.aestallon.adventofcode.year2023.util.AocIO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class IfYouGiveASeedAFertilizerTest {

  private static final Logger log = LoggerFactory.getLogger(IfYouGiveASeedAFertilizerTest.class);

  @Test
  void exampleInputShouldYieldExampleResult() {
    final var input = """
        seeds: 79 14 55 13
                
        seed-to-soil map:
        50 98 2
        52 50 48
                
        soil-to-fertilizer map:
        0 15 37
        37 52 2
        39 0 15
                
        fertilizer-to-water map:
        49 53 8
        0 11 42
        42 0 7
        57 7 4
                
        water-to-light map:
        88 18 7
        18 25 70
                
        light-to-temperature map:
        45 77 23
        81 45 19
        68 64 13
                
        temperature-to-humidity map:
        0 69 1
        1 0 69
                
        humidity-to-location map:
        60 56 37
        56 93 4""".lines().toList();
    final var problem = new IfYouGiveASeedAFertilizer(input);
    assertThat(problem.solvePart1()).isEqualTo(35L);
    assertThat(problem.solvePart2()).isEqualTo(46L);
  }

  @Test
  void problemInputShouldYieldCorrectResult() {
    final var input = AocIO.lines("05", "input05.txt");
    final var problem = new IfYouGiveASeedAFertilizer(input);

    log.info("The lowest location is [ {} ]", problem.solvePart1());
    log.info("The lowest reinterpreted location is [ {} ]", problem.solvePart2());

    assertThat(problem.solvePart1()).isEqualTo(1_181_555_926L);
    //assertThat(problem.solvePart2()).isEqualTo(11_787_590L);
  }

}