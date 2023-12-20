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

record Condition(Property p, Operator o, long value) {

  static Condition parse(String s) {
    final char pChar = s.charAt(0);
    final Property p = switch (pChar) {
      case 'x' -> Property.X;
      case 'm' -> Property.M;
      case 'a' -> Property.A;
      case 's' -> Property.S;
      default -> throw new IllegalArgumentException(
          "Prop " + pChar + " in [" + s + "] is not a valid property!");
    };

    final char oChar = s.charAt(1);
    final Operator o = switch (oChar) {
      case '<' -> Operator.LT;
      case '>' -> Operator.GT;
      default -> throw new IllegalArgumentException(
          "Prop " + oChar + " in [" + s + "] is not a valid operator!");
    };

    final long value = Long.parseLong(s.substring(2));

    return new Condition(p, o, value);
  }

  enum Property {X, M, A, S}

  enum Operator {LT, GT}


  boolean test(MachinePart part) {
    final long operand = switch (p) {
      case X -> part.x();
      case M -> part.m();
      case A -> part.a();
      case S -> part.s();
    };
    return switch (o) {
      case LT -> operand < value;
      case GT -> operand > value;
    };
  }

}
