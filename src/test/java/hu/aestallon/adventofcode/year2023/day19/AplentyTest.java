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

package hu.aestallon.adventofcode.year2023.day19;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static hu.aestallon.adventofcode.year2023.util.AocIO.lines;
import static org.assertj.core.api.Assertions.assertThat;

class AplentyTest {

  private static final Logger log = LoggerFactory.getLogger(AplentyTest.class);
  
  @Test
  void example() {
    final var input = """
        px{a<2006:qkq,m>2090:A,rfg}
        pv{a>1716:R,A}
        lnx{m>1548:A,A}
        rfg{s<537:gd,x>2440:R,A}
        qs{s>3448:A,lnx}
        qkq{x<1416:A,crn}
        crn{x>2662:A,R}
        in{s<1351:px,qqz}
        qqz{s>2770:qs,m<1801:hdj,R}
        gd{a>3333:R,R}
        hdj{m>838:A,pv}
                
        {x=787,m=2655,a=1222,s=2876}
        {x=1679,m=44,a=2067,s=496}
        {x=2036,m=264,a=79,s=2244}
        {x=2461,m=1339,a=466,s=291}
        {x=2127,m=1623,a=2188,s=1013}""".lines().toList();
    final var problem = new Aplenty(input);
    assertThat(problem.solvePart1()).isEqualTo(19_114L);
    assertThat(problem.solvePart2()).isEqualTo(167_409_079_868_000L);
  }

  @Test
  void problem() {
    final var input = lines("19", "input19.txt");
    final var problem = new Aplenty(input);

    final long solution1 = problem.solvePart1();
    log.info("The sum of ccepted items is [ {} ]", solution1);
  }

}
