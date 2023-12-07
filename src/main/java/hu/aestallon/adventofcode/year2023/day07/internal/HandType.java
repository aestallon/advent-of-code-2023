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

enum HandType {

  FIVE_OF_A_KIND(0),
  FOUR_OF_A_KIND(1),
  FULL_HOUSE(2),
  THREE_OF_A_KIND(3),
  TWO_PAIR(4),
  ONE_PAIR(5),
  HIGH_CARD(6);

  private final int precedence;

  HandType(int precedence) {
    this.precedence = precedence;
  }

  int precedence() { return precedence; }

}
