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

package hu.aestallon.adventofcode.year2023.day07.internal;

import java.util.Arrays;

enum Rank {

  _A('A', 0),
  _K('K', 1),
  _Q('Q', 2),
  _J('J', 3),
  _T('T', 4),
  _9('9', 5),
  _8('8', 6),
  _7('7', 7),
  _6('6', 8),
  _5('5', 9),
  _4('4', 10),
  _3('3', 11),
  _2('2', 12),
  JOKER('Y', 13);

  private static Rank parse(final char symbol) {
    return Arrays.stream(Rank.values())
        .filter(it -> it.symbol == symbol)
        .findFirst()
        .orElseThrow();
  }

  static Rank parse(final int codePoint) {
    return parse((char) codePoint);
  }

  static Rank parseWithJoker(final int codePoint) {
    final char c = (char) codePoint;
    return c == 'J'
        ? JOKER
        : parse(c);
  }

  private final char symbol;
  private final int  precedence;

  Rank(final char symbol, final int precedence) {
    this.symbol = symbol;
    this.precedence = precedence;
  }

  char symbol()    {return symbol;}

  int precedence() {return precedence;}

}
