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

package hu.aestallon.adventofcode.year2023.day19;

import hu.aestallon.adventofcode.year2023.day19.internal.Bound;
import hu.aestallon.adventofcode.year2023.day19.internal.MachinePart;
import hu.aestallon.adventofcode.year2023.day19.internal.Workflow;
import hu.aestallon.adventofcode.year2023.day19.internal.WorkflowInstruction;
import hu.aestallon.adventofcode.year2023.day19.internal.WorkflowLabel;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

final class Aplenty {

  private final Map<WorkflowLabel, Workflow> workflowByName;
  private final List<MachinePart>            parts;

  Aplenty(List<String> lines) {
    final int emptyLineIdx = lines.indexOf("");
    workflowByName = lines.subList(0, emptyLineIdx).stream()
        .map(Workflow::parse)
        .collect(toMap(Workflow::label, it -> it));
    parts = lines.subList(emptyLineIdx + 1, lines.size()).stream()
        .map(MachinePart::parse)
        .toList();
  }

  long solvePart1() {
    final Set<MachinePart> accepted = new HashSet<>();
    for (var p : parts) {
      WorkflowLabel label = WorkflowLabel.IN;
      while (!WorkflowLabel.FINAL_LABELS.contains(label)) {
        label = workflowByName.get(label).apply(p);
      }

      if (WorkflowLabel.ACCEPTED.equals(label)) {
        accepted.add(p);
      }
    }

    return accepted.stream().mapToLong(MachinePart::sum).sum();
  }

  long solvePart2() {
    final Bound.Interval limit = new Bound.Interval(0, 4001);
    final Bound initialBound = new Bound(limit, limit, limit, limit);

    Set<WorkflowInstruction> instructions = workflowByName
        .get(WorkflowLabel.IN)
        .process(initialBound);
    Set<Bound> acceptedBounds = new HashSet<>();
    while (!instructions.isEmpty()) {
      for (var i : instructions) {
        if (WorkflowLabel.ACCEPTED.equals(i.label())) {
          acceptedBounds.add(i.bound());
        }
      }
      instructions = instructions.stream()
          .filter(it -> !WorkflowLabel.FINAL_LABELS.contains(it.label()))
          .flatMap(it -> workflowByName.get(it.label()).process(it.bound()).stream())
          .collect(Collectors.toSet());
    }

    return sizeOfDistinctUnionOf(acceptedBounds);
  }

  private static long sizeOfDistinctUnionOf(Collection<Bound> bounds) {
    return -1L;
  }

}
