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

package hu.aestallon.adventofcode.year2023.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class AocIO {

  private static final Logger log = LoggerFactory.getLogger(AocIO.class);

  public static List<String> lines(String... path) {
    final var loc = Arrays.stream(path).collect(Collectors.joining("/", "/", ""));

    log.debug("Loading classpath resource from [ {} ]", loc);
    try (var in = AocIO.class.getResourceAsStream(loc);
         var reader = new InputStreamReader(in);
         var br = new BufferedReader(reader);
         var lines = br.lines()) {
      return lines.toList();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      return Collections.emptyList();
    }
  }
}
