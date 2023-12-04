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

public final class Digits {

  private Digits() {}

  public static boolean isDigit(final char c) {
    return Character.isDigit(c);
  }

  public static int digit(final int codePoint) {
    return Character.digit(codePoint, 10);
  }

  public static int digitCount(long num) {
    int length = 0;
    long temp = 1;
    while (temp <= num) {
      length++;
      temp *= 10;
    }
    return length;
  }

}
