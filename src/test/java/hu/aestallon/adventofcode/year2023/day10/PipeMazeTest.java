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

package hu.aestallon.adventofcode.year2023.day10;

import hu.aestallon.adventofcode.year2023.util.AocIO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

class PipeMazeTest {

  private static final Logger log = LoggerFactory.getLogger(PipeMazeTest.class);
  
  @Test
  void exampleInputShouldYieldExampleResult_1() {
    final var input = """
        -L|F7
        7S-7|
        L|7||
        -L-J|
        L|-JF""".lines().toList();
    final var problem = PipeMaze.parse(input);
    assertThat(problem.solvePart1()).isEqualTo(4L);
  }

  @Test
  void exampleInputShouldYieldExampleResult_2() {
    final var input = """
        7-F7-
        .FJ|7
        SJLL7
        |F--J
        LJ.LJ""".lines().toList();
    final var problem = PipeMaze.parse(input);
    assertThat(problem.solvePart1()).isEqualTo(8L);
  }

  @Test
  void examplePart2() {
    final var input = """
        FF7FSF7F7F7F7F7F---7
        L|LJ||||||||||||F--J
        FL-7LJLJ||||||LJL-77
        F--JF--7||LJLJ7F7FJ-
        L---JF-JLJ.||-FJLJJ7
        |F|F-JF---7F7-L7L|7|
        |FFJF7L7F-JF7|JL---7
        7-L-JL7||F7|L7F-7F7|
        L.L7LFJ|||||FJL7||LJ
        L7JLJL-JLJLJL--JLJ.L""".lines().toList();
    final var problem = PipeMaze.parse(input);
    assertThat(problem.solvePart2()).isEqualTo(10L);
  }

  @Test
  void problemInputShouldYieldProblemResult() {
    final var input = AocIO.lines("10", "input10.txt");
    final var problem = PipeMaze.parse(input);

    final long solution1 = problem.solvePart1();
    log.info("The farthest point in the pipe-loop is [ {} ]", solution1);
    assertThat(solution1).isEqualTo(6_815L);

    final long solution2 = problem.solvePart2();
    log.info("The count of points inside is [ {} ]", solution2);
    assertThat(solution2).isEqualTo(269L);
  }

}