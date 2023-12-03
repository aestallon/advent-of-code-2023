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

import java.util.Arrays;
import java.util.Objects;

public final class Sample {

  private enum Colour {
    RED("red"),
    GREEN("green"),
    BLUE("blue");

    private static Colour parse(String s) {
      Objects.requireNonNull(s, "s must not be null!");
      return Arrays.stream(Colour.values())
          .filter(c -> s.equalsIgnoreCase(c.strVal))
          .findFirst()
          .orElseThrow();
    }

    private final String strVal;

    Colour(String strVal) {
      this.strVal = strVal;
    }
  }

  private record ColourQuantity(Colour colour, int quantity) {}

  public static Sample parse(String s) {
    return Arrays.stream(s.split(","))
        .map(String::trim)
        .map(x -> x.split("\\s"))
        .map(arr -> {
          if (arr.length != 2) {
            throw new IllegalArgumentException("Illegal format " + Arrays.toString(arr));
          }

          final int quantity = Integer.parseInt(arr[0]);
          final Colour colour = Colour.parse(arr[1]);
          return new ColourQuantity(colour, quantity);
        })
        .reduce(
            empty(),
            (sm, cq) -> {
              switch (cq.colour) {
                case RED -> sm.r += cq.quantity;
                case GREEN -> sm.g += cq.quantity;
                case BLUE -> sm.b += cq.quantity;
              }
              return sm;
            },
            (sm1, sm2) -> new Sample(sm1.r + sm2.r, sm1.g + sm2.g, sm1.b + sm2.b));
  }

  public static Sample max(Sample s1, Sample s2) {
    return new Sample(Math.max(s1.r, s2.r), Math.max(s1.g, s2.g), Math.max(s1.b, s2.b));
  }

  public static Sample empty() {
    return new Sample(0, 0, 0);
  }

  public static Sample of(int r, int g, int b) {
    if (r < 0 || g < 0 || b < 0) {
      throw new IllegalArgumentException();
    }

    return new Sample(r, g, b);
  }

  int r;
  int g;
  int b;

  private Sample(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  /** Returns the "power" of the sample set: the product of the colours. */
  public long power() {
    return ((long) r) * g * b;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {return true;}
    if (obj == null || obj.getClass() != this.getClass()) {return false;}
    var that = (Sample) obj;
    return this.r == that.r &&
           this.g == that.g &&
           this.b == that.b;
  }

  @Override
  public int hashCode() {
    return Objects.hash(r, g, b);
  }

  @Override
  public String toString() {
    return "Sample[ " +
           "r=" + r + ", " +
           "g=" + g + ", " +
           "b=" + b + " ]";
  }

}
