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

package hu.aestallon.adventofcode.year2023.day04.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Scratchcard {

  private static final Logger log = LoggerFactory.getLogger(Scratchcard.class);

  public static void logParseError(String input, String reason) {
    log.error("Failed to parse [ {} ] as [ Scratchcard ] - {}!", input, reason);
  }

  public static Scratchcard parse(String s) {
    if (s == null || s.isBlank()) {
      logParseError(s, "Input is null or blank");
      throw new IllegalArgumentException("s must not be null or empty!");
    }

    final int headerDelimiter = s.indexOf(':');
    if (headerDelimiter < 0) {
      logParseError(s, "Header missing");
    }

    final String[] headerParts = s.substring(0, headerDelimiter).trim().split("\\s");
    final int id = Integer.parseInt(headerParts[headerParts.length - 1]);

    final var bodyStr = s.substring(headerDelimiter + 1);
    final String[] bodyParts = bodyStr.split("\\|");
    if (bodyParts.length != 2) {
      logParseError(s, "Body part count is not 2");
    }

    final var winningNumbers = processBodyPart(bodyParts[0]);
    final var playingNumbers = processBodyPart(bodyParts[1]);
    return new Scratchcard(id, winningNumbers, playingNumbers);
  }

  private static Set<Integer> processBodyPart(String s) {
    return Arrays.stream(s.trim().split("\\s"))
        .filter(it -> !it.isBlank())
        .map(Integer::parseInt)
        .collect(Collectors.toSet());
  }

  private final int          id;
  private final Set<Integer> winningNumbers;
  private final Set<Integer> playingNumbers;
  private final int          matches;

  private Scratchcard(int id, Set<Integer> winningNumbers, Set<Integer> playingNumbers) {
    this.id = id;
    this.winningNumbers = winningNumbers;
    this.playingNumbers = playingNumbers;
    this.matches = (int) playingNumbers.stream()
        .filter(winningNumbers::contains)
        .count();
  }

  public int id() {
    return id;
  }

  public int matches() {
    return matches;
  }

  public long value() {
    return 1L << (matches - 1);
  }

}
