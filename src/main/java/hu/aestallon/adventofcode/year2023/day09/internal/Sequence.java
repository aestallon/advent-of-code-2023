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

package hu.aestallon.adventofcode.year2023.day09.internal;

import java.util.Arrays;

public record Sequence(long[] ns) {

  public enum Position {FIRST, LAST}

  public static Sequence parse(String s) {
    return new Sequence(Arrays.stream(s.split("\\s")).mapToLong(Long::parseLong).toArray());
  }

  public long lastValue() {
    return ns[ns.length - 1];
  }

  public long valueAt(Position position) {
    return position == Position.FIRST ? ns[0] : lastValue();
  }

  public boolean isConstant() {
    final long lastValue = lastValue();
    for (int i = 0; i < ns.length - 1; i++) {
      if (ns[i] != lastValue) {
        return false;
      }
    }
    return true;
  }

  public Sequence diff() {
    final long[] diffs = new long[ns.length - 1];
    for (int i = 1; i < ns.length; i++) {
      diffs[i - 1] = ns[i] - ns[i - 1];
    }
    return new Sequence(diffs);
  }

  public Sequence extendBy(long n) {
    final long[] arr = new long[ns.length + 1];
    System.arraycopy(ns, 0, arr, 0, ns.length);
    arr[arr.length - 1] = n;
    return new Sequence(arr);
  }

  public Sequence prependBy(long n) {
    final long[] arr = new long[ns.length + 1];
    System.arraycopy(ns, 0, arr, 1, ns.length);
    arr[0] = n;
    return new Sequence(arr);
  }

  public Sequence extrapolate(long by, Position at) {
    return at == Position.FIRST ? prependBy(by) : extendBy(by);
  }

  @Override
  public String toString() {
    return Arrays.toString(ns);
  }

}
