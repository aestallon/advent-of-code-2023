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

package hu.aestallon.adventofcode.year2023.day15.internal;

import java.util.Objects;

public final class Instruction {

  enum Operation {
    ADD, REMOVE
  }

  public static Instruction parse(String s) {
    final Operation op;
    final int focalLength;
    final String label;

    final int assignmentIdx = s.indexOf('=');
    final int removalIdx = s.indexOf('-');
    if (assignmentIdx > 0) {
      op = Operation.ADD;
      focalLength = Integer.parseInt(s.substring(assignmentIdx + 1));
      label = s.substring(0, assignmentIdx);
    } else if (removalIdx > 0) {
      op = Operation.REMOVE;
      focalLength = 0;
      label = s.substring(0, removalIdx);
    } else {
      throw new IllegalArgumentException(s + " does not contain any of '=' or '-'!");
    }
    return new Instruction(s, label, op, focalLength);
  }

  private final String    literal;
  private final String    label;
  private final Operation op;
  private final int       focalLength;

  private Instruction(String literal, String label, Operation op, int focalLength) {
    this.literal = literal;
    this.label = label;
    this.op = op;
    this.focalLength = focalLength;
  }


  public String literal() {
    return literal;
  }

  public String label() {
    return label;
  }

  Operation op() {
    return op;
  }

  public int focalLength() {
    return focalLength;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {return true;}
    if (o == null || getClass() != o.getClass()) {return false;}
    Instruction that = (Instruction) o;
    return Objects.equals(literal, that.literal);
  }

  @Override
  public int hashCode() {
    return HolidayHash.hash(literal);
  }

}
