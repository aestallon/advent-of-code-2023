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

package hu.aestallon.adventofcode.year2023.day19.internal;

public record MachinePart(long x, long m, long a, long s) {

  public static MachinePart parse(String s) {
    final var vals = s.substring(1, s.length() - 1).split(",");
    return new MachinePart(
        extractLong(vals[0]),
        extractLong(vals[1]),
        extractLong(vals[2]),
        extractLong(vals[3]));
  }

  private static long extractLong(final String val) {
    return Long.parseLong(val.substring(val.indexOf('=') + 1));
  }

  public long sum() {
    return x + m + a + s;
  }
}
