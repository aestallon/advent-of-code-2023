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

package hu.aestallon.adventofcode.year2023.day09;

import hu.aestallon.adventofcode.year2023.day09.internal.Sequence;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

final class MirageMaintenance {

  private final List<Sequence> sequences;

  MirageMaintenance(List<String> lines) {
    this.sequences = lines.stream().map(Sequence::parse).toList();
  }

  long solvePart1() {
    return sequences.stream()
        .map(it -> predict(it, Sequence.Position.LAST))
        .mapToLong(Sequence::lastValue)
        .sum();
  }

  long solvePart2() {
    return sequences.stream()
        .map(it -> predict(it, Sequence.Position.FIRST))
        .mapToLong(it -> it.valueAt(Sequence.Position.FIRST))
        .sum();
  }

  private Sequence predict(Sequence sequence, Sequence.Position position) {
    if (sequence.isConstant()) {
      return sequence.extrapolate(sequence.valueAt(position), position);
    }

    final Deque<Sequence> stack = new ArrayDeque<>();

    Sequence currSeq = sequence;
    while (!currSeq.isConstant()) {
      stack.push(currSeq);
      currSeq = currSeq.diff();
    }
    currSeq = currSeq.extrapolate(currSeq.valueAt(position), position);

    while (!stack.isEmpty()) {
      Sequence s = stack.pop();
      currSeq = s.extrapolate(
          s.valueAt(position)
          + currSeq.valueAt(position) * ((position == Sequence.Position.FIRST) ? -1 : 1),
          position);
    }

    return currSeq;
  }

}
