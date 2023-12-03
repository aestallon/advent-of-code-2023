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

package hu.aestallon.adventofcode.year2023.day02.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public record Game(int id, Set<Sample> samples) {

  private static final Logger log = LoggerFactory.getLogger(Game.class);

  public static void logParseError(String input, String reason) {
    log.error("Failed to parse [ {} ] as [ Game ] - {}!", input, reason);
  }

  public static Game parse(String s) {
    if (s == null || s.isBlank()) {
      logParseError(s, "NULL or empty string");
      throw new IllegalArgumentException("Game string must not be null!");
    }

    final int colonIdx = s.indexOf(':');
    if (colonIdx < 0) {
      logParseError(s, "no header found");
      throw new IllegalArgumentException("Colon missing!");
    }

    final var header = s.substring(0, colonIdx).trim();
    int lastWspInHeader = header.indexOf(' ');
    if (lastWspInHeader < 0) {
      logParseError(s, "header cannot be split to title and ID");
      throw new IllegalArgumentException("header-ID separator missing!");
    }

    final int id = Integer.parseInt(header.substring(lastWspInHeader + 1));

    if (colonIdx == s.length() - 1) {
      logParseError(s, "terminates on colon");
      throw new IllegalArgumentException("No content found!");
    }

    final var content = s.substring(colonIdx + 1);
    final Set<Sample> samples = Arrays.stream(content.split(";"))
        .map(Sample::parse)
        .collect(Collectors.toSet());

    return new Game(id, samples);
  }

  public Game {
    Objects.requireNonNull(samples, "Sample Set must not be null!");
  }

  public boolean isPossible(Sample sample) {
    return samples.stream().noneMatch(s -> s.r > sample.r || s.g > sample.g || s.b > sample.b);
  }

  public Sample lowerBound() {
    return samples().stream().reduce(Sample.empty(), Sample::max);
  }
}
